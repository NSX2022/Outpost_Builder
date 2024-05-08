package entity;

import main.GamePanel;


import java.awt.*;
import java.awt.image.BufferedImage;


public class Build_Preview extends Entity{

    public Rectangle territoryCheck;

    public Build_Preview(GamePanel gp, BufferedImage image){
        super(gp);

        images[0] = grayScaleImage(image);
        //un-clickable
        clickArea = new Rectangle(0, 0, 0, 0);
        landClaim = new Rectangle(0, 0, 0, 0);
        territoryCheck = new Rectangle(0,0,48,48);

    }

    public void setImage(BufferedImage image){
        images[0] = grayScaleImage(image);
    }

    public BufferedImage grayScaleImage(BufferedImage image){
        BufferedImage toRet = image;
        toRet = op.filter(toRet, null);
        return toRet;
    }
}
