package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    //Menu when each building is clicked
    //0 = empty menu 1 = Stats (npc base), 2 = ui for player king court, 3 = ai king court, 4 = citizen
    public int menuType = 0;

    //default solidArea for all entities
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    //default clickArea
    public Rectangle clickArea = new Rectangle(0, 0, 48, 48);
    public int clickAreaDefaultX, clickAreaDefaultY;

    public boolean collisionOn = false;
    public BufferedImage image;
    public String name;
    public boolean menuOn = false;
    public int level = 0;
    public int reIndex;
    public int resourceYield;
    public int health;
    public int resourcesGained = 0;

    GamePanel gp;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage setup(String imagePath, int divider) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, gp.tileSize / divider, gp.tileSize / divider);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void draw(Graphics2D g2, GamePanel gp) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        //only render in frame
        if(     worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY  - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(image, screenX, screenY, null);
        }

        if(gp.keyH.checkDrawTime) {
            g2.drawRect(clickArea.x, clickArea.y, clickArea.width, clickArea.height);
        }

        if(menuOn) {
            gp.ui.drawMenu(this);
            g2.drawRect(clickArea.x, clickArea.y, clickArea.width, clickArea.height);
        }
    }
}
