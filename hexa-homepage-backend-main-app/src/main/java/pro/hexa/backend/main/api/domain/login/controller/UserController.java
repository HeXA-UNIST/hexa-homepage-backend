package pro.hexa.backend.main.api.domain.login.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.hexa.backend.main.api.domain.login.dto.UserCreateRequestDto;
import pro.hexa.backend.main.api.domain.login.service.UserService;
import pro.hexa.backend.main.api.domain.login.dto.UserLoginRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.LoginResponse;



@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(description = "회원가입")
    @PostMapping("/create")
    public ResponseEntity<String> userSignup(@RequestBody UserCreateRequestDto request) {
        return new ResponseEntity<>(userService.userSignup(request), HttpStatus.OK);
    }

    @Operation(description = "로그인")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> userLogin(@RequestBody UserLoginRequestDto request) {
        boolean loginSuccessful = userService.userLogin(request);

        if (loginSuccessful) {
                // 로그인 성공
            LoginResponse loginResponse = LoginResponse.builder()
                    .id("사용자ID")
                    .accessToken("액세스토큰")
                    .refreshToken("리프레시토큰")
                    .build();
            return ResponseEntity.ok(loginResponse);
        } else {
                // 로그인 실패
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
