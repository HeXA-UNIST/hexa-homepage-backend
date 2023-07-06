package pro.hexa.backend.main.api.domain.login.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.hexa.backend.main.api.domain.login.dto.FindIdRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.FindIdResponse;
import pro.hexa.backend.main.api.domain.login.dto.FindIdWithCodeRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.FindIdWithCodeResponse;
import pro.hexa.backend.main.api.domain.login.dto.UserCreateRequestDto;
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
    
    /*
    아이디 비번 찾기 api 구상
    
    아이디 찾기 / 인증번호 받기
    POST /user/findid
    request
    {
        name: "이름",
        email: "email@example.com",
    }
    response
    {
        error: 0, // email을 보내는데 실패하면 1
    }

    아이디 찾기 / 인증번호 확인
    POST /user/findidWithCode
    request
    {
        name: "이름",
        email: "email@example.com",
        code: "인증번호",
    }
    response
    {
        error: 0, // 인증코드가 틀리면 1
        id: "사용자 id".
    }

    비번 찾기 / 사용자 id 입력
    POST /user/findpw
    request
    {
        id: "사용자 id",
    }
    response
    {
        error: 0, // 사용자 id가 없으면 1
    }

    비번 찾기 / 인증번호 받기
    POST /user/findpwSendCode
    request
    {
        id : "앞서 입력했던 사용자 id"
        name: "이름",
        email: "email@example.com",
    }
    response
    {
        error: 0, // email을 보내는데 실패하면 1
    }

    비번 찾기 / 인증번호 확인
    POST /user/findpwWithCode
    request
    {
        name: "이름",
        email: "email@example.com",
        code: "인증번호",
    }
    response
    {
        error: 0, // 인증코드가 틀리면 1
    }

    비번 찾기 / 비밀번호 변경
    흠 그냥 하면 안 될거같은데. 뭔가 토큰을 발급해줘야 하나?

    */


    @Operation(description = "회원가입 인증코드 발급")
    @PostMapping("/findid")
    public ResponseEntity<FindIdResponse> userFindId(@RequestBody FindIdRequestDto request) {
        return new ResponseEntity<>(userService.userFindId(request), HttpStatus.OK);
    }


    @Operation(description = "회원가입 인증코드 인증")
    @PostMapping("/findidWithCode")
    public ResponseEntity<FindIdWithCodeResponse> userFindId(@RequestBody FindIdWithCodeRequestDto request) {
        return new ResponseEntity<>(userService.userFindIdWithCode(request), HttpStatus.OK);
    }
}
