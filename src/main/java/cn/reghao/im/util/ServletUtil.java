package cn.reghao.im.util;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author reghao
 * @date 2021-06-02 13:16:58
 */
public class ServletUtil {
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static String getSessionId() {
        return getRequest().getSession().getId();
    }

    /**
     * 获取 query 参数值
     *
     * @param
     * @return
     * @date 2021-06-02 下午1:19
     */
    public static String getRequestParam(String param, String defaultValue){
        String parameter = getRequest().getParameter(param);
        return StringUtils.isEmpty(parameter) ? defaultValue : parameter;
    }

    public static HttpServletRequest getRequest(){
        return getServletRequest().getRequest();
    }

    public static HttpServletResponse getResponse(){
        return getServletRequest().getResponse();
    }

    private static ServletRequestAttributes getServletRequest(){
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }
}
