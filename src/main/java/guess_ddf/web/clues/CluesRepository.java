package guess_ddf.web.clues;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.ArrayList;
import java.util.Optional;

public interface CluesRepository extends MongoRepository<Clues, String> {

    @Query(value = "{ '_id':  ?0}", fields = "{ 'emojis': 1, '_id':  0}")
    Optional<CluesEmoji> findByIdEmojisOnly(String id);
}