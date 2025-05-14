package ra.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.security.model.dto.request.FormLogin;
import ra.security.model.dto.response.JwtResponse;
import ra.security.security.jwt.JWTProvider;
import ra.security.security.principle.UserDetailsCustom;

@RestController
@RequestMapping("/auth")
// api.hunghx.com/v1/auth/sign-in
public class AuthController {
    @Autowired
    protected JWTProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/sign-in")
    public ResponseEntity<?> handleLogin(@RequestBody FormLogin formLogin){
        Authentication auth;
        try{
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(formLogin.getUsername(), formLogin.getPassword()));
        }catch (AuthenticationException e){
            return new ResponseEntity<>("thoog tin tk ko đúng", HttpStatus.BAD_REQUEST);
        }

        // đăng nhập thành công thì trả về token
        // lấy ra username của người dùng đã xác thực ở
        UserDetailsCustom userDetails = (UserDetailsCustom) auth.getPrincipal();
        String token = jwtProvider.generateToken(userDetails.getUsername());
        JwtResponse jwtResponse = JwtResponse.builder()
                .accessToken(token)
                .username(userDetails.getUsername())
                .id(userDetails.getId())
                .authorities(userDetails.getAuthorities())
                .build();
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }
}
