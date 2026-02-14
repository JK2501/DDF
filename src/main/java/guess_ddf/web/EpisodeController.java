package guess_ddf.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EpisodeController {

    private final EpisodeService service;

    public EpisodeController(EpisodeService service) {
        this.service = service;
    }

    @GetMapping("/episodes")
    public String getEpisodes(Model model) {
        model.addAttribute("episodes", service.findAll());
        return "episodes";
    }

    @GetMapping("/episode/{number}")
    public String getEpisode(@PathVariable int number, Model model) {
        model.addAttribute("episode", service.findByNumber(number));
        return "episode";
    }

    @GetMapping("/guess")
    public String showInitialGuessPage(HttpSession session, Model model) {
        ArrayList<String> hints =  new ArrayList<String>(List.of("Geeks","for","Geeks"));
        session.setAttribute("hints", hints);
        model.addAttribute("hints", hints);
        return "guess";
    }

    @PostMapping("/guess")
    public String showFollowupGuessPage(@RequestParam String guess, HttpSession session, Model model) {
        // first guess
        if(session.getAttribute("guesses") == null) { session.setAttribute("guesses",  new ArrayList<Episode>()); }

        // get hints and prev. guesses
        ArrayList<String> hints = (ArrayList<String>) session.getAttribute("hints");
        ArrayList<Episode> guessedEpisodes = (ArrayList<Episode>) session.getAttribute("guesses");

        // find episode matching the guess
        Episode guessedEpisode = service.findByTitle(guess);

        // add episode if not already contained
        if(guessedEpisode != null && !Episode.isContained(guessedEpisodes, guessedEpisode)) {
            guessedEpisodes.add(guessedEpisode);
        }

        // re-render page
        session.setAttribute("guesses", guessedEpisodes);
        model.addAttribute("hints", hints);
        model.addAttribute("guesses", guessedEpisodes);
        return "guess";
    }
}
