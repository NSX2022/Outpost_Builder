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
    public int resourceYield;
    public int worldX, worldY;
    public String name;
    public int health;
    public int menuType = 0;

    MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    };

    //Menu when each building is clicked
    //0 = empty menu 1 = Stats (npc base), 2 = ui for player base

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


}
