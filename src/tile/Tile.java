package tile;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Tile {

    public BufferedImage[] images = new BufferedImage[99];

    public boolean collision = false;
    public int frame = 0;
    public String name = "";
    public int worldX, worldY;

    public final String[] tagsLib = {
            "Fertile", "Flammable", "Mineable", "Lumber", "Destroyed"
    };
    public String[] tags;


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