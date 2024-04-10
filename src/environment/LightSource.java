package environment;

import main.GamePanel;

import java.awt.*;

public class LightSource {
    public GamePanel gp;
    public int worldX, worldY, circleSize;
    public Color[] color = new Color[12];
    public float[] fraction = new float[12];
    public int brightness = 1;

    public LightSource(int worldX, int worldY, int circleSize, GamePanel gp){
        this.worldX = worldX;
        this.worldY = worldY;
        this.gp = gp;
        this.circleSize = circleSize;

        //Standard colors
        color[0] = new Color(0,0,0,0.1f * brightness);
        color[1] = new Color(0,0,0,0.42f * brightness);
        color[2] = new Color(0,0,0,0.52f * brightness);
        color[3] = new Color(0,0,0,0.61f * brightness);
        color[4] = new Color(0,0,0,0.69f * brightness);
        color[5] = new Color(0,0,0,0.76f * brightness);
        color[6] = new Color(0,0,0,0.82f * brightness);
        color[7] = new Color(0,0,0,0.87f * brightness);
        color[8] = new Color(0,0,0,0.91f * brightness);
        color[9] = new Color(0,0,0,0.94f * brightness);
        color[10] = new Color(0,0,0,0.96f * brightness);
        color[11] = new Color(0,0,0,0.98f * brightness);

        fraction[0] = 0f;
        fraction[1] = 0.4f;
        fraction[2] = 0.5f;
        fraction[3] = 0.6f;
        fraction[4] = 0.65f;
        fraction[5] = 0.7f;
        fraction[6] = 0.75f;
        fraction[7] = 0.8f;
        fraction[8] = 0.85f;
        fraction[9] = 0.9f;
        fraction[10] = 0.95f;
        fraction[11] = 1f;
    }

    public int[] getScreenCoords () {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        //only render in frame
        if(     worldX + (circleSize) > gp.player.worldX - gp.player.screenX &&
                worldX - circleSize < gp.player.worldX + gp.player.screenX &&
                worldY + circleSize > gp.player.worldY - gp.player.screenY &&
                worldY  - circleSize < gp.player.worldY + gp.player.screenY) {
            return new int[]{screenX, screenY};
        }
        return new int[]{-1,-1};
    }

    public boolean isOnScreen() {
        if(     worldX + (circleSize) > gp.player.worldX - gp.player.screenX &&
                worldX - circleSize < gp.player.worldX + gp.player.screenX &&
                worldY + circleSize > gp.player.worldY - gp.player.screenY &&
                worldY  - circleSize < gp.player.worldY + gp.player.screenY) {
            return true;
        }
        return false;
    }

    public void updateCoords(int newWorldX, int newWorldY) {
        worldX = newWorldX;
        worldY = newWorldY;
    }
}
