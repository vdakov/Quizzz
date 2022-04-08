package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionActivityCtrlTest {



    @Test
    void resetJokers1() throws ExecutionException, InterruptedException {
        QuestionActivityCtrl questionActivityCtrl = new QuestionActivityCtrl(new ServerUtils(), new SceneCtrl());
        questionActivityCtrl.resetJokers();
        assertEquals(false, questionActivityCtrl.getHintJokerUsed());
    }

    @Test
    void resetJokers2() throws ExecutionException, InterruptedException {
        QuestionActivityCtrl questionActivityCtrl = new QuestionActivityCtrl(new ServerUtils(), new SceneCtrl());
        questionActivityCtrl.resetJokers();
        assertEquals(false, questionActivityCtrl.getTimeJokerUsed());
    }

    @Test
    void resetJokers3() throws ExecutionException, InterruptedException {
        QuestionActivityCtrl questionActivityCtrl = new QuestionActivityCtrl(new ServerUtils(), new SceneCtrl());
        questionActivityCtrl.resetJokers();
        assertEquals(false, questionActivityCtrl.getDoublePointJokerUsed());
    }

    @Test
    void getQuestionNumber() throws ExecutionException, InterruptedException {
        QuestionActivityCtrl questionActivityCtrl = new QuestionActivityCtrl(new ServerUtils(), new SceneCtrl());
        assertEquals(-1, questionActivityCtrl.getQuestionNumber());
    }



    @Test
    void getTypeOfMessage1() throws ExecutionException, InterruptedException {
        QuestionActivityCtrl questionActivityCtrl = new QuestionActivityCtrl(new ServerUtils(), new SceneCtrl());
        assertEquals(" ☺ delia", questionActivityCtrl.getTypeOfMessage("1", "delia"));
    }


    @Test
    void getTypeOfMessage2() throws ExecutionException, InterruptedException {
        QuestionActivityCtrl questionActivityCtrl = new QuestionActivityCtrl(new ServerUtils(), new SceneCtrl());
        assertEquals(" ☹ delia", questionActivityCtrl.getTypeOfMessage("2", "delia"));
    }
    @Test
    void getTypeOfMessage3() throws ExecutionException, InterruptedException {
        QuestionActivityCtrl questionActivityCtrl = new QuestionActivityCtrl(new ServerUtils(), new SceneCtrl());
        assertEquals(" ⚇ delia", questionActivityCtrl.getTypeOfMessage("3", "delia"));
    }

    @Test
    void getTypeOfMessage4() throws ExecutionException, InterruptedException {
        QuestionActivityCtrl questionActivityCtrl = new QuestionActivityCtrl(new ServerUtils(), new SceneCtrl());
        assertEquals(" ☃ delia", questionActivityCtrl.getTypeOfMessage("4", "delia"));
    }

    @Test
    void getTypeOfMessage5() throws ExecutionException, InterruptedException {
        QuestionActivityCtrl questionActivityCtrl = new QuestionActivityCtrl(new ServerUtils(), new SceneCtrl());
        assertEquals(" ☠ delia", questionActivityCtrl.getTypeOfMessage("5", "delia"));
    }

    @Test
    void getTypeOfMessage6() throws ExecutionException, InterruptedException {
        QuestionActivityCtrl questionActivityCtrl = new QuestionActivityCtrl(new ServerUtils(), new SceneCtrl());
        assertEquals("Hint by delia", questionActivityCtrl.getTypeOfMessage("Hint", "delia"));
    }

    @Test
    void getTypeOfMessage7() throws ExecutionException, InterruptedException {
        QuestionActivityCtrl questionActivityCtrl = new QuestionActivityCtrl(new ServerUtils(), new SceneCtrl());
        assertEquals("x2 Points by delia", questionActivityCtrl.getTypeOfMessage("x2 Points", "delia"));
    }

    @Test
    void getTypeOfMessage8() throws ExecutionException, InterruptedException {
        QuestionActivityCtrl questionActivityCtrl = new QuestionActivityCtrl(new ServerUtils(), new SceneCtrl());
        assertEquals("Half Time by delia", questionActivityCtrl.getTypeOfMessage("Half Time", "delia"));
    }

    @Test
    void getTypeOfMessageNull() throws ExecutionException, InterruptedException {
        QuestionActivityCtrl questionActivityCtrl = new QuestionActivityCtrl(new ServerUtils(), new SceneCtrl());
        assertEquals(null, questionActivityCtrl.getTypeOfMessage("9", "delia"));
    }

    @Test
    void getAddedPointsInt() throws ExecutionException, InterruptedException {
        QuestionActivityCtrl questionActivityCtrl = new QuestionActivityCtrl(new ServerUtils(), new SceneCtrl());
        assertEquals(0, questionActivityCtrl.getPointsInt());
    }

    @Test
    void updateTimeLeft() throws ExecutionException, InterruptedException {
        QuestionActivityCtrl questionActivityCtrl = new QuestionActivityCtrl(new ServerUtils(), new SceneCtrl());
    }
}