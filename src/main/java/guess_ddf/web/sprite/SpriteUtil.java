package guess_ddf.web.sprite;

import org.springframework.stereotype.Component;

@Component
public class SpriteUtil {

    private final int X = 68;
    private final int Y = 68;

    private final String SPRITE_0 = "/cover/tile_00.webp";
    private final String SPRITE_1 = "/cover/tile_01.webp";
    private final String SPRITE_2 = "/cover/tile_02.webp";

    public String getSpriteURL(int number) {
        String numberAsString = String.format("%03d", number);
        int d3 = numberAsString.charAt(0) - '0';
        if(d3 == 0){ return SPRITE_0; }
        if(d3 == 1){ return SPRITE_1; }
        if(d3 == 2){ return SPRITE_2; }
        return SPRITE_0;
    }

    public int getXLocOnSprite(int number) {
        String numberAsString = String.format("%03d", number);
        int d1 = numberAsString.charAt(2) - '0';
        return (-1) * (Y) * (d1);
    }

    public int getYLocOnSprite(int number) {
        String numberAsString = String.format("%03d", number);
        int d2 = numberAsString.charAt(1) - '0';
        return (-1) * (Y) * (d2);
    }

    public String getCombinedStyling(int number) {
        return String.format("background: url(%s); background-position: %dpx %dpx",
                getSpriteURL(number),
                getXLocOnSprite(number),
                getYLocOnSprite(number)
        );
    }

}
