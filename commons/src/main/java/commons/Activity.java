package commons;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Activity {
    @Id
    private String id;
    private String title, source;
    private int consumption;

    private Activity() {

    }

    public Activity(String id, String title, String source, int consumption) {
        this.id = id;
        this.title = title;
        this.source = source;
        this.consumption = consumption;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public int getConsumption() {
        return consumption;
    }
}
