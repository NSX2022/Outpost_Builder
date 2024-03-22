package tile;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Tile {

    public BufferedImage image;
    public boolean collision = false;

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




}