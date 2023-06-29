package pro.hexa.backend.main.api.common.config.security;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import pro.hexa.backend.main.api.common.exception.CommonExceptionResponse;
import pro.hexa.backend.main.api.common.utils.FilterUtils;

public class CustomAccessDeniedHandler implements
    AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
        throws IOException {
        // handle 메서드를 사용하면서 발생할 예외처리를 하는 구문.
        // 쉽게 말하자면 Exception 을 하나로만 처리하는 게 아니라, exception의
        // 종류를 세분화 시켜서 디버깅에 있어 이점을 가져가기 위함이다.
        // 협업에서도 소통의 이점을 가져갈 수 있다.
        // 이를 통해 메서드에서는 그냥 throws "발생할 예외"{} 구문을 간단히 표현하고,
        // main 쪽에서 try catch finally 구문을 사용하게 끔 돌릴 수 있음.
        CommonExceptionResponse commonExceptionResponse = new CommonExceptionResponse(HttpStatus.FORBIDDEN, accessDeniedException.getMessage());
        FilterUtils.response(response, HttpStatus.FORBIDDEN, commonExceptionResponse);
    }
}
