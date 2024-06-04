package faction;

import entity.*;
import main.GamePanel;
import object.*;
import tile.Tile;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Faction {

    GamePanel gp;

    public String name;
    public Boolean isDefeated = false;
    public Boolean isPlayer = false;
    public Area territory = new Area();
    public Area worldTerritory = new Area();


    //Resources
    //IMPORTANT, REMEMBER ORDER
    //order and starting values:
    //Index 0 = gold0, 1 = stone8, 2 = lumber8, 3 = money0, 4 = smokeleaf0, 5 = iron8, 6 = silk0, 7 = gem0, 8 = wheat10
    public int[] resources = new int[]{0,12,9,0,0,9,0,0,20};
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
    public Point placeAt = new Point(16, 16);

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
        factionBuildings = new Entity[gp.objDisplayLimit/4];
    }


    public void update() throws Exception {
        if(!isPlayer && !isDefeated) {
            Random rand = new Random();
            //TODO: build up a nation and all of that stuff

            if(!gp.ui.gameFinished){
                switch (currentState){
                    case economyState:
                        int[] incomes = new int[]{wheatIncome, lumberIncome, ironIncome, stoneIncome};

                        switch(rand.nextInt(0,4)){
                            case 0:
                                if(getSmallest(incomes, incomes.length) == wheatIncome) {
                                    nextBuilding = "Farm";
                                }else if(getSmallest(incomes, incomes.length) == lumberIncome){
                                    nextBuilding = "Lumberyard";
                                }else if(getSmallest(incomes, incomes.length) == ironIncome){
                                    nextBuilding = "Mine";
                                }else if(getSmallest(incomes, incomes.length) == stoneIncome){
                                    nextBuilding = "Quarry";
                                }
                                break;
                            case 1:
                                if(getSmallest(incomes, incomes.length) == lumberIncome) {
                                    nextBuilding = "Lumberyard";
                                }else if(getSmallest(incomes, incomes.length) == wheatIncome){
                                    nextBuilding = "Farm";
                                }else if(getSmallest(incomes, incomes.length) == stoneIncome){
                                    nextBuilding = "Quarry";
                                }else if(getSmallest(incomes, incomes.length) == ironIncome){
                                    nextBuilding = "Mine";
                                }
                                break;
                            case 2:
                                if(getSmallest(incomes, incomes.length) == ironIncome) {
                                    nextBuilding = "Mine";
                                }else if(getSmallest(incomes, incomes.length) == stoneIncome){
                                    nextBuilding = "Quarry";
                                }else if(getSmallest(incomes, incomes.length) == wheatIncome){
                                    nextBuilding = "Farm";
                                }else if(getSmallest(incomes, incomes.length) == lumberIncome){
                                    nextBuilding = "Lumberyard";
                                }
                                break;
                            case 3:
                                if(getSmallest(incomes, incomes.length) ==  stoneIncome) {
                                    nextBuilding = "Quarry";
                                }else if(getSmallest(incomes, incomes.length) == wheatIncome){
                                    nextBuilding = "Farm";
                                }else if(getSmallest(incomes, incomes.length) == lumberIncome){
                                    nextBuilding = "Lumberyard";
                                }else if(getSmallest(incomes, incomes.length) == ironIncome){
                                    nextBuilding = "Mine";
                                }
                                break;
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

                boolean stop = false;
                for(int i = 0; i < 30; i++) {
                    if(!stop) {
                        updateTerritory();
                        updateBuildings();
                        placeAt = getBuildPoint();
                        if (aiCanPlace()) {
                            build();
                            stop = true;
                        }
                    }
                }
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
            if(otherFactions[i] == null){
                continue;
            }
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
        if(hasLibrary){
            power *= 1.2;
        }
    }

    public void updateTerritory() {
        territory.reset();
        worldTerritory.reset();
        for(int i = 0; i < factionBuildings.length; i++) {
            if(factionBuildings[i] != null && factionBuildings[i] instanceof Building) {
                //math for centering
                Rectangle adjRect = new Rectangle(factionBuildings[i].landClaim.x - 72,
                        factionBuildings[i].landClaim.y - 72,
                        factionBuildings[i].landClaim.width, factionBuildings[i].landClaim.height);
                territory.add(new Area(adjRect));

                Rectangle worldRect = new Rectangle(factionBuildings[i].worldClaim.x - 72,
                        factionBuildings[i].worldClaim.y - 72, factionBuildings[i].worldClaim.width,
                        factionBuildings[i].worldClaim.height);
                worldTerritory.add(new Area(worldRect));
            }
        }
    }

    public boolean aiCanPlace() throws Exception {
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
            if(placeAt == null){
                if(gp.printDebugs) {
                    System.out.println("placeAt is null");
                }
                return false;
            }
            if((gp.tileM.getTile(placeAt.x, placeAt.y) == null)){

                if(gp.printDebugs) {
                    System.out.println("Faction.java line 256");
                    System.out.println("Tile at placeAt is null" + placeAt.x + ":" + placeAt.y);
                }

                return false;
            }
            if(worldTerritory.contains(placeAt)){
                if(gp.tileM.getTile(placeAt.x, placeAt.y).tags.contains("Water")){
                    if(gp.printDebugs) {
                        System.out.println("AI cant place on water");
                    }
                    return false;
                }
            }
            for(int i = 0; i < otherFactions.length; i++){
                if(otherFactions[i] == null){
                    continue;
                }
                if(otherFactions[i].worldTerritory.contains(placeAt) || otherFactions[i].worldTerritory.intersects(new Rectangle(placeAt.x, placeAt.y, 48, 48))){
                    if(gp.printDebugs) {
                        System.out.println("AI cant place on other territory");
                    }
                    return false;
                }
            }
            if(!worldTerritory.contains(placeAt)){
                if(gp.printDebugs) {
                    System.out.println("AI cant place outside of territory");
                }
                return false;
            }
            for(int i = 0; i < factionBuildings.length; i++){
                if(factionBuildings[i] == null){
                    continue;
                }
                if(factionBuildings[i].worldX == placeAt.x && factionBuildings[i].worldY == placeAt.y){
                    if(gp.printDebugs) {
                        System.out.println("AI cant place on a building");
                    }
                    return false;
                }
            }

        }else{
            if(gp.printDebugs) {
                System.out.println("AI cant afford");
            }
            return false;
        }

        return true;
    }

    public Point getBuildPoint() {
        int x;
        int y;
        Random rand = gp.rand;

        ArrayList<Tile> contained = new ArrayList<>();
        ArrayList<Tile> inter = new ArrayList<>();

        for (int col = gp.waterBuffer - 1; col < gp.maxWorldCol - gp.waterBuffer; col++) {
            for (int row = gp.waterBuffer - 1; row < gp.maxWorldRow - gp.waterBuffer; row++) {
                Tile tile = gp.tileM.landTiles[col][row];
                if (tile != null) {
                    switch (tile.inFactionTerritory(this)){
                        case 0:
                            /*if(gp.printDebugs) {
                                System.out.println("Tile in territory type 0");
                            }

                             */
                            break;
                        case 1:
                            if(!tile.tags.contains("Water")){
                                if(gp.printDebugs) {
                                    System.out.println("Tile in territory type 1++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                                }
                                contained.add(tile);
                            }else{
                                //ai selected water tile
                            }

                            break;
                        case 2:
                            if(!tile.tags.contains("Water")){
                                if(gp.printDebugs) {
                                    System.out.println("Tile in territory type 2++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                                }
                                inter.add(tile);
                            }else{
                                //ai tried to place in water
                            }
                            break;
                    }
                }
            }
        }

        if(gp.printDebugs) {
            System.out.println(contained.size());
            System.out.println(inter.size());
        }

        Tile chosen = null;

        if(currentState == economyState){
            if(rand.nextBoolean()){
                chosen = contained.get(rand.nextInt(contained.size()));
            }else{
                if(gp.printDebugs) {
                    System.out.println("line 332 GamePanel");
                }
                chosen = inter.get(rand.nextInt(inter.size()));

            }
        } else if (currentState == expandState) {
            chosen = inter.get(rand.nextInt(inter.size()));
        }

        assert chosen != null;

        x = chosen.buildPoint.x;
        y = chosen.buildPoint.y;

        x = Math.round((float) x / gp.tileSize)*gp.tileSize;
        y = Math.round((float) y / gp.tileSize)*gp.tileSize;

        return new Point(x, y);
    }

    public void setupOtherFactions(){
        //ONLY USE THIS ONCE
        for(int i = 0; i < gp.factions.length; i++){
            if(gp.factions[i] != this){
                otherFactions[nearestEmpty()] = gp.factions[i];
            }
        }
    }

    public void build(){
        switch(nextBuilding){
            case "Farm":
                for (int i = 0; i < this.factionBuildings.length; i++) {
                    if (this.factionBuildings[i] == null) {
                        Entity ent = new ENT_Farm(gp, this);
                        this.factionBuildings[i] = ent;
                        ent.worldX = placeAt.x;
                        ent.worldY = placeAt.y;
                        for (int j = 0; j < gp.ent.length; j++) {
                            if (gp.ent[j] == null) {
                                gp.ent[j] = ent;
                                j = 9999999;
                            }
                        }

                        gp.keyH.subtractResources(this, gp.ui.farmCost);
                        gp.updateFlags();
                        gp.ui.incrementCost(nextBuilding);
                        wheatIncome++;
                        break;
                    }
                }
                break;
            case "Mine":
                for (int i = 0; i < this.factionBuildings.length; i++) {
                    if (this.factionBuildings[i] == null) {
                        Entity ent = new ENT_Mine(gp, this);
                        this.factionBuildings[i] = ent;
                        ent.worldX = placeAt.x;
                        ent.worldY = placeAt.y;
                        for (int j = 0; j < gp.ent.length; j++) {
                            if (gp.ent[j] == null) {
                                gp.ent[j] = ent;
                                j = 9999999;
                            }
                        }

                        gp.keyH.subtractResources(this, gp.ui.mineCost);
                        gp.updateFlags();
                        gp.ui.incrementCost(nextBuilding);
                        ironIncome++;
                        break;
                    }
                }
                break;
            case "Fort":
                for (int i = 0; i < this.factionBuildings.length; i++) {
                    if (this.factionBuildings[i] == null) {
                        Entity ent = new ENT_Fortress(gp, this);
                        this.factionBuildings[i] = ent;
                        ent.worldX = placeAt.x;
                        ent.worldY = placeAt.y;
                        for (int j = 0; j < gp.ent.length; j++) {
                            if (gp.ent[j] == null) {
                                gp.ent[j] = ent;
                                j = 9999999;
                            }
                        }

                        gp.keyH.subtractResources(this, gp.ui.fortCost);
                        gp.updateFlags();
                        gp.ui.incrementCost(nextBuilding);
                        hasFort = true;
                        break;
                    }
                }
                break;
            case "Outpost":
                for (int i = 0; i < this.factionBuildings.length; i++) {
                    if (this.factionBuildings[i] == null) {
                        Entity ent = new ENT_Outpost(gp, this);
                        this.factionBuildings[i] = ent;
                        ent.worldX = placeAt.x;
                        ent.worldY = placeAt.y;
                        for (int j = 0; j < gp.ent.length; j++) {
                            if (gp.ent[j] == null) {
                                gp.ent[j] = ent;
                                j = 9999999;
                            }
                        }

                        gp.keyH.subtractResources(this, gp.ui.outpostCost);
                        gp.updateFlags();
                        gp.ui.incrementCost(nextBuilding);
                        break;
                    }
                }
                break;
            case "Wall":
                for (int i = 0; i < this.factionBuildings.length; i++) {
                    if (this.factionBuildings[i] == null) {
                        Entity ent = new ENT_WallCross(gp, this);
                        this.factionBuildings[i] = ent;
                        ent.worldX = placeAt.x;
                        ent.worldY = placeAt.y;
                        for (int j = 0; j < gp.ent.length; j++) {
                            if (gp.ent[j] == null) {
                                gp.ent[j] = ent;
                                j = 9999999;
                            }
                        }

                        gp.keyH.subtractResources(this, gp.ui.wallCost);
                        gp.updateFlags();
                        gp.ui.incrementCost(nextBuilding);
                        break;
                    }
                }
                break;
            case "Lumberyard":
                for (int i = 0; i < this.factionBuildings.length; i++) {
                    if (this.factionBuildings[i] == null) {
                        Entity ent = new ENT_Lumberyard(gp, this);
                        this.factionBuildings[i] = ent;
                        ent.worldX = placeAt.x;
                        ent.worldY = placeAt.y;
                        for (int j = 0; j < gp.ent.length; j++) {
                            if (gp.ent[j] == null) {
                                gp.ent[j] = ent;
                                j = 9999999;
                            }
                        }

                        gp.keyH.subtractResources(this, gp.ui.lumberyardCost);
                        gp.updateFlags();
                        gp.ui.incrementCost(nextBuilding);
                        lumberIncome++;
                        break;
                    }
                }
                break;
            case "Quarry":
                for (int i = 0; i < this.factionBuildings.length; i++) {
                    if (this.factionBuildings[i] == null) {
                        Entity ent = new ENT_Quarry(gp, this);
                        this.factionBuildings[i] = ent;
                        ent.worldX = placeAt.x;
                        ent.worldY = placeAt.y;
                        for (int j = 0; j < gp.ent.length; j++) {
                            if (gp.ent[j] == null) {
                                gp.ent[j] = ent;
                                j = 9999999;
                            }
                        }

                        gp.keyH.subtractResources(this, gp.ui.quarryCost);
                        gp.updateFlags();
                        gp.ui.incrementCost(nextBuilding);
                        stoneIncome++;
                        break;
                    }
                }
                break;
            case "Library":
                for (int i = 0; i < this.factionBuildings.length; i++) {
                    if (this.factionBuildings[i] == null) {
                        Entity ent = new ENT_Library(gp, this);
                        this.factionBuildings[i] = ent;
                        ent.worldX = placeAt.x;
                        ent.worldY = placeAt.y;
                        for (int j = 0; j < gp.ent.length; j++) {
                            if (gp.ent[j] == null) {
                                gp.ent[j] = ent;
                                j = 9999999;
                            }
                        }

                        gp.keyH.subtractResources(this, gp.ui.libraryCost);
                        gp.updateFlags();
                        gp.ui.incrementCost(nextBuilding);
                        hasLibrary = true;
                        break;
                    }
                }
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
