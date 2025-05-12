package ra.security.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ra.security.security.principle.UserDetailsServiceCustom;

import java.io.IOException;
@Component
public class JWTAuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private UserDetailsServiceCustom userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Chan lai loc token JWT
        String token = getTokenFromRequest(request);
        // xác thực và giải mã token
        if (token!=null && jwtProvider.validateToken(token)){
            String username = jwtProvider.getUserNameFromToken(token);

            // lưu đối tượng đã được xác thực vào securitycontext
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication); // lưu vào context
        }
        filterChain.doFilter(request,response); // gửi request tới các tầng tiếp theo
    }
    private String getTokenFromRequest(HttpServletRequest request){
        String authorization = request.getHeader("Authorization"); // Bearer .....
        if (authorization!=null && authorization.startsWith("Bearer ")){
            return authorization.substring(7);
        }
        return null;
    }
}
