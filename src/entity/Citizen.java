package entity;

import faction.Faction;
import main.GamePanel;

import java.awt.*;

public class Citizen extends Entity {

    public Faction faction;
    //default ID for Citizens
    public int menuType = 999;


    public Citizen(GamePanel gp, Faction faction) {
        super(gp);
        this.faction = faction;
        landClaim = new Rectangle(0,0,0,0);
    }

    //Can be a soldier, spy, merchant, etc. that goes around the map doing stuff
    //TODO: Everything
}
