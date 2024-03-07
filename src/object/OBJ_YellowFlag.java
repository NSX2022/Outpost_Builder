package object;

import main.GamePanel;

import java.awt.image.BufferedImage;

public class OBJ_YellowFlag extends SuperObject {
    GamePanel gp;

    public OBJ_YellowFlag(GamePanel gp) {
        name = "yellow_flag";
        image = setup("yellow_flag", gp, 2);
    }

    public BufferedImage image() {
        return image;
    }
}
