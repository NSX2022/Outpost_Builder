package main;

import entity.*;
import faction.Faction;
import object.*;
import tile.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {
    //Screen Settings
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48*48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 9;
    public final double screenWidth = tileSize * maxScreenCol; // 768
    public final double screenHeight = tileSize * maxScreenRow; // 576
    public final int objDisplayLimit = 32;
    public final int entDisplayLimit = 32;
    Font pixelText16b;

    //FULLSCREEN
    public double screenWidth2 = screenWidth;
    public double screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;

    //World Settings do not allow player to touch
    public final int maxWorldCol = 71;
    public final int maxWorldRow = 68;

    //system
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Sound music = new Sound();
    public Sound se = new Sound();
    public UI ui = new UI(this);
    public UtilityTool uTool = new UtilityTool();
    Thread gameThread;
    public int latestFPS;
    public Point clickPoint = new Point();
    public Random rand = new Random();
    public Long seed = rand.nextLong(100000000, 999999999);

    public double screenRatioX;
    public double screenRatioY;
    public NameGenerator nameGen = new NameGenerator();

    //entity and obj
    public Camera player = new Camera(this, keyH);
    public SuperObject[] obj = new SuperObject[objDisplayLimit];
    public Entity[] ent = new Entity[entDisplayLimit];

    //game state
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public int gameType = -1;
    //0 = default
    //1 = Normal
    //2 = Sandbox
    //3 = Simulation
    public boolean menuOn = false;
    public int entMenuNum;
    public Boolean gameStart = false;

    //fps
    int FPS = 60; //60

    //title logo
    public BufferedImage titleLogo;

    //Factions
    public int maxFactions = 10;
    public Faction[] factions = new Faction[maxFactions];
    public SuperObject[][] factionFlags = new SuperObject[objDisplayLimit][50];
    public boolean showTerritory = true;

    public GamePanel() {
        this.setPreferredSize(new Dimension((int)screenWidth, (int)screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        try {
            InputStream is = getClass().getResourceAsStream("/font/MP16OSF.ttf");
            pixelText16b = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch(FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            titleLogo = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/tile_entity/building_fortress.png")));
            titleLogo = uTool.scaleImage(titleLogo, tileSize, tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setupGame() {

        playMusic(0);

        gameState = titleState;

        tempScreen = new BufferedImage((int)screenWidth2, (int)screenHeight2, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                clickPoint = e.getPoint();
                //System.out.println(clickPoint);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        setFullScreen();
        getScreenRatio();
        rand.setSeed(seed);
        System.out.println("Seed: " + seed);
    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        int seconds = 0;
        int totalSeconds = 0;

        while(gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta >= 1){ //delta >= 1
                update();
                drawToTempScreen();
                drawToScreen();
                delta = 0;
                drawCount++;
            }

            if(timer >= 1000000000) {
                //System.out.println("FPS: " + drawCount);
                latestFPS = drawCount;
                drawCount = 0;
                timer = 0;
                getScreenRatio();
                if(FPS >= 60) {
                    if(gameState == playState){
                        seconds++;
                    }
                    totalSeconds++;
                }
                if(gameState == playState){
                    if(seconds % 2 == 0){
                        for(int i = 0; i < tileM.tile.length; i++){
                            if(tileM.tile[i] != null /*&& tileM.tile[i].animated*/){
                                tileM.tile[i].nextFrame();
                            }
                        }
                        for(int i = 0; i < ent.length; i++){
                            if(ent[i] != null){
                                ent[i].nextFrame();
                            }
                        }
                    }
                    if(seconds % 30 == 0) {
                        for(int i = 0; i < factions.length; i++) {
                            if(factions[i] != null) {
                                factions[i].updateBuildings();
                            }
                        }
                        System.out.println("Faction flags updated");
                        if(keyH.checkDrawTime) {
                            ui.addMessage("Faction Flags Updated (Debug)");
                        }
                        //TODO: Update territory
                    }
                    if(seconds >= 60) {
                        for(int i = 0; i < factions.length; i++) {
                            if(factions[i] != null) {
                                for(int k = 0; k < factions[i].factionBuildings.length; k++) {
                                    if (factions[i].factionBuildings[k] instanceof Building && factions[i].factionBuildings[k].reIndex > -1) {
                                        ((Building) factions[i].factionBuildings[k]).genResources();
                                    }
                                }
                            }
                        }
                        seconds = 0;
                    }
                }
            }

            for(int i = 0; i < ent.length; i++) {
                //check to see if the click point touches the draw area of an entity (Building, Citizen)
                if(ent[i] != null && !Objects.equals(ent[i].name, "camera") && gameState == playState) {
                    int screenX = ent[i].worldX - player.worldX + player.screenX;
                    int screenY = ent[i].worldY - player.worldY + player.screenY;

                    ent[i].clickArea.x = screenX;
                    ent[i].clickArea.y = screenY;

                    ent[i].landClaim.x = screenX;
                    ent[i].landClaim.y = screenY;

                    if(ent[i] instanceof ENT_Tree) {
                        ((ENT_Tree) ent[i]).detectionArea.x = screenX - ((ENT_Tree) ent[i]).detectionArea.width / 4;
                        ((ENT_Tree) ent[i]).detectionArea.y = screenY - ((ENT_Tree) ent[i]).detectionArea.height / 4;
                    }
                    //Also add for other detection areas^

                    Rectangle2D scaledClickArea = new Rectangle2D.Double(ent[i].clickArea.x * screenRatioX,
                            ent[i].clickArea.y * screenRatioY, ent[i].clickArea.width * screenRatioX,
                            ent[i].clickArea.height * screenRatioY);

                    if(clickPoint != null && scaledClickArea.contains(clickPoint)) {
                        //System.out.println(ent[i].name + " was clicked! ");
                        for(int k = 0; k < ent.length; k++) {
                            if(ent[k] != null) {
                                ent[k].menuOn = false;
                            }
                        }
                        if(!menuOn) {
                            if(ent[i] != null) {
                                ent[i].menuOn = true;
                            }
                        }

                        clickPoint = null;
                    }
                    if(ent[i].menuOn) {
                        entMenuNum = i;
                        menuOn = true;
                    }
                    /*
                    if(drawCount == 0 && ent[i].name.equals("Farm") && rand.nextBoolean()){
                        System.out.println(ent[i].name);
                        System.out.println("clickArea " + ((Rectangle2D.Double) scaledClickArea).x + "x:y" + ((Rectangle2D.Double) scaledClickArea).y);
                        System.out.println(screenRatioX + " " + screenRatioY);
                        g2.setColor(Color.white);
                        g2.fill(scaledClickArea);
                    }
                     */

                    if(clickPoint != null) {
                        for(int j = 0; j < ent.length; j++) {
                            if(ent[i] != null) {
                                ent[i].menuOn = false;
                            }
                        }
                        menuOn = false;
                    }
                }
            }
        }
    }

    public void update() {
        if(gameState == playState) {
            player.update();
        }
        if (gameState == pauseState) {

        }
    }

    public void drawToTempScreen() {
        //debug
        long drawStart = 0;
        if(keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }


        //TITLE SCREEN or IN GAME check
        if(gameState == titleState) {
            ui.draw(g2);
        } else {
            // tile
            tileM.draw(g2);

            //faction territories
            if(showTerritory) {
                for(int i = 0; i < factions.length; i++){
                    if(factions[i] != null && factions[i].territory != null){
                        factions[i].updateTerritory();
                        g2.setColor(factions[i].factionColor);
                        g2.draw(factions[i].territory);
                    }
                }
            }

            //entities
            for(int i = 0; i < ent.length; i++) {
                if(ent[i] != null) {
                    ent[i].draw(g2, this);
                }
            }

            //object
            for(int i = 0; i < obj.length; i++) {
                if(obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }

            //faction flags
            if(keyH.drawFactionFlags && gameType != 3) {
                for(int i = 0; i < factions.length; i++) {
                    if(factions[i] != null) {
                        for(int k = 0; k < factions[i].factionBuildings.length; k++) {
                            if (factions[i].factionBuildings[k] instanceof Building) {
                                ((Building) factions[i].factionBuildings[k]).flag.draw(g2, this);
                            }
                        }
                    }
                }
            }

            player.draw(g2);

            //UI
            ui.draw(g2);
            if(menuOn) {
                ui.drawMenu(ent[entMenuNum]);
            }

            //DEBUG

            if(keyH.checkDrawTime) {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;
                g2.setColor(Color.white);
                g2.setFont(new Font("pixelText16b", Font.PLAIN, 30));
                g2.drawString("Draw Time: " + passed, 10, 400);
                //System.out.println("Draw Time: " + passed);
            }
        }
    }

    public void setFullScreen() {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void drawToScreen() {

        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, (int)screenWidth2, (int)screenHeight2, null);
        g.dispose();
    }

    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {

        music.stop();
    }
    public void playSE(int i) {

        se.setFile(i);
        se.play();
    }
    public void stopSE() {

        se.stop();
    }

    public boolean checkInRect(int x1, int y1, int x2, int y2, int x, int y) {
        return x > x1 && x < x2 &&
                y > y1 && y < y2;
    }

    public void getScreenRatio() {
        screenRatioX = screenWidth2 / screenWidth;
        screenRatioY = screenHeight2 / screenHeight;
    }

    //World gen
    //TODO: World gen methods
    public void genTiles(int x1, int x2, int y1, int y2) {

    }

    public void genFactions(int aiFactionsNum) {
        int xOffset = 0;
        int yOffset = 0;

        //Player
        switch (gameType) {
            case 0:
                System.out.println("Gametype 0 Error");
                System.exit(1);
                break;
            case 1:
                player.playerFaction.resources[0] = 0;
                player.playerFaction.resources[1] = 8;
                player.playerFaction.resources[2] = 8;
                player.playerFaction.resources[3] = 0;
                player.playerFaction.resources[4] = 0;
                player.playerFaction.resources[5] = 8;
                player.playerFaction.resources[6] = 0;
                player.playerFaction.resources[7] = 0;
                player.playerFaction.resources[8] = 10;

                player.playerFaction.isPlayer = true;
                player.playerFaction.playerRep = 999;
                player.playerFaction.relation = Faction.playerRelation.FRIENDLY;
                break;
            case 2:
                player.playerFaction.resources[0] = 9999;
                player.playerFaction.resources[1] = 9999;
                player.playerFaction.resources[2] = 9999;
                player.playerFaction.resources[3] = 9999;
                player.playerFaction.resources[4] = 9999;
                player.playerFaction.resources[5] = 9999;
                player.playerFaction.resources[6] = 9999;
                player.playerFaction.resources[7] = 9999;
                player.playerFaction.resources[8] = 9999;

                player.playerFaction.isPlayer = true;
                player.playerFaction.playerRep = 999;
                player.playerFaction.relation = Faction.playerRelation.FRIENDLY;
                break;
            case 3:
                //TODO: Simulation mode
                break;
        }
        System.out.println(gameType);
        factions[0] = player.playerFaction;
        if(gameType != 3){
            factions[0].factionBuildings[0] = new ENT_KingCourt(this, factions[0]);
            aSetter.addPremadeEntity(30, 30, factions[0].factionBuildings[0]);
            player.worldY = factions[0].factionBuildings[0].worldY;
            player.worldX = factions[0].factionBuildings[0].worldX;
            factions[0].factionBuildings[0].menuType = 2;
            factions[0].factionColor = Color.green;
        }
        player.playerFaction.name = "Player";
        player.playerFaction.gpPos = 0;

        //AI Factions
        for(int i = 1; i < aiFactionsNum + 1; i++) {
            factions[i] = new Faction(this);
            factions[i].playerRep = 0;
            factions[i].isPlayer = false;
            factions[i].isDefeated = false;
            factions[i].gpPos = i;
            //Faction Color
            float r = (float) (rand.nextFloat() / 2f + 0.1);
            float g = (float) (rand.nextFloat() / 2f + 0.1);
            float b = (float) (rand.nextFloat() / 2f + 0.1);
            factions[i].factionColor = new Color(r,g,b);
            //TODO: Generate all buildings
            factions[i].factionBuildings[0] = new ENT_KingCourt(this, factions[i]);
            //TODO: Select a rectangle on the map for AI faction starting territory and place generated buildings
            //TODO: Rectangle for territory is centered on King's Court and can expand
            aSetter.addPremadeEntity(30 * i + xOffset, 29 + yOffset, factions[i].factionBuildings[0]);
            factions[i].relation = Faction.playerRelation.NEUTRAL;
            factions[i].name = nameGen.newName();

            xOffset += 5;
            yOffset +=1;
        }

        //For testing
        factions[1].factionBuildings[1] = aSetter.newEntity("farm", factions[1], 10, 11);

        //place AI factions in corners
        if(aiFactionsNum >= 4) {
        //corner coords
            //TODO: make compatible with random sized maps
            factions[1].factionBuildings[0].worldX = 9 * tileSize;
            factions[1].factionBuildings[0].worldY = 10 * tileSize;

            factions[2].factionBuildings[0].worldX = 9 * tileSize;
            factions[2].factionBuildings[0].worldY = 57 * tileSize;

            factions[3].factionBuildings[0].worldX = 61 * tileSize;
            factions[3].factionBuildings[0].worldY = 10 * tileSize;

            factions[4].factionBuildings[0].worldX = 61 * tileSize;
            factions[4].factionBuildings[0].worldY = 57 * tileSize;
        }

        if(gameType != 3) {
            aSetter.setObject();
            aSetter.setEntity();
        }

        for(int i = 0; i < factions.length; i++) {
            if(factions[i] != null) {
                factions[i].updateBuildings();
                factions[i].updateTerritory();
            }
        }

        System.out.println("Faction flags updated");
    }

    @Override
    public synchronized void addMouseListener(MouseListener l) {
        super.addMouseListener(l);
    }
}
