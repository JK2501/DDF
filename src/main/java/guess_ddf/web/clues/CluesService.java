package guess_ddf.web.clues;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.rmi.server.ObjID;
import java.util.*;

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
        List<String> emptyValues  = new ArrayList<String>(List.of("-", "-", "-", "-"));
        return repository.findByIdEmojisOnly(id).map(CluesEmoji::getEmojis).orElse(emptyValues);
    }

    public List<String> findByIdQuoteOnly(String id) {
        List<String> emptyValues  = new ArrayList<String>(List.of("-"));
        return repository.findByIdQuoteOnly(id).map(CluesQuote::getQuote).orElse(emptyValues);
    }

    public Clues getNth(int n) {
        Clues emptyClue = new Clues(new ObjID().toString(), new ArrayList<String>(List.of("-", "-", "-", "-")), new ArrayList<String>(List.of("-")));
        Pageable pageable = PageRequest.of(n - 1, 1, Sort.by(Sort.Direction.ASC, "id"));
        Page<Clues> page = repository.findAll(pageable);
        return page.hasContent() ? page.getContent().get(0) : emptyClue;
    }
}
