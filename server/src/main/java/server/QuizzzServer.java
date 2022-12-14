package server;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Actions.Action;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import server.services.ActionService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
@EntityScan(basePackages = {"commons", "server"})
public class QuizzzServer {

    /**
     * The main method that launches the server-side app
     *
     * @param args the arguments for running the app
     */
    public static void main(String[] args) throws FileNotFoundException {
        SpringApplication.run(QuizzzServer.class, args);
    }


    /**
     * A CommandLineRunner that initializes the database every time the application starts up
     *
     * @param actionService used to write to the database
     * @return
     */
    @Bean
    CommandLineRunner runner(ActionService actionService) {
        if (actionService.getRepository().count() < 1) {
            return args -> {
                ObjectMapper mapper = new ObjectMapper();
                TypeReference<List<Action>> typeReference = new TypeReference<List<Action>>() {
                }; //requires us to have a list of activities/actions
                InputStream inputStream = TypeReference.class.getResourceAsStream("/activities.json"); //converts the JSON array to the reference type List<Action>


                try {
                    List<Action> actions = mapper.readValue(inputStream, typeReference); //does the actual mapping to the list
                    // it won't save certain activities that do not match the current database, but this should be optimised
                    for (int i = 0; i < actions.size(); i++) {
                        if (actions.get(i).getConsumption() > Integer.MAX_VALUE || actions.get(i).getSource() == null || actions.get(i).getSource().length() > 255) {
                            actions.remove(i);
                            i--;
                        }
                    }

                    actionService.save(actions); //saves the actions to the repository
                    System.out.println("ACTIVITIES SAVED"); //confirmation message
                } catch (IOException e) {
                    System.out.println(e);
                    System.out.println("ACTIVITIES NOT SAVED"); //failure message
                }

            };
        }
        return args -> {
        }; // do nothing if there are activities in repository
    }
}
