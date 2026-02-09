package guess_ddf.web;

import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class EpisodeImporter {

    private List<Episode> episodes = new ArrayList<Episode>();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Episode> importEpisodes(String jsonPath) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(jsonPath);
        JsonNode root = this.mapper.readTree(is);
        JsonNode serieNode = root.get("serie");

        List<EpisodeDto> dtos = new ArrayList<>();

        for(JsonNode episodeNode : serieNode) {
            dtos.add(this.mapper.treeToValue(episodeNode, EpisodeDto.class));
        }

        this.episodes = dtos.stream().map(dto -> new Episode(
                dto.getNumber(),
                dto.getTitle(),
                dto.getAuthor(),
                dto.getRadioAuthor(),
                dto.getFullDescription(),
                dto.getShortDescription(),
                dto.getReleaseDate(),
                dto.getLength(),
                dto.getSpeakingRoles(),
                dto.getCover()
        )).toList();

        return this.episodes;
    }

    public void exportEpisodes() {
        this.mapper.writerWithDefaultPrettyPrinter().writeValue(new File("episodes.json"), this.episodes);
    }
}
