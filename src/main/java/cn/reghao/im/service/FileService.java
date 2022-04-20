package cn.reghao.im.service;

import cn.reghao.im.db.mapper.FileMessageMapper;
import cn.reghao.im.model.po.FileMessage;
import cn.reghao.im.util.ImageOps;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author reghao
 * @date 2022-04-20 14:13:36
 */
@Service
public class FileService {
    private String baseDir = "/home/reghao/opt/file/group0";
    private final FileMessageMapper fileMessageMapper;

    public FileService(FileMessageMapper fileMessageMapper) {
        this.fileMessageMapper = fileMessageMapper;
    }

    public FileMessage saveFile(MultipartFile multipartFile, long recordId, int fileType) throws IOException {
        String originalName = multipartFile.getOriginalFilename();
        String suffix = originalName.substring(originalName.lastIndexOf(".") + 1);
        long size = multipartFile.getSize();

        InputStream in = multipartFile.getInputStream();
        byte[] bytes = in.readAllBytes();
        String sha256sum = DigestUtils.sha256Hex(bytes);
        ImageOps.Size size1 = ImageOps.info(new ByteArrayInputStream(bytes));
        String path = String.format("im/%s_%sx%s.%s", sha256sum, size1.getWidth(), size1.getHeight(), suffix);
        String filePath = String.format("%s/%s", baseDir, path);
        saveFile(filePath, bytes);

        String url = String.format("http://localhost:8003/group0/node0/%s", path);
        FileMessage fileMessage = new FileMessage(recordId, originalName, suffix, size, fileType, url);
        fileMessageMapper.save(fileMessage);
        return fileMessage;
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
