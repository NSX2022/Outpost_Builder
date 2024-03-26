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

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {

        this.gp = gp;

        tile = new Tile[10000];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/templateMap.txt", 0);
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
        setup(4, "port00", true); //for trading
    }


    public void setup(int index, String imageName, boolean collision) {

        UtilityTool uTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].collision = collision;
            tile[index].images[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imageName + ".png")));
            tile[index].images[0] = uTool.scaleImage(tile[index].images[0], gp.tileSize, gp.tileSize);
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
