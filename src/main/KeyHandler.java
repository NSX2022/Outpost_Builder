package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    //DEBUG
    public boolean checkDrawTime = false;
    GamePanel gp;
    public int gameTypeHolder;

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
                            //TODO: Load last game from .dat file
                            break;
                        case 2:
                            //TODO: Controls/help screen
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
                            //TODO: Proper setup
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
                            //TODO: Proper setup
                            gp.gameType = gp.keyH.gameTypeHolder;
                            gp.genFactions(4);
                            gp.gameState = gp.playState;
                            gp.stopMusic();
                            gp.playMusic(1);
                            break;
                        case 3:
                            gameTypeHolder = 3;
                            gp.gameStart = true;
                            //TODO: Proper setup
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
        if(code == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }

        //TODO: Delete later
        if(code == KeyEvent.VK_M) {
            gp.ui.addMessage("Debug Message :)");
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
        if(code == KeyEvent.VK_F) {
            drawFactionFlags = !drawFactionFlags;
        }
        if(code == KeyEvent.VK_G) {
            gp.ui.showIcons = !gp.ui.showIcons;
        }
    }
}
