package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ENT_Tree extends Entity{

    //detect if the Lumberyard building is next to a tree
    public Rectangle detectionArea = new Rectangle(-48, -48, 96, 96);
    public int detectionAreaDefaultX, detectionAreaDefaultY;

    public ENT_Tree(GamePanel gp) {
        super(gp);
        name = "Tree";
        health = 100;
        image = setup("/entity/tile_entity/tree_entity",1);
        maxHealth = health;
        reIndex = -1;
        menuType = -1;
    }

    public BufferedImage image() {
        return image;
    }
}
