package server.entities.Actions;

public class Action {
    private String id;
    private String title;
    private String consumption;
    private String imagePath;
    private String source;

    public Action(String id, String title, String consumption, String imagePath, String source) {
        this.id = id;
        this.title = title;
        this.consumption = consumption;
        this.imagePath = imagePath;
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public static boolean isSmart(Action action) {
        // to be implemented
        return true;
    }
}
