package guess_ddf.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EpisodeController {

    private final EpisodeService service;

    public EpisodeController(EpisodeService service) {
        this.service = service;
    }

    @GetMapping("/episodes")
    String getEpisodes(Model model) {
        model.addAttribute("episodes", service.findAll());
        return "episodes";
    }

    @GetMapping("/episode/{number}")
    String getEpisode(@PathVariable int number, Model model) {
        model.addAttribute("episode", service.findByNumber(number));
        return "episode";
    }
}
