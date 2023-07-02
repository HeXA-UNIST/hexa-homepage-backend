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
import pro.hexa.backend.main.api.domain.login.dto.UserFindIdRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.UserFindPasswordRequestDto;
import pro.hexa.backend.main.api.domain.login.service.UserService;


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

    @Operation(description = "아이디 찾기")
    @PostMapping("/find_id")
    public ResponseEntity<String> findUserId(@RequestBody UserFindIdRequestDto request) {
        String userId = userService.findUserId(request);
        return ResponseEntity.ok(userId);
    }

    @Operation(description = "비밀번호 찾기")
    @PostMapping("/find_password")
    public ResponseEntity<String> findUserPassword(@RequestBody UserFindPasswordRequestDto request) {
        String newPassword = userService.findUserPassword(request);
        return ResponseEntity.ok(newPassword);
    }
}
