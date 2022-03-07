package server.controllers;

import org.springframework.stereotype.Service;
import server.entities.Actions.*;
import server.repositories.ActivityRepository;

import java.util.List;


@Service
public class ActionService {

    private ActivityRepository repository;

    public ActionService(ActivityRepository repository){
        this.repository=repository;
    }


   public Iterable<Action> save(List<Action> activities){
        return this.getRepository().saveAll(activities);
    }

    public Iterable<Action> list(){
        return this.getRepository().findAll();//returns a list of all users
    }

    public Action save(Action action){
        return getRepository().save(action);
    }

    public ActivityRepository getRepository() {
        return repository;
    }
}
