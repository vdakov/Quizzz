package server.entities;

import commons.Exceptions.NotEnoughActivitiesException;
import commons.Questions.Question;
//import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.util.TestPropertyValues;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoomTest {

//    private List<Pair<Question, String>> testList;
    private Room r = new Room("1", "1", null);

    RoomTest() throws NotEnoughActivitiesException {
//        ActionCatalog testCatalog= new ActionCatalog();
//        testCatalog.addAction(new Action("id","none","title",100,"me"));
//        testCatalog.addAction(new Action("id","none","title",100,"me"));
//        testCatalog.addAction(new Action("id","none","title",100,"me"));
//        testCatalog.addAction(new Action("id","none","title",100,"me"));
//        this.testList=
//                QuestionGenerator.generateQuestions(testCatalog, 4, 0, 4, new Random());
//        this.test=new Game("mgfrz", testList);
    }

    @Test
    void testRoomStatus() {
        assertEquals(Room.RoomStatus.WAITING, r.getRoomStatus());
        r.setRoomStatus(Room.RoomStatus.ONGOING);
        assertEquals(Room.RoomStatus.ONGOING, r.getRoomStatus());
        r.setRoomStatus(Room.RoomStatus.FINISHED);
        assertEquals(Room.RoomStatus.FINISHED, r.getRoomStatus());

    }

    @Test
    void testBasicGetterAndSetterMethod() {
        assertEquals("1", r.getRoomId());
        assertEquals("1", r.getRoomCreator());
    }

    @Test
    void getGameQuestionsWithAnswers() {
//        TestPropertyValues.Pair p = TestPropertyValues.Pair.of("pair", "pair");
//        Question question = new Question
//        r.getQuestion(0);
//        assertEquals();
    }

}