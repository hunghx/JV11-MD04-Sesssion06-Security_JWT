package ra.security.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ra.security.security.principle.UserDetailsCustom;

import java.util.Collection;
import java.util.List;

@Builder
@Getter
@Setter
public class JwtResponse {
    private String accessToken;
    private final String type = "Bearer";
    private String username;
    private Long id;
    private Collection<? extends GrantedAuthority> authorities;
}
