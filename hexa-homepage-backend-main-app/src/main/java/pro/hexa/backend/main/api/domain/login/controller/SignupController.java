package pro.hexa.backend.main.api.domain.login.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import pro.hexa.backend.main.api.domain.login.dto.JwtRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.UserCreateRequestDto;
import pro.hexa.backend.main.api.domain.login.service.UserService;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class SignupController {

    private final UserService userService;

    @Operation(description = "회원가입")
    @GetMapping("/create")
    public ResponseEntity<String> userSignup(@RequestBody UserCreateRequestDto request) {
        return new ResponseEntity<>(userService.userSignupService(request), HttpStatus.OK);
    }

    @Operation(description = "로그인")
    @GetMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody JwtRequestDto request) {
        return new ResponseEntity<>(userService.userLoginService(request), HttpStatus.OK);
    }
}
