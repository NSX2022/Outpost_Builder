package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ENT_Boulder extends Entity{

    //detect if the Mine building is next to a boulder
    public Rectangle detectionArea = new Rectangle(-48, -48, 96, 96);
    public int detectionAreaDefaultX, detectionAreaDefaultY;

    public ENT_Boulder(GamePanel gp) {
        super(gp);
        name = "Boulder";
        health = 300;
        images[0] = setup("/entity/tile_entity/boulder",1);
        maxHealth = health;
        reIndex = -1;
        menuType = -1;
    }

    public BufferedImage image() {
        return images[0];
    }
}
