package ra.security.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import ra.security.model.dto.response.ErrorResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint { // xử lí thông tin xác thực ko đúng

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // xử lý thông tin trả về cho client
        response.setHeader("error","authentication");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(401);
        Map<String , Object> map =new HashMap<>();
        map.put("error",new ErrorResponse(401, HttpStatus.UNAUTHORIZED.toString(),authException.getMessage()));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(),map); // trả về respone cho client dưới dạng json

    }
}
