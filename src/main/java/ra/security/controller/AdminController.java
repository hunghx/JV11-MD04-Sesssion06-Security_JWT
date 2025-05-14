package ra.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ra.security.model.dto.response.UserResponseDto;
import ra.security.service.IAdminService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
///api.hunghx.com/v1/user/**
public class AdminController {
//    private final IAdminService adminService;
//    @PostMapping("/users/{userId}/role/{roleId}")
//    public ResponseEntity<?> addRoleToUser(@PathVariable Long userId, @PathVariable Long roleId){
//        UserResponseDto dto = adminService.addRoleToUser(userId, roleId);
//        Map<String , UserResponseDto> map = new HashMap<>();
//        map.put("data", dto);
//        return new ResponseEntity<>(map, HttpStatus.OK);
//    }

    @GetMapping("/admin/list")
    // phaan quyeen theo phuong thu
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> admin(){
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    @GetMapping("/user/list")
    public ResponseEntity<?> user(){
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    @GetMapping("/manager/list")
    public ResponseEntity<?> manager(){
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    @GetMapping("/user-mana/list")
    public ResponseEntity<?> twoRole(){
        // 2 quyeenf
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    @GetMapping("/public/list")
    public ResponseEntity<?> publicApi(){
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
