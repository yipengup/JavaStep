package com.example.springsessiondemo.filter;

import com.example.springsessiondemo.common.CommonValues;
import com.example.springsessiondemo.common.ErrorCode;
import com.example.springsessiondemo.exceptions.BusinessException;
import com.example.springsessiondemo.response.BaseResponse;
import com.example.springsessiondemo.util.JsonUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.session.web.http.SessionRepositoryFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * session 过滤器
 *
 * @author yipengup
 * @date 2021/9/24
 */
@Component
@Order(RedisHttpSessionFilter.ORDER)
public class RedisHttpSessionFilter extends OncePerRequestFilter {

    /**
     * 设置默认的过滤器执行顺序， 这里需要在SessionRepositoryFilter之后执行
     */
    public static final int ORDER = SessionRepositoryFilter.DEFAULT_ORDER + 1;
    public static final String SESSION_KEY = "user";

    /**
     * 不需要过滤的路径
     */
    private final List<String> excludeCheckPath = new ArrayList<>();

    /**
     * 是否开启refer校验, 默认为false不校验
     */
    private final Boolean checkReferFlag;

    /**
     * 配置的域名来源， 比如 http://127.0.0.1
     */
    private final String[] referDomains;

    public RedisHttpSessionFilter(
            @Value("${backend.checkReferFlag:false}") Boolean checkReferFlag,
            @Value("${backend.referDomains:''}") String referDomains,
            @Value("${backend.excludeCheckPath:''}") String excludeCheckPath) {
        this.referDomains = parseDomains(referDomains);
        this.checkReferFlag = checkReferFlag;
        if (StringUtils.isNotEmpty(excludeCheckPath)) {
            this.excludeCheckPath.addAll(Arrays.asList(excludeCheckPath.split(CommonValues.COMMA)));
        }
    }


    @Override

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // refer校验
            checkRefer(request);

            // 判断是否校验session
            if (CollectionUtils.isNotEmpty(excludeCheckPath)) {
                AntPathMatcher matcher = new AntPathMatcher();
                String uri = request.getRequestURI();
                boolean excludeSession = excludeCheckPath.stream().anyMatch(p -> matcher.match(p, uri));
                if (excludeSession) {
                    filterChain.doFilter(request, response);
                }
            }

            // 校验session
            HttpSession session = request.getSession();
            if (Objects.isNull(session) || Objects.isNull(session.getAttribute(SESSION_KEY))) {
                throw new BusinessException(ErrorCode.BACKEND_SESSION_CHECK_ERROR);
            }

            filterChain.doFilter(request, response);
        } catch (BusinessException e) {
            // 捕获BusinessException后
            response.setCharacterEncoding("utf-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            try (PrintWriter writer = response.getWriter()) {
                BaseResponse<Object> baseResponse = new BaseResponse<>();
                baseResponse.setStatus(false);
                baseResponse.setErrorCode(e.getErrorCode().getCode());
                baseResponse.setErrorDesc(e.getErrorCode().getDesc());
                writer.print(JsonUtils.convertToString(baseResponse));
            }
        }
    }

    /**
     * 解析配置的域名
     *
     * @param str 域名列表字符串
     * @return 可以请求的域名列表
     */
    private String[] parseDomains(String str) {
        if (StringUtils.isEmpty(str)) {
            return new String[0];
        }

        String[] urls = str.trim().split(",");
        for (int i = 0; i < urls.length; i++) {
            String url = urls[i].trim();
            if (url.length() <= 0) {
                continue;
            }

            urls[i] = url.endsWith("/") ? url : url + "/";
        }
        return urls;
    }

    /**
     * 防CSRF攻击，检查refer有效性
     * 需来源本站域名
     */
    private void checkRefer(HttpServletRequest request) {
        if (!checkReferFlag || referDomains.length <= 0) {
            return;
        }

        String refer = request.getHeader(HttpHeaders.REFERER);
        if (StringUtils.isEmpty(refer)) {
            throw new BusinessException(ErrorCode.REQUEST_REFER_ERROR);
        }

        boolean validRefer = StringUtils.startsWithAny(refer, referDomains);
        if (!validRefer) {
            throw new BusinessException(ErrorCode.BACKEND_SESSION_CHECK_ERROR);
        }
    }
}
