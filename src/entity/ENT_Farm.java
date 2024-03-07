package entity;

import faction.Faction;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class ENT_Farm extends Building {
    public ENT_Farm(GamePanel gp, Faction faction) {
        super(gp, faction);
        name = "Farm";
        image = setup("/entity/farm",1);
        health = 20;
    }

    public BufferedImage image() {
        return image;
    }
}
