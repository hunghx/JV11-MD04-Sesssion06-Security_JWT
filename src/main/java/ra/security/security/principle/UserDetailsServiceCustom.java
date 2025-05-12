package ra.security.security.principle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.security.exception.NotFoundException;
import ra.security.model.entity.User;
import ra.security.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceCustom implements UserDetailsService{
    @Autowired
    private  IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmailOrPhone(username).orElseThrow(() -> new NotFoundException("Username not found"));
        // bieens đổi user => userDeetails
        List<GrantedAuthority> list = new ArrayList<>();
        user.getRoleSet().forEach(role->{
            list.add(new SimpleGrantedAuthority(role.getRoleName().name()));
        });
        return UserDetailsCustom.builder()
                .id(user.getId())
                .username(username)
                .password(user.getPassword())
                .fullName(user.getFullName())
                .authorities(list)
                .build();
    }
}
