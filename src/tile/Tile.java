package tile;

import faction.Faction;
import main.GamePanel;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class Tile {

    public BufferedImage[] images = new BufferedImage[99];

    //TODO: Store all tiles in a 2D array based off of columns and rows

    public boolean collision = false;
    public int frame = 0;
    public String name = "";
    public int worldX, worldY;
    public int col, row;
    public Point buildPoint;
    GamePanel gp;

    //TODO: Update area
    public Rectangle detectionArea = new Rectangle(0,0,48,48);

    public ArrayList<String> tags = new ArrayList<>();
    /*
        Flammable, Fertile, Destroyed, Obstructed, Water
     */

    public Tile(String name, BufferedImage[] frames, Boolean collision, GamePanel gp) {
        images = frames;
        this.name = name;
        this.collision = collision;
        this.gp = gp;
        buildPoint = new Point(worldX, worldY);
        detectionArea.x = worldX;
        detectionArea.y = worldY;
    }

    public Tile(Tile t) {
        this.images = t.images;
        this.name = t.name;
        this.collision = t.collision;
        this.gp = t.gp;
        buildPoint = new Point(worldX, worldY);
        detectionArea.x = worldX;
        detectionArea.y = worldY;
    }

    public Tile(String name, BufferedImage img, Boolean collision, GamePanel gp) {
        images[0] = img;
        this.name = name;
        this.collision = collision;
        this.gp = gp;
        buildPoint = new Point(worldX, worldY);
        detectionArea.x = worldX;
        detectionArea.y = worldY;
    }

    public Tile(BufferedImage[] images, boolean collision, String name, int col, int row, GamePanel gp) {
        this.images = images;
        this.collision = collision;
        this.name = name;
        this.col = col;
        this.row = row;
        this.gp = gp;
        buildPoint = new Point(worldX, worldY);
        detectionArea.x = worldX;
        detectionArea.y = worldY;
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

    @Override
    public String toString() {
        return "TILE X:" + worldX+"TILE Y:"+worldY;
    }

    //TODO
    public int inFactionTerritory(Faction faction) {
        //TODO: Return 1 for CONTAINED, return 2 for INTERSECTS, 0 for not present
        //System.out.println(faction.name + "'s territory");
        if(gp.printDebugs) {
            System.out.println(this.detectionArea.toString());
            System.out.println(this);
            System.out.println("TERRITORY BOUNDS:" + faction.worldTerritory.getBounds());
            System.out.println(faction.placeAt);
        }
        if(faction.worldTerritory.contains(detectionArea)){
            return 1;
        }else if(faction.worldTerritory.intersects(detectionArea)){
            return 2;
        }
        return 0;
    }
}