package guess_ddf.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EpisodeController {

    private final EpisodeService service;

    public EpisodeController(EpisodeService service) {
        this.service = service;
    }

    @GetMapping("/episodes")
    String getEpisode(Model model) {
        model.addAttribute("episodes", service.findAll());
        return "episodes";
    }
}
