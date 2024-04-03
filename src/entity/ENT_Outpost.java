package entity;

import faction.Faction;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class ENT_Outpost extends Building {
    public ENT_Outpost(GamePanel gp, Faction faction) {
        super(gp, faction);
        name = "Outpost";
        images[0] = setup("/entity/tile_entity/hut_building",1);
        health = 75;
        maxHealth = health;
        reIndex = -1;
        menuType = 1;
        powerVal = 3;
    }

    public BufferedImage image() {
        return images[frame];
    }
}
