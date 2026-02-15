package guess_ddf.web.episode;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "Episodes")
public class Episode {

    @Id
    private ObjectId id;
    private int number;
    private String title;
    private String author;
    private String radioAuthor;
    private String fullDescription;
    private String shortDescription;
    private LocalDate releaseDate;
    private int length;
    private ArrayList<SpeakingRole> speakingRoles;
    private String cover;

    public Episode() {}

    public Episode(int number, String title, String author, String radioAuthor, String fullDescription, String shortDescription, LocalDate releaseDate, int length, ArrayList<SpeakingRole> speakingRoles, String cover) {
        this.number = number;
        this.title = title;
        this.author = author;
        this.radioAuthor = radioAuthor;
        this.fullDescription = fullDescription;
        this.shortDescription = shortDescription;
        this.releaseDate = releaseDate;
        this.length = length;
        this.speakingRoles = speakingRoles;
        this.cover = cover;
    }

    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public String getNumberAsEpisodeLink() {
        return String.format("/episode/%d", number);
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCombinedTitle() {
        return String.format("%03d_%s", number, title);
    }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getRadioAuthor() { return radioAuthor; }
    public void setRadioAuthor(String radioAuthor) { this.radioAuthor = radioAuthor; }

    public String getFullDescription() { return fullDescription; }
    public void setFullDescription(String fullDescription) { this.fullDescription = fullDescription; }

    public String getShortDescription() { return shortDescription; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public int getLength() { return length; }
    public void setLength(int length) { this.length = length; }

    public ArrayList<SpeakingRole> getSpeakingRoles() { return speakingRoles; }
    public void setSpeakingRoles(ArrayList<SpeakingRole> speakingRoles) { this.speakingRoles = speakingRoles; }

    public String getCover() { return cover; }
    public void setCover(String cover) { this.cover = cover; }

    public String getTitleAsImageSourceLookup(){
        String formatted = String.format("%s/%03d_%s.%s", "/cover", number, title, "jpg").replace(":", "").replace(" ", "%20");
        return formatted;
    }

    public String getSpeakingRolesAsString() {
        ArrayList<String> roles = new ArrayList<>();
        for(SpeakingRole speakingRole : speakingRoles) { roles.add(speakingRole.getRole()); }
        return String.join(", ", roles);
    }

    public String getLengthAsString() {
        int minutes = length / 60000;
        int seconds = (length % 60000) / 1000;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public static boolean isContained(List<Episode> episodes, Episode newEpisode) {
        return episodes.stream().anyMatch(episode -> episode.title.equals(newEpisode.title));
    }

    @Override
    public String toString() {
        return String.format("[%d, %s, %s, %s]", number, title, author, releaseDate);
    }
}
