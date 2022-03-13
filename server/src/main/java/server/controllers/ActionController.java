package server.controllers;

import commons.Actions.Action;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/activities")
public class ActionController {


    private ActionService service; //serves the controller with all sorts of "business" methods


    public ActionController(ActionService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public Iterable<Action> list() {
        return service.list();
    }


    //sends GET HTTP response to client through the ID of the JSON if it exists
    @GetMapping("/{id}")
    public Action getById(@PathVariable("id") String id) {
        return service.getById(id);
    }


    //returns a random activity due to a GET request of the client
    @GetMapping("/random")
    public Action getRandom() {
        List<Action> activities = (List<Action>) service.list();
        return activities.get((int) Math.floor(Math.random() * activities.size()));
    }

    //allows the user to add a new activity to the repository and stores it in it permanently through an HTTP POST Request
    @PostMapping(path = {"", "/add"})
    public void add(@RequestBody Action a) {
        try {

            long temp = a.getConsumption() + 1;
            if (a.getTitle() != null) service.save(a);
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


        Action activity = service.getById(id.toString()); //.orElseThrow(() -> new IllegalStateException(("No such activity!!!")));

        if (title != null && title.length() > 0) {
            activity.setTitle(title);
        }

        if (consumption > 0) {
            activity.setConsumption(consumption);
        }

        /*if (source != null && source.length() > 0) {
            activity.setSource(source);
        }*/

        service.save(activity);
    }


    //test mapping to use as println cuz I am pretty bad at writing tests :(
    @GetMapping("/alert")
    public void alert() {
        System.out.println("HELLO");
    }


    //Allows the user to delete an item from the repository if it exists through and HTTP DELETE Request
    @DeleteMapping(path = "/delete/{id}")
    public void update(@PathVariable("id") String id) {
        service.delete(id);
    }


    //allows the user to update all the fields of an activity except the ID, but only if the object with that ID already exists
//    @PutMapping(path = "/update/{id}")
//    public void update(@PathVariable("id") String id,
//                       @RequestParam(required = false) String image_path,
//                       @RequestParam(required = false) String title,
//                       @RequestParam(required = false) int consumption,
//                       @RequestParam(required = false) String source) {
//
//        service.update(id, image_path, String title, consumption, source);
//        Action activity = getRepo().findById(id).orElseThrow(() -> new IllegalStateException(("No such activity!!!")));
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
//        getRepo().save(activity);
//
//    }


}
