package commons.Actions;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ActionCatalogTest {

    private final ActionCatalog actionCatalog = new ActionCatalog();

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

}