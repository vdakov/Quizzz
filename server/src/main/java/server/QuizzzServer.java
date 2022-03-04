package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = { "commons", "server" })
public class QuizzzServer {

    /**
     * The main method that launches the server-side app
     * @param args the arguments for running the app
     */
    public static void main(String[] args) {
        SpringApplication.run(QuizzzServer.class, args);
    }
}