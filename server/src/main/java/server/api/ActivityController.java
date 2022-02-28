package server.api;

import commons.Activity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ActivityRepository;

import java.util.List;

@RestController
@RequestMapping("api/activities")
public class ActivityController {
    ActivityRepository repo;

    public ActivityController(ActivityRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getById(@PathVariable("id") Long id) {
        repo.findAll();
        return !repo.existsById(id) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(repo.getById(id));
    }

    @GetMapping(path = {"", "/"})
    public List<Activity> getAll() {
        return repo.findAll();
    }

    @GetMapping("/random")
    public Activity getRandom() {
        List<Activity> activities = repo.findAll();
        return activities.get((int) Math.floor(Math.random() * activities.size()));
    }

    @PostMapping(path = {"", "/add"})
    public void add(@RequestBody Activity a) {
        try {
            int temp = a.getConsumption() + 1;
            if (a.getTitle() != null && a.getSource() != null) repo.save(a);
        } catch (Exception e) {
            throw new IllegalStateException("POST Request Failed");
        }
    }







}
