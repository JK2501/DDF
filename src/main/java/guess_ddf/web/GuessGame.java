package guess_ddf.web;

import guess_ddf.web.episode.Episode;
import guess_ddf.web.riddle.Riddle;

import java.util.*;

public class GuessGame {
    private Riddle riddle;

    private String cluesType;
    private List<String> clues;
    private LinkedHashSet<Episode> guesses;
    private Map<String, Episode> episodeMap;
    private List<String> cluesDisplayed;
    private boolean gameOver;

    public GuessGame(Riddle riddle, String cluesType, List<String> clues, Map<String, Episode> episodeMap) {
        this.riddle = riddle;
        this.cluesType = cluesType.toLowerCase();
        this.clues = clues;
        this.episodeMap = episodeMap;
        this.cluesDisplayed = new ArrayList<>();
        this.guesses = new LinkedHashSet<>();
        this.gameOver = false;
    }

    public Riddle getRiddle() { return riddle; }
    public void setRiddle(Riddle riddle) { this.riddle = riddle; }

    public String getCluesType() { return cluesType; }
    public void setCluesType(String cluesType) { this.cluesType = cluesType; }

    public List<String> getClues() { return clues; }
    public void setClues(List<String> clues) { this.clues = clues; }

    public LinkedHashSet<Episode> getGuesses() { return guesses; }
    public void setGuesses(LinkedHashSet<Episode> guesses) { this.guesses = guesses; }

    public List<Episode> getGuessesReversed() {
        List<Episode> reversedGuesses = new ArrayList<>(this.guesses);
        Collections.reverse(reversedGuesses);
        return reversedGuesses;
    }

    public Map<String, Episode> getEpisodeMap() { return episodeMap; }
    public void setEpisodeMap(Map<String, Episode> episodeMap) { this.episodeMap = episodeMap; }

    public List<Episode> getEpisodes() {
        return episodeMap.values().stream().sorted(Comparator.comparing(Episode::getNumber)).toList();
    }

    public List<String> getCluesDisplayed() { return cluesDisplayed; }
    public void setCluesDisplayed(List<String> cluesDisplayed) { this.cluesDisplayed = cluesDisplayed; }

    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }
}
