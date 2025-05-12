package ra.security.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerAddDto {
    @NotBlank(message = "Khong duoc de trong")
    private String firstName;
    @NotBlank(message = "Khong duoc de trong")
    private String lastName;
    @NotBlank(message = "Khong duoc de trong")
    @Email(message = "Khong dung dinh dang email")
    private String email;
    @NotBlank(message = "Khong duoc de trong")
    @Size(min = 10 , max = 11 , message = "so dien thoai phai co 10-11 so")
    private String phone;
    private String address;
    private Boolean sex;
}
