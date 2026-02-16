package guess_ddf.web;

import guess_ddf.web.clues.CluesEmoji;
import guess_ddf.web.episode.Episode;
import guess_ddf.web.episode.EpisodeService;
import guess_ddf.web.clues.CluesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@SuppressWarnings("unchecked")
public class GuessController {
    private final EpisodeService episodeService;
    private final CluesService cluesService;

    private final String riddleId = "698a1681a7dd566eba2126f6";

    public GuessController(EpisodeService episodeService, CluesService cluesService) {
        this.episodeService = episodeService;
        this.cluesService = cluesService;
    }

    @GetMapping("/guess")
    public String showInitialGuessPage(HttpSession session, Model model) {
        // retrieve clues from db
        List<String> clues = cluesService.findByIdEmojisOnly(riddleId);

        // add clues, guessed and guesses to session storage
        session.setAttribute("clues", clues);
        session.setAttribute("guesses", new ArrayList<Episode>());
        session.setAttribute("guessed", false);

        model.addAttribute("clues", clues.subList(0, 1));
        return "guess";
    }

    @PostMapping("/guess")
    public String showFollowupGuessPage(@RequestParam String guess, HttpSession session, Model model) {
        // get clues, prev. guesses and number of clues to be displayed
        List<String> clues = (List<String>) session.getAttribute("clues");
        List<Episode> guessedEpisodes = (List<Episode>) session.getAttribute("guesses");
        boolean guessed = (boolean) session.getAttribute("guessed");

        int dClues = 2;
        if(!guessedEpisodes.isEmpty()){
            dClues = Math.min(guessedEpisodes.size() + 2, clues.size());
        }

        // find episode matching the guess
        Episode guessedEpisode = episodeService.findByTitle(guess);

        // add episode if not null and not already contained
        if(!guessed) {
            if (guessedEpisode != null && !Episode.isContained(guessedEpisodes, guessedEpisode)) {
                guessedEpisodes.add(guessedEpisode);
                if (guessedEpisode.getId().toString().equals(riddleId)) {
                    dClues = clues.size();
                    session.setAttribute("guessed", true);
                }
            }
        }
        else {
            dClues = clues.size();
        }

        session.setAttribute("guesses", guessedEpisodes);

        // re-render page
        model.addAttribute("clues", clues.subList(0, dClues));
        model.addAttribute("guesses", guessedEpisodes);
        model.addAttribute("riddleId", riddleId);
        return "guess";
    }
}
