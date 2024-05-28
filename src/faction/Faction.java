package faction;

import entity.Building;
import entity.ENT_WallCross;
import entity.Entity;
import main.GamePanel;
import object.*;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.util.Arrays;
import java.util.Random;

public class Faction {

    GamePanel gp;

    public String name;
    public Boolean isDefeated = false;
    public Boolean isPlayer = false;
    public Area territory = new Area();


    //Resources
    //IMPORTANT, REMEMBER ORDER
    //order and starting values:
    //Index 0 = gold0, 1 = stone8, 2 = lumber8, 3 = money0, 4 = smokeleaf0, 5 = iron8, 6 = silk0, 7 = gem0, 8 = wheat10
    public int[] resources = new int[]{0,12,8,0,0,8,0,0,20};
    public int gpPos = -1;

    //King's Court as the central building, when clicked opens a menu to view whole faction?
    public Entity[] factionBuildings;

    public Color factionColor;

    //Progression
    public boolean hasFort = false;
    public boolean hasLibrary = false;

    //AI
    private String nextBuilding;
    //use if needed --> private String priorityResource;
    private final int economyState = 0;
    //Build up resource generators
    private final int expandState = 1;
    //Capture large amounts of territory
    private final int rushPortState = 2;
    //Build access to ports
    private final int captureResourcesState = 3;
    //Seize control of natural resources (trees, boulders, etc.)
    public int currentState = economyState;
    private int stoneIncome = 0;
    private int wheatIncome = 0;
    private int ironIncome = 0;
    private int lumberIncome = 0;
    private Faction[] otherFactions = new Faction[99];
    public Point placeAt;

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
        relation = playerRelation.NEUTRAL;
        factionBuildings = new Entity[gp.objDisplayLimit];
    }


    public void update() {
        if(!isPlayer && !isDefeated) {
            setupOtherFactions();
            //TODO: build up a nation and all of that stuff

            if(!gp.ui.gameFinished){
                switch (currentState){
                    case economyState:
                        int[] incomes = new int[]{wheatIncome, lumberIncome, ironIncome, stoneIncome};

                        if(getSmallest(incomes, incomes.length) == wheatIncome) {
                            nextBuilding = "Farm";
                        }else if(getSmallest(incomes, incomes.length) == lumberIncome){
                            nextBuilding = "Lumberyard";
                        }else if(getSmallest(incomes, incomes.length) == ironIncome){
                            nextBuilding = "Mine";
                        }else if(getSmallest(incomes, incomes.length) == stoneIncome){
                            nextBuilding = "Quarry";
                        }

                        if(buildLibFort()) {
                            if (canAfford(gp.ui.libraryCost) && !hasLibrary) {
                                nextBuilding = "Library";
                            } else if (canAfford(gp.ui.fortCost) && !hasFort) {
                                nextBuilding = "Fort";
                            }
                        }

                        break;
                    case expandState:
                        nextBuilding = "Outpost";
                        break;
                    case rushPortState:
                        break;
                    case captureResourcesState:
                        break;
                }
                placeAt = getBuildPoint();
                while(!aiCanPlace()){
                    placeAt = getBuildPoint();
                }
                build();
            }
        }
    }

    public boolean buildLibFort(){
        if(!hasMostPower()){
            return true;
        }
        return false;
    }

    public boolean hasMostPower(){

        for(int i = 0; i < otherFactions.length; i++){
            if(this.power < otherFactions[i].power){
                return false;
            }
        }

        return true;
    }

    public int getSmallest(int[] a, int total){
        int temp;
        for (int i = 0; i < total; i++)
        {
            for (int j = i + 1; j < total; j++)
            {
                if (a[i] > a[j])
                {
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
        return a[0];
    }

    public void updateBuildings() {
        //Updated for new flag system

        for(int i = 0; i < factionBuildings.length; i++) {
            if(factionBuildings[i] != null && factionBuildings[i] instanceof Building && !(factionBuildings[i] instanceof ENT_WallCross)) {
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

        power = 0;
        for(int i = 0; i < factionBuildings.length; i++){
            if(factionBuildings[i] != null && factionBuildings[i] instanceof Building && !(factionBuildings[i] instanceof ENT_WallCross)) {
                if(((Building)factionBuildings[i]).powerVal > 0) {
                    power += ((Building)factionBuildings[i]).powerVal;
                }
            }
        }
        if(hasFort){
            power *= 1.2;
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

    //TODO
    public boolean aiCanPlace(){
        int[] cost = new int[99];

        switch(nextBuilding){
            //TODO
            case "Farm":
                cost = gp.ui.farmCost;
                break;
            case "Mine":
                cost = gp.ui.mineCost;
                break;
            case "Fort":
                cost = gp.ui.fortCost;
                break;
            case "Outpost":
                cost = gp.ui.outpostCost;
                break;
            case "Wall":
                cost = gp.ui.wallCost;
                break;
            case "Lumberyard":
                cost = gp.ui.lumberyardCost;
                break;
            case "Quarry":
                cost = gp.ui.quarryCost;
                break;
            case "Library":
                cost = gp.ui.libraryCost;
                break;
        }

        if(canAfford(cost)){
            if(territory.contains(placeAt)){
                if(gp.tileM.getTile(placeAt.x, placeAt.y).tags.contains("Water")){
                    return false;
                }
            }
            for(int i = 0; i < otherFactions.length; i++){
                if(otherFactions[i].territory.contains(placeAt)){
                    return false;
                }
            }
            if(!territory.contains(placeAt)){
                return false;
            }
            for(int i = 0; i < factionBuildings.length; i++){
                if(factionBuildings[i].worldX == placeAt.x && factionBuildings[i].worldY == placeAt.y){
                    return false;
                }
            }

        }else{
            return false;
        }
        return true;
    }

    public Point getBuildPoint() {
        int x = -1;
        int y = -1;
        Random rand = gp.rand;

        // Convert the Area to line segments
        PathIterator pathIterator = territory.getPathIterator(null);
        double[] coords = new double[6];
        double perimeter = 0;

        while (!pathIterator.isDone()) {
            int type = pathIterator.currentSegment(coords);
            if (type == PathIterator.SEG_LINETO) {
                double x1 = coords[0];
                double y1 = coords[1];
                double x2 = coords[2];
                double y2 = coords[3];
                perimeter += Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
            }
            pathIterator.next();
        }

        // Generate a random number within the range [0, perimeter]
        double randomValue = rand.nextDouble() * perimeter;

        // Find the corresponding segment
        double remainingValue = randomValue;
        pathIterator = territory.getPathIterator(null);
        while (!pathIterator.isDone()) {
            int type = pathIterator.currentSegment(coords);
            if (type == PathIterator.SEG_LINETO) {
                double x1 = coords[0];
                double y1 = coords[1];
                double x2 = coords[2];
                double y2 = coords[3];
                double segmentLength = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
                if (remainingValue <= segmentLength) {
                    double fraction = remainingValue / segmentLength;
                    double randomX = x1 + fraction * (x2 - x1);
                    double randomY = y1 + fraction * (y2 - y1);
                    x = (int) Math.round(randomX);
                    y = (int) Math.round(randomY);
                    break;
                }
                remainingValue -= segmentLength;
            }
            pathIterator.next();
        }

        x = Math.round((float) x / gp.tileSize)*gp.tileSize;
        y = Math.round((float) y / gp.tileSize)*gp.tileSize;

        return new Point(x, y);
    }

    public void setupOtherFactions(){
        for(int i = 0; i < gp.factions.length; i++){
            if(gp.factions[i] != this){
                otherFactions[nearestEmpty()] = gp.factions[i];
            }
        }
    }

    public void build(){
        //TODO
        switch(nextBuilding){
            //TODO
            case "Farm":

                break;
            case "Mine":

                break;
            case "Fort":

                break;
            case "Outpost":

                break;
            case "Wall":

                break;
            case "Lumberyard":

                break;
            case "Quarry":

                break;
            case "Library":

                break;
        }
    }

    private int nearestEmpty(){
        for(int i = 0; i < otherFactions.length; i++){
            if(otherFactions[i] == null){
                return i;
            }
        }

        return -1;
    }

    public boolean canAfford(int[] costs){
        for(int i = 0; i < gp.factions[0].resources.length; i++){
            if(this.resources[i] < costs[i]){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
}
