package main;

import entity.Entity;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font pixelText16b;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public int commandNum = 0;
    public int titleScreenState = 0; // 0: title screen 1: mode select 2: story?
    public Rectangle menuRect;

    double playTime = 0;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/MP16OSF.ttf");
            pixelText16b = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMessage(String text) {

        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(pixelText16b);
        g2.setColor(Color.white);

        if(gp.gameState == gp.playState) {
            drawMessage();
        }
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        drawFPScounter();
    }

    public void drawFPScounter() {
        g2.setColor(Color.magenta);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 10f));
        String text = String.valueOf(gp.latestFPS);
        int x = getXforCenteredText(text);
        int y = 10;
        g2.drawString(text, x, y);
    }

    public void drawTitleScreen() {

        if(titleScreenState == 0) {
            g2.setColor(new Color(70, 120, 80));
            g2.fillRect(0, 0, (int)gp.screenWidth2, (int)gp.screenHeight2);

            //TITLE
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96f));
            String text = "Nations Will Rise";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 2;

            //SHADOW
            g2.setColor(Color.black);
            g2.drawString(text, x + 5, y + 5);
            //MAIN COLOR
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            //LOGO Image (the castle)
            x = (int)gp.screenWidth / 2 - (int)gp.tileSize * 3 + 64;
            y += gp.tileSize - 36;
            g2.drawImage(gp.titleLogo, x, y, gp.tileSize * 3, gp.tileSize * 3, null);

            //MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48f));

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize * 4;
            g2.drawString(text, x ,y);
            if(commandNum == 0) {
                g2.drawString(">", x - gp.tileSize ,y);
            }

            text = "LOAD GAME (TODO)";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x ,y);
            if(commandNum == 1) {
                g2.drawString(">", x - gp.tileSize ,y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x ,y);
            if(commandNum == 2) {
                g2.drawString(">", x - gp.tileSize ,y);
            }
        }else if(titleScreenState == 1) {
            g2.setColor(Color.black);
            g2.fillRect(0, 0, (int)gp.screenWidth2, (int)gp.screenHeight2);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f));
            g2.setColor(Color.white);

            String text = "Back";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 2;
            g2.drawString(text, x ,y);
            if(commandNum == 0) {
                g2.drawString(">", x - gp.tileSize ,y);
            }

            text = "Normal";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.drawString(">", x - gp.tileSize ,y);
            }

            text = "Sandbox";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.drawString(">", x - gp.tileSize ,y);
            }
        }


    }

    public void drawMessage() {

        int messageX = gp.tileSize * 2;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16f));

        for(int i = 0; i < message.size(); i++) {

            if(message.get(i) != null) {

                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 25;

                if(messageCounter.get(i) > 200) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80f));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = (int)gp.screenHeight/2;

        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return (int)gp.screenWidth/2 - length/2;
    }

    public void drawMenu(Entity entity) {
        if(entity.menuType > 0){
            //Menu backdrop
            int x = (int) (gp.screenWidth - gp.tileSize * 4);
            int y = 0;
            g2.setColor(Color.black);
            menuRect = new Rectangle(x, y, (int) (gp.tileSize * 4),(int) gp.screenHeight);
            g2.fillRect(x,y, (int) (gp.tileSize * 4),(int) gp.screenHeight);

            //Menu text
            switch (entity.menuType) {
                case 1:
                    //Building name
                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
                    g2.setColor(Color.white);
                    y += gp.tileSize;
                    x += gp.tileSize / 3;
                    String text = entity.name;
                    g2.drawString(text, x, y);

                    //stats
                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 22f));
                    y += gp.tileSize;
                    x += gp.tileSize/4;
                    text = "level " + entity.level;
                    g2.drawString(text, x, y);

                    y += gp.tileSize/2;
                    text = "yield: ";
                    g2.drawString(text, x, y);

                    y += gp.tileSize/2;
                    switch(entity.reIndex) {
                        case 0:
                            text = entity.resourceYield + " gold";
                            break;
                        case 1:
                            text = entity.resourceYield + " stone";
                            break;
                        case 2:
                            text = entity.resourceYield + " lumber";
                            break;
                        case 3:
                            text = entity.resourceYield + " currency";
                            break;
                        case 4:
                            text = entity.resourceYield + " smokeleaf";
                            break;
                        case 5:
                            text = entity.resourceYield + " iron";
                            break;
                        case 6:
                            text = entity.resourceYield + " silk";
                            break;
                        case 7:
                            text = entity.resourceYield + " gem";
                            break;
                        case 8:
                            text = entity.resourceYield + " wheat";
                            break;
                        case -1:
                            text = "Nothing";
                    }
                    g2.drawString(text, x, y);

                    x -= gp.tileSize/4;
                    y += gp.tileSize/2;
                    text = "Total yield: " + entity.resourcesGained;
                    g2.drawString(text, x, y);

                    y += gp.tileSize;
                    text = "[Q] to exit";
                    g2.drawString(text, x, y);

                    break;
                case 2:
                    //King's Court
                    break;
                case 3:
                    //King's Court (AI)
                    break;
                case 4:
                    //Entity name
                    break;
                case 5:
                    //Trade Menu
                    break;
            }
        }
    }

}
