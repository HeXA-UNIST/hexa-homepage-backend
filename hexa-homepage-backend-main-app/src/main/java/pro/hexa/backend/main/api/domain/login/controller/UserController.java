package pro.hexa.backend.main.api.domain.login.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import pro.hexa.backend.domain.user.domain.User;
import pro.hexa.backend.main.api.domain.login.dto.*;
import pro.hexa.backend.main.api.domain.login.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


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
    // userService.userSignup() 메서드를 통해서 string을 반환한다. 그 반환값은 user의 id이다.
    @Operation(description = "아이디 찾기")
    @PostMapping("/findId")
    public ResponseEntity<String> findUserId(@RequestBody UserFindIdRequestDto request){
        ResponseEntity<String> result = new ResponseEntity<>(userService.findUserId(request), HttpStatus.OK);
        return result;
    }

    @Operation(description = "비밀번호 찾기")
    @PostMapping("/findPassword")
    public String findUserPasswordById(@RequestBody UserFindPasswordFirstRequestDto request){
        String id = userService.findUserPasswordFirst(request);

        return "redirect:/findPassword/" + id;
    }
    @Operation(description = "이메일 인증")
    @PostMapping("/findPassword/{id}/authentication")
    public String findUserPasswordByEmail(@RequestBody UserFindPasswordSecondRequestDto request
                                        , @PathVariable("id") String id){

        String userId = userService.findUserPasswordSecond(request);

        return "redirect:/findPassword/" + userId + "/inputPassword";
    }


    @Operation(description = "비밀번호 재설정")
    @PostMapping("/findPassword/{id}/modifyPassword")
    public String changeUserPassword(@RequestBody UserFindPasswordThirdRequestDto request, @PathVariable("id") String id){
        String userId = userService.changePassword(request, id);
        return "redirect:/findPassword/Success";
    }
}
