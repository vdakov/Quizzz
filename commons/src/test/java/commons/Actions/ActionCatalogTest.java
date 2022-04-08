package commons.Actions;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionCatalogTest {

    private final ActionCatalog actionCatalog = new ActionCatalog();

    @Test
    void getNormalActions() {
        assertEquals("[]", actionCatalog.getNormalActions());
    }

    @Test
    void getSmartActions() {
        assertEquals("[]", actionCatalog.getSmartActions());
    }

    @Test
    void getUsedSmartActions() {
        assertEquals("[]", actionCatalog.getSmartActions());
    }

}