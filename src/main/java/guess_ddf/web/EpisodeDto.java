package guess_ddf.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import tools.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EpisodeDto {

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
    private LocalDate releaseDate;

    @JsonProperty("gesamtdauer")
    private int length;

    private ArrayList<SpeakingRole> speakingRoles;

    private String cover;

    public EpisodeDto() {}

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

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) {
        if(releaseDate == null || releaseDate.isEmpty()) { releaseDate = "1970-01-01"; }
        this.releaseDate = LocalDate.parse(releaseDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public int getLength() { return length; }
    public void setLength(int length) {
        this.length = length;
    }

    @JsonProperty("sprechrollen")
    private void unpackSpeakingRoles(JsonNode nodes) {
        this.speakingRoles = new ArrayList<>();
        for(JsonNode node : nodes) {
            String role = node.get("rolle").asText();
            String speaker = node.get("sprecher").asText();

            String pseudonym = "-";
            if(node.get("pseudonym") != null) { pseudonym = node.get("pseudonym").asText(); }

            SpeakingRole speakingRole = new SpeakingRole(speaker, role, pseudonym);
            speakingRoles.add(speakingRole);
        }
    }

    public ArrayList<SpeakingRole> getSpeakingRoles() { return speakingRoles; }
    public void setSpeakingRoles(ArrayList<SpeakingRole> speakingRoles) {
        this.speakingRoles = speakingRoles;
    }

    @JsonProperty("links")
    private void unpackLinks(JsonNode node) {
        JsonNode coverNode = node.get("cover");

        if (coverNode == null || coverNode.isNull()) {
            return;
        }

        if (coverNode.isArray()) {
            // Case: Map<String, ArrayList> -> take the first element
            this.cover = coverNode.has(0) ? coverNode.get(0).asText() : null;
        } else {
            // Case: Map<String, String> -> take as text
            this.cover = coverNode.asText();
        }
    }

    public String getCover() { return cover; }
    public void setCover(String cover) {
        this.cover = cover;
    }
}
