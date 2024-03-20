package faction;

import entity.Building;
import entity.Entity;
import main.GamePanel;
import object.*;

import java.util.Arrays;

public class Faction {

    GamePanel gp;

    public int playerRep;
    public String name;
    public Boolean isDefeated = false;
    public Boolean isPlayer = false;


    //Resources
    //IMPORTANT, REMEMBER ORDER
    //order and starting values:
    //Index 0 = gold0, 1 = stone8, 2 = lumber8, 3 = money0, 4 = smokeleaf0, 5 = iron8, 6 = silk0, 7 = gem0, 8 = wheat10
    public int[] resources = new int[]{0,8,8,0,0,8,0,0,10};
    public int gpPos = -1;

    //TODO: King's Court as the central building, when clicked opens a menu to view whole faction
    public Entity[] factionBuildings = new Entity[99];
    public SuperObject[] flags = new SuperObject[99];

    public int population;

    public enum playerRelation {
        FRIENDLY,
        HOSTILE,
        NEUTRAL,
        ALLIED,
        DEFEATED

    }

    //Power based off of number of buildings, military, and resources
    public int power;

    //Default, change later
    public playerRelation relation = playerRelation.DEFEATED;

    public Faction(GamePanel gp) {
        this.gp = gp;
        //System.out.println((factionBuildings).length);
    }


    public void update() {
        if(!isPlayer && !isDefeated) {
            //TODO: build up a nation and all of that stuff
            //TODO: Create StateMachine and actionLock timer
            //actionLock: pray to our lord and saviour RyiSnow
        }
    }

    public void updateBuildings() {
        int flagX;
        int flagY;

        for(int i = 0; i < flags.length; i++) {
            if(flags[i] != null) {
                switch(relation) {
                    case DEFEATED:

                        flagX = flags[i].worldX;
                        flagY = flags[i].worldY;
                        flags[i] = new OBJ_WhiteFlag(gp);
                        flags[i].worldX = flagX;
                        flags[i].worldY = flagY;
                        break;
                    case ALLIED:
                        flagX = flags[i].worldX;
                        flagY = flags[i].worldY;
                        flags[i] = new OBJ_BlueFlag(gp);
                        flags[i].worldX = flagX;
                        flags[i].worldY = flagY;
                        break;
                    case FRIENDLY:
                        flagX = flags[i].worldX;
                        flagY = flags[i].worldY;
                        flags[i] = new OBJ_GreenFlag(gp);
                        flags[i].worldX = flagX;
                        flags[i].worldY = flagY;
                        break;
                    case HOSTILE:
                        flagX = flags[i].worldX;
                        flagY = flags[i].worldY;
                        flags[i] = new OBJ_RedFlag(gp);
                        flags[i].worldX = flagX;
                        flags[i].worldY = flagY;
                        break;
                    case NEUTRAL:
                        flagX = flags[i].worldX;
                        flagY = flags[i].worldY;
                        flags[i] = new OBJ_YellowFlag(gp);
                        flags[i].worldX = flagX;
                        flags[i].worldY = flagY;
                        break;
                }
            }

        }
        gp.factionFlags[gpPos] = flags;
    }

    @Override
    public String toString() {
        return name;
    }
}
