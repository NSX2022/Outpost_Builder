package entity;

import faction.Faction;
import main.GamePanel;

public class Citizen extends Entity {

    public Faction faction;

    public Citizen(GamePanel gp, Faction faction) {
        super(gp);
        this.faction = faction;
    }

    //Can be a soldier, spy, merchant, etc. that goes around the map doing stuff
}
