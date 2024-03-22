package object;

import main.GamePanel;

import java.awt.image.BufferedImage;

public class OBJ_Flag extends SuperObject {
    GamePanel gp;
    public BufferedImage[] images = new BufferedImage[10];

    public OBJ_Flag(GamePanel gp) {
        this.gp = gp;
        images[0] = setup("white_flag", gp, 2);
        images[1] = setup("yellow_flag", gp, 2);
        images[2] = setup("red_flag", gp, 2);
        images[3] = setup("blue_flag", gp, 2);
        images[4] = setup("green_flag", gp, 2);
        image = images[0];
    }
}
