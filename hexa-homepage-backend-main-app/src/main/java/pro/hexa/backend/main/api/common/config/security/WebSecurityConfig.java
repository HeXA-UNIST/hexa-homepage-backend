package pro.hexa.backend.main.api.common.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pro.hexa.backend.domain.user.repository.UserRepository;
import pro.hexa.backend.main.api.common.auth.repository.RefreshTokenRedisRepository;
import pro.hexa.backend.main.api.common.config.security.LoginAuthenticationFilter;

@Configuration
@EnableWebSecurity // 이 어노테이션이 WebSecurityConfigurerAdapter를 상속받는 클래스에 쓰는 어노테이션임
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    public static final String[] AUTHENTICATION_UNNECESSARY_REQUESTS={

    };
    // filterChain메서드는 http를 받아 SecurityFilterChain을 반환한다.

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // authenicationManager 객체는 AuthenticationConfiguration객체를 생성자의 인자로 넣어서 만든다.
        AuthenticationManager authenticationManager = authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));

        // UsernamePasswordAuthenticationFilter를 상속받은 loginAuthenticationFilter는 생성자로 AuthenticationManager를 인자로 받는다.
        Filter loginAuthenticationFilter = loginAuthenticationFilter(authenticationManager);
        // jwtAuthorization(권한 인가)는 authenticationManager를 인자로 받는다. (원래는 UserRepository도 받을 수 있다)
        Filter jwtAuthorizationFilter = jwtAuthorizationFilter(authenticationManager);

        http.cors().configurationSource(corsConfigurationSource()).and()
            .csrf().disable() // jwt를 사용하는 방식이어서 csrf를 꺼준다.
//                .userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder())
            .addFilterAt(loginAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // UsernamePasswordAuthenticationFilter자리에 loginAuthentication을 대체함.

            .addFilterBefore(jwtAuthorizationFilter,UsernamePasswordAuthenticationFilter.class) // 권한 filter. 위 filter 이후에 filtering하도록 넣음.
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // 무상태성 (jwt이기 때문에)
            .authorizeRequests().anyRequest().permitAll().and()
            .formLogin().disable()
            .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            .accessDeniedHandler(new CustomAccessDeniedHandler());


        return http.build();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(), HttpMethod.HEAD.name(),
            HttpMethod.POST.name(), HttpMethod.PUT.name(),
            HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name(), HttpMethod.PATCH.name()));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Arrays.asList("Content-Disposition"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // AuthenticationManager는 인터페이스이다. 즉, AuthenticationManager를 리턴한다는 것은 다른 말로, AuthenticationManager인터페이스를
    // implements하는 AuthenticationConfiguration을 리턴하는 것..?
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    // 로그인 인증을 위한 요청을 할 때, Intercept하는 filter이다. AuthenticationManager를 인자로 받고,
    // WebSecurityConfig가 가지고 있던 objectMapper, userRepository, refreshTokenRedisRepository를 이용해 LoginAuthenicationFilter를 생성하고,
    // 생성한 LoginAuthenticationFilter객체에 AuthenticationManager를 등록하여 반환해준다.
    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter(AuthenticationManager authenticationManager){
        // CustomAuthenticationFilter를 Bean으로 등록하는 과정에서 userName파라미터와 Password파라미터를 설정할 수 있다.
        // 이러한 과정을 거치면 UsernamePasswordToken이 발급되게 된다
        LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter(objectMapper, userRepository,
            refreshTokenRedisRepository);
        loginAuthenticationFilter.setAuthenticationManager(authenticationManager);

        return loginAuthenticationFilter;
    }
    // JwtAuthorizationFilter의 생성자 AuthenticationManager를 인자로 받는다.
    // WebSecurityConfig가 가지고 있는 userRepository를 사용하여 JwtAuthorizationFilter를 생성한다.
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(AuthenticationManager authenticationManager){


        return new JwtAuthorizationFilter(authenticationManager, userRepository);
    }
//    @Bean
//    public CustomAuthenticationProvider customAuthenticationProvider() {
//        return new CustomAuthenticationProvider(new UserDetailsServiceImpl(userRepository), new BCryptPasswordEncoder());
//    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // 비밀번호 암호화하는 객체
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
