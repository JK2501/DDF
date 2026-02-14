package guess_ddf.web;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EpisodeService {

    private final EpisodeRepository repository;
    private final EpisodeImporter episodeImporter;

    public EpisodeService(EpisodeRepository repository, EpisodeImporter episodeImporter) {
        this.repository = repository;
        this.episodeImporter = episodeImporter;
    }

    @PostConstruct
    public void databaseCheck() {

        // database is not empty
        if (repository.count() != 0) {
            return;
        }

        // database is empty
        System.out.println(String.format("======= Populating Database from: %s", "JSON"));
        List<Episode> episodes = episodeImporter.importEpisodes("serie.json");
        repository.saveAll(episodes);
        System.out.println(String.format("======= Inserted %d episodes", repository.count()));

        // export episodes to JSON
        episodeImporter.exportEpisodes();
        System.out.println("======= Exporting episodes to JSON");
    }

    public List<Episode> findAll() {
        return repository.findAll();
    }

    public Episode findByNumber(int number) {
        return repository.findByNumber(number);
    }

    public Episode findByTitle(String title) {
        return repository.findByTitle(title);
    }

    public List<Episode> findByCharacters(ArrayList<String> characters) {
        return repository.findByCharacters(characters);
    }

}
