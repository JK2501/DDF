package guess_ddf.web;

import java.util.HashMap;
import java.util.Map;

public class GuessGameSession {
    private Map<String, GuessGame> games = new HashMap<>();

    public GuessGame getGame(String type){ return games.get(type.toLowerCase()); }
    public void setGame(String type, GuessGame game){ games.put(type.toLowerCase(), game); }
    public Map<String, GuessGame> getGames() { return games; }
}
