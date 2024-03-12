package entity;

import faction.Faction;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class ENT_Fortress extends Building {
    public ENT_Fortress(GamePanel gp, Faction faction) {
        super(gp, faction);
        name = "Fortress";
        image = setup("/entity/building_fortress",1);
        health = 150;
        reIndex = -1;
        menuType = 1;
    }

    public BufferedImage image() {
        return image;
    }
}
