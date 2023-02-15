package pro.hexa.backend.main.api.domain.login.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.hexa.backend.domain.user.domain.User;
import pro.hexa.backend.domain.user.model.GENDER_TYPE;
import pro.hexa.backend.domain.user.model.STATE_TYPE;
import pro.hexa.backend.domain.user.repository.UserRepository;
import pro.hexa.backend.main.api.domain.login.dto.JwtRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.LoginResponse;
import pro.hexa.backend.main.api.domain.login.dto.UserCreateRequestDto;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Transactional
    public String userSignupService(UserCreateRequestDto request){
        boolean userExists = userRepository.existsById(request.getId());

        if (userExists) return null;

        if (!request.getPassword1().equals(request.getPassword2())) return null;

        if(request.getPassword1()==request.getPassword2()){

            User user = new User();
            user.setId(request.getId());
            user.setEmail(request.getEmail());
            user.setGender(GENDER_TYPE.values()[request.getGender()]);
            user.setState(STATE_TYPE.values()[request.getState()]);
            user.setRegYear(Short.valueOf(request.getRegYear()));
            user.setRegNum(request.getRegNum());
            user.setName(request.getName());
            user.setPassword(passwordEncoder.encode(request.getPassword1()));

            userRepository.save(user);

            return  user.getId();
        }else{
            return null;
        }


    }

    public LoginResponse userLoginService(JwtRequestDto request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return LoginResponse.builder()
                .id(request.getUserName())
                .accessToken("ABASFAFSDESDFSDFEF")
                .refreshToken("ABASFAFSDESDFSDFEF")
                .build();
    }
}
