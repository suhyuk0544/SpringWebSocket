package xyz.suhyuk0544.springwebsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import xyz.suhyuk0544.springwebsocket.Principal.CustomPrincipal;

import javax.security.auth.Subject;
import java.util.Scanner;

@SpringBootApplication
public class SpringWebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebSocketApplication.class, args);
    }


//    public static void main(String[] args) {
//        Scanner input = new Scanner(Syst
//    }
}
