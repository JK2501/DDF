package guess_ddf.web;

import guess_ddf.web.clues.Clues;
import guess_ddf.web.riddle.Riddle;
import guess_ddf.web.riddle.RiddleService;

import java.util.Arrays;
import java.util.List;

public enum GuessType {

    EMOJIS {
        @Override
        public Riddle generateRiddle(RiddleService service) {
            return service.generateDailyRiddleForToday();
        }

        @Override
        public List<String> generateClues(Riddle riddle) {
            return Clues.getEmojis(riddle);
        }
    },

    QUOTE {
        @Override
        public Riddle generateRiddle(RiddleService service) {
            return service.generateDailyRiddleForTomorrow();
        }

        @Override
        public List<String> generateClues(Riddle riddle) {
            return Clues.getQuote(riddle);
        }
    };

    public static GuessType fromString(String value) {
        return Arrays.stream(values())
                .filter(t -> t.name().equalsIgnoreCase(value))
                .findFirst().orElse(null);
    }

    public abstract Riddle generateRiddle(RiddleService service);
    public abstract List<String> generateClues(Riddle riddle);
}
