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
    - check this
    - check that
    - check the other




