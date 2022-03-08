package server.controllers;


import org.springframework.stereotype.Service;
import commons.Actions.*;
import server.repositories.ActivityRepository;

import java.util.List;


@Service
public class ActionService {

    //the variable of the interface that holds the database
    private ActivityRepository repository;


    public ActionService(ActivityRepository repository) {
        this.repository = repository;
    }

    //enables us to save a list of a lot of activities all at once
    public Iterable<Action> save(List<Action> activities) {
        return this.getRepository().saveAll(activities);
    }

    //returns a list of all activities currently in the repository
    public Iterable<Action> list() {
        return this.getRepository().findAll(); //returns a list of all users
    }

    //saves a single activity
    public Action save(Action action) {
        return getRepository().save(action);
    }

    //returns the entire repository-good practice even though I hate it
    public ActivityRepository getRepository() {
        return repository;
    }

    //completes the delete request of a single activity id
    public void delete(String id) {
        getRepository().deleteById(id);
    }

    //returns a single action delted by id
    public Action getById(String id) {
        Action activity = repository.findById(id).orElseThrow(() -> new IllegalStateException(("No such activity!!!")));

        return activity;

        //return !getRepository().existsById(id) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(getRepository().getById(id));
        //this is sydney's code about the same thing, I do not know why it does not work even though by all acounts it should so I just changed it
    }

//    public void update(@PathVariable("id") String id,
//                       @RequestParam(required = false) String image_path,
//                       @RequestParam(required = false) String title,
//                       @RequestParam(required = false) int consumption,
//                       @RequestParam(required = false) String source){
//
//        Action activity = repository.findById(id).orElseThrow(() -> new IllegalStateException(("No such activity!!!")));
//
//        if (title != null && title.length() > 0) {
//            activity.setTitle(title);
//        }
//
//        if (consumption > 0) {
//            activity.setConsumption(consumption);
//        }
//
//        if (source != null && source.length() > 0) {
//            activity.setSource(source);
//        }
//
//        repository.save(activity);
//    }
}
