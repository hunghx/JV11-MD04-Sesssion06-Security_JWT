package ra.security.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ra.security.model.entity.Role;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
public class UserResponseDto {
    private Long id;
    private String username,email,fullName,avatar,phone,address;
    private LocalDate createdAt,updatedAt;
    private boolean status, isDeleted;
    private Set<Role> roleSet;
}
