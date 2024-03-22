package main;

import entity.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    public Font pixelText16b;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public int commandNum = 0;
    public int titleScreenState = 0; // 0: title screen 1: mode select 2: story?
    public Rectangle menuRect;

    //Icon images, in order
    public BufferedImage money_icon;
    public BufferedImage wheat_icon;
    public BufferedImage lumber_icon;
    public BufferedImage iron_icon;
    public BufferedImage stone_icon;
    public BufferedImage gold_icon;
    public BufferedImage smokeleaf_icon;
    public BufferedImage silk_icon;
    public BufferedImage gem_icon;

    public double playTime = 0;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public Boolean showIcons = true;

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
        loadIcons();
    }

    public void addMessage(String text) {

        message.add(text);
        messageCounter.add(0);
    }

    public void loadIcons() {
        money_icon = setup("/ui/icons/money_icon",2);
        wheat_icon = setup("/ui/icons/wheat_icon",2);
        lumber_icon = setup("/ui/icons/lumber_icon",2);
        iron_icon = setup("/ui/icons/iron_icon",2);
        stone_icon = setup("/ui/icons/stone_icon",2);
        gold_icon = setup("/ui/icons/gold_icon",2);
        smokeleaf_icon = setup("/ui/icons/smokeleaf_icon",2);
        silk_icon = setup("/ui/icons/silk_icon",2);
        gem_icon = setup("/ui/icons/gem_icon",2);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(pixelText16b);
        g2.setColor(Color.white);

        if(gp.gameState == gp.playState) {
            if(showIcons) {
                drawResourceIcons();
            }
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
            y += gp.tileSize * 3.5;
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

            text = "CONTROLS";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.drawString(">", x - gp.tileSize ,y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x ,y);
            if(commandNum == 3) {
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
            //9999999 of each resource for player
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.drawString(">", x - gp.tileSize ,y);
            }

            text = "Simulation (TODO)";
            //player is only an observer
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 3) {
                g2.drawString(">", x - gp.tileSize ,y);
            }
        } else if(titleScreenState == 2) {
            g2.setColor(Color.black);
            g2.fillRect(0, 0, (int)gp.screenWidth2, (int)gp.screenHeight2);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f));
            g2.setColor(Color.white);

            String text = "> Back";
            int x = getXforCenteredText(text);
            int y = gp.tileSize;
            g2.drawString(text, x ,y);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20f));
            text = "F to toggle flags, G to toggle resource icons";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);

            text = "C to toggle territory";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);

            text = "WASD to move camera";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);

            text = "-/+ to zoom (TODO)";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);

        }
    }

    public void drawMessage() {

        int messageX = gp.tileSize * 2;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16f));

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
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30f));

            //Menu text
            switch (entity.menuType) {
                case 1:
                    //Building name
                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30f));
                    g2.setColor(Color.white);
                    y += gp.tileSize;
                    x += gp.tileSize / 3;
                    String text = entity.name;
                    g2.drawString(text, x, y);

                    //stats
                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 22f));
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

                    y += gp.tileSize/2;
                    text = "Faction: ";
                    g2.drawString(text, x, y);

                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 18f));

                    y += gp.tileSize/2;
                    text = String.valueOf(entity.faction);
                    g2.drawString(text, x, y);

                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 22f));

                    y += gp.tileSize * 2;
                    text = "[click] to exit";
                    g2.drawString(text, x, y);

                    break;
                case 2:
                    //King's Court
                    g2.setColor(Color.white);
                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 14f));

                    y += gp.tileSize/2;
                    text = "Faction: Player";
                    g2.drawString(text, x, y);

                    y += gp.tileSize;
                    text = "[click] to exit";
                    g2.drawString(text, x, y);

                    //TODO: Player faction info + faction management
                    break;
                case 3:
                    //King's Court (AI)
                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 22f));
                    g2.setColor(Color.white);
                    y += gp.tileSize/2;
                    text = "Faction: ";
                    g2.drawString(text, x, y);

                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 18f));
                    y += gp.tileSize/2;
                    text = entity.faction.toString();
                    g2.drawString(text, x, y);

                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 22f));
                    y += gp.tileSize;
                    text = "[click] to exit";
                    g2.drawString(text, x, y);
                    break;
                case 4:
                    //Entity name
                    g2.setColor(Color.white);
                    y += gp.tileSize;
                    text = "[click] to exit";
                    g2.drawString(text, x, y);
                    break;
                case 5:
                    //Trade
                    //Move later if necessary?
                    y += gp.tileSize;
                    text = "[click] to exit";
                    g2.drawString(text, x, y);
                    break;
            }
        }
    }
    public void drawResourceIcons() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28f));
        //left edge of the screen: draw icons and a number of how many
        int x = 0;
        int y = 0;
        String text = "";
        g2.setColor(Color.white);

        if(showIcons && gp.gameType != 3) {
            g2.drawImage(money_icon, x, y, null);

            y += gp.tileSize / 3 + gp.tileSize / 8;
            x += gp.tileSize / 3 + gp.tileSize / 5;
            text = String.valueOf(gp.player.playerFaction.resources[3]);
            g2.drawString(text, x, y);

            y += gp.tileSize / 12;
            x = 0;
            g2.drawImage(wheat_icon, x, y, null);
            y += gp.tileSize / 3 + gp.tileSize / 8;
            x += gp.tileSize / 3 + gp.tileSize / 5;
            text = String.valueOf(gp.player.playerFaction.resources[8]);
            g2.drawString(text, x, y);

            y += gp.tileSize / 12;
            x = 0;
            g2.drawImage(lumber_icon, x, y, null);
            y += gp.tileSize / 3 + gp.tileSize / 8;
            x += gp.tileSize / 3 + gp.tileSize / 5;
            text = String.valueOf(gp.player.playerFaction.resources[2]);
            g2.drawString(text, x, y);

            y += gp.tileSize / 12;
            x = 0;
            g2.drawImage(iron_icon, x, y, null);
            y += gp.tileSize / 3 + gp.tileSize / 8;
            x += gp.tileSize / 3 + gp.tileSize / 5;
            text = String.valueOf(gp.player.playerFaction.resources[5]);
            g2.drawString(text, x, y);

            y += gp.tileSize / 12;
            x = 0;
            g2.drawImage(stone_icon, x, y, null);
            y += gp.tileSize / 3 + gp.tileSize / 8;
            x += gp.tileSize / 3 + gp.tileSize / 5;
            text = String.valueOf(gp.player.playerFaction.resources[1]);
            g2.drawString(text, x, y);

            y += gp.tileSize / 12;
            x = 0;
            g2.drawImage(gold_icon, x, y, null);
            y += gp.tileSize / 3 + gp.tileSize / 8;
            x += gp.tileSize / 3 + gp.tileSize / 5;
            text = String.valueOf(gp.player.playerFaction.resources[0]);
            g2.drawString(text, x, y);

            y += gp.tileSize / 12;
            x = 0;
            g2.drawImage(smokeleaf_icon, x, y, null);
            y += gp.tileSize / 3 + gp.tileSize / 8;
            x += gp.tileSize / 3 + gp.tileSize / 5;
            text = String.valueOf(gp.player.playerFaction.resources[4]);
            g2.drawString(text, x, y);

            y += gp.tileSize / 12;
            x = 0;
            g2.drawImage(silk_icon, x, y, null);
            y += gp.tileSize / 3 + gp.tileSize / 8;
            x += gp.tileSize / 3 + gp.tileSize / 5;
            text = String.valueOf(gp.player.playerFaction.resources[6]);
            g2.drawString(text, x, y);

            y += gp.tileSize / 12;
            x = 0;
            g2.drawImage(gem_icon, x, y, null);
            y += gp.tileSize / 3 + gp.tileSize / 8;
            x += gp.tileSize / 3 + gp.tileSize / 5;
            text = String.valueOf(gp.player.playerFaction.resources[7]);
            g2.drawString(text, x, y);
        }
    }

    public BufferedImage setup(String imagePath, int divider) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, gp.tileSize / divider, gp.tileSize / divider);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

    //TODO: Particle System (RyiSnow)
}
