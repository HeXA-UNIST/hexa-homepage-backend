package pro.hexa.backend.main.api.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonExceptionResponse extends ExceptionResponse{
    private final String message; // final 변수는 생성자에서 초기화하고 불변임.

    public CommonExceptionResponse(HttpStatus status, String message){
        super(status);
        this.message=message;
    }
}
