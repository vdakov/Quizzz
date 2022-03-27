package server.controllers;

import commons.Actions.Action;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.*;
import server.services.ActionService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    @PostMapping("/alert")
    public void alert(@RequestBody String input) {
        System.out.println(input);
    }


    //Allows the user to delete an item from the repository if it exists through and HTTP DELETE Request
    @DeleteMapping(path = "/delete/{id}")
    public void update(@PathVariable("id") String id) {
        service.delete(id);
    }

    /**
     * Sends the client in its body a base64-encoded image corresponding to the question they are on
     *
     * @param folderNum the folder where the image is stored
     * @param imagePath the name of the actual image
     * @return a base64-encoded image
     * @throws IOException in case the location is not found
     */
    @GetMapping(path = "/sendImage/{folderNum}/{imagePath}")
    public @ResponseBody
    String getImage(@PathVariable String folderNum, @PathVariable String imagePath) throws IOException {
        byte[] array = FileUtils.readFileToByteArray(new File("server/src/main/resources/activity-bank-pictures/" + folderNum + "/" + imagePath));
        String base64String = Base64.encodeBase64String(array);
        return base64String;
    }

    /**
     * Implementation for receiving an image from the client- groundwork for admin panel
     *
     * @param base64    the base64 encoded image the client sends
     * @param imageName the name the image will be saved as
     * @throws IOException
     */
    @PutMapping(path = "/receiveImage/{imageName}")
    public void receiveImage(@RequestBody String base64, @PathVariable String imageName) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(Base64.decodeBase64(base64));

        File savedImage = new File("server/src/main/resources/" + "Contributions/" + imageName);
        FileOutputStream outputStream = new FileOutputStream(savedImage);
        outputStream.write(bis.readAllBytes());


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


}
