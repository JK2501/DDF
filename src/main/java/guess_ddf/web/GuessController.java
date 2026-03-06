package guess_ddf.web;

import guess_ddf.web.clues.Clues;
import guess_ddf.web.clues.Riddle;
import guess_ddf.web.episode.Episode;
import guess_ddf.web.episode.EpisodeService;
import guess_ddf.web.clues.RiddleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.function.Supplier;

@Controller
@SuppressWarnings("unchecked")
public class GuessController {
    private final EpisodeService episodeService;
    private final RiddleService riddleService;

    private Riddle riddle;

    private final Map<String, Supplier<Riddle>> riddleTypes;
    private final Map<String, Supplier<List<String>>> clueTypes;

    public GuessController(
            EpisodeService episodeService,
            RiddleService riddleService,
            @Value("${spring.application.url[0].prefix}") String emojisURL,
            @Value("${spring.application.url[1].prefix}") String quoteURL
    )
    {
        this.episodeService = episodeService;
        this.riddleService = riddleService;

        this.riddleTypes = Map.of(
                emojisURL, this.riddleService::generateDailyRiddleForToday,
                quoteURL, this.riddleService::generateDailyRiddleForTomorrow
        );
        this.clueTypes = Map.of(
                emojisURL, this::guessByEmojis,
                quoteURL, this::guessByQuote
        );
    }

    @GetMapping("/{type}")
    public String showInitialGuessPage(@PathVariable String type, HttpSession session, Model model) {

        Supplier<List<String>> clueType = clueTypes.get(type);
        Supplier<Riddle> riddleType = riddleTypes.get(type);

        if (clueType == null) { throw new ResponseStatusException(HttpStatus.NOT_FOUND); }

        // retrieve riddle from db
        this.riddle = riddleType.get();
        // retrieve clues from db
        List<String> clues = clueType.get();

        // add attributes to session storage
        session.setAttribute("clues", clues);
        session.setAttribute("iClues", 1);
        session.setAttribute("guesses", new ArrayList<Episode>());
        session.setAttribute("guessed", false);
        session.setAttribute("cluesType", type);

        model.addAttribute("clues", clues.subList(0, 1));
        model.addAttribute("episodes", episodeService.findAll());
        return "guess";
    }

    @PostMapping("/guess")
    public String showFollowupGuessPage(@RequestParam String guess, HttpSession session, Model model) {

        boolean guessed = (boolean) session.getAttribute("guessed");
        List<Episode> guessedEpisodes = (List<Episode>) session.getAttribute("guesses");
        List<String> clues = (List<String>) session.getAttribute("clues");
        int iClues = (int) session.getAttribute("iClues");

        // user already guessed the episode => display all clues; do not add new episodes
        if(guessed) {  model.addAttribute("clues", clues); }
        // user did not guess episode in prev attempt
        else {
            // find episode matching the guess
            Episode guessedEpisode = episodeService.findByTitle(guess);
            // a valid episode
            if (guessedEpisode != null && !Episode.isContained(guessedEpisodes, guessedEpisode)) {
                // add episode to guesses
                guessedEpisodes.add(guessedEpisode);
                // win => display all clues; set guessed true;
                if (guessedEpisode.getId().toString().equals(riddle.getId())) {
                    session.setAttribute("iClues", clues.size());
                    session.setAttribute("guessed", true);
                    model.addAttribute("clues", clues);
                }
                // not win
                else {
                    iClues = Math.min(clues.size(), iClues + 1);
                    session.setAttribute("iClues", iClues);
                    model.addAttribute("clues", clues.subList(0, iClues));
                }
            }
            // not a valid episode => display all prev. revealed clues; no new episodes added
            else { model.addAttribute("clues", clues.subList(0, iClues)); }
        }

        // reverse the list of guesses episodes
        Collections.reverse(guessedEpisodes);

        model.addAttribute("guesses", guessedEpisodes);
        model.addAttribute("riddleId", riddle.getId());
        model.addAttribute("episodes", episodeService.findAll());
        return "guess";
    }

    private List<String> guessByEmojis() {
        return Clues.getEmojis(riddle);
    }

    private List<String> guessByQuote() {
        return Clues.getQuote(riddle);
    }
}
