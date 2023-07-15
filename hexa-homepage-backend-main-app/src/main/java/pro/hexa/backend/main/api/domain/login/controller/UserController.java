package pro.hexa.backend.main.api.domain.login.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.hexa.backend.dto.EmailRequestDto;
import pro.hexa.backend.main.api.common.exception.DataNotFoundException;
import pro.hexa.backend.main.api.domain.login.dto.EmailAuthenticationRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.EmailAuthenticationTokenDto;
import pro.hexa.backend.main.api.domain.login.dto.EmailSendingRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.UserCreateRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.UserFindPasswordWithIdRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.UserPasswordChangeRequestDto;
import pro.hexa.backend.main.api.domain.login.service.UserService;

import javax.validation.Valid;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final main.java.pro.hexa.backend.service.EmailService emailService;

    @Operation(description = "회원가입")
    @PostMapping("/create")
    public ResponseEntity<String> userSignup(@RequestBody UserCreateRequestDto request) {
        return new ResponseEntity<>(userService.userSignup(request), HttpStatus.CREATED);
    }

    // userService.userSignup() 메서드를 통해서 string을 반환한다. 그 반환값은 user의 id이다.
    @PostMapping("/email")
    public ResponseEntity<Void> sendAuthWithEmail(@RequestBody @Valid EmailSendingRequestDto request) throws DataNotFoundException {
        // 아이디 찾기에서 제일 먼저 하는 것 == 이메일 인증
        // validation check
        EmailRequestDto emailRequestDto = userService.makeEmailRequestDto(request);
        emailService.send(emailRequestDto); // 이메일 보내기
//        ResponseEntity<String> result = new ResponseEntity<>(emailRequestDto.getContent(), HttpStatus.OK);

        return ResponseEntity.ok().build();
    }

    @Operation(description = "아이디 찾기 & 비밀번호 찾기 이메일 인증")
    @PostMapping("/authentication")
    public ResponseEntity<EmailAuthenticationTokenDto> findUserId(@RequestBody EmailAuthenticationRequestDto request) throws DataNotFoundException {
        ResponseEntity<EmailAuthenticationTokenDto> result = new ResponseEntity<>(userService.emailAuthenticationWithNameAndEmail(request), HttpStatus.CREATED);
        return result;
    }


    @Operation(description = "비밀번호 찾기")
    @PostMapping("/findPassword")
    public ResponseEntity<String> findUserPasswordById(@RequestBody UserFindPasswordWithIdRequestDto request) throws DataNotFoundException {
        ResponseEntity<String> result = new ResponseEntity<>(userService.findUserPasswordWithId(request), HttpStatus.CREATED);
        return result;
    }

//    @Operation(description = "이메일 인증")
//    @PostMapping("/findPassword/authentication")
//    public ResponseEntity<EmailAuthenticationTokenDto> findUserPasswordByEmail(@RequestBody EmailAuthenticationRequestDto request) throws DataNotFoundException {
//        ResponseEntity<EmailAuthenticationTokenDto> result = new ResponseEntity<>(userService.emailAuthenticationWithNameAndEmail(request), HttpStatus.OK);
//        return result; // 이메일 인증이 완료된 토큰을 response로 준다.
//    }


    @Operation(description = "비밀번호 재설정")
    @PostMapping("/findPassword/modifyPassword")
    public ResponseEntity<Void> changeUserPassword(@RequestHeader EmailAuthenticationTokenDto tokenDto, @RequestBody UserPasswordChangeRequestDto request) {
        // request header에 있는 토큰의 유효성 검사
        String userId = userService.validateJwtToken(tokenDto);
        ResponseEntity<Void> result = new ResponseEntity<>(userService.changePassword(request, userId), HttpStatus.CREATED);
        return result;
    }
}
