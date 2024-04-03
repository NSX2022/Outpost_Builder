package entity;

import faction.Faction;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class ENT_Lumberyard extends Building {
    public ENT_Lumberyard(GamePanel gp, Faction faction) {
        super(gp, faction);
        name = "Lumberyard";
        images[0] = setup("/entity/tile_entity/logging_camp",1);
        health = 75;
        maxHealth = health;
        reIndex = 2;
        menuType = 1;
        powerVal = 2;
        resourceYield = 1;
    }

    public BufferedImage image() {
        return images[frame];
    }
}
