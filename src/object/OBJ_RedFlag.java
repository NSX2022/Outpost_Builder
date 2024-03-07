package object;

import main.GamePanel;

import java.awt.image.BufferedImage;

public class OBJ_RedFlag extends SuperObject {
    GamePanel gp;

    public OBJ_RedFlag(GamePanel gp) {
        name = "red_flag";
        image = setup("red_flag", gp, 2);
    }

    public BufferedImage image() {
        return image;
    }
}
