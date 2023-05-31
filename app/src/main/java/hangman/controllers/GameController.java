package hangman.controllers;

import com.google.gson.Gson;
import hangman.interfaces.IdentifierGeneration;
import hangman.models.Game;
import hangman.models.Guess;
import spark.Request;
import spark.Response;

import java.util.*;

public class GameController {

    private static HashMap<UUID, Game> games = new HashMap();
    private static List<String> words = Arrays.asList("Banana", "Canine", "Unosquare", "Airport");

    private final IdentifierGeneration identifierGeneration;

    public GameController(IdentifierGeneration identifierGeneration) {
        this.identifierGeneration = identifierGeneration;
    }

    public UUID createGame() {
        var newGameId = identifierGeneration.retrieveIdentifier();
        var newGame = new Game(6, retrieveWord());

        games.put(newGameId, newGame);

        return newGameId;
    }

    public Game getGame(Request request, Response response) {
        var gameArgument = request.params("game_id");
        var gameId = UUID.fromString(gameArgument);
        if (gameId == null || !games.containsKey(gameId)) {
            //response.status(404);
            throw new IllegalArgumentException("Game id is not valid");
            //return null;
        }

        return games.get(gameId);
    }

    public String deleteGame(Request request, Response response) {
        var gameArgument = request.params("game_id");
        var gameId = UUID.fromString(gameArgument);
        if (gameId == null || !games.containsKey(gameId)) {
            response.status(404);
            return "Invalid game id";
        }
        else {
            games.remove(gameId);
        }
        return "Game successfully deleted";
    }

    public Game makeGuess(Request request, Response response) {
        var game = getGame(request, response);

        // check that the game is valid and has in progress status
        if (game != null && game.getStatus().equals("In Progress")) {
            var guess = new Gson().fromJson(request.body(), Guess.class);

            if (guess == null || guess.getLetter() == null || guess.getLetter().length() != 1 || !Character.isLetter(guess.getLetter().charAt(0))) {
                throw new IllegalArgumentException("Guess must be supplied with one A to Z letter");
            }
            String guessedLetter = guess.getLetter().toUpperCase();

            // check that the letter has not been guessed already
            if (!game.getIncorrectGuesses().contains(guessedLetter) && !game.getWord().toUpperCase().contains(guessedLetter)) {

                // check if word contains guessed letter
                if(game.getUnmaskedWord().toUpperCase().contains(guessedLetter)) {

                    char[] updatedWord = game.getWord().toCharArray();

                    for (int i = 0; i < game.getUnmaskedWord().length(); i++) {
                        // if the guessed letter matches a letter in the unmasked word then update word
                        if (game.getUnmaskedWord().toUpperCase().charAt(i) == guessedLetter.charAt(0)) {
                            updatedWord[i] = guessedLetter.charAt(0);
                        }
                    }

                    game.setWord(String.valueOf(updatedWord));

                    // if all letters have been revealed set status to Won
                    if (!game.getWord().contains("_")) {
                        game.setStatus("Won");
                    }
                }
                else {
                    // add incorrect guess to list and reduce remaining guesses by 1
                    game.getIncorrectGuesses().add(guessedLetter);
                    game.setRemainingGuesses(game.getRemainingGuesses() - 1);

                    // update status to Lost when remaining guesses reaches zero
                    if (game.getRemainingGuesses() == 0) {
                        game.setStatus("Lost");
                    }
                }

            }
            else {
                // return error message if letter has been guessed before
                throw new IllegalArgumentException("Letter has already been guessed.  Choose a different letter");
            }

            return game;
        }
        return null;
    }

    private static String retrieveWord() {
        var rand = new Random();
        return words.get(rand.nextInt(words.size() - 1));
    }
}
