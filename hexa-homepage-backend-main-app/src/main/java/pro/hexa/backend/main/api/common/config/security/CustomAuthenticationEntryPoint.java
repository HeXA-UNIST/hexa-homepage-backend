package pro.hexa.backend.main.api.common.config.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import pro.hexa.backend.main.api.common.exception.CommonExceptionResponse;
import pro.hexa.backend.main.api.common.utils.FilterUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements
        AuthenticationEntryPoint {
    //EntryPoint란 예외 발생시 다시 돌아가기위한 원점임.
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        CommonExceptionResponse commonExceptionResponse = new CommonExceptionResponse(HttpStatus.UNAUTHORIZED, authException.getMessage());
        FilterUtils.response(response, HttpStatus.UNAUTHORIZED, commonExceptionResponse);
    }
}
