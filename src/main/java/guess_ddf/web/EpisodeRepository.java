package guess_ddf.web;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface EpisodeRepository extends MongoRepository<Episode, String> {

    @Query("{number: ?0}")
    Episode findByNumber(int number);
}
