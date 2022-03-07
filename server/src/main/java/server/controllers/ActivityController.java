package server.controllers;




import org.springframework.web.bind.annotation.*;
import server.entities.Actions.Action;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    private ActionService service; //serves the controller with all sorts of "business" methods


    public ActivityController(ActionService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public Iterable<Action> list(){
        return service.list();
    }




}

