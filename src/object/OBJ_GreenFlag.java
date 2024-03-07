package object;

import main.GamePanel;

import java.awt.image.BufferedImage;

public class OBJ_GreenFlag extends SuperObject {
    GamePanel gp;

    public OBJ_GreenFlag(GamePanel gp) {
        name = "green_flag";
        image = setup("green_flag", gp,2);
    }

    public BufferedImage image() {
        return image;
    }
}
