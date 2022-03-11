package commons.Actions;

public class Action {
    private String  id;
    private String  title;
    private Integer consumption;
    private String  imagePath;

    /**
     * Constructor to create a new action
     *
     * @param id the id of the action
     * @param title the title of the activity
     * @param consumption the energy it uses in wH
     * @param imagePath the path to the image representing the image
     */
    public Action(String id, String title, Integer consumption, String imagePath) {
        this.id          = id;
        this.title       = title;
        this.consumption = consumption;
        this.imagePath   = imagePath;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setConsumption(Integer consumption) {
        this.consumption = consumption;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
}
