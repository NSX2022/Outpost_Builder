package entity;

import faction.Faction;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class ENT_Hut extends Building {
    public ENT_Hut(GamePanel gp, Faction faction) {
        super(gp, faction);
        name = "Hut";
        images[0] = setup("/entity/tile_entity/hut_building",1);
        health = 20;
        maxHealth = health;
        reIndex = -1;
        menuType = 1;
    }

    public BufferedImage image() {
        return images[frame];
    }
}
