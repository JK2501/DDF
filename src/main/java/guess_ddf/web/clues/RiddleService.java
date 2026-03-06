package guess_ddf.web.clues;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.rmi.server.ObjID;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class RiddleService {

    private final RiddleRepository riddleRepository;

    public RiddleService(RiddleRepository riddleRepository) {
        this.riddleRepository = riddleRepository;
    }

    public List<Riddle> findAll() {
        return riddleRepository.findAll();
    }

    public Riddle getNthRiddle(int n) {
        Riddle emptyRiddle = new Riddle(
                new ObjID().toString(),
                new ArrayList<String>(List.of("-", "-", "-", "-")),
                new ArrayList<String>(List.of("-"))
        );
        Pageable pageable = PageRequest.of(n - 1, 1, Sort.by(Sort.Direction.ASC, "id"));
        Page<Riddle> page = riddleRepository.findAll(pageable);
        return page.hasContent() ? page.getContent().get(0) : emptyRiddle;
    }

    private Riddle generateRiddle(long seed){
        Random rand = new Random(seed);
        int index = rand.nextInt(this.findAll().size());
        return this.getNthRiddle(index);
    }

    public Riddle generateDailyRiddleForToday() {
        long seed = LocalDate.now(ZoneOffset.UTC).toEpochDay();
        return this.generateRiddle(seed);
    }

    public Riddle generateDailyRiddleForTomorrow() {
        long seed = LocalDate.now(ZoneOffset.UTC).plusDays(1).toEpochDay();
        return this.generateRiddle(seed);
    }

    public Riddle generateRandomRiddle(){
        long seed = System.currentTimeMillis();
        return this.generateRiddle(seed);
    }
}
