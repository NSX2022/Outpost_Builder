package main;

import entity.*;

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

    //Build menu
    public BufferedImage shift_tip;
    public BufferedImage money_icon_mini;
    public BufferedImage wheat_icon_mini;
    public BufferedImage lumber_icon_mini;
    public BufferedImage iron_icon_mini;
    public BufferedImage stone_icon_mini;
    public BufferedImage gold_icon_mini;
    public BufferedImage smokeleaf_icon_mini;
    public BufferedImage silk_icon_mini;
    public BufferedImage gem_icon_mini;
    //contd.
    public BufferedImage farm;
    public BufferedImage mine;
    public BufferedImage fortress;
    public BufferedImage outpost;
    public BufferedImage wall;
    public BufferedImage logging_camp;
    public BufferedImage quarry;
    public int menuNum;
    public Build_Preview preview;

    //ONE place to put building cost values, edit them here
    public int[] farmCost = {0,2,4,0,0,1,0,0,0};
    public int[] mineCost = {0,1,6,0,0,1,0,0,3};
    public int[] fortCost = {0,6,4,10,0,10,0,0,16};
    public int[] outpostCost = {0,2,2,0,0,1,0,0,4};
    public int[] wallCost = {0,2,0,0,0,0,0,0,1};
    public int[] lumberyardCost = {0,4,0,0,0,2,0,0,8};
    public int[] quarryCost = {0,0,3,0,0,2,0,0,6};

    public int baseFontSize = 6;

    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public Boolean showIcons = true;

    //buildMenu
    public Boolean buildMenu = false;

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
        preview = new Build_Preview(gp, farm);
        preview.worldX = 99999;
        preview.worldY = 99999;
        farm = setup("/entity/tile_entity/farm",1);
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

        //Build Menu
        shift_tip = setup("/ui/build_menu/shift_tip",1);

        money_icon_mini = setup("/ui/icons/money_icon",3);
        wheat_icon_mini = setup("/ui/icons/wheat_icon",3);
        lumber_icon_mini = setup("/ui/icons/lumber_icon",3);
        iron_icon_mini = setup("/ui/icons/iron_icon",3);
        stone_icon_mini = setup("/ui/icons/stone_icon",3);
        gold_icon_mini = setup("/ui/icons/gold_icon",3);
        smokeleaf_icon_mini = setup("/ui/icons/smokeleaf_icon",3);
        silk_icon_mini = setup("/ui/icons/silk_icon",3);
        gem_icon_mini = setup("/ui/icons/gem_icon",3);
        //icons for buildings
        farm = setup("/entity/tile_entity/farm",1);
        mine = setup("/entity/tile_entity/mine",1);
        fortress = setup("/entity/tile_entity/building_fortress",1);
        outpost = setup("/entity/tile_entity/outpost",1);
        wall = setup("/entity/tile_entity/wall_cross",1);
        logging_camp = setup("/entity/tile_entity/logging_camp",1);
        quarry = setup("/entity/tile_entity/quarry",1);

    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(pixelText16b);
        g2.setColor(Color.white);

        if(gp.gameState == gp.playState) {
            if(showIcons) {
                drawResourceIcons();
            }
            drawBuildMenu();
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
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 12f + baseFontSize));
        String text = String.valueOf(gp.latestFPS);
        int x = getXforCenteredText(text);
        int y = 10 + baseFontSize;
        g2.drawString(text, x, y);
    }

    public void drawTitleScreen() {

        if(titleScreenState == 0) {
            g2.setColor(new Color(70, 120, 80));
            g2.fillRect(0, 0, (int)gp.screenWidth2, (int)gp.screenHeight2);

            //TITLE
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 98f + baseFontSize));
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
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f + baseFontSize));

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

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f + baseFontSize));
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

            text = "Simulation";
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

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f + baseFontSize));
            g2.setColor(Color.white);

            String text = "> Back";
            int x = getXforCenteredText(text);
            int y = gp.tileSize;
            g2.drawString(text, x ,y);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20f + baseFontSize));
            text = "Toggles: F flags, G resource icons, C territory";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);

            text = "WASD to move camera";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);

            text = "R to reset camera";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);

            text = "SHIFT to open build menu";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);

            text = "1-9 to select building";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);

            text = "P to pause";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);

            text = "ESC to save and quit";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);

            }
    }

    public void drawMessage() {

        int messageX = gp.tileSize * 2;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16f + baseFontSize));

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
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80f + baseFontSize));
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
        String text;

        if(entity.menuType > 0 && !buildMenu){
            //Menu backdrop
            int x = (int) (gp.screenWidth - gp.tileSize * 4);
            int y = 0;
            g2.setColor(Color.black);
            menuRect = new Rectangle(x, y, (int) (gp.tileSize * 4),(int) gp.screenHeight);
            g2.fillRect(x,y, (int) (gp.tileSize * 4),(int) gp.screenHeight);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30f + baseFontSize));

            //Menu text
            switch (entity.menuType) {
                case 1:
                    //Building name
                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30f + baseFontSize));
                    g2.setColor(Color.white);
                    y += gp.tileSize;
                    x += gp.tileSize / 5;
                    text = entity.name;
                    g2.drawString(text, x, y);

                    //stats
                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20f + baseFontSize));
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

                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 18f + baseFontSize));

                    y += gp.tileSize/2;
                    text = String.valueOf(entity.faction);
                    g2.drawString(text, x, y);

                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 22f + baseFontSize));

                    y += gp.tileSize * 2;
                    text = "[click] to exit";
                    g2.drawString(text, x, y);

                    break;
                case 2:
                    //King's Court
                    g2.setColor(Color.white);
                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 14f + baseFontSize));

                    y += gp.tileSize/2;
                    text = "Faction: Player";
                    g2.drawString(text, x, y);

                    y += gp.tileSize;
                    text = "[click] to exit";
                    g2.drawString(text, x, y);



                    //Player faction info + faction management???
                    break;
                case 3:
                    //King's Court (AI)
                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 22f + baseFontSize));
                    g2.setColor(Color.white);
                    y += gp.tileSize/2;
                    text = "Faction: ";
                    g2.drawString(text, x, y);

                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 18f + baseFontSize));
                    y += gp.tileSize/2;
                    if(entity.faction.toString().length() > 16){
                        text = entity.faction.toString().substring(0, entity.faction.toString().indexOf(" "));
                        g2.drawString(text, x, y);
                        y += gp.tileSize/2;
                        text = entity.faction.toString().substring(entity.faction.toString().indexOf(" ") + 1);
                        g2.drawString(text, x, y);
                    }else{
                        text = entity.faction.toString();
                        g2.drawString(text, x, y);
                    }

                    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 22f + baseFontSize));
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
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28f + baseFontSize));
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

    public void drawBuildMenu(){
        if(gp.gameType != 3){

            int x = (int)gp.screenWidth/2 - gp.tileSize *  6;
            int y;
            if(buildMenu){
                y = (int) gp.screenHeight - gp.tileSize *  5;
                g2.drawImage(shift_tip, x, y, null);
                g2.setColor(Color.black);
                y = (int) gp.screenHeight - gp.tileSize *  4;
                g2.fillRect(x, y, gp.tileSize * 12, gp.tileSize * 4);

                //Farm
                menuNum = 1;
                x = (int)gp.screenWidth/2 - gp.tileSize *  6;
                y = (int) gp.screenHeight - gp.tileSize * 4;
                drawMenuBuildCost(x, y, farmCost, farm);
                //Mine
                menuNum = 2;
                x += (int) (gp.tileSize * 1.1);
                drawMenuBuildCost(x, y, mineCost, mine);
                //Fortress
                menuNum = 3;
                x += (int) (gp.tileSize * 1.1);
                drawMenuBuildCost(x, y, fortCost, fortress);
                //Outpost
                menuNum = 4;
                x += (int) (gp.tileSize * 1.1);
                drawMenuBuildCost(x, y, outpostCost, outpost);
                //Wall
                menuNum = 5;
                x += (int) (gp.tileSize * 0.6);
                drawMenuBuildCost(x, y, wallCost, wall);
                //Lumberyard
                menuNum = 6;
                x += (int) (gp.tileSize * 1.1);
                drawMenuBuildCost(x, y, lumberyardCost, logging_camp);
                //Quarry
                menuNum = 7;
                x += (int) (gp.tileSize * 1.1);
                drawMenuBuildCost(x, y, quarryCost, quarry);


            }else{
                y = (int) gp.screenHeight - gp.tileSize;
                g2.drawImage(shift_tip, x, y, null);
            }
        }else{
            buildMenu = false;
        }
    }

    public void drawMenuBuildCost(int x, int y, int[] costs, BufferedImage building){
        int xPos = x;
        int yPos = y;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 8f + baseFontSize));
        g2.drawImage(building, xPos, yPos, null);

        yPos += (int) (gp.tileSize/2);

        g2.setColor(Color.yellow);
        g2.drawString(String.valueOf(menuNum), xPos, yPos);

        yPos += (int) (gp.tileSize/3.2);
        for(int i = 0; i < costs.length; i++){
            if(costs[i] > 0){
                yPos += (int) (gp.tileSize/2.1);
                switch (i){
                    case 0:
                        g2.drawImage(gold_icon_mini, xPos, yPos, null);
                        g2.setColor(Color.pink);
                        g2.drawString(String.valueOf(costs[i]), xPos, yPos);
                        break;
                    case 1:
                        g2.drawImage(stone_icon_mini, xPos, yPos, null);
                        g2.setColor(Color.pink);
                        g2.drawString(String.valueOf(costs[i]), xPos, yPos);
                        break;
                    case 2:
                        g2.drawImage(lumber_icon_mini, xPos, yPos, null);
                        g2.setColor(Color.pink);
                        g2.drawString(String.valueOf(costs[i]), xPos, yPos);
                        break;
                    case 3:
                        g2.drawImage(money_icon_mini, xPos, yPos, null);
                        g2.setColor(Color.pink);
                        g2.drawString(String.valueOf(costs[i]), xPos, yPos);
                        break;
                    case 4:
                        g2.drawImage(smokeleaf_icon_mini, xPos, yPos, null);
                        g2.setColor(Color.pink);
                        g2.drawString(String.valueOf(costs[i]), xPos, yPos);
                        break;
                    case 5:
                        g2.drawImage(iron_icon_mini, xPos, yPos, null);
                        g2.setColor(Color.pink);
                        g2.drawString(String.valueOf(costs[i]), xPos, yPos);
                        break;
                    case 6:
                        g2.drawImage(silk_icon_mini, xPos, yPos, null);
                        g2.setColor(Color.pink);
                        g2.drawString(String.valueOf(costs[i]), xPos, yPos);
                        break;
                    case 7:
                        g2.drawImage(gem_icon_mini, xPos, yPos, null);
                        g2.setColor(Color.pink);
                        g2.drawString(String.valueOf(costs[i]), xPos, yPos);
                        break;
                    case 8:
                        g2.drawImage(wheat_icon_mini, xPos, yPos, null);
                        g2.setColor(Color.pink);
                        g2.drawString(String.valueOf(costs[i]), xPos, yPos);
                        break;
                }
            }
        }

    }

    public BufferedImage setup(String imagePath, double divider) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, (int) (gp.tileSize / divider), (int) (gp.tileSize / divider));
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

    //TODO: Particle System (RyiSnow)
}
