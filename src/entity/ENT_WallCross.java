package entity;

import faction.Faction;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class ENT_WallCross extends Building {
    public ENT_WallCross(GamePanel gp, Faction faction) {
        super(gp, faction);
        name = "Wall";
        image = setup("/entity/wall_cross",1);
        health = 225;
    }

    public BufferedImage image() {
        return image;
    }
}
