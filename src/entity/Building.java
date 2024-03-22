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

    //TODO: Bind faction flags to each building

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
            //System.out.println(name + " generated " + resourceYield);
            /*if(gp.keyH.checkDrawTime) {
                gp.ui.addMessage(name + " generated " + resourceYield);
            }
             */
        }
    }
    @Override
    public String toString() {
        return name + level;
    }
}
