package server.entities;

import commons.Exceptions.NotEnoughActivitiesException;

import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;


import java.util.List;


class GameTest {

    private List<Pair<Question, String>> testList;
    private Game test;

    GameTest() throws NotEnoughActivitiesException {
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
    void getGameId() {
//        assertEquals(test.getGameId(),"mgfrz");
    }

    @Test
    void setGameId() {
    }

    @Test
    void getGameQuestionsWithAnswers() {
    }

    @Test
    void setGameQuestionsWithAnswers() {
    }

    @Test
    void addUser() {
    }

    @Test
    void removeUser() {
    }

    @Test
    void getQuestion() {
    }

    @Test
    void getQuestionAnswer() {
    }

    @Test
    void getPlayerScore() {
    }

    @Test
    void updatePlayerScore() {
    }

    @Test
    void getNumPlayers() {
    }

    @Test
    void getRandomPlayer() {
    }
}