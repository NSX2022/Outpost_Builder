package environment;

import main.GamePanel;

import java.awt.*;

public class LightSource {
    public GamePanel gp;
    public int worldX, worldY, circleSize;
    public Color[] color = new Color[12];
    public float[] fraction = new float[12];

    public LightSource(int worldX, int worldY, int circleSize, GamePanel gp){
        this.worldX = worldX;
        this.worldY = worldY;
        this.gp = gp;
        this.circleSize = circleSize;
    }

    public int[] getScreenCoords () {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        //only render in frame
        if(     worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY  - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            return new int[]{screenX, screenY};
        }
        return  new int[]{-1,-1};
    }

    public void updateCoords(int newWorldX, int newWorldY) {
        worldX = newWorldX;
        worldY = newWorldY;
    }
}
