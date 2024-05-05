package xyz.suhyuk0544.springwebsocket;

import jakarta.persistence.Index;
import jakarta.persistence.criteria.CriteriaBuilder;
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
//        for (int j = 1; j < 10; j++) {
//            for (int k = 1; k < 10; k++) {
//                System.out.println(j + "*" + k + "=" + j*k);
//            }
//        }
//    }

}
