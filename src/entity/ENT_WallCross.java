package entity;

import faction.Faction;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ENT_WallCross extends Building {
    public ENT_WallCross(GamePanel gp, Faction faction) {
        super(gp, faction);
        name = "Wall";
        images[0] = setup("/entity/tile_entity/wall_cross",1);
        health = 225;
        maxHealth = health;
        //Has no Territory
        landClaim = new Rectangle(0,0,0,0);
        reIndex = -1;
        menuType = 0;
        powerVal = 0;
        //Adjust solidArea if Citizen entities can walk through it
    }

    public BufferedImage image() {
        return images[frame];
    }
}
