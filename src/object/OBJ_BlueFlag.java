package object;

import main.GamePanel;

import java.awt.image.BufferedImage;

public class OBJ_BlueFlag extends SuperObject {
    GamePanel gp;

    public OBJ_BlueFlag(GamePanel gp) {
        name = "blue_flag";
        image = setup("blue_flag", gp,2);
    }

    public BufferedImage image() {
        return image;
    }
}
