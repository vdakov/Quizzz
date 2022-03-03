package commons;

import javax.persistence.*;

@Entity
public class Action {
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

    private Action() {

    }

    public Action(String title, String source, int consumption) {
        this.title = title;
        this.source = source;
        this.consumption = consumption;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setConsumption(int consumption) {
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
