package pro.hexa.backend.main.api.domain.login.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import pro.hexa.backend.domain.user.domain.User;
import pro.hexa.backend.main.api.common.exception.BadRequestException;
import pro.hexa.backend.main.api.common.exception.BadRequestType;
import pro.hexa.backend.main.api.domain.login.dto.*;
import pro.hexa.backend.main.api.domain.login.service.EmailSender;
import pro.hexa.backend.main.api.domain.login.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.Map;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailSender emailSender;
    @Operation(description = "회원가입")
    @PostMapping("/create")
    public ResponseEntity<String> userSignup(@RequestBody UserCreateRequestDto request) {
        return new ResponseEntity<>(userService.userSignup(request), HttpStatus.OK);
    }
    // userService.userSignup() 메서드를 통해서 string을 반환한다. 그 반환값은 user의 id이다.
    @PostMapping("/email")
    public ResponseEntity<Void> authEmail(@RequestBody @Valid EmailRequestDto request){
        // 아이디 찾기에서 제일 먼저 하는 것 == 이메일 인증
        emailSender.authEmail(request); // 이메일 보내는 API
        return ResponseEntity.ok().build();
    }
    @Operation(description = "아이디 찾기")
    @PostMapping("/findId")
    public ResponseEntity<String> findUserId(@RequestBody UserFindIdRequestDto request){
        ResponseEntity<String> result = new ResponseEntity<>(userService.findUserId(request), HttpStatus.OK);
        return result; // 성공시 id를 반환
    }


    @Operation(description = "비밀번호 찾기")
    @PostMapping("/findPassword")
    public ResponseEntity<String> findUserPasswordById(@RequestBody UserFindPasswordFirstRequestDto request){
        ResponseEntity<String> result = new ResponseEntity<>(userService.findUserPasswordFirst(request), HttpStatus.OK);
        return result;
    }
    @Operation(description = "이메일 인증")
    @PostMapping("/findPassword/authentication")
    public ResponseEntity<String> findUserPasswordByEmail(@RequestBody UserFindPasswordSecondRequestDto request){
        ResponseEntity<String> result = new ResponseEntity<>(userService.findUserPasswordSecond(request), HttpStatus.OK);
        return result;
    }


    @Operation(description = "비밀번호 재설정")
    @PostMapping("/findPassword/modifyPassword")
    public ResponseEntity<Boolean> changeUserPassword(@RequestBody UserFindPasswordThirdRequestDto request){
        ResponseEntity<Boolean> result = new ResponseEntity<>(userService.changePassword(request), HttpStatus.OK);
        return result;
    }
}
