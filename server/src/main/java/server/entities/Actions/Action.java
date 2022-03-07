package server.entities.Actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;


//CHECK IF LOMBOK IS ALLOWED
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
public class Action {
    @Id
    private final String  id;

    private final String  title;

    private final Integer consumption;

    private final String  imagePath;

    private final String source;

    /**
     * Constructor to create a new action
     *  @param id the id of the action
     * @param title the title of the activity
     * @param consumption the energy it uses in wH
     * @param imagePath the path to the image representing the image
     * @param source
     */

    public Action(   @JsonProperty("id") String id, @JsonProperty("image_path") String imagePath, @JsonProperty("title") String title, @JsonProperty("consumption_in_wh") Integer consumption,
                       @JsonProperty("source") String source) {

        this.id          = id;
        this.title       = title;
        this.consumption = consumption;
        this.imagePath   = imagePath;
        this.source =  source;
    }

    public Action() {
        this.id          = "";
        this.title       = "";
        this.consumption = 0;
        this.imagePath   = "";
        this.source =  "";
    }


    /**
     * Returns the id of the current action
     *
     * @return the id of the current action
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the title of the current action
     *
     * @return the title of the current action
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the consumption of the current action
     *
     * @return the consumption of the current action
     */
    public Integer getConsumption() {
        return consumption;
    }

    /**
     * Returns the path to the image representing the action
     * @return the String representing the path to the image
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Checks if an activity is smart
     * We define a smart activity an activity that contains number, so the consumption can be changed based on that duration
     *
     * @return whether the activity is smart
     */
    public boolean isSmart() {
        return this.getTitle().matches(".*[1-9].*");
    }

    @Override
    public String toString() {
        return "Action{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", consumption=" + consumption +
                ", imagePath='" + imagePath + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
