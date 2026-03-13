package guess_ddf.web.riddle;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RiddleRepository extends MongoRepository<Riddle, String> {

}