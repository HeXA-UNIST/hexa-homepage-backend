package pro.hexa.backend.main.api.common.config.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CustomCookieFilter extends OncePerRequestFilter {

    @Value("${my.cookie.key}")
    private String key;

    @Value("${my.cookie.value}")
    private String value;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        // 쿠키 검증 로직

        // /admin 하위 경로에 대한 요청인지 확인하고, 쿠키가 유효한지 검사
        if (request.getRequestURI().startsWith("/admin")) {
            Cookie[] cookies = request.getCookies();
            boolean isAdminCookiePresent = false;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (key.equals(cookie.getName()) && value.equals(cookie.getValue())) {
                        isAdminCookiePresent = true;
                        break;
                    }
                }
            }
            if (!isAdminCookiePresent) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }
        }

        // 다음 필터로 이동
        filterChain.doFilter(request, response);
    }
}

