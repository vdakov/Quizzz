package commons.Actions;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActionCatalogTest {

    private final ActionCatalog actionCatalog = new ActionCatalog();
    private final ActionCatalog actionCatalog2 = new ActionCatalog(List.of(new Action("id", "path", "title", 1000, "source"), new Action("id2", "path2", "title2", 10000, "source2")));

    @Test
    void getNormalActions() {
        assertEquals("[]", actionCatalog.getNormalActions().toString());
    }

    @Test
    void getSmartActions() {
        assertEquals("[]", actionCatalog.getSmartActions().toString());
    }

    @Test
    void getUsedSmartActions() {
        assertEquals("[]", actionCatalog.getSmartActions().toString());
    }

    @Test
    void testGetNormalActions() {
      //  assertEquals(actionCatalog2.getAction(), actionCatalog2.getNormalActions().toString());
    }

    @Test
    void testGetSmartActions() {
      //  assertEquals("[commons.Actions.Action@56db847e]", actionCatalog2.getSmartActions().toString());
    }

    @Test
    void testGetUsedSmartActions() {

    }

    @Test
    void shuffleNormalActions() {
        assertTrue(actionCatalog2.getNormalActions().containsAll(actionCatalog2.shuffleNormalActions()));
    }

    @Test
    void shuffleSmartActions() {
        assertTrue(actionCatalog2.getSmartActions().containsAll(actionCatalog2.shuffleSmartActions()));
    }

    @Test
    void shuffleUsedSmartActions() {
        assertTrue(actionCatalog2.getUsedSmartActions().containsAll(actionCatalog2.shuffleUsedSmartActions()));
    }

    @Test
    void addAction() {
    }


    @Test
    void searchForConsumption() {
        assertEquals(null, actionCatalog.searchForConsumption(0, 1000));
    }
}