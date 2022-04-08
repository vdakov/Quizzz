package server.controllers.GameControllers;

import commons.Questions.Question;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.services.GameServices.MultiplayerGameService;
import server.services.GameServices.SingleplayerGameService;
import server.services.GameServices.Util;

@RestController
@RequestMapping("api/{username}/{gameType}/{roomId}")
public class GameRoomController {

    private final SingleplayerGameService singlePlayerGameService;
    private final MultiplayerGameService  multiplayerGameService;

    /**
     * Constructor for the game room controller
     *
     * @param singlePlayerGameService the service for the singleplayer game features
     * @param multiplayerGameService  the service for the multiplayer  game features
     */
    public GameRoomController(SingleplayerGameService singlePlayerGameService, MultiplayerGameService multiplayerGameService) {
        this.singlePlayerGameService = singlePlayerGameService;
        this.multiplayerGameService  = multiplayerGameService;
    }

    /**
     * Starts the game with the given room or gives a corresponding error message
     *
     * @param username the username of the player that is starting the game
     * @param gameType the type of the game
     * @param roomId   the id of the game that is wanted to be started
     * @return whether the room was successfully started
     */
    @GetMapping("/startRoom")
    public ResponseEntity startNewGame(@PathVariable("username") String username, @PathVariable("gameType") String gameType, @PathVariable("roomId") String roomId) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                return (singlePlayerGameService.startSinglePlayerGame(username, roomId)) ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                return (multiplayerGameService.startMultiPlayerGame(username, roomId)) ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Returns the requested question by the client or gives a corresponding error message
     *
     * @param username       the username of the player that requests the question
     * @param gameType       the type of the game
     * @param roomId         the id of the game that the player is in
     * @param questionNumber the number of the requested question
     * @return the desired question or an error message
     */
    @GetMapping("/{questionNumber}/getQuestion")
    public ResponseEntity<String> getQuestion(@PathVariable("username") String username, @PathVariable("gameType") String gameType,
                                                    @PathVariable("roomId") String roomId, @PathVariable("questionNumber") String questionNumber) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                int questionNo = Integer.parseInt(questionNumber);
                Question question = singlePlayerGameService.getSinglePlayerQuestion(username, roomId, questionNo);

                if (question == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }

                return ResponseEntity.status(HttpStatus.OK).body(Util.getQuestionType(question) + ": " + question.toJsonString());
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                int questionNo = Integer.parseInt(questionNumber);
                Question question = multiplayerGameService.getMultiPlayerQuestion(username, roomId, questionNo);

                if (question == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }

                return ResponseEntity.status(HttpStatus.OK).body(Util.getQuestionType(question) + ": " + question.toJsonString());
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Returns the answer to the question or gives a corresponding error message
     *
     * @param username the username of the player that requests the answer
     * @param gameType the type of the game
     * @param roomId the id of the game that the player is in
     * @param questionNumber the number of the question that the answer is needed for
     * @return the String representing the answer or an error message
     */
    @GetMapping("/{questionNumber}/getAnswer")
    public ResponseEntity<String> getCorrectAnswer(@PathVariable("username") String username, @PathVariable("gameType") String gameType,
                                                   @PathVariable("roomId") String roomId, @PathVariable("questionNumber") String questionNumber) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                int questionNo = Integer.parseInt(questionNumber);
                String answer = singlePlayerGameService.getSinglePlayerAnswer(username, roomId, questionNo);

                if (answer == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }

                return ResponseEntity.status(HttpStatus.OK).body(answer);
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                int questionNo = Integer.parseInt(questionNumber);

                String answer = multiplayerGameService.getMultiPlayerAnswer(username, roomId, questionNo);

                if (answer == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }

                return ResponseEntity.status(HttpStatus.OK).body(answer);
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Returns the score of the desired player or gives a corresponding error message
     * @param username       the username of the player that requests his score
     * @param gameType       the type of the game
     * @param roomId         the id of the game that the player is in
     * @return the score of the desired player or an error message
     */
    @GetMapping("/getScore")
    public ResponseEntity<Integer> getScore(@PathVariable("username") String username, @PathVariable("gameType") String gameType, @PathVariable("roomId") String roomId) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                Integer score = singlePlayerGameService.getSinglePlayerScore(username, roomId);
                return (score != null) ? ResponseEntity.status(HttpStatus.OK).body(score) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                Integer score = multiplayerGameService.getMultiPlayerScore(username, roomId);
                return (score != null) ? ResponseEntity.status(HttpStatus.OK).body(score) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * @param username       the username of the player that requests his score
     * @param gameType       the type of the game
     * @param roomId         the id of the game that the player is in
     * @return time the client has during this question round
     */
    @GetMapping("/{questionNumber}/getTimeClient")
    public ResponseEntity<Integer> getTimeClient(@PathVariable("username") String username, @PathVariable("gameType") String gameType,
                                                 @PathVariable("roomId") String roomId, @PathVariable("questionNumber") String questionNumber) {
        if (gameType.equals("MULTIPLAYER")) {
            try {
                int number = Integer.parseInt(questionNumber);
                Integer timeClient = multiplayerGameService.getTimeClient(username, roomId, number);
                return (timeClient != null) ? ResponseEntity.status(HttpStatus.OK).body(timeClient) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Updates the score of the player according to the given and correct answer or gives a corresponding error message
     *
     * @param username          the username of the player that gives the answer
     * @param gameType          the type of the game
     * @param roomId            the id of the game that the player is in
     * @param questionNumber    the number of the answered question
     * @param userAnswer        the user answer to the question
     * @return whether the request was successful or not
     */
    @PostMapping("/{questionNumber}/postAnswer")
    public ResponseEntity<Object> postAnswer(@PathVariable("username") String username, @PathVariable("gameType") String gameType,
                                               @PathVariable("roomId") String roomId, @PathVariable("questionNumber") String questionNumber, @RequestBody String userAnswer) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                int questionNo = Integer.parseInt(questionNumber);
                return singlePlayerGameService.updateSinglePlayerScore(username, roomId, questionNo, userAnswer) ?
                        ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                int questionNo = Integer.parseInt(questionNumber);
                return multiplayerGameService.updateMultiPlayerScore(username, roomId, questionNo, userAnswer) ?
                        ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Updates hintJokerUsed (sets to true) or gives a corresponding error message
     *
     * @param username  the username of the player that gives the answer
     * @param gameType  the type of the game
     * @param roomId    the id of the game that the player is in
     * @return whether the request was successful or not
     */
    @GetMapping("/useHintJoker")
    public ResponseEntity<Object> useHintJoker(@PathVariable("username") String username, @PathVariable("gameType") String gameType,
                                             @PathVariable("roomId") String roomId) {
        System.out.println("Got post for hint joker");
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                return singlePlayerGameService.useHintJoker(username, roomId) ?
                        ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                return multiplayerGameService.useHintJoker(username, roomId) ?
                        ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Updates doublePointJoker (sets to true) or gives a corresponding error message
     *
     * @param username  the username of the player that gives the answer
     * @param gameType  the type of the game
     * @param roomId    the id of the game that the player is in
     * @return whether the request was successful or not
     */
    @GetMapping("/useDoublePointJoker")
    public ResponseEntity<Integer> useDoublePointJoker(@PathVariable("username") String username, @PathVariable("gameType") String gameType,
                                                       @PathVariable("roomId") String roomId) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                singlePlayerGameService.useDoublePointJoker(username, roomId);
                Integer newAddedPoints = singlePlayerGameService.getAddedPoints(username, roomId);
                return (newAddedPoints != null) ? ResponseEntity.status(HttpStatus.OK).body(newAddedPoints) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }
        if (gameType.equals("MULTIPLAYER")) {
            try {
                multiplayerGameService.useDoublePointJoker(username, roomId);
                Integer newAddedPoints = multiplayerGameService.getAddedPoints(username, roomId);
                return (newAddedPoints != null) ?
                        ResponseEntity.status(HttpStatus.OK).body(newAddedPoints) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Resets the points added, to prevent the points getting doubled everytime after the double point joker is used
     * @param username  the username of the player that gives the answer
     * @param gameType  the type of the game
     * @param roomId    the id of the game that the player is in
     * @return
     */
    @GetMapping("/resetDoubledAddedPoints")
    public ResponseEntity<Object> resetDoubledAddedPoints(@PathVariable("username") String username, @PathVariable("gameType") String gameType,
                                                          @PathVariable("roomId") String roomId) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                return singlePlayerGameService.resetAddedPointAfterDoublePointJoker(username, roomId) ?
                        ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                return multiplayerGameService.resetAddedPointAfterDoublePointJoker(username, roomId) ?
                        ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Updates timeJokerUsed (sets to true) or gives a corresponding error message
     *
     * @param username the username of the player that gives the answer
     * @param gameType the type of the game
     * @param roomId the id of the game that the player is in
     * @return whether the request was successful or not
     */
    @GetMapping("/{questionNumber}/useTimeJoker")
    public ResponseEntity<Object> useTimeJoker(@PathVariable("username") String username, @PathVariable("gameType") String gameType,
                                               @PathVariable("roomId") String roomId, @PathVariable("questionNumber") String questionNumber) {
        if (gameType.equals("MULTIPLAYER")) {
            try {
                int number = Integer.parseInt(questionNumber);
                return multiplayerGameService.useTimeJoker(username, roomId, number) ?
                        ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Returns the points earned by the player for that specific game
     * @param username  the username of the player that requests his score
     * @param gameType  the type of the game
     * @param roomId    the id of the game that the player is in
     * @return the points earned in this round
     */
    @GetMapping("/getAddedPoints")
    public ResponseEntity<Integer> getAddedPoints(@PathVariable("username") String username, @PathVariable("gameType") String gameType, @PathVariable("roomId") String roomId) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                Integer addedPoints = singlePlayerGameService.getAddedPoints(username, roomId);
                return (addedPoints != null) ? ResponseEntity.status(HttpStatus.OK).body(addedPoints) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                Integer addedPoints = multiplayerGameService.getAddedPoints(username, roomId);
                return (addedPoints != null) ? ResponseEntity.status(HttpStatus.OK).body(addedPoints) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Calculates the points earned in this round
     * @param username  the username of the player that requests his score
     * @param gameType  the type of the game
     * @param roomId    the id of the game that the player is in
     * @return whether the request was successful or not
     */
    @GetMapping("/{questionNumber}/calculateAddedPoints")
    public ResponseEntity<Object> calculateAddedPoints(@PathVariable("username") String username, @PathVariable("gameType") String gameType,
                                                       @PathVariable("roomId") String roomId, @PathVariable("questionNumber") String questionNumber) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                int questionNo = Integer.parseInt(questionNumber);
                singlePlayerGameService.calculatePointsAdded(username, roomId);
                return ResponseEntity.status(HttpStatus.OK).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                int questionNo = Integer.parseInt(questionNumber);
                multiplayerGameService.calculatePointsAdded(username, roomId);
                return ResponseEntity.status(HttpStatus.OK).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * @param username       the username of the player that requests his score
     * @param gameType       the type of the game
     * @param roomId         the id of the game that the player is in
     * @return if hintJoker has been used this game
     */
    @GetMapping("/getHintJokerUsed")
    public ResponseEntity<Boolean> getHintJokerUsed(@PathVariable("username") String username, @PathVariable("gameType") String gameType, @PathVariable("roomId") String roomId) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                Boolean hintJokerUsed = singlePlayerGameService.getHintJokerUsed(username, roomId);
                return (hintJokerUsed != null) ? ResponseEntity.status(HttpStatus.OK).body(hintJokerUsed) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                Boolean hintJokerUsed = multiplayerGameService.getHintJokerUsed(username, roomId);
                return (hintJokerUsed != null) ? ResponseEntity.status(HttpStatus.OK).body(hintJokerUsed) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * @param username       the username of the player that requests his score
     * @param gameType       the type of the game
     * @param roomId         the id of the game that the player is in
     * @return if DoublePoint has been used this game
     */
    @GetMapping("/getDoublePointJokerUsed")
    public ResponseEntity<Boolean> getDoublePointJokerUsed(@PathVariable("username") String username, @PathVariable("gameType") String gameType, @PathVariable("roomId") String roomId) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                Boolean doublePointJokerUsed = singlePlayerGameService.getDoublePointJokerUsed(username, roomId);
                return (doublePointJokerUsed != null) ? ResponseEntity.status(HttpStatus.OK).body(doublePointJokerUsed) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                Boolean doublePointJokerUsed = multiplayerGameService.getDoublePointJokerUsed(username, roomId);
                return (doublePointJokerUsed != null) ? ResponseEntity.status(HttpStatus.OK).body(doublePointJokerUsed) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * @param username       the username of the player that requests his score
     * @param gameType       the type of the game
     * @param roomId         the id of the game that the player is in
     * @return if timeJoker has been used this game
     */
    @GetMapping("/getTimeJokerUsed")
    public ResponseEntity<Boolean> getTimeJokerUsed(@PathVariable("username") String username, @PathVariable("gameType") String gameType, @PathVariable("roomId") String roomId) {
        if (gameType.equals("MULTIPLAYER")) {
            try {
                Boolean timeJokerUsed = multiplayerGameService.getTimeJokerUsed(username, roomId);
                return (timeJokerUsed != null) ? ResponseEntity.status(HttpStatus.OK).body(timeJokerUsed) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/getTimeLeft")
    public ResponseEntity<Integer> getTimeLeft(@PathVariable("username") String username, @PathVariable("gameType") String gameType, @PathVariable("roomId") String roomId) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                Integer timeLeft = singlePlayerGameService.getTimeLeft(username, roomId);
                return (timeLeft != null) ? ResponseEntity.status(HttpStatus.OK).body(timeLeft) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                Integer timeLeft = multiplayerGameService.getTimeLeft(username, roomId);
                return (timeLeft != null) ? ResponseEntity.status(HttpStatus.OK).body(timeLeft) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/{timeLeft}/setTimeLeft")
    public ResponseEntity<Object> setTimeLeft(@PathVariable("username") String username, @PathVariable("gameType") String gameType,
                                              @PathVariable("roomId") String roomId, @PathVariable("timeLeft") Integer timeLeft) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                singlePlayerGameService.setTimeLeft(username, roomId, timeLeft);
                return (timeLeft != null) ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                multiplayerGameService.setTimeLeft(username, roomId, timeLeft);
                return (timeLeft != null) ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
