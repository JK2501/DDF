package guess_ddf.web;

import guess_ddf.web.clues.Clues;
import guess_ddf.web.clues.CluesEmoji;
import guess_ddf.web.episode.Episode;
import guess_ddf.web.episode.EpisodeService;
import guess_ddf.web.clues.CluesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Supplier;

@Controller
@SuppressWarnings("unchecked")
public class GuessController {
    private final EpisodeService episodeService;
    private final CluesService cluesService;

    private String riddle = "";
    private final Map<String, Supplier<List<String>>> clueMethods = Map.of(
            "guessByEmojis", this::guessByEmojis,
            "guessByQuote", this::guessByQuote
    );

    public GuessController(EpisodeService episodeService, CluesService cluesService) {
        this.episodeService = episodeService;
        this.cluesService = cluesService;
    }

    @GetMapping("/{type}")
    public String showInitialGuessPage(@PathVariable String type, HttpSession session, Model model) {
        this.riddle = generateDailyRiddle();

        Supplier<List<String>> clueMethod = clueMethods.get(type);

        if (clueMethod == null) { throw new ResponseStatusException(HttpStatus.NOT_FOUND); }

        // retrieve clues from db
        List<String> clues = clueMethod.get();

        // add clues, guessed and guesses to session storage
        session.setAttribute("clues", clues);
        session.setAttribute("iClues", 1);
        session.setAttribute("guesses", new ArrayList<Episode>());
        session.setAttribute("guessed", false);

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
                if (guessedEpisode.getId().toString().equals(riddle)) {
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

        model.addAttribute("guesses", guessedEpisodes);
        model.addAttribute("riddleId", riddle);
        model.addAttribute("episodes", episodeService.findAll());
        return "guess";
    }

    private String generateDailyRiddle(){
        long seed = LocalDate.now(ZoneOffset.UTC).toEpochDay();
        Random rand = new Random(seed);
        int index = rand.nextInt(cluesService.findAll().size());
        Clues riddle = cluesService.getNth(index);
        return riddle.getId().toString();
    }

    private List<String> guessByEmojis() {
        return cluesService.findByIdEmojisOnly(riddle);
    }

    private List<String> guessByQuote() {
        return cluesService.findByIdQuoteOnly(riddle);
    }
}
