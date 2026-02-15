package guess_ddf.web.episode;

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
}
