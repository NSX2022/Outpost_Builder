package entity;

import faction.Faction;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class ENT_Mine extends Building {
    public ENT_Mine(GamePanel gp, Faction faction) {
        super(gp, faction);
        name = "Mine";
        images[0] = setup("/entity/tile_entity/mine",1);
        images[1] = setup("/entity/tile_entity/mineA", 1);
        health = 50;
        maxHealth = health;
        reIndex = 5;
        menuType = 1;
        resourceYield = 1;
        powerVal = 2;
    }

    public BufferedImage image() {
        return images[frame];
    }
}
