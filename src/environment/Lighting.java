package environment;

import main.GamePanel;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lighting {

    private GamePanel gp;
    private BufferedImage darknessFilter;

    public Lighting(GamePanel gp) {
        this.gp = gp;
        initializeDarknessFilter();
    }

    private void initializeDarknessFilter() {
        darknessFilter = new BufferedImage(gp.maxWorldRow * gp.tileSize, gp.maxWorldCol * gp.tileSize
                , BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = darknessFilter.createGraphics();
        Color darkColor = new Color(0, 0, 0, gp.darknessOpacity); // 5% of 255 (maximum alpha value)
        g2d.setColor(darkColor);

        // Fill the entire darkness filter with the transparent dark color
        g2d.fillRect(gp.maxWorldRow * gp.tileSize * -1, gp.maxWorldCol * gp.tileSize * -1
                , gp.maxWorldRow * gp.tileSize, gp.maxWorldCol * gp.tileSize);
        g2d.dispose();
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(darknessFilter, 0, 0, null);
    }

    public void updateDarkness(List<LightSource> lights) {
        /*TODO: Update only when new lights are added, not every frame
          TODO: use a BufferedImage that scrolls with worldX/worldY

         */
        //Creates an intermediate BufferedImage to render darkness and light sources separately
        BufferedImage tempBuffer = new BufferedImage((int) gp.screenWidth, (int) gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D tempG2 = tempBuffer.createGraphics();

        // Clear the temporary buffer
        tempG2.setColor(new Color(0, 0, 0, 0));
        tempG2.fillRect(0, 0, (int) gp.screenWidth, (int) gp.screenHeight);

        // Render darkness onto the temporary buffer
        tempG2.setColor(new Color(0, 0, 0, gp.darknessOpacity)); // current transparency
        tempG2.fillRect(0, 0, (int) gp.screenWidth, (int) gp.screenHeight);

        // Draw light sources onto the temporary buffer
        for (LightSource light : lights) {
            if (light.isOnScreen()) {
                drawLight(tempG2, light);
            }
        }

        // Blend the darkness and light sources
        Graphics2D g2 = darknessFilter.createGraphics();
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, (int) gp.screenWidth, (int) gp.screenHeight);
        g2.setComposite(AlphaComposite.SrcOver);
        g2.drawImage(tempBuffer, 0, 0, null);
        g2.dispose();

        // Dispose of the temporary graphics context
        tempG2.dispose();
    }

    private void drawLight(Graphics2D g2, LightSource light) {
        int[] screenCoords = light.getScreenCoords();
        if (screenCoords[0] != -1 && screenCoords[1] != -1) {
            int circleSize = light.circleSize;

            Point2D center = new Point2D.Float(screenCoords[0], screenCoords[1]);
            float[] fractions = light.fraction;
            Color[] colors = light.color;

            RadialGradientPaint gradient = new RadialGradientPaint(
                    center,
                    circleSize,
                    fractions,
                    colors,
                    MultipleGradientPaint.CycleMethod.NO_CYCLE);

            // Save the original composite
            Composite originalComposite = g2.getComposite();

            // Set the composite to DstOut to "knock out" the darkness
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OUT));

            // Fill the light source shape
            g2.setPaint(gradient);
            g2.fill(new Ellipse2D.Double(screenCoords[0] - circleSize / 2.0, screenCoords[1] - circleSize / 2.0, circleSize, circleSize));

            // Restore the original composite
            g2.setComposite(originalComposite);
        }
    }

    public void moveDarkness(int x, int y) {
        // Calculate the offset based on player's movement
        int offsetX = x - (int) (gp.screenWidth / 2);
        int offsetY = y - (int) (gp.screenHeight / 2);

        // Create a temporary BufferedImage to store the translated darkness filter
        BufferedImage translatedDarknessFilter = new BufferedImage((int) gp.screenWidth, (int) gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = translatedDarknessFilter.createGraphics();
        // Draw the existing darkness filter at the new position
        g2d.drawImage(darknessFilter, offsetX, offsetY, null);
        // Disposethe graphics context
        g2d.dispose();
        // Update the darkness filter with the translated image
        darknessFilter = translatedDarknessFilter;
    }

}
