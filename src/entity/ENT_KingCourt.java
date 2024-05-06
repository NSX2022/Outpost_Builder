package entity;

import faction.Faction;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class ENT_KingCourt extends Building {
    public ENT_KingCourt(GamePanel gp, Faction faction) {
        super(gp, faction);
        name = "King's Court";
        images[0] = setup("/entity/tile_entity/king_court",1);
        health = 200;
        maxHealth = health;
        reIndex = 3;
        resourceYield = 1;
        powerVal = 0;
        menuType = 3;
    }

    public BufferedImage image() {
        return images[frame];
    }
}
