package environment;

import main.GamePanel;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class Lighting {

    GamePanel gp;
    public BufferedImage darknessFilter;
    public float brightness = 1f;
    ArrayList<LightSource> lights;
    Color[] color = new Color[12];
    float[] fraction = new float[12];

    public Lighting(GamePanel gp) {
        this.gp = gp;
        // Create a buffered image

        darknessFilter = new BufferedImage((int) gp.screenWidth, (int) gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();

        // Create a gradation effect
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

        g2.fillRect(0, 0, (int) gp.screenWidth, (int) gp.screenHeight);
        g2.dispose();
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(darknessFilter,0,0, null);
    }

    public void updateDarkness() {
        darknessFilter = new BufferedImage((int) gp.screenWidth, (int) gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();
        lights = gp.lights;

        Area screenArea = new Area(new Rectangle2D.Double(0,0,gp.screenWidth,gp.screenHeight));

        for(int i = 0; i < lights.size(); i++){
            //use lights.get(i);
            if(lights.get(i) == null || lights.get(i).getScreenCoords()[0] == -1 ||  lights.get(i).getScreenCoords()[1] == -1){
                continue;
            }

            int[] coords = lights.get(i).getScreenCoords();

            Shape circleShape = new Ellipse2D.Double(coords[0],coords[1], lights.get(i).circleSize, lights.get(i).circleSize);

            Area lightArea = new Area(circleShape);

            screenArea.subtract(lightArea);

            // Create a gradation effect
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

            // Create a gradation paint settings
            RadialGradientPaint gPaint = new RadialGradientPaint(coords[0], coords[1], ((float) lights.get(i).circleSize /2), fraction, color);
            // Set the gradient data on g2
            g2.setPaint(gPaint);
            g2.fillRect(0, 0, (int) gp.screenWidth, (int) gp.screenHeight);
        }
        System.out.println(lights.size());
        g2.dispose();
    }

}