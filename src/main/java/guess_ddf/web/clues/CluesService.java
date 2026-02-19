package guess_ddf.web.clues;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Clues getNth(int n) {
        Pageable pageable = PageRequest.of(n - 1, 1, Sort.by(Sort.Direction.ASC, "id"));
        Page<Clues> page = repository.findAll(pageable);
        return page.hasContent() ? page.getContent().get(0) : null;
    }
}
