package com.aimspeed.common.http;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie操作工具类
 * @author AimSpeed
 */
public abstract class HttpCookieUtils {

	/**
	 * 添加cookie
	 * @author AimSpeed
	 * @param name 对应的key
     * @param value 对应的value
     * @param seconds 存活时间(秒)
	 * @param response 响应类对象
	 * @return HttpServletResponse 返回添加的响应类
	 */
    public static HttpServletResponse addCookie(String name, String value, int seconds,
                                                HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(seconds);
        response.addCookie(cookie);
        return response;
    }

    /**
     * 添加Cookie
     * @author AimSpeed
     * @param name 对应的key
     * @param value 对应的value
     * @param response 响应类对象
     * @return HttpServletResponse  返回添加的响应类
     */
    public static HttpServletResponse addCookie(String name, String value, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        response.addCookie(cookie);
        return response;
    }

    /**
     * 获取到Cookie对象
     * @author AimSpeed
     * @param request 请求类对象
     * @param name key
     * @return Cookie 查询到的Cookie对象
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

    /**
     * 获取到对应的cookie的值
     * @author AimSpeed
     * @param request 请求包
     * @param name key
     * @return String 获取到的Value
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        return null != cookie ? cookie.getValue() : null;
    }

    /**
     * 删除Cookie
     * @author AimSpeed
     * @param request 请求包类对象
     * @param name key
     * @param response 响应包类对象
     */
    public static void removeCookie(HttpServletRequest request, String name, HttpServletResponse response) {
        String key = getCookieValue(request, name);
        if (null != key) {
            addCookie(name, null, 0, response);
        }
    }


}
