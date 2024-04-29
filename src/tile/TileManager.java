package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class TileManager {

    GamePanel gp;
    public Tile[][] mapTiles;
    //use tileTemplates to store the template of each tile, based off of it's index (like old ver)
    public Tile[] tileTemplates = new Tile[99];


    public TileManager(GamePanel gp) {

        this.gp = gp;
        //Obselete with 2D Array
        //getTileImage();
        //loadMap("/maps/templateMap.txt", 0);

        initializeTiles();
        genMap();
    }

    public void initializeTiles() {
        tileTemplates[0] = new Tile("grass", setup("grass00"), false);
        tileTemplates[1] = new Tile("mountain", setup("mountain00"), false);
        tileTemplates[2] = new Tile("water0", setup("water00"), false);
        tileTemplates[3] = new Tile("water1", new BufferedImage[]{setup("water01"), setup("water01A"), setup("water01B")}, false);
    }

    public BufferedImage setup(String imageName) {
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

    /*
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
     */

    public void genMap() {
        Random rand = gp.rand;

        gp.maxWorldCol = rand.nextInt(64, 94);
        gp.maxWorldRow = rand.nextInt(64, 94);

        gp.waterBuffer = 20;

        mapTiles = new Tile[gp.maxWorldCol][gp.maxWorldRow];

        generateLandMap();
        addWaterMargin();
        addLandVariation();
        addResourceEntities();
    }

    private void addWaterMargin() {
        Random rand = gp.rand;
        int waterMargin = gp.waterBuffer;

        for (int row = 0; row < gp.maxWorldRow; row++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {
                // Create a new tile and set its properties
                Tile tile;
                if (row < waterMargin || row >= gp.maxWorldRow - waterMargin ||
                        col < waterMargin || col >= gp.maxWorldCol - waterMargin) {
                    if (rand.nextInt(0, 100) > 92) {
                        tile = new Tile(tileTemplates[3].images, tileTemplates[3].collision, tileTemplates[3].name, col, row);
                    } else {
                        tile = new Tile(tileTemplates[2].images, tileTemplates[2].collision, tileTemplates[2].name, col, row);
                    }
                } else {
                    tile = new Tile(tileTemplates[0].images, tileTemplates[0].collision, tileTemplates[0].name, col, row);
                }
                tile.worldX = col * gp.tileSize;
                tile.worldY = row * gp.tileSize;

                mapTiles[col][row] = tile;
            }
        }
    }

    private void generateLandMap() {
        Random rand = gp.rand;

        for (int row = 0; row < gp.maxWorldRow; row++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {
                // Create a new tile and set its properties
                Tile tile = new Tile(tileTemplates[0].images, tileTemplates[0].collision, tileTemplates[0].name, col, row);
                tile.worldX = col * gp.tileSize;
                tile.worldY = row * gp.tileSize;

                mapTiles[col][row] = tile;
            }
        }
    }

    public void addLandVariation() {
        //TODO: Spread sand, snow, et al in the land section of the map as biomes
    }

    public void addResourceEntities() {
        //TODO: Add trees, rocks, et al
    }

    public void draw(Graphics2D g2) {
        int playerWorldX = gp.player.worldX;
        int playerWorldY = gp.player.worldY;
        int playerScreenX = gp.player.screenX;
        int playerScreenY = gp.player.screenY;
        int tileSize = gp.tileSize;

        for (int col = 0; col < gp.maxWorldCol; col++) {
            for (int row = 0; row < gp.maxWorldRow; row++) {
                Tile tile = mapTiles[col][row];
                if (tile != null) {
                    int screenX = tile.worldX - playerWorldX + playerScreenX;
                    int screenY = tile.worldY - playerWorldY + playerScreenY;

                    // Render the tile if it's within the screen bounds
                    if (screenX + tileSize > 0 && screenX < gp.screenWidth &&
                            screenY + tileSize > 0 && screenY < gp.screenHeight) {
                        g2.drawImage(tile.getImage(), screenX, screenY, null);
                    }
                }
            }
        }
    }
    
    public Tile getTile(int worldX, int worldY) {
        int col = worldX / gp.tileSize;
        int row = worldY / gp.tileSize;
        if (col >= 0 && col < gp.maxWorldCol && row >= 0 && row < gp.maxWorldRow) {
            return mapTiles[col][row];
        }else{
            System.out.println("Attempted to access Tile outside of the map");
        }
        return null;
    }
}
