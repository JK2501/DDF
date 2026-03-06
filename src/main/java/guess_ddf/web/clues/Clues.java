package guess_ddf.web.clues;

import java.util.List;
import java.util.Optional;

public class Clues {
    public static List<String> getEmojis(Riddle riddle){
        return Optional.ofNullable(riddle.getEmojis()).orElse(List.of("-", "-", "-", "-"));
    }

    public static List<String> getQuote(Riddle riddle){
        return Optional.ofNullable(riddle.getQuote()).orElse(List.of("-"));
    }
}
