package pro.hexa.backend.main.api.common.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pro.hexa.backend.domain.user.domain.User;
import pro.hexa.backend.domain.user.repository.UserRepository;
import pro.hexa.backend.main.api.common.auth.domain.RefreshToken;
import pro.hexa.backend.main.api.common.auth.repository.RefreshTokenRedisRepository;
import pro.hexa.backend.main.api.common.config.security.dto.LoginTokenDto;
import pro.hexa.backend.main.api.common.config.security.dto.LoginUserDto;
import pro.hexa.backend.main.api.common.exception.AuthorizationExceptionType;
import pro.hexa.backend.main.api.common.exception.BadRequestException;
import pro.hexa.backend.main.api.common.exception.BadRequestType;
import pro.hexa.backend.main.api.common.jwt.Jwt;
import pro.hexa.backend.main.api.common.utils.FilterUtils;

@RequiredArgsConstructor
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            //AttemptAuthentication메서드에서는 Request Body에 있는 데이터를 DTO객체로 변환한 후 providerManager의
            // authentication 메서드에 전달하게 됨.
            // 만약 Authentication메서드의 결과가 성공이라면 인증된 객체가 successfulAuthentication 메서드로
            // 돌아오고, 발행된 토큰과 같은 적절한 데이터를 포함한 HTTP response를 돌려주면 된다.
            // authentication메서드의 결과가 실패하면 인자로 넘어온 예외를 활용해 사용자에게 적절한 데이터를 보여줘
            // 이후 프로세스를 진행할 수 있도록 하면 된다.
            LoginUserDto loginUserDto = objectMapper.readValue(request.getInputStream(), LoginUserDto.class);

            String userId = loginUserDto.getUserId();
            String password = loginUserDto.getPassword();

            if (userId == null) {
                userId = "";
            }

            if (password == null) {
                password = "";
            }

            userId = userId.trim();
            // 얻어낸 userId와 password를 기반으로 UsernamePasswordAuthenticationToken을 발급.
            UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(userId, password);
            // 얻어낸 UsernamePasswordAuthenticationToken을 request에 detail로 저장
            setDetails(request, authRequest);
            // 실제로 UsernamePasswordAuthenticationToken에 입력된 userId와 password를 가지고
            // 인증(authenticate)를 수행하여 그 인증 값을 반환함.
            return this.getAuthenticationManager().authenticate(authRequest);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    //TODO: 로그인 성공했을 때랑 실패했을 떄 함수 정의
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
        Authentication authResult) throws IOException {
        String userId = ((UserDetails) authResult.getPrincipal()).getUsername();
        // 원래 UserDetailsService의 구현체가 해야할 일을 filter가 한번에 처리하는 것인가.?
        // UserDetailsService 구현체는 userId에 해당하는 유저를 찾고, 없을시에 notfound와 같은 Exception을 만든다.
        // 여기서의 successfulAuthentication 메서드는 그러한 과정을 나타내는 듯.?
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new BadRequestException(BadRequestType.CANNOT_FIND_USER));
        // 인증이 완료된 유저에 대해서, 토큰을 발급하는 과정이다.
        // AccessToken은 userId를 받아 토큰 유효 기간, userId, 토큰 발행 시각을 기록한
        // Json을 String 형태로 compact해서 저장한 토큰이다.
        String accessToken = Jwt.generateAccessToken(user.getId());
        // RefreshToken이 위의 AccessToken과 다른점은 UserId가 Claim되어 있지 않다는 것이다.
        String refreshToken = Jwt.generateRefreshToken();
        RefreshToken refreshTokenEntity = RefreshToken.create(accessToken, refreshToken);
        // 발행된 토큰을 RedisRepository에 저장해둔다.
        refreshTokenRedisRepository.save(refreshTokenEntity);
        
        // 인증이 성공한 곳에선 loginTokenDto와 같은 토큰을 발행해준다.
        LoginTokenDto loginTokenDto = new LoginTokenDto(accessToken, refreshToken);
        // 인증이 성공했기 때문에 응답을 돌려주고, 돌려줄 떄 HttpStatus는 OK 타입으로 돌려주고,
        //  loginTokenDto를 다시 반환해준다.
        FilterUtils.response(response, HttpStatus.OK, loginTokenDto);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException failed) throws IOException, ServletException {
        FilterUtils.responseError(response, HttpStatus.BAD_REQUEST, AuthorizationExceptionType.LOGIN_FAILED, failed);
    }

}
