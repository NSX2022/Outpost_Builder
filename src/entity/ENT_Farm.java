package entity;

import faction.Faction;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class ENT_Farm extends Building {
    public ENT_Farm(GamePanel gp, Faction faction) {
        super(gp, faction);
        name = "Farm";
        images[0] = setup("/entity/tile_entity/farm",1);
        images[1] = setup("/entity/tile_entity/farmA",1);
        health = 20;
        maxHealth = health;
        reIndex = 8;
        menuType = 1;
        resourceYield = 2;
        powerVal = 2;
    }

    public BufferedImage image() {
        return images[frame];
    }
}
