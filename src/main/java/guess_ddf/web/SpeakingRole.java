package guess_ddf.web;

public class SpeakingRole {
    private String speaker;
    private String role;
    private String pseudonym = "";

    public SpeakingRole(String speaker, String role, String pseudonym) {
        this.speaker = speaker;
        this.role = role;
        this.pseudonym = pseudonym;
    }

    public String getSpeaker() { return speaker; }
    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getRole() { return role; }
    public void setRole(String role) {
        this.role = role;
    }

    public String getPseudonym() { return pseudonym; }
    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }
}