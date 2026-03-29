package guess_ddf.web.sprite;

import org.springframework.stereotype.Component;

@Component
public class SpriteUtil {

    private static final int TILE_SIZE = 68;

    private static final String[] SPRITES = {
            "/cover/tile_00.webp",
            "/cover/tile_01.webp",
            "/cover/tile_02.webp"
    };

    public String getSpriteURL(int number) {
        int d3 = number / 100; // hundreds digit
        return d3 < SPRITES.length ? SPRITES[d3] : SPRITES[0];
    }

    public int getXLocOnSprite(int number) {
        int d1 = number % 10; // ones digit
        return -TILE_SIZE * d1;
    }

    public int getYLocOnSprite(int number) {
        int d2 = (number / 10) % 10; // tens digit
        return -TILE_SIZE * d2;
    }

    public String getCombinedStyling(int number) {
        int x = getXLocOnSprite(number);
        int y = getYLocOnSprite(number);

        return "background: url(" + getSpriteURL(number) + "); " +
                "background-position: " + x + "px " + y + "px";
    }
}