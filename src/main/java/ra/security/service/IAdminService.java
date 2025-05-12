package ra.security.service;

import ra.security.model.dto.response.UserResponseDto;

public interface IAdminService {
    UserResponseDto addRoleToUser(Long userId, Long roleId);
}
