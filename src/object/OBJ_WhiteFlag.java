package object;

import main.GamePanel;

import java.awt.image.BufferedImage;

public class OBJ_WhiteFlag extends SuperObject {
    GamePanel gp;

    public OBJ_WhiteFlag(GamePanel gp) {
        name = "white_flag";
        image = setup("white_flag", gp, 2);
    }

    public BufferedImage image() {
        return image;
    }
}
