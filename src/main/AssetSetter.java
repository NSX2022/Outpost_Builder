package main;


import entity.*;
import faction.Faction;
import object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        //addObject(51, 40, "red_flag");

        /*addObject(52, 40, "green_flag");

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
        if(/*faction != null*/true) { //Place a Building
            switch(entName) {
                case "farm":
                    for(int i = 0; i < gp.ent.length; i++)  {
                        if(gp.ent[i] == null) {
                            gp.ent[i] = new ENT_Farm(gp, faction);
                            gp.ent[i].worldX = x * gp.tileSize;
                            gp.ent[i].worldY = y * gp.tileSize;
                            break;
                        }
                    }
                    break;
                case "mine":
                    for(int i = 0; i < gp.obj.length; i++)  {
                        if(gp.ent[i] == null) {
                            gp.ent[i] = new ENT_Mine(gp, faction);
                            gp.ent[i].worldX = x * gp.tileSize;
                            gp.ent[i].worldY = y * gp.tileSize;
                            break;
                        }
                    }
                    break;
                case "king_court":
                    for(int i = 0; i < gp.obj.length; i++)  {
                        if(gp.ent[i] == null) {
                            gp.ent[i] = new ENT_KingCourt(gp, faction);
                            gp.ent[i].worldX = x * gp.tileSize;
                            gp.ent[i].worldY = y * gp.tileSize;
                            break;
                        }
                    }
                    break;
                case "hut_building":
                    for(int i = 0; i < gp.obj.length; i++)  {
                        if(gp.ent[i] == null) {
                            gp.ent[i] = new ENT_Hut(gp, faction);
                            gp.ent[i].worldX = x * gp.tileSize;
                            gp.ent[i].worldY = y * gp.tileSize;
                            break;
                        }
                    }
                    break;
                case "building_fortress":
                    for(int i = 0; i < gp.obj.length; i++)  {
                        if(gp.ent[i] == null) {
                            gp.ent[i] = new ENT_Fortress(gp, faction);
                            gp.ent[i].worldX = x * gp.tileSize;
                            gp.ent[i].worldY = y * gp.tileSize;
                            break;
                        }
                    }
                    break;
                case "wall_cross":
                    for(int i = 0; i < gp.obj.length; i++)  {
                        if(gp.ent[i] == null) {
                            gp.ent[i] = new ENT_WallCross(gp, faction);
                            gp.ent[i].worldX = x * gp.tileSize;
                            gp.ent[i].worldY = y * gp.tileSize;
                            break;
                        }
                    }
            }
        }else{ //Factionless Entity

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
