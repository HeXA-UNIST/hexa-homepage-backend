package pro.hexa.backend.main.api.common.auth.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24)
public class RefreshToken {

    @Id
    private Long id;
    // 여기 id는 어떻게 초기화 되는가.?

    @Indexed
    private String accessToken;

    @Indexed
    private String refreshToken;

    public static RefreshToken create(String accessToken, String refreshToken) {
        // 전체적으로 create함수는 accessToken과 refreshToken을 받아서 멤버를 초기화 한다.
        RefreshToken refreshTokenEntity = new RefreshToken(); // <- 인자 없는 생성자 어노테이션으로 생성 후 사용.
        refreshTokenEntity.accessToken = accessToken;
        refreshTokenEntity.refreshToken = refreshToken;
        return refreshTokenEntity;
    }
}
