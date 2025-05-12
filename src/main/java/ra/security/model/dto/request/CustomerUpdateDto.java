package ra.security.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerUpdateDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private Boolean sex;
}
