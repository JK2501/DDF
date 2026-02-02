package guess_ddf.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EpisodeController {

    @GetMapping
    String getEpisode(Model model) {
        model.addAttribute("episode", "001-Super-Papagei");
        return "episode";
    }

}
