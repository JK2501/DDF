package guess_ddf.web.clues;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CluesService {

    private final CluesRepository repository;

    public CluesService(CluesRepository repository) {
        this.repository = repository;
    }

    public List<Clues> findAll() {
        return repository.findAll();
    }

    public List<String> findByIdEmojisOnly(String id) {
        return repository.findByIdEmojisOnly(id).map(CluesEmoji::getEmojis).orElse(Collections.emptyList());
    }

}
