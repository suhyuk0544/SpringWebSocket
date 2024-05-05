package xyz.suhyuk0544.springwebsocket.Principal;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.suhyuk0544.springwebsocket.Principal.User.UserInfo;
import xyz.suhyuk0544.springwebsocket.Principal.User.UserInfoDto;
import xyz.suhyuk0544.springwebsocket.Principal.User.UserInfoRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserInfoRepository userInfoRepository;

//    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userInfoRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public UserInfo saveUserInfo(UserInfoDto userInfoDto){

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return userInfoRepository.save(UserInfo.builder()
                        .email(userInfoDto.email())
                        .password(passwordEncoder.encode(userInfoDto.password()))
                        .build());

    }

    public UserInfo selectUserInfo(ArrayList<Principal> userInfos) {
        if (userInfos.size() < 2)
            return null;

        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        return (UserInfo) userInfos.get(threadLocalRandom.nextInt(userInfos.size()-1));
    }

}
