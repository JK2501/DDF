package guess_ddf.web.riddle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.rmi.server.ObjID;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class RiddleService {

    private final ZoneId DE = ZoneId.of("Europe/Berlin");
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

    private long generateBaseSeed(){
        return LocalDate.now(DE).toEpochDay();
    }

    public Riddle generateDailyRiddle1() {
        long randomSeed = new Random(this.generateBaseSeed() ^ 0x9E3779B97F4A7C15L).nextLong();
        return this.generateRiddle(randomSeed);
    }

    public Riddle generateDailyRiddle2() {
        long randomSeed = new Random(this.generateBaseSeed() ^ 0xC2B2AE3D27D4EB4FL).nextLong();
        return this.generateRiddle(randomSeed);
    }

    public Riddle generateRandomRiddle(){
        long seed = System.currentTimeMillis();
        return this.generateRiddle(seed);
    }
}
