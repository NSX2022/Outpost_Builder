package tile;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Tile {

    public BufferedImage[] images = new BufferedImage[99];

    //TODO: Store all tiles in a 2D array based off of columns and rows

    public boolean collision = false;
    public int frame = 0;
    public String name = "";
    public int worldX, worldY;
    public int col, row;

    public final String[] tagsLib = {
            "Fertile", "Flammable", "Mineable", "Lumber", "Destroyed"
    };
    public String[] tags;

    public Tile(String name, BufferedImage[] frames, Boolean collision) {
        images = frames;
        this.name = name;
        this.collision = collision;
    }

    public Tile(Tile t) {
        this.images = t.images;
        this.name = t.name;
        this.collision = t.collision;
    }

    public Tile(String name, BufferedImage img, Boolean collision) {
        images[0] = img;
        this.name = name;
        this.collision = collision;
    }

    public Tile(BufferedImage[] images, boolean collision, String name, int col, int row) {
        this.images = images;
        this.collision = collision;
        this.name = name;
        this.col = col;
        this.row = row;
    }

    public void addTag(String tag){
        for(int i = 0; i < tagsLib.length; i++){
            if(tagsLib[i].equals(tag)){
                i = 999999;
            }
            if(i == tagsLib.length){
                return;
            }
        }
        for(int i = 0; i < tags.length; i++){
            if(tags[i] == null){
                tags[i] = tag;
                return;
            }
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