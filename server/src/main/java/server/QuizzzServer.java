package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = { "commons", "server" })
public class QuizzzServer {

    public static void main(String[] args) {
        SpringApplication.run(QuizzzServer.class, args);
    }
}