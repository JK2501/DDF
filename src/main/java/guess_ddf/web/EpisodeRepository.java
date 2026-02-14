package guess_ddf.web;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface EpisodeRepository extends MongoRepository<Episode, String> {

    @Query("{number: ?0}")
    Episode findByNumber(int number);

    @Query("{title:  ?0}")
    Episode findByTitle(String title);

    @Query("{'speakingRoles.role': { $all:  ?0} }")
    List<Episode> findByCharacters(ArrayList<String> characters);
}