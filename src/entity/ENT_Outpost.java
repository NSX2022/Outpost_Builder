package entity;

import faction.Faction;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class ENT_Outpost extends Building {
    public ENT_Outpost(GamePanel gp, Faction faction) {
        super(gp, faction);
        name = "Hut";
        images[0] = setup("/entity/tile_entity/hut_building",1);
        health = 75;
        maxHealth = health;
        reIndex = -1;
        menuType = 1;
        powerVal = 3;
        resCost = new int[]{0,2,2,0,0,1,0,0,4};
    }

    public BufferedImage image() {
        return images[frame];
    }
}
