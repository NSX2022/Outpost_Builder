package entity;

import faction.Faction;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class ENT_Library extends Building {
    public ENT_Library(GamePanel gp, Faction faction) {
        super(gp, faction);
        name = "Library";
        images[0] = setup("/entity/tile_entity/library",1);
        health = 50;
        maxHealth = health;
        reIndex = -1;
        menuType = 1;
        powerVal = 6;
    }

    public BufferedImage image() {
        return images[frame];
    }
}
