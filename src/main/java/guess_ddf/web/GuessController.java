package guess_ddf.web;

import guess_ddf.web.clues.Clues;
import guess_ddf.web.riddle.Riddle;
import guess_ddf.web.episode.Episode;
import guess_ddf.web.episode.EpisodeService;
import guess_ddf.web.riddle.RiddleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.function.Supplier;

@Controller
@SessionAttributes("game")
public class GuessController {
    private final EpisodeService episodeService;
    private final RiddleService riddleService;

    public GuessController(EpisodeService episodeService, RiddleService riddleService)
    {
        this.episodeService = episodeService;
        this.riddleService = riddleService;
    }

    @GetMapping("/{type}")
    public String showInitialGuessPage(
            @PathVariable String type,
            Model model
    ) {
        GuessType guessType = GuessType.fromString(type);
        if (guessType == null) { throw new ResponseStatusException(HttpStatus.NOT_FOUND); }

        Riddle riddle = guessType.generateRiddle(riddleService);

        System.out.println(riddle.toString());

        List<String> clues = guessType.generateClues(riddle);
        List<Episode> episodes = episodeService.findAll();

        GuessGame game = new GuessGame(riddle, guessType.name(), clues, episodes);
        game.setCluesDisplayed(clues.subList(0, 1));

        model.addAttribute("game", game);
        return "guess";
    }

    @PostMapping("/guess")
    public String showFollowupGuessPage(@RequestParam String guess, @ModelAttribute("game") GuessGame game) {

        Riddle riddle = game.getRiddle();
        List<String> clues = game.getClues();
        List<String> cluesDisplayed = game.getCluesDisplayed();
        List<Episode> guesses = game.getGuesses();

        // user already guessed the episode => display all clues; do not add new episodes
        if(game.isGameOver()) {  game.setCluesDisplayed(clues); }
        // user did not guess episode in prev attempt
        else {
            // find episode matching the guess
            Episode guessedEpisode = episodeService.findByTitle(guess);
            // a valid episode
            if (guessedEpisode != null && !Episode.isContained(guesses, guessedEpisode)) {
                // add episode to guesses
                guesses.add(guessedEpisode);
                game.setGuesses(guesses);
                // win => display all clues; set guessed true;
                if (guessedEpisode.getId().toString().equals(riddle.getId())) {
                    game.setCluesDisplayed(game.getClues());
                    game.setGameOver(true);
                }
                // not win
                else {
                    int index = Math.min(clues.size(), cluesDisplayed.size() + 1);
                    game.setCluesDisplayed(clues.subList(0, index));
                }
            }
        }
        return "guess";
    }
}
