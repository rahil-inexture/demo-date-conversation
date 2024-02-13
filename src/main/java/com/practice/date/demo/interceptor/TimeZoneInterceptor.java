package com.practice.date.demo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.TimeZone;

public class TimeZoneInterceptor implements HandlerInterceptor {
    private static final ThreadLocal<TimeZone> CLIENT_TIME_ZONE_THREAD_LOCAL = new ThreadLocal<>();
    private static final ThreadLocal<TimeZone> SERVER_TIME_ZONE_THREAD_LOCAL = new ThreadLocal<>();
    private final static Logger logger = LoggerFactory.getLogger(TimeZoneInterceptor.class);
    @Value("${time-zone-interceptor.header-name:User-TimeZone}")
    private String clientTimeZoneHeaderName;
    @Value("${application.default.server.timezone}")
    private String serverTimeZone;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("TimeZoneInterceptor:: Inside preHandle");
        SERVER_TIME_ZONE_THREAD_LOCAL.set(TimeZone.getTimeZone(serverTimeZone));

        // Client Time Zone
        String clientTimeZoneHeader = request.getHeader(clientTimeZoneHeaderName);
        if (clientTimeZoneHeader != null) {
            logger.info("TimeZoneInterceptor:: found header {}", clientTimeZoneHeader);
            TimeZone timeZone = TimeZone.getTimeZone(clientTimeZoneHeader);
            CLIENT_TIME_ZONE_THREAD_LOCAL.set(timeZone);
        } else {
            logger.info("TimeZoneInterceptor:: header not found, setting default time zone");
            CLIENT_TIME_ZONE_THREAD_LOCAL.set(TimeZone.getTimeZone(serverTimeZone));
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("TimeZoneInterceptor:: Inside afterCompletion");
        CLIENT_TIME_ZONE_THREAD_LOCAL.remove();
        SERVER_TIME_ZONE_THREAD_LOCAL.remove();

        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    public static TimeZone getClientTimeZone() {
        return CLIENT_TIME_ZONE_THREAD_LOCAL.get();
    }

    public static TimeZone getServerTimeZone() {
        return SERVER_TIME_ZONE_THREAD_LOCAL.get();
    }
}