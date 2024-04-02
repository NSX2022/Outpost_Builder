package faction;

import entity.Building;
import entity.Entity;
import main.GamePanel;
import object.*;

import java.awt.*;
import java.awt.geom.Area;
import java.util.Arrays;

public class Faction {

    GamePanel gp;

    public int playerRep;
    public String name;
    public Boolean isDefeated = false;
    public Boolean isPlayer = false;
    public Area territory = new Area();


    //Resources
    //IMPORTANT, REMEMBER ORDER
    //order and starting values:
    //Index 0 = gold0, 1 = stone8, 2 = lumber8, 3 = money0, 4 = smokeleaf0, 5 = iron8, 6 = silk0, 7 = gem0, 8 = wheat10
    public int[] resources = new int[]{0,8,8,0,0,8,0,0,10};
    public int gpPos = -1;

    //King's Court as the central building, when clicked opens a menu to view whole faction?
    public Entity[] factionBuildings = new Entity[99];

    public int population;
    public Color factionColor;

    public enum playerRelation {
        FRIENDLY,
        HOSTILE,
        NEUTRAL,
        ALLIED,
        DEFEATED

    }

    //Power based off of number of buildings, military
    public int power = 0;

    //Default
    public playerRelation relation = playerRelation.DEFEATED;

    public Faction(GamePanel gp) {
        this.gp = gp;
        //System.out.println((factionBuildings).length);
    }


    public void update() {
        if(!isPlayer && !isDefeated) {
            //TODO: build up a nation and all of that stuff
            //TODO: Create StateMachine and actionLock timer
            //TODO: Update power
            //actionLock: pray to our lord and saviour RyiSnow
        }
    }

    public void updateBuildings() {
        //Updated for new flag system

        for(int i = 0; i < factionBuildings.length; i++) {
            if(factionBuildings[i] != null && factionBuildings[i] instanceof Building) {
                switch(relation) {
                    case DEFEATED:
                        ((Building) factionBuildings[i]).flag.image = ((Building) factionBuildings[i]).flag.images[0];
                        break;
                    case ALLIED:
                        ((Building) factionBuildings[i]).flag.image = ((Building) factionBuildings[i]).flag.images[3];
                        break;
                    case FRIENDLY:
                        ((Building) factionBuildings[i]).flag.image = ((Building) factionBuildings[i]).flag.images[4];
                        break;
                    case HOSTILE:
                        ((Building) factionBuildings[i]).flag.image = ((Building) factionBuildings[i]).flag.images[2];
                        break;
                    case NEUTRAL:
                        ((Building) factionBuildings[i]).flag.image = ((Building) factionBuildings[i]).flag.images[1];
                        break;
                }
                ((Building) factionBuildings[i]).flag.worldX = factionBuildings[i].worldX;
                ((Building) factionBuildings[i]).flag.worldY = factionBuildings[i].worldY;
            }
        }
    }

    public void updateTerritory() {
        territory.reset();
        for(int i = 0; i < factionBuildings.length; i++) {
            if(factionBuildings[i] != null && factionBuildings[i] instanceof Building) {
                //math for centering
                Rectangle adjRect = new Rectangle(factionBuildings[i].landClaim.x - 72,
                        factionBuildings[i].landClaim.y - 72,
                        factionBuildings[i].landClaim.width, factionBuildings[i].landClaim.height);
                territory.add(new Area(adjRect));
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
