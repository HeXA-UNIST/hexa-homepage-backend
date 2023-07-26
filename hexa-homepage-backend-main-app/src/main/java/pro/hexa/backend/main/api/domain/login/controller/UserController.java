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
import pro.hexa.backend.main.api.domain.login.dto.UserFindPasswordRequestDto1;
import pro.hexa.backend.main.api.domain.login.dto.UserFindPasswordRequestDto2;
import pro.hexa.backend.main.api.domain.login.dto.UserFindPasswordRequestDto3;
import pro.hexa.backend.main.api.domain.login.dto.UserFindVerificationRequestDto;
import pro.hexa.backend.main.api.domain.login.service.UserService;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(description = "회원가입")
    @PostMapping("/create")
    public ResponseEntity<String> userSignup(@RequestBody UserCreateRequestDto request) {
        return new ResponseEntity<>(userService.signupUser(request), HttpStatus.OK);
    }

    @Operation(description = "아이디 찾기(인증번호 전송)")
    @PostMapping("/find_id(SendVerificationCode)")
    public ResponseEntity<String> findUserIdSendVerificationCode(@RequestBody UserFindIdRequestDto request) {
        userService.findUserSendVerificationCode(request);
        return ResponseEntity.ok("Verification code is sent successfully!");
    }

    @Operation(description = "아이디 찾기(인증번호 확인)")
    @PostMapping("/find_id(verifyVerificationCode)")
    public ResponseEntity<String> IdverifyVerificationCode(@RequestBody UserFindVerificationRequestDto request) {
        String userid = userService.verifyId(request);
        return ResponseEntity.ok(userid);
    }

    @Operation(description = "비밀번호 찾기(id 입력)")
    @PostMapping("/find_passwordbyId")
    public ResponseEntity<String> findUserPasswordById(@RequestBody UserFindPasswordRequestDto1 request) {
        String newPassword = userService.findUserPasswordById(request);
        return ResponseEntity.ok(newPassword);
    }

    @Operation(description = "비밀번호 찾기(인증번호 전송)")
    @PostMapping("/find_password_SendVerificationCode")
    public ResponseEntity<String> findUserPasswordSendVerificationCode(@RequestBody UserFindIdRequestDto request) {
        userService.findUserSendVerificationCode(request);
        return ResponseEntity.ok("Verification code is sent successfully!");
    }

    @Operation(description = "비밀번호 찾기(인증번호 확인)")
    @PostMapping("/find_password_verifyVerificiationCode")
    public ResponseEntity<String> PasswordVerifyVerificationCode(@RequestBody UserFindPasswordRequestDto2 request) {
        String generatePassword = userService.verifyPassword(request);
        return ResponseEntity.ok(generatePassword);
    }

    @Operation(description = "비밀번호 찾기(비밀번호 변경)")
    @PostMapping("/password_change")
    public ResponseEntity<Boolean> changingUserPassword(@RequestBody UserFindPasswordRequestDto3 request, String Id) {
        userService.changeUserPassword(request, Id);
        return ResponseEntity.ok(true);
    }
}
