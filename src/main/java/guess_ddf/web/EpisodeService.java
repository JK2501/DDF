package guess_ddf.web;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class EpisodeService {

    private final List<Episode> episodes = new ArrayList<Episode>();

    public List<Episode> getEpisodes() {
        return episodes;
    }

    @PostConstruct
    public void loadEpisodes() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        InputStream is = getClass().getClassLoader().getResourceAsStream("serie.json");
        JsonNode root = mapper.readTree(is);
        JsonNode serieNode = root.get("serie");

        for(JsonNode episodeNode : serieNode) {
            episodes.add(mapper.treeToValue(episodeNode, Episode.class));
        }
    }
}
