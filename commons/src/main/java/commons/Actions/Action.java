package commons.Actions;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;


//tag to mark this object as a database object
@Entity
public class Action {
    @Id
    private final String id;

    private final String title;

    private final long consumption;

    private final String imagePath;

    private final String source;

    /**
     * Constructor to create a new action
     * <p>
     * It takes in a bunch of @JsonPropery tags as the fields in this constructor
     * come from the JSON file activities
     *
     * @param id          the id of the action
     * @param title       the title of the activity
     * @param consumption the energy it uses in wH
     * @param imagePath   the path to the image representing the image
     * @param source
     */
    public Action(@JsonProperty("id") String id, @JsonProperty("image_path") String imagePath, @JsonProperty("title") String title, @JsonProperty("consumption_in_wh") long consumption,
                  @JsonProperty("source") String source) {

        this.id = id;
        this.title = title;
        this.consumption = consumption;
        this.imagePath = imagePath;
        this.source = source;
    }

    /**
     * EMPTY CONSTRUCTOR BECAUSE FOR SOME GOD DAMNED REASON
     * H2 REQUIRES ME TO HAVE A CONSTRUCTOR WITH NO ARGUMENTS
     * EVEN THOUGH LITERALLY EVERY TIME I TRY TO MAKE A DATABASE I USE THE JSON
     * FILES BUT OTHERWISE AN EXCEPTION IS THROWN EACH TIME SO I WII LUST ROLL WITH IT
     */
    public Action() {
        this.id = "";
        this.title = "";
        this.consumption = 0;
        this.imagePath = "";
        this.source = "";
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
    public long getConsumption() {
        return consumption;
    }

    /**
     * Returns the path to the image representing the action
     *
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
    @JsonIgnore
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

    /**
     * returns the source of the JSON as a String
     *
     * @return a string of the source
     */
    public String getSource() {
        return source;
    }
}
