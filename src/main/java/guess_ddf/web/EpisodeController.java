package guess_ddf.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EpisodeController {

    private final EpisodeService episodeService;

    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @GetMapping
    String getEpisode(Model model) {
        model.addAttribute("episodes", episodeService.getEpisodes());
        return "episodes";
    }

}
