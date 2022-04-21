package cn.reghao.im.service;

import cn.reghao.im.db.mapper.FileInfoMapper;
import cn.reghao.im.model.constant.FileMsgType;
import cn.reghao.im.model.dto.PartUploadInitiate;
import cn.reghao.im.model.po.FileInfo;
import cn.reghao.im.model.vo.InitiateVo;
import cn.reghao.im.util.ImageOps;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author reghao
 * @date 2022-04-20 14:13:36
 */
@Service
public class FileService {
    private String tmpDir = "/var/tmp/im";
    private String baseDir = "/home/reghao/opt/file/group0";
    private FileInfoMapper fileInfoMapper;
    private final Map<String, String> map = new HashMap<>();

    public FileService(FileInfoMapper fileInfoMapper) {
        this.fileInfoMapper = fileInfoMapper;
    }

    public InitiateVo initFilePart(PartUploadInitiate partUploadInitiate) throws IOException {
        String originalName = partUploadInitiate.getFileName();
        long size = partUploadInitiate.getFileSize();
        String uploadId = UUID.randomUUID().toString().replace("-", "");
        String destDirPath = String.format("%s/%s", tmpDir, uploadId);
        File destDir = new File(destDirPath);
        if (!destDir.exists()) {
            FileUtils.forceMkdirParent(destDir);
        }
        map.put(uploadId, originalName);

        // 分片大小
        long splitSize = 1024*1024;
        return new InitiateVo(uploadId, splitSize);
    }

    public boolean savePartFile(String uploadId, int partIndex, int partNum, MultipartFile multipartFile)
            throws IOException {
        String destDirPath = String.format("%s/%s", tmpDir, uploadId);
        String filePath = String.format("%s/%s", destDirPath, partIndex);
        saveFile(filePath, multipartFile.getBytes());

        File dir = new File(destDirPath);
        int total = dir.list().length;
        if (total == partNum) {
            mergePartFile(uploadId);
            return true;
        }
        return false;
    }

    public void mergePartFile(String uploadId) throws IOException {
        String originalName = map.get(uploadId);
        String suffix = originalName.substring(originalName.lastIndexOf(".") + 1);
        String path = String.format("im/%s.%s", uploadId, suffix);
        String destFilePath = String.format("%s/%s", baseDir, path);
        File destFile = new File(destFilePath);
        boolean isCreated = destFile.createNewFile();
        if (!isCreated) {
            throw new IOException("文件创建失败");
        }

        String partFileDirPath = String.format("%s/%s", tmpDir, uploadId);
        File partFileDir = new File(partFileDirPath);
        List<File> list = Arrays.stream(Objects.requireNonNull(partFileDir.listFiles()))
                .sorted(Comparator.comparingInt(file -> Integer.parseInt(file.getName())))
                .collect(Collectors.toList());

        RandomAccessFile rafWrite = new RandomAccessFile(destFile, "rw");
        rafWrite.seek(0);
        byte[] buf = new byte[1024];
        for (File file : list) {
            RandomAccessFile rafRead = new RandomAccessFile(file, "rw");
            int len;
            while ((len = rafRead.read(buf)) != -1) {
                rafWrite.write(buf, 0, len);
            }
            rafRead.close();
        }
        rafWrite.close();

        String url = String.format("http://localhost:8003/group0/node0/%s", path);
        // TODO 根据 content-type 设置文件类型
        FileInfo fileInfo = new FileInfo(uploadId, originalName, suffix, 0, FileMsgType.attachment.getCode(), url);
        fileInfoMapper.save(fileInfo);
    }

    public FileInfo saveFile(MultipartFile multipartFile, long recordId, int fileType) throws IOException {
        String originalName = multipartFile.getOriginalFilename();
        String suffix = originalName.substring(originalName.lastIndexOf(".") + 1);
        long size = multipartFile.getSize();

        InputStream in = multipartFile.getInputStream();
        byte[] bytes = in.readAllBytes();
        String fileId = UUID.randomUUID().toString().replace("-", "");
        ImageOps.Size size1 = ImageOps.info(new ByteArrayInputStream(bytes));
        String path = String.format("im/%s_%sx%s.%s", fileId, size1.getWidth(), size1.getHeight(), suffix);
        String filePath = String.format("%s/%s", baseDir, path);
        saveFile(filePath, bytes);

        String url = String.format("http://localhost:8003/group0/node0/%s", path);
        FileInfo fileInfo = new FileInfo(fileId, originalName, suffix, size, fileType, url);
        fileInfoMapper.save(fileInfo);
        return fileInfo;
    }

    private void saveFile(String path, byte[] bytes) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            return;
        }

        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            FileUtils.forceMkdir(parentDir);
        }

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        fos.close();
    }
}
