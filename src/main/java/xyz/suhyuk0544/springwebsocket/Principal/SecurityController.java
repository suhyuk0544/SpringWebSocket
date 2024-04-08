package xyz.suhyuk0544.springwebsocket.Principal;

import com.fasterxml.jackson.databind.util.JSONPObject;
import io.netty.handler.codec.socks.SocksResponseType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.suhyuk0544.springwebsocket.Principal.User.UserInfo;
import xyz.suhyuk0544.springwebsocket.Principal.User.UserInfoDto;

import java.security.Principal;
import java.util.Map;

@Controller
@AllArgsConstructor
@Slf4j
public class SecurityController {

    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping(value = "/save",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveUserInfo(@RequestBody Map<String,Object> json){

        JSONObject jsonObject = new JSONObject(json);

        log.info("{}",jsonObject);

        userDetailsService.saveUserInfo(new UserInfoDto(jsonObject.getString("email"), jsonObject.getString("password")));

        return new ResponseEntity<>(HttpStatus.OK);

    }


    @GetMapping(value = "/user")
    public ResponseEntity<String> findUserInfo(@AuthenticationPrincipal UserInfo principal){

        log.info(principal.getUsername());

        return ResponseEntity.ok(principal.getUsername());
    }

}
