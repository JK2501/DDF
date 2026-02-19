package guess_ddf.web.clues;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "Clues")
public class Clues {

    @Id
    private String id;
    private ArrayList<String> emojis;

    public Clues() {}

    public Clues(String id, ArrayList<String> emojis) {
        this.id = id;
        this.emojis = emojis;
    }

    public String getId() { return id; }
    public void setId(String id) {}

    public ArrayList<String> getEmojis() { return emojis; }
    public void setEmojis(ArrayList<String> emojis) { this.emojis = emojis; }
}