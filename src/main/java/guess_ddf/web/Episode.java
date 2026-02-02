package guess_ddf.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Episode {

    @JsonProperty("nummer")
    private int number;

    @JsonProperty("titel")
    private String title;

    @JsonProperty("autor")
    private String author;

    @JsonProperty("hörspielskriptautor")
    private String radioAuthor;

    @JsonProperty("gesamtbeschreibung")
    private String fullDescription;

    @JsonProperty("beschreibung")
    private String shortDescription;

    @JsonProperty("veröffentlichungsdatum")
    private String releaseDate;

    @JsonProperty("gesamtdauer")
    private int length;

    @JsonProperty("sprechrollen")
    private ArrayList<SpeakingRole> speakingRoles;

    public Episode() {}

    public int getNumber() { return number; }
    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() { return author; }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRadioAuthor() { return radioAuthor; }
    public void setRadioAuthor(String radioAuthor) {
        this.radioAuthor = radioAuthor;
    }

    public String getFullDescription() { return fullDescription; }
    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getShortDescription() { return shortDescription; }
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getLength() { return length; }
    public void setLength(int length) {
        this.length = length;
    }

    public ArrayList<SpeakingRole> getSpeakingRoles() { return speakingRoles; }
    public void setSpeakingRoles(ArrayList<SpeakingRole> speakingRoles) {
        this.speakingRoles = speakingRoles;
    }

    public String getSpeakingRolesAsString() {
        ArrayList<String> roles = new ArrayList<>();
        for(SpeakingRole speakingRole : speakingRoles) { roles.add(speakingRole.getRole()); }
        return String.join(", ", roles);
    }

    @Override
    public String toString() {
        return "Episode [number=" + number + ", title=" + title + ", author=" + author + "]";
    }
}
