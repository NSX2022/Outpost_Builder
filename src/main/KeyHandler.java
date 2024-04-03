package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    //DEBUG
    public boolean checkDrawTime = false;
    GamePanel gp;
    public int gameTypeHolder;
    public int toPlace;
    public boolean canPlace = false;

    //In-game view
    public boolean drawFactionFlags = true;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //Unused
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //TITLE SCREEN

        if(gp.gameState == gp.titleState) {
            if(gp.ui.titleScreenState == 0) {
                if(code == KeyEvent.VK_SPACE) {
                    switch(gp.ui.commandNum) {
                        case 0:
                            gp.ui.titleScreenState = 1;
                            break;
                        case 1:
                            //TODO: Load and enter last game from .dat file
                            break;
                        case 2:
                            gp.ui.titleScreenState = 2;
                            break;
                        case 3:
                            System.exit(0);
                            break;
                    }
                }
                if(code == KeyEvent.VK_W) {
                    if(gp.ui.commandNum > 0) {
                        gp.ui.commandNum--;
                    }
                }
                if(code == KeyEvent.VK_S) {
                    if(gp.ui.commandNum < 3) {
                        gp.ui.commandNum++;
                    }
                }
            }else if(gp.ui.titleScreenState == 1){
                if(code == KeyEvent.VK_SPACE) {
                    switch(gp.ui.commandNum) {
                        case 0:
                            gp.ui.titleScreenState = 0;
                            break;
                        case 1:
                            gameTypeHolder = 1;
                            gp.gameStart = true;
                            gp.gameType = gp.keyH.gameTypeHolder;
                            gp.genFactions(4);
                            gp.gameState = gp.playState;
                            gp.stopMusic();
                            gp.playMusic(1);
                            break;
                        case 2:
                            //Sandbox
                            gameTypeHolder = 2;
                            System.out.println(gp.gameType);
                            gp.gameStart = true;
                            gp.gameType = gp.keyH.gameTypeHolder;
                            gp.genFactions(4);
                            gp.gameState = gp.playState;
                            gp.stopMusic();
                            gp.playMusic(1);
                            break;
                        case 3:
                            gameTypeHolder = 3;
                            gp.gameStart = true;
                            gp.gameType = gp.keyH.gameTypeHolder;
                            gp.genFactions(4);
                            gp.gameState = gp.playState;
                            gp.stopMusic();
                            gp.playMusic(1);
                    }
                }
                if(code == KeyEvent.VK_W) {
                    if(gp.ui.commandNum > 0) {
                        gp.ui.commandNum--;
                    }
                }
                if(code == KeyEvent.VK_S) {
                    if(gp.ui.commandNum < 3) {
                        gp.ui.commandNum++;
                    }
                }
            } else if(gp.ui.titleScreenState == 2) {

                if(code == KeyEvent.VK_SPACE) {
                    gp.ui.titleScreenState = 0;
                }
            }

        }

        //CAMERA
        if(code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if(code == KeyEvent.VK_P) {
            if(gp.gameState == gp.playState){
                gp.gameState = gp.pauseState;
            }else if(gp.gameState == gp.pauseState){
                gp.gameState = gp.playState;
            }
        }
        /*TEST: Zoom in/out

        if(code == KeyEvent.VK_MINUS){
            gp.zoomInOut(-1);
        }
        if(code == KeyEvent.VK_EQUALS){
            gp.zoomInOut(1);
        }
         */
        if(code == KeyEvent.VK_ESCAPE) {
            //TODO save before exiting
            System.exit(0);
        }

        //TODO: Delete later
        if(code == KeyEvent.VK_M) {
            gp.ui.addMessage("Debug Message :)");
        }

        //TODO: Arrow keys to move where a building should be placed after selecting it
        if(gp.ui.buildMenu){
            if(code == KeyEvent.VK_UP) {
                gp.ui.preview.worldY -= gp.tileSize;
            }
            if(code == KeyEvent.VK_DOWN) {
                gp.ui.preview.worldY += gp.tileSize;
            }
            if(code == KeyEvent.VK_LEFT) {
                gp.ui.preview.worldX -= gp.tileSize;
            }
            if(code == KeyEvent.VK_RIGHT) {
                gp.ui.preview.worldX += gp.tileSize;
            }

            //Select building from menu
            if(code == KeyEvent.VK_1){
                gp.ui.addMessage("ENTER to place Farm");
                toPlace = 1;
                gp.ui.preview.setImage(gp.ui.farm);
                gp.ui.preview.worldX = Math.round((float) gp.player.worldX / gp.tileSize)*gp.tileSize;
                gp.ui.preview.worldY = Math.round((float) gp.player.worldY / gp.tileSize)*gp.tileSize;
            }
            if(code == KeyEvent.VK_2){
                gp.ui.addMessage("ENTER to place Mine");
                toPlace = 2;
                gp.ui.preview.setImage(gp.ui.mine);
                gp.ui.preview.worldX = Math.round((float) gp.player.worldX / gp.tileSize)*gp.tileSize;
                gp.ui.preview.worldY = Math.round((float) gp.player.worldY / gp.tileSize)*gp.tileSize;
            }
            if(code == KeyEvent.VK_3){
                gp.ui.addMessage("ENTER to place Fortress");
                toPlace = 3;
                gp.ui.preview.setImage(gp.ui.fortress);
                gp.ui.preview.worldX = Math.round((float) gp.player.worldX / gp.tileSize)*gp.tileSize;
                gp.ui.preview.worldY = Math.round((float) gp.player.worldY / gp.tileSize)*gp.tileSize;
            }
            if(code == KeyEvent.VK_4){
                gp.ui.addMessage("ENTER to place Outpost");
                toPlace = 4;
                gp.ui.preview.setImage(gp.ui.outpost);
                gp.ui.preview.worldX = Math.round((float) gp.player.worldX / gp.tileSize)*gp.tileSize;
                gp.ui.preview.worldY = Math.round((float) gp.player.worldY / gp.tileSize)*gp.tileSize;
            }
            if(code == KeyEvent.VK_5){
                gp.ui.addMessage("ENTER to place Wall");
                toPlace = 5;
                gp.ui.preview.setImage(gp.ui.wall);
                gp.ui.preview.worldX = Math.round((float) gp.player.worldX / gp.tileSize)*gp.tileSize;
                gp.ui.preview.worldY = Math.round((float) gp.player.worldY / gp.tileSize)*gp.tileSize;
            }
            if(code == KeyEvent.VK_6){
                gp.ui.addMessage("ENTER to place Lumberyard");
                toPlace = 6;
                gp.ui.preview.setImage(gp.ui.logging_camp);
                gp.ui.preview.worldX = Math.round((float) gp.player.worldX / gp.tileSize)*gp.tileSize;
                gp.ui.preview.worldY = Math.round((float) gp.player.worldY / gp.tileSize)*gp.tileSize;
            }
            if(code == KeyEvent.VK_7){
                gp.ui.addMessage("ENTER to place Quarry");
                toPlace = 7;
                gp.ui.preview.setImage(gp.ui.quarry);
                gp.ui.preview.worldX = Math.round((float) gp.player.worldX / gp.tileSize)*gp.tileSize;
                gp.ui.preview.worldY = Math.round((float) gp.player.worldY / gp.tileSize)*gp.tileSize;
            }

        }
        if(code == KeyEvent.VK_R){
            gp.player.worldX = (gp.maxWorldCol / 2) * gp.tileSize;;
            gp.player.worldY = (gp.maxWorldRow / 2) * gp.tileSize;;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }

        //DEBUG
        if(code == KeyEvent.VK_T) {
            checkDrawTime = !checkDrawTime;
        }

        //ui and player settings
        if(code == KeyEvent.VK_F) {
            drawFactionFlags = !drawFactionFlags;
        }
        if(code == KeyEvent.VK_G) {
            gp.ui.showIcons = !gp.ui.showIcons;
        }
        if(code == KeyEvent.VK_C) {
            gp.showTerritory = !gp.showTerritory;
        }

        //Build menu
        if(code == KeyEvent.VK_SHIFT){
            if(gp.ui.buildMenu){
                //TODO: Unselect to-be-built building
                canPlace = false;
                gp.ui.preview.worldX = 99999;
                gp.ui.preview.worldY = 99999;

            }else{
                canPlace = true;
            }
            gp.ui.buildMenu = !gp.ui.buildMenu;
        }
        if(canPlace){
            if(code == KeyEvent.VK_ENTER){
                //TODO: place building after checking if space occupied by building or entity
                if(gp.factions[0].territory.intersects(gp.ui.preview.territoryCheck)){
                    //check all entities and objects
                    //testing
                    //System.out.println("toPlace value: " + toPlace);

                }
            }
        }
    }
}
