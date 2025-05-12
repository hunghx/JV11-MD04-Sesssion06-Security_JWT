package ra.security.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ra.security.security.jwt.JWTAuthTokenFilter;
import ra.security.security.principle.UserDetailsServiceCustom;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JWTAuthTokenFilter jwtAuthenticationFilter;

    // khai báo userDetails Sẻvice
    @Autowired
    private UserDetailsServiceCustom userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() { // mã hóa mật khẩu
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() { // cấu hình chia sẻ tài nguyên với các domain
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173/","http://localhost:5174/"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean // phân quyền
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource())) // chia sẻ tài nguyên
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // phi trạng thai
                // xử lí lỗi :
                .exceptionHandling(handler ->
                        handler.accessDeniedHandler(new AccessDeniedExceptionHandler()) // ko có quyen
                                .authenticationEntryPoint(new AuthenticationEntryPointHandler()) // ko đc xac thực
                )
                .authorizeHttpRequests( // cấu hình phân quyền theo đường dẫn
                        // /api.hunghx.com/v1/auth/sign-in
                        auth -> auth.requestMatchers("/api.hunghx.com/v1/auth/**").permitAll() // đăng nhập đăng kí
                                .requestMatchers("/api.hunghx.com/v1/admin/**").hasAuthority("ADMIN") // chỉ con quyê admin
//                                .requestMatchers("/api.hunghx.com/v1/admin/**").hasRole("USER") // chỉ con quyê admin
                                .requestMatchers("/api.hunghx.com/v1/user/**").hasAuthority("USER")
                                .requestMatchers("/api.hunghx.com/v1/manager/**").hasAuthority("MANAGER")
                                .requestMatchers("/api.hunghx.com/v1/user-mana/**").hasAnyAuthority("USER", "MANAGER")
                                .requestMatchers("/api.hunghx.com/v1/public/**").permitAll()
                                .anyRequest().authenticated() // còn lại thì phải được xác thực
                );
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
