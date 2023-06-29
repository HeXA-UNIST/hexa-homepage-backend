package pro.hexa.backend.main.api.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpStatus;
import pro.hexa.backend.main.api.common.exception.AuthorizationExceptionResponse;
import pro.hexa.backend.main.api.common.exception.AuthorizationExceptionType;

@Slf4j
@UtilityClass
public final class FilterUtils {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public static void response(HttpServletResponse response, HttpStatus status, Object object) throws IOException {
        // 이 메서드는 response를 하기전에 그 값을 설정하는데 그 의의가 있다. 암호화 및 HttpStatus를 설정한다.
        // 인증 성공 시 OK가 이 메서드로 전달될 것이다.
        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(status.value());
        if (object != null) {
            // 입력받은 loginTokenDto를 객체 형식으로 받고, String으로 바꿔준 후 response에 기록한다.
            response.getWriter().write(objectMapper.writeValueAsString(object)); // String형태로 Serialize함.
        }
    }

    public static void responseError(HttpServletResponse response, HttpStatus status, AuthorizationExceptionType exceptionType,
        Exception e) throws IOException {
        log.error(e.getMessage(), e);
        AuthorizationExceptionResponse authorizationExceptionResponse = new AuthorizationExceptionResponse(exceptionType);
        response(response, status, authorizationExceptionResponse);
    }
}
