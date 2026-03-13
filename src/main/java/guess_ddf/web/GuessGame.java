package guess_ddf.web;

import guess_ddf.web.episode.Episode;
import guess_ddf.web.riddle.Riddle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuessGame {
    private Riddle riddle;

    private String cluesType;
    private List<String> clues;
    private List<Episode> guesses;
    private List<Episode> episodes;
    private List<String> cluesDisplayed;
    private boolean gameOver;

    public GuessGame(Riddle riddle, String cluesType, List<String> clues, List<Episode> episodes) {
        this.riddle = riddle;
        this.cluesType = cluesType.toLowerCase();
        this.clues = clues;
        this.episodes = episodes;
        this.cluesDisplayed = new ArrayList<>();
        this.guesses = new ArrayList<>();
        this.gameOver = false;
    }

    public Riddle getRiddle() { return riddle; }
    public void setRiddle(Riddle riddle) { this.riddle = riddle; }

    public String getCluesType() { return cluesType; }
    public void setCluesType(String cluesType) { this.cluesType = cluesType; }

    public List<String> getClues() { return clues; }
    public void setClues(List<String> clues) { this.clues = clues; }

    public List<Episode> getGuesses() { return guesses; }
    public void setGuesses(List<Episode> guesses) { this.guesses = guesses; }

    public List<Episode> getGuessesReversed() {
        List<Episode> reversedGuesses = new ArrayList<>(this.guesses);
        Collections.reverse(reversedGuesses);
        return reversedGuesses;
    }

    public List<Episode> getEpisodes() { return episodes; }
    public void setEpisodes(List<Episode> episodes) { this.episodes = episodes; }

    public List<String> getCluesDisplayed() { return cluesDisplayed; }
    public void setCluesDisplayed(List<String> cluesDisplayed) { this.cluesDisplayed = cluesDisplayed; }

    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }
}
