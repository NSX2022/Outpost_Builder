package entity;

import faction.Faction;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class ENT_Quarry extends Building {
    public ENT_Quarry(GamePanel gp, Faction faction) {
        super(gp, faction);
        name = "Quarry";
        images[0] = setup("/entity/tile_entity/quarry",1);
        images[1] = setup("/entity/tile_entity/quarryA",1);
        health = 75;
        maxHealth = health;
        reIndex = 1;
        menuType = 1;
        powerVal = 2;
        resourceYield = 1;
    }

    public BufferedImage image() {
        return images[frame];
    }
}
