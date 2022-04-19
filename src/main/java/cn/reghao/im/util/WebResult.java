package cn.reghao.im.util;

import cn.reghao.jutil.jdk.result.Result;
import cn.reghao.jutil.jdk.result.ResultStatus;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author reghao
 * @date 2022-04-16 20:38:19
 */
public class WebResult {
    private final int code;
    private final String message;
    private final long timestamp;
    private Object data;
    private static final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    private WebResult(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    private void setData(Object data) {
        this.data = data;
    }

    public static String success() {
        WebResult webBody = new WebResult(200, ResultStatus.SUCCESS.getMsg());
        return gson.toJson(webBody);
    }

    public static String successWithMsg(String message) {
        WebResult webBody = new WebResult(200, message);
        return gson.toJson(webBody);
    }

    public static String result(Result result) {
        WebResult webBody = new WebResult(result.getCode(), result.getMsg());
        return gson.toJson(webBody);
    }

    public static String success(Object data) {
        WebResult webBody = new WebResult(200, ResultStatus.SUCCESS.getMsg());
        webBody.setData(data);
        return gson.toJson(webBody);
    }

    public static String fail(Object data) {
        WebResult webBody = new WebResult(400, ResultStatus.FAIL.getMsg());
        webBody.setData(data);
        return gson.toJson(webBody);
    }

    public static String failWithMsg(String message) {
        WebResult webBody = new WebResult(400, message);
        return gson.toJson(webBody);
    }

    public static String error(Object data) {
        WebResult webBody = new WebResult(ResultStatus.ERROR.getCode(), ResultStatus.ERROR.getMsg());
        webBody.setData(data);
        return gson.toJson(webBody);
    }

    public static String errorMsg(String message) {
        WebResult webBody = new WebResult(ResultStatus.ERROR.getCode(), message);
        return gson.toJson(webBody);
    }
}
