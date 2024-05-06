package entity;

import faction.Faction;
import main.GamePanel;
import main.UtilityTool;
import object.OBJ_Flag;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Building extends Entity{

    public enum buildingState {
        NORMAL,
        DAMAGED,
        DEFEATED
    }

    public OBJ_Flag flag = new OBJ_Flag(gp);
    //Index 0 = gold, 1 = stone, 2 = lumber, 3 = money, 4 = smokeleaf, 5 = iron, 6 = silk, 7 = gem, 8 = wheat
    public int powerVal = 0;

    public Building(GamePanel gp, Faction faction) {
        super(gp);
        this.faction = faction;
    }

    public BufferedImage setup(String imageName, GamePanel gp) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/entity/tile_entity/"+imageName+".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }
    public void genResources() {
        if(reIndex > -1) {
            faction.resources[reIndex] += resourceYield;
            resourcesGained += resourceYield;
        }
    }
    @Override
    public String toString() {
        return name + level;
    }
}
