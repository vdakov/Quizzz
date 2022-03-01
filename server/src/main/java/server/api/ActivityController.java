package server.api;

import commons.Activity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ActivityRepository;

import java.util.List;

@RestController
@RequestMapping("api/activities")
public class ActivityController {

    private ActivityRepository repo; //the repository containing the JSONs of activities


    public ActivityController(ActivityRepository repo) {
        this.repo = repo;
    }

    public ActivityRepository getRepo() {
        return repo;
    }

    //sends GET HTTP response to client through the ID of the JSON if it exists
    @GetMapping("/{id}")
    public ResponseEntity<Activity> getById(@PathVariable("id") Long id) {
        getRepo().findAll();
        return !getRepo().existsById(id) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(getRepo().getById(id));
    }

    //returns all of the activities to the client in JSON format
    @GetMapping(path = {"", "/"})
    public List<Activity> getAll() {
        return getRepo().findAll();
    }


    //returns a random activity due to a GET request of the cleint
    @GetMapping("/random")
    public Activity getRandom() {
        List<Activity> activities = getRepo().findAll();
        return activities.get((int) Math.floor(Math.random() * activities.size()));
    }

    //allows the user to add a new activity to the repository and stores it in it permanently through an HTTP POST Request
    @PostMapping(path = {"", "/add"})
    public void add(@RequestBody Activity a) {
        try {
            int temp = a.getConsumption() + 1;
            if (a.getTitle() != null && a.getSource() != null) getRepo().save(a);
        } catch (Exception e) {
            throw new IllegalStateException("POST Request Failed");
        }
    }


    //allows the user to update all the fields of an activity except the ID, but only if the object with that ID already exists
    @PutMapping(path = "/update/{id}")
    public void update(@PathVariable("id") Long id,
                       @RequestParam(required = false) String title,
                       @RequestParam(required = false) int consumption,
                       @RequestParam(required = false) String source) {


        Activity activity = getRepo().findById(id).orElseThrow(() -> new IllegalStateException(("No such activity!!!")));

        if (title != null && title.length() > 0) {
            activity.setTitle(title);
        }

        if (consumption > 0) {
            activity.setConsumption(consumption);
        }

        if (source != null && source.length() > 0) {
            activity.setSource(source);
        }

        getRepo().save(activity);

    }

    //Allows the user to delete an item from the repository if it exists through and HTTP DELETE Request
    @DeleteMapping(path = "/delete/{id}")
    public void update(@PathVariable("id") Long id) {
        Activity activity = getRepo().findById(id).orElseThrow(() -> new IllegalStateException(("No such activity!!!")));
        getRepo().delete(activity);
    }


}
