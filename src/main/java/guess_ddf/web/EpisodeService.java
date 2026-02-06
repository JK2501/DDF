package guess_ddf.web;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Profile("setup")
public class EpisodeService {

    private final List<Episode> episodes = new ArrayList<Episode>();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Episode> getEpisodes() {
        return episodes;
    }

    @PostConstruct
    public void readEpisodesFromFile() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("serie.json");
        JsonNode root = mapper.readTree(is);
        JsonNode serieNode = root.get("serie");

        for(JsonNode episodeNode : serieNode) {
            episodes.add(mapper.treeToValue(episodeNode, Episode.class));
        }
    }

    @PreDestroy
    public void writeEpisodesToFile() throws Exception {
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("episodes.json"), episodes);
    }
}
