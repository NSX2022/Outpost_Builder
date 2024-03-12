package entity;

import faction.Faction;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Building extends Entity{

    public Faction faction;


    enum buildingState {
        NORMAL,
        DAMAGED,
        DEFEATED
    }

    public Building(GamePanel gp, Faction faction) {
        super(gp);
        this.faction = faction;
    }

    public BufferedImage setup(String imageName, GamePanel gp) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/entity/"+imageName+".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }
    public void genResources() {
        if(reIndex > -1 /*&& reIndex < faction.resources.length*/) {
            faction.resources[reIndex] += resourceYield;
            resourcesGained += resourceYield;
        }
    }
}
