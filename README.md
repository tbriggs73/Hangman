# Java Hangman API 

Task completed by Trevor Briggs on 31st May 2023


## Changes made to Game class

The following methods have been created in the Game class to enable the retrieval and updating of Game objects:
- getRemainingGuesses()
- setRemainingGuesses(int remainingGuesses)
- getWord()
- setWord(String word)
- getUnmaskedWord()
- getIncorrectGuesses()
- getStatus()
- setStatus(String status) 

## Changes made to GameController class
1. Addition of a deleteGame() method which removes a valid game from the games HashMap
2. Updated the createGame() method so new games are created with 6 incorrect guesses rather than the original 3
3. Inclusion of meaningful error messages when a game id is not valid
4. Updated the makeGuess() method to include the logic to make the Hangman game work.  The method works as follows:
    - If the retrieved game is valid and has an "In progress" status then a guess is retrieved.
    - The guess is validated to ensure it consists of a single A to Z character.  The game supports guesses made in upper and lower case.
    - If a guessed letter is repeated the user will be presented with a message asking them to guess again.
    - A valid guessed letter will be checked against the letters in the unmasked word.  If there are matched letters then these will be revealed in the masked word.  If no matches are found then the guessed letter is added to the incorrect guesses list and the remaining guesses counter is reduced by 1.
    - If the masked word no longer contains any "_" characters then the word is fully revealed and the game status is set to "Won".
    - If the remaining guesses counter reaches zero then the game is lost and the game status is set to "Lost".
