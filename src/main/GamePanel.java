package main;

import entity.Camera;
import entity.Entity;
import object.SuperObject;
import tile.TileManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable{
    //Screen Settings
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48*48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 9;
    public final int screenWidth = tileSize * maxScreenCol; // 768
    public final int screenHeight = tileSize * maxScreenRow; // 576
    public final int objDisplayLimit = 32;
    public final int entDisplayLimit = 32;
    Font pixelText16b;

    //FULLSCREEN
    public int screenWidth2 = screenWidth;
    public int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;

    //World Settings do not allow player to touch
    public final int maxWorldCol = 71;
    public final int maxWorldRow = 68;

    //system
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Sound music = new Sound();
    public Sound se = new Sound();
    public UI ui = new UI(this);
    public UtilityTool uTool = new UtilityTool();
    Thread gameThread;
    public int latestFPS;

    //entity and obj
    public Camera player = new Camera(this, keyH);
    public SuperObject[] obj = new SuperObject[objDisplayLimit];
    public Entity[] ent = new Entity[entDisplayLimit];

    //game state
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public int gameType = 0;
    //0 = default
    //1 = Normal
    //2 = Sandbox

    //fps
    int FPS = 60; //60

    //title logo
    public BufferedImage titleLogo;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
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
            titleLogo = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/entity/building_fortress.png")));
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

        aSetter.setObject();
        //playMusic(0);

        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth2, screenHeight2, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();

        setFullScreen();
    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta >= 1){ //delta >= 1
                update();
                drawToTempScreen();
                drawToScreen();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                latestFPS = drawCount;
                drawCount = 0;
                timer = 0;
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

            player.draw(g2);

            //UI
            ui.draw(g2);

            //DEBUG

            if(keyH.checkDrawTime) {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;
                g2.setColor(Color.white);
                g2.setFont(new Font("pixelText16b", Font.PLAIN, 30));
                g2.drawString("Draw Time: " + passed, 10, 400);
                System.out.println("Draw Time: " + passed);
            }
        }
        //g2.dispose();
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
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {

        se.stop();
    }
    public void playSE(int i) {

        se.setFile(i);
        se.play();
    }
}
