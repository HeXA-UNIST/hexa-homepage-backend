package pro.hexa.backend.main.api.domain.login.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.hexa.backend.domain.user.repository.UserRepository;
import pro.hexa.backend.main.api.domain.login.dto.UserCreateRequestDto;
import pro.hexa.backend.main.api.domain.login.dto.JwtRequestDto;


@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;


    public String userSignupService(UserCreateRequestDto request){
        try{
            //userRepository.createUser(???)
            return("signup success");
        }finally{
        }

    }

    public String userLoginService(JwtRequestDto request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        return ("asd");
    }
}
