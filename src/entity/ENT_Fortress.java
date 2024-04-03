package entity;

import faction.Faction;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class ENT_Fortress extends Building {
    public ENT_Fortress(GamePanel gp, Faction faction) {
        super(gp, faction);
        name = "Fortress";
        images[0] = setup("/entity/tile_entity/building_fortress",1);
        health = 150;
        maxHealth = health;
        reIndex = -1;
        menuType = 1;
        powerVal = 6;
    }

    public BufferedImage image() {
        return images[frame];
    }
}
