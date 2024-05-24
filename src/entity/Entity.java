package entity;

import environment.LightSource;
import faction.Faction;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.IOException;
import java.util.Objects;

public class Entity {
    public volatile int worldX, worldY;
    public int speed;
    public ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

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
    //default landClaim (territory, hut, fortress have larger)
    public Rectangle landClaim = new Rectangle(-192, -192, 192, 192);

    public boolean collisionOn = false;
    public BufferedImage[] images = new BufferedImage[99];
    public String name;
    public boolean menuOn = false;
    public int level = 0;
    public int reIndex = -1;
    public int resourceYield = 0;
    public int maxHealth;
    public int health = 99999999;
    public int resourcesGained = 0;
    public int frame = 0;

    public Faction faction;

    GamePanel gp;

    public LightSource lightSource = new LightSource(0,0,0,null);

    public Entity(GamePanel gp) {
        this.gp = gp;
        lightSource.circleSize = 360;
        lightSource.worldX = worldX;
        lightSource.worldY = worldY;
        lightSource.gp = gp;
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
            g2.drawImage(images[frame], screenX, screenY, null);
        }

        if (this instanceof Build_Preview) {
            g2.setColor(Color.white);
            g2.drawRect(((Build_Preview) this).territoryCheck.x, ((Build_Preview) this).territoryCheck.y, ((Build_Preview) this).territoryCheck.width, ((Build_Preview) this).territoryCheck.height);
        }

        if(gp.keyH.checkDrawTime) {
            g2.setColor(Color.magenta);
            g2.drawRect(clickArea.x, clickArea.y, clickArea.width, clickArea.height);
        }

        if(menuOn) {
            if(gp.ui.buildMenu && !gp.ui.power_menu){
                gp.ui.drawMenu(this);
            }
            g2.setColor(Color.white);
            g2.drawRect(clickArea.x, clickArea.y, clickArea.width, clickArea.height);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20f));


            if(this instanceof ENT_Tree) {
                g2.setColor(Color.pink);
                g2.drawRect(((ENT_Tree) this).detectionArea.x, ((ENT_Tree) this).detectionArea.y, ((ENT_Tree)this).detectionArea.width, ((ENT_Tree)this).detectionArea.height);
            }
            g2.setColor(Color.white);
            //Also add for other entities with detection areas
        }
        if(menuOn || health < maxHealth){
            if(health >= maxHealth/1.2) {
                g2.setColor(Color.green);
            }else if(health >= 2) {
                g2.setColor(Color.yellow);
            }else{
                g2.setColor(Color.red);
            }
            g2.drawString(String.valueOf(health), clickArea.x + gp.tileSize/2, clickArea.y + gp.tileSize);
        }
        if(faction != null) {
            //Draw first letter of faction name in Faction Color
            char toDraw = faction.name.charAt(0);
            g2.setColor(faction.factionColor);
            g2.setFont(gp.ui.pixelText16b);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 22f));
            g2.drawString(String.valueOf(toDraw), screenX + gp.tileSize/2, screenY + gp.tileSize/2);
        }
    }

    public int largest()
    {
        int max = 0;

        for (int i = 0; i < images.length; i++){
            if (images[i] != null) {
                max = i;
            }
        }

        return max;
    }

    public void nextFrame() {
        if(frame < largest()) {
            frame++;
        }else{
            frame = 0;
        }
    }

    public BufferedImage getImage() {
        return images[frame];
    }

    public void setImage(BufferedImage image) {
        images[frame] = image;
    }
}