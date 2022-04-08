package commons.Actions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {

    private final Action action = new Action("id", "path", "title", 1000, "source");

    @Test
    void setId() {
        action.setId("nn");
        assertEquals("nn", action.getId());
    }

    @Test
    void setTitle() {
        action.setTitle("tt");
        assertEquals("tt", action.getTitle());
    }

    @Test
    void setConsumption() {
        action.setConsumption(2);
        assertEquals(2, action.getConsumption());
    }

    @Test
    void setSource() {
        action.setSource("ss");
        assertEquals("ss", action.getSource());
    }

    @Test
    void setImagePath() {
        action.setImagePath("ii");
        assertEquals("ii", action.getImagePath());
    }

    @Test
    void getSource() {
        assertEquals("source", action.getSource());
    }

    @Test
    void getId() {
        assertEquals("id", action.getId());
    }

    @Test
    void getTitle() {
        assertEquals("title", action.getTitle());
    }

    @Test
    void getConsumption() {
        assertEquals(1000, action.getConsumption());
    }

    @Test
    void getImagePath() {
        assertEquals("path", action.getImagePath());
    }

    @Test
    void isSmart() {
        assertEquals(false, action.isSmart());
    }
}