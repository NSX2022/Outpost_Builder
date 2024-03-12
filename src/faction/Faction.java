package faction;

import entity.Building;
import entity.Entity;
import main.GamePanel;

public class Faction {

    GamePanel gp;

    public Entity[] factionBuildings;
    public int playerRep;
    public String name;
    public Boolean isDefeated = false;
    public Boolean isPlayer = false;


    //Resources
    //IMPORTANT, REMEMBER ORDER
    //order and starting values:
    //Index 0 = gold0, 1 = stone8, 2 = lumber8, 3 = money0, 4 = smokeleaf0, 5 = iron8, 6 = silk0, 7 = gem0, 8 = wheat10
    public int[] resources = new int[]{0,8,8,0,0,8,0,0,10};

    //TODO: King's Court as the central building, when clicked opens a menu to view whole faction
    public Building[] buildings;

    public int population;
    public enum playerRelation {
        FRIENDLY,
        HOSTILE,
        NEUTRAL,
        ALLIED,
        DEFEATED

    };
    //Default, change later
    public playerRelation relation = playerRelation.DEFEATED;

    public Faction(GamePanel gp) {
        this.gp = gp;
    }

    public void genFaction() {

    }

    public void update() {
        if(!isPlayer && !isDefeated) {
            //TODO: build up a nation and all of that stuff
        }
    }
}
