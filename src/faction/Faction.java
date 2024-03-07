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
    //Index 0 = gold, 1 = stone, 2 = lumber, 3 = money, 4 = greenleaf, 5 = iron, 6 = silk, 7 = gem
    public int[] resources;

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
