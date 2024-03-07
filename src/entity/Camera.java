package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Camera extends Entity {
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Camera (GamePanel gp, KeyHandler keyH) {

        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize / 3) + 10;
        screenY = gp.screenHeight/2 - (gp.tileSize / 3);

        solidArea = new Rectangle();
        solidArea.x = 6; //20
        solidArea.y = 6; //20
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 4;
        solidArea.height = 4;
        //1px * 6 and perfect center on the Camera cross :)

        setDefaultValues();
        getPlayerImage();
    }
    public void getPlayerImage(){

        up1 = setup("/camera/up1", 3);
        up2 = setup("/camera/up2", 3);
        down1 = setup("/camera/down1", 3);
        down2 = setup("/camera/down2", 3);
        left1 = setup("/camera/left1", 3);
        left2 = setup("/camera/left2", 3);
        right1 = setup("/camera/right1", 3);
        right2 = setup("/camera/right2", 3);
    }
    public void setDefaultValues() {
        worldX = gp.tileSize * gp.maxWorldRow / 2;
        worldY = gp.tileSize * gp.maxWorldCol / 2;
        speed = 6;
        direction = "down";
    }

    public void update() {
        if(keyH.upPressed || keyH.downPressed ||
                keyH.leftPressed || keyH.rightPressed) {
            if(keyH.upPressed) {
                direction = "up";
            }
            else if(keyH.downPressed) {
                direction = "down";
            }
            else if(keyH.leftPressed) {
                direction = "left";
            }
            else if(keyH.rightPressed) {
                direction = "right";
            }

            //check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //check obj collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //if false, player moves
            if(collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            /*spriteCounter++;
            if(spriteCounter > 12) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                }
                else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

             */
        }
    }
    public void pickUpObject(int i) {

    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch(direction) {
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, null);

        //hitbox debug
        if(keyH.checkDrawTime) {
            g2.setColor(Color.PINK);
            g2.fillRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }
}
