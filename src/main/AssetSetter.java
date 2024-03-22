package main;


import entity.*;
import faction.Faction;
import object.*;

public class AssetSetter {

    GamePanel gp;

    Entity ent;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        //mainly a test function
        //all on-world-gen objects in beta ver will generate randomly

    }

    public void setEntity() {
        addEntity(51, 41, "tree", null);

        addEntity(51, 35, "farm", gp.player.playerFaction);
        addEntity(51, 34, "mine", gp.player.playerFaction);
    }

    public void addEntity(int x, int y, String entName, Faction faction){
        //Place a Building
        switch(entName) {
            case "farm":
                for(int i = 0; i < gp.ent.length; i++)  {
                    if(gp.ent[i] == null) {
                        ent = new ENT_Farm(gp, faction);
                        ent.worldX = x * gp.tileSize;
                        ent.worldY = y * gp.tileSize;
                        gp.ent[i] = ent;
                        i = gp.ent.length;
                    }
                }
                for(int i = 0; i < gp.factions[faction.gpPos].factionBuildings.length; i++) {
                    if(gp.factions[faction.gpPos].factionBuildings[i] == null) {
                        gp.factions[faction.gpPos].factionBuildings[i] = ent;

                        i = gp.factions[faction.gpPos].factionBuildings.length;
                    }
                }
                break;
            case "mine":
                for(int i = 0; i < gp.ent.length; i++)  {
                    if(gp.ent[i] == null) {
                        ent = new ENT_Mine(gp, faction);
                        ent.worldX = x * gp.tileSize;
                        ent.worldY = y * gp.tileSize;
                        gp.ent[i] = ent;
                        i = gp.obj.length;
                    }
                }
                for(int i = 0; i < gp.factions[faction.gpPos].factionBuildings.length; i++) {
                    if(gp.factions[faction.gpPos].factionBuildings[i] == null) {
                        gp.factions[faction.gpPos].factionBuildings[i] = ent;

                        i = gp.factions[faction.gpPos].factionBuildings.length;
                    }
                }
                break;
            case "king_court":
                for(int i = 0; i < gp.ent.length; i++)  {
                    if(gp.ent[i] == null) {
                        ent = new ENT_KingCourt(gp, faction);
                        ent.worldX = x * gp.tileSize;
                        ent.worldY = y * gp.tileSize;
                        gp.ent[i] = ent;
                        i = gp.obj.length;
                    }
                }
                for(int i = 0; i < gp.factions[faction.gpPos].factionBuildings.length; i++) {
                    if(gp.factions[faction.gpPos].factionBuildings[i] == null) {
                        gp.factions[faction.gpPos].factionBuildings[i] = ent;

                        i = gp.factions[faction.gpPos].factionBuildings.length;
                    }
                }
                break;
            case "hut_building":
                for(int i = 0; i < gp.ent.length; i++)  {
                    if(gp.ent[i] == null) {
                        ent = new ENT_Hut(gp, faction);
                        ent.worldX = x * gp.tileSize;
                        ent.worldY = y * gp.tileSize;

                        gp.ent[i] = ent;
                        i = gp.obj.length;
                    }
                }
                for(int i = 0; i < gp.factions[faction.gpPos].factionBuildings.length; i++) {
                    if(gp.factions[faction.gpPos].factionBuildings[i] == null) {
                        gp.factions[faction.gpPos].factionBuildings[i] = ent;

                        i = gp.factions[faction.gpPos].factionBuildings.length;
                    }
                }
                break;
            case "building_fortress":
                for(int i = 0; i < gp.ent.length; i++)  {
                    if(gp.ent[i] == null) {
                        ent = new ENT_Fortress(gp, faction);
                        ent.worldX = x * gp.tileSize;
                        ent.worldY = y * gp.tileSize;

                        gp.ent[i] = ent;
                        i = gp.obj.length;
                    }
                }
                for(int i = 0; i < gp.factions[faction.gpPos].factionBuildings.length; i++) {
                    if(gp.factions[faction.gpPos].factionBuildings[i] == null) {
                        gp.factions[faction.gpPos].factionBuildings[i] = ent;

                        i = gp.factions[faction.gpPos].factionBuildings.length;
                    }
                }
                break;
            case "wall_cross":
                for(int i = 0; i < gp.ent.length; i++)  {
                    if(gp.ent[i] == null) {
                        ent = new ENT_WallCross(gp, faction);
                        ent.worldX = x * gp.tileSize;
                        ent.worldY = y * gp.tileSize;

                        gp.ent[i] = ent;
                        i = gp.obj.length;
                    }
                }
                for(int i = 0; i < gp.factions[faction.gpPos].factionBuildings.length; i++) {
                    if(gp.factions[faction.gpPos].factionBuildings[i] == null) {
                        gp.factions[faction.gpPos].factionBuildings[i] = ent;

                        i = gp.factions[faction.gpPos].factionBuildings.length;
                    }
                }
                break;
            case "tree":
                for(int i = 0; i < gp.ent.length; i++)  {
                    if(gp.ent[i] == null) {
                        gp.ent[i] = new ENT_Tree(gp);
                        gp.ent[i].worldX = x * gp.tileSize;
                        gp.ent[i].worldY = y * gp.tileSize;
                        break;
                    }
                }
        }
    }
    public void addPremadeEntity(int x, int y, Entity entity) {
        for(int i = 0; i < gp.ent.length; i++)  {
            if(gp.ent[i] == null) {
                gp.ent[i] = entity;
                gp.ent[i].worldX = x * gp.tileSize;
                gp.ent[i].worldY = y * gp.tileSize;
                break;
            }
        }
    }
    public Entity newEntity(String entName, Faction faction, int x, int y) {
        switch(entName) {
            case "farm":
                for(int i = 0; i < gp.ent.length; i++)  {
                    if(gp.ent[i] == null) {
                        ent = new ENT_Farm(gp, faction);
                        ent.worldX = x * gp.tileSize;
                        ent.worldY = y * gp.tileSize;
                        gp.ent[i] = ent;
                        i = gp.ent.length;
                    }
                }
                for(int i = 0; i < gp.factions[faction.gpPos].factionBuildings.length; i++) {
                    if(gp.factions[faction.gpPos].factionBuildings[i] == null) {
                        gp.factions[faction.gpPos].factionBuildings[i] = ent;

                        i = gp.factions[faction.gpPos].factionBuildings.length;
                    }
                }
                break;
            case "mine":
                for(int i = 0; i < gp.ent.length; i++)  {
                    if(gp.ent[i] == null) {
                        ent = new ENT_Mine(gp, faction);
                        ent.worldX = x * gp.tileSize;
                        ent.worldY = y * gp.tileSize;
                        gp.ent[i] = ent;
                        i = gp.obj.length;
                    }
                }
                for(int i = 0; i < gp.factions[faction.gpPos].factionBuildings.length; i++) {
                    if(gp.factions[faction.gpPos].factionBuildings[i] == null) {
                        gp.factions[faction.gpPos].factionBuildings[i] = ent;

                        i = gp.factions[faction.gpPos].factionBuildings.length;
                    }
                }
                break;
            case "king_court":
                for(int i = 0; i < gp.ent.length; i++)  {
                    if(gp.ent[i] == null) {
                        ent = new ENT_KingCourt(gp, faction);
                        ent.worldX = x * gp.tileSize;
                        ent.worldY = y * gp.tileSize;
                        gp.ent[i] = ent;
                        i = gp.obj.length;
                    }
                }
                for(int i = 0; i < gp.factions[faction.gpPos].factionBuildings.length; i++) {
                    if(gp.factions[faction.gpPos].factionBuildings[i] == null) {
                        gp.factions[faction.gpPos].factionBuildings[i] = ent;

                        i = gp.factions[faction.gpPos].factionBuildings.length;
                    }
                }
                break;
            case "hut_building":
                for(int i = 0; i < gp.ent.length; i++)  {
                    if(gp.ent[i] == null) {
                        ent = new ENT_Hut(gp, faction);
                        ent.worldX = x * gp.tileSize;
                        ent.worldY = y * gp.tileSize;

                        gp.ent[i] = ent;
                        i = gp.obj.length;
                    }
                }
                for(int i = 0; i < gp.factions[faction.gpPos].factionBuildings.length; i++) {
                    if(gp.factions[faction.gpPos].factionBuildings[i] == null) {
                        gp.factions[faction.gpPos].factionBuildings[i] = ent;

                        i = gp.factions[faction.gpPos].factionBuildings.length;
                    }
                }
                break;
            case "building_fortress":
                for(int i = 0; i < gp.ent.length; i++)  {
                    if(gp.ent[i] == null) {
                        ent = new ENT_Fortress(gp, faction);
                        ent.worldX = x * gp.tileSize;
                        ent.worldY = y * gp.tileSize;

                        gp.ent[i] = ent;
                        i = gp.obj.length;
                    }
                }
                for(int i = 0; i < gp.factions[faction.gpPos].factionBuildings.length; i++) {
                    if(gp.factions[faction.gpPos].factionBuildings[i] == null) {
                        gp.factions[faction.gpPos].factionBuildings[i] = ent;

                        i = gp.factions[faction.gpPos].factionBuildings.length;
                    }
                }
                break;
            case "wall_cross":
                for(int i = 0; i < gp.ent.length; i++)  {
                    if(gp.ent[i] == null) {
                        ent = new ENT_WallCross(gp, faction);
                        ent.worldX = x * gp.tileSize;
                        ent.worldY = y * gp.tileSize;

                        gp.ent[i] = ent;
                        i = gp.obj.length;
                    }
                }
                for(int i = 0; i < gp.factions[faction.gpPos].factionBuildings.length; i++) {
                    if(gp.factions[faction.gpPos].factionBuildings[i] == null) {
                        gp.factions[faction.gpPos].factionBuildings[i] = ent;

                        i = gp.factions[faction.gpPos].factionBuildings.length;
                    }
                }
                break;
            case "tree":
                for(int i = 0; i < gp.ent.length; i++)  {
                    if(gp.ent[i] == null) {
                        ent = new ENT_Tree(gp);
                        ent.worldX = x * gp.tileSize;
                        ent.worldY = y * gp.tileSize;
                        break;
                    }
                }
        }
        return ent;
    }
}
