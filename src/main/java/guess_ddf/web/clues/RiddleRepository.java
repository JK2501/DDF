package guess_ddf.web.clues;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RiddleRepository extends MongoRepository<Riddle, String> {

}