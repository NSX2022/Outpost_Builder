package entity;

import faction.Faction;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class ENT_Mine extends Building {
    public ENT_Mine(GamePanel gp, Faction faction) {
        super(gp, faction);
        name = "Mine";
        image = setup("/entity/mine",1);
        health = 50;
        reIndex = 5;
        menuType = 1;
        resourceYield = 1;
    }

    public BufferedImage image() {
        return image;
    }
}
