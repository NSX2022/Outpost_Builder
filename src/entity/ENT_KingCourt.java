package entity;

import faction.Faction;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class ENT_KingCourt extends Building {
    public ENT_KingCourt(GamePanel gp, Faction faction) {
        super(gp, faction);
        name = "King\'s Court";
        image = setup("/entity/king_court",1);
        health = 200;
        reIndex = -1;
        menuType = 3;
    }

    public BufferedImage image() {
        return image;
    }
}
