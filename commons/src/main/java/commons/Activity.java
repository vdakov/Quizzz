package commons;

import javax.persistence.*;

@Entity
public class Activity {
    @Id
    @SequenceGenerator(
            name="activity_name",
            sequenceName="activity_name",
            allocationSize =1
    )
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator="activity_name"
    )
    private Long id;
    private String title, source;
    private int consumption;

    private Activity() {

    }

    public Activity(String title, String source, int consumption) {
        this.title = title;
        this.source = source;
        this.consumption = consumption;
    }



    public Long getId() {
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
