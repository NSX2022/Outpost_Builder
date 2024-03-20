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

        addEntity(51, 41, "tree", null);

        addEntity(51, 35, "farm", gp.player.playerFaction);


        //old Demo
        /*addObject(51, 40, "red_flag");
        addObject(52, 40, "green_flag");
        addObject(53, 40, "blue_flag");
        addObject(54, 40, "white_flag");
        addObject(55, 40, "yellow_flag");
        addEntity(51, 40, "farm", null);
        addEntity(52, 40, "mine", null);
        addEntity(53, 40, "king_court", null);
        addEntity(54, 40, "hut_building", null);
        addEntity(55, 40, "building_fortress", null);
        //Rectangle of walls
        addEntity(51, 41, "wall_cross", null);
        addEntity(52, 41, "wall_cross", null);
        addEntity(53, 41, "wall_cross", null);
        addEntity(54, 41, "wall_cross", null);
        addEntity(51, 42, "wall_cross", null);
        addEntity(51, 43, "wall_cross", null);
        addEntity(51, 44, "wall_cross", null);
        addEntity(52, 44, "wall_cross", null);
        addEntity(53, 44, "wall_cross", null);
        addEntity(54, 44, "wall_cross", null);
        addEntity(54, 43, "wall_cross", null);
        addEntity(54, 42, "wall_cross", null);
        addEntity(54, 41, "wall_cross", null);
         */
    }

    public void setEntity() {

    }

    public void addObject(int x, int y, String objName){
        //TODO: Redo for Building-Flag system
        switch(objName) {
            case "white_flag":
                for(int i = 0; i < gp.obj.length; i++)  {
                    if(gp.obj[i] == null) {
                        gp.obj[i] = new OBJ_WhiteFlag(gp);
                        gp.obj[i].worldX = x * gp.tileSize;
                        gp.obj[i].worldY = y * gp.tileSize;
                        break;
                    }
                }
                break;
            case "red_flag":
                for(int i = 0; i < gp.obj.length; i++)  {
                    if(gp.obj[i] == null) {
                        gp.obj[i] = new OBJ_RedFlag(gp);
                        gp.obj[i].worldX = x * gp.tileSize;
                        gp.obj[i].worldY = y * gp.tileSize;
                        break;
                    }
                }
                break;
            case "blue_flag":
                for(int i = 0; i < gp.obj.length; i++)  {
                    if(gp.obj[i] == null) {
                        gp.obj[i] = new OBJ_BlueFlag(gp);
                        gp.obj[i].worldX = x * gp.tileSize;
                        gp.obj[i].worldY = y * gp.tileSize;
                        break;
                    }
                }
                break;
            case "green_flag":
                for(int i = 0; i < gp.obj.length; i++)  {
                    if(gp.obj[i] == null) {
                        gp.obj[i] = new OBJ_GreenFlag(gp);
                        gp.obj[i].worldX = x * gp.tileSize;
                        gp.obj[i].worldY = y * gp.tileSize;
                        break;
                    }
                }
                break;
            case "yellow_flag":
                for(int i = 0; i < gp.obj.length; i++)  {
                    if(gp.obj[i] == null) {
                        gp.obj[i] = new OBJ_YellowFlag(gp);
                        gp.obj[i].worldX = x * gp.tileSize;
                        gp.obj[i].worldY = y * gp.tileSize;
                        break;
                    }
                }
                break;
        }
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
                    if(gp.factions[faction.gpPos].factionBuildings[i] != null) {
                        gp.factions[faction.gpPos].factionBuildings[i] = ent;

                        i = gp.factions[faction.gpPos].factionBuildings.length;
                    }
                }
                break;
            case "mine":
                for(int i = 0; i < gp.obj.length; i++)  {
                    if(gp.ent[i] == null) {
                        ent = new ENT_Mine(gp, faction);
                        ent.worldX = x * gp.tileSize;
                        ent.worldY = y * gp.tileSize;
                        gp.ent[i] = ent;
                        i = gp.obj.length;
                    }
                }
                for(int i = 0; i < gp.factions[faction.gpPos].factionBuildings.length; i++) {
                    if(gp.factions[faction.gpPos].factionBuildings[i] != null) {
                        gp.factions[faction.gpPos].factionBuildings[i] = ent;

                        i = gp.factions[faction.gpPos].factionBuildings.length;
                    }
                }
                break;
            case "king_court":
                for(int i = 0; i < gp.obj.length; i++)  {
                    if(gp.ent[i] == null) {
                        ent = new ENT_KingCourt(gp, faction);
                        ent.worldX = x * gp.tileSize;
                        ent.worldY = y * gp.tileSize;
                        gp.ent[i] = ent;
                        i = gp.obj.length;
                    }
                }
                for(int i = 0; i < gp.factions[faction.gpPos].factionBuildings.length; i++) {
                    if(gp.factions[faction.gpPos].factionBuildings[i] != null) {
                        gp.factions[faction.gpPos].factionBuildings[i] = ent;

                        i = gp.factions[faction.gpPos].factionBuildings.length;
                    }
                }
                break;
            case "hut_building":
                for(int i = 0; i < gp.obj.length; i++)  {
                    if(gp.ent[i] == null) {
                        ent = new ENT_Hut(gp, faction);
                        ent.worldX = x * gp.tileSize;
                        ent.worldY = y * gp.tileSize;

                        gp.ent[i] = ent;
                        i = gp.obj.length;
                    }
                }
                for(int i = 0; i < gp.factions[faction.gpPos].factionBuildings.length; i++) {
                    if(gp.factions[faction.gpPos].factionBuildings[i] != null) {
                        gp.factions[faction.gpPos].factionBuildings[i] = ent;

                        i = gp.factions[faction.gpPos].factionBuildings.length;
                    }
                }
                break;
            case "building_fortress":
                for(int i = 0; i < gp.obj.length; i++)  {
                    if(gp.ent[i] == null) {
                        ent = new ENT_Fortress(gp, faction);
                        ent.worldX = x * gp.tileSize;
                        ent.worldY = y * gp.tileSize;

                        gp.ent[i] = ent;
                        i = gp.obj.length;
                    }
                }
                for(int i = 0; i < gp.factions[faction.gpPos].factionBuildings.length; i++) {
                    if(gp.factions[faction.gpPos].factionBuildings[i] != null) {
                        gp.factions[faction.gpPos].factionBuildings[i] = ent;

                        i = gp.factions[faction.gpPos].factionBuildings.length;
                    }
                }
                break;
            case "wall_cross":
                for(int i = 0; i < gp.obj.length; i++)  {
                    if(gp.ent[i] == null) {
                        ent = new ENT_WallCross(gp, faction);
                        ent.worldX = x * gp.tileSize;
                        ent.worldY = y * gp.tileSize;

                        gp.ent[i] = ent;
                        i = gp.obj.length;
                    }
                }
                for(int i = 0; i < gp.factions[faction.gpPos].factionBuildings.length; i++) {
                    if(gp.factions[faction.gpPos].factionBuildings[i] != null) {
                        gp.factions[faction.gpPos].factionBuildings[i] = ent;

                        i = gp.factions[faction.gpPos].factionBuildings.length;
                    }
                }
                break;
            case "tree":
                for(int i = 0; i < gp.obj.length; i++)  {
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
}
