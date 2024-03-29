package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {

        this.gp = gp;

        tile = new Tile[10000];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        //loadMap("/maps/templateMap.txt", 0);
        genMap();
    }

    public void getTileImage() {
        BufferedImage[] frameSet = new BufferedImage[99];
        //Set tiles here, use frameSet for animated
        /* Example for animated grass
        frameSet[0] = newSetup("grass00");
        frameSet[1] = newSetup("grass00A");
        tile[0] = new Tile();
        tile[0].images = frameSet;
        frameSet = new BufferedImage[99];
         */

        setup(0, "grass00", false);
        setup(1, "mountain00", true);
        setup(2, "water00", true); //waters 00 and 01 are the impassable border tiles

        setup(3, "water01", true);
        /*
        frameSet[0] = newSetup("water01");
        frameSet[1] = newSetup("water01A");
        frameSet[2] = newSetup("water01B");
        tile[3].images = frameSet;
         */

        setup(4, "port00", true); //for trading
    }


    public void setup(int index, String imageName, boolean collision) {

        UtilityTool uTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].collision = collision;
            tile[index].images[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imageName + ".png")));
            tile[index].images[0] = uTool.scaleImage(tile[index].images[0], gp.tileSize, gp.tileSize);
            tile[index].name = imageName;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public BufferedImage newSetup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage toRet = new BufferedImage(48, 48, 2);

        try{
            toRet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imageName + ".png")));
            toRet = uTool.scaleImage(toRet, gp.tileSize, gp.tileSize);
        }catch (Exception e){
            e.printStackTrace();
        }
        return toRet;
    }

    public void loadMap(String map, int indexMargin) {
        try {
            InputStream is = getClass().getResourceAsStream(map);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while(col < gp.maxWorldCol) {

                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    num += indexMargin;

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void genMap() {

        Random rand = gp.rand;

        gp.maxWorldCol = rand.nextInt(64, 94);
        gp.maxWorldRow = rand.nextInt(64, 94);

        gp.waterBuffer = 20;

        generateLandMap();
        addWaterMargin();
        gp.aSetter.setEntity();
        gp.aSetter.setObject();
    }
    private void addWaterMargin() {
        Random rand = gp.rand;
        int waterMargin = gp.waterBuffer;


        for (int row = 0; row < gp.maxWorldRow; row++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {
                if (row < waterMargin || row >= gp.maxWorldRow - waterMargin ||
                        col < waterMargin || col >= gp.maxWorldCol - waterMargin) {
                    if(rand.nextInt(0, 100) > 92 ){
                        mapTileNum[col][row] = 3;
                    }else{
                        mapTileNum[col][row] = 2;
                    }
                }
            }
        }
    }
    private void generateLandMap() {
        Random rand = gp.rand;

        for (int row = 0; row < gp.maxWorldRow; row++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {
                mapTileNum[col][row] = 0;
            }
        }
        //System.out.println(gp.maxWorldRow);
        //System.out.println(gp.maxWorldCol);
    }
    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            //only render in frame
            if(     worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY  - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                tile[tileNum].worldX = worldX;
                tile[tileNum].worldY = worldY;
                g2.drawImage(tile[tileNum].images[tile[tileNum].frame], screenX, screenY, null);
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
