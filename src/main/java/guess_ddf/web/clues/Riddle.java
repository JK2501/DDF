package guess_ddf.web.clues;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Riddles")
public class Riddle {

    @Id
    private String id;
    private List<String> emojis;
    private List<String> quote;

    public Riddle() {}

    public Riddle(String id, List<String> emojis, List<String> quote) {
        this.id = id;
        this.emojis = emojis;
        this.quote = quote;
    }

    public String getId() { return id; }
    public void setId(String id) {}

    public List<String> getEmojis() { return emojis; }
    public void setEmojis(List<String> emojis) { this.emojis = emojis; }

    public List<String> getQuote() { return quote; }
    public void setQuote(List<String> quote) { this.quote = quote; }
}