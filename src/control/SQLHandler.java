package control;

import mapObjects.Tree;
import model.GameObjects.UserInterface;
import model.MapBuildingObject.MapBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLHandler {

    private boolean getDebugMsg = true;
    private Statement stmt;

    private MapBuilder mapBuilder;
    private UserInterface userInterface;

    public SQLHandler(MapBuilder mapBuilder, UserInterface userInterface){
        this.mapBuilder = mapBuilder;
        this.userInterface = userInterface;
    }

    public void handleSQL(){

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql.webhosting24.1blu.de/db85565x2810214?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "s85565_2810214", "kkgbeste");
            stmt = con.createStatement();

            //dropAll();

            try{
                stmt.execute("CREATE TABLE JA_Farmer(" +
                        "ID int NOT NULL AUTO_INCREMENT," +
                        "geld int," +
                        "holz int," +
                        "harmonie int," +
                        "PRIMARY KEY(ID)" +
                        ")");
                stmt.execute("INSERT INTO  JA_Farmer (geld, holz, harmonie)" +
                        "VALUES (500, 0, 0);");
                stmt.execute("CREATE TABLE JA_Grass(" +
                        "x int NOT NULL," +
                        "y int NOT NULL," +
                        "treePlantable boolean NOT NULL," +
                        "PRIMARY KEY (x,y)" +
                        ");");
                stmt.execute("CREATE TABLE JA_Baum (" +
                        "ID int NOT NULL AUTO_INCREMENT," +
                        "art varchar(255) NOT NULL," +
                        "x int NOT NULL," +
                        "y int NOT NULL," +
                        "PRIMARY KEY (ID)," +
                        "FOREIGN KEY (x,y) REFERENCES JA_Grass(x, y)" +
                        ");");
                stmt.execute("CREATE TABLE JA_Tier (" +
                        "ID int NOT NULL AUTO_INCREMENT," +
                        "art varchar(255) NOT NULL," +
                        "stallID int," +
                        "PRIMARY KEY (ID)" + //Komma nicht vergessen
                        //"FOREIGN KEY (stallID) REFERENCES JA_STALL(ID)" +
                        ");");
                mapBuilder.loadFromTxt();
            }catch (Exception e){
                try{
                    loadDatabase();
                }catch(Exception e2){
                    if(getDebugMsg) System.out.println("ResultSets nicht erstellt");
                }

            }
        }catch(Exception e){
            if(getDebugMsg) e.printStackTrace();
        }
    }

    public void updateWood(){
        try {
            stmt.execute("UPDATE JA_Farmer SET holz = " + userInterface.getWood() +" WHERE ID = 1;");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateCash(){
        try {
            stmt.execute("UPDATE JA_Farmer SET geld = " + userInterface.getCash() +" WHERE ID = 1;");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateHarmony(){
        try {
            stmt.execute("UPDATE JA_Farmer SET harmonie = " + userInterface.getHarmony() +" WHERE ID = 1;");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addTree(int x, int y, String art){
        if(art == "T") art = "Eiche";
        else if(art == "B") art = "Birke";
        try {
            stmt.execute("INSERT INTO JA_Baum (x, y, art)" +
                    "VALUES ('" + x + "', '" + y + "', '" + art + "');");
        }catch(Exception e){
            if(getDebugMsg) System.out.println("Baum nicht hinzugefügt");
        }
    }

    public void addGrass(int x, int y, boolean treePlantable){
        int i = 0;
        if(treePlantable){
            i = 1;
        }
        try {
            stmt.execute("INSERT INTO JA_Grass (x, y, treePlantable)" +
                    "VALUES ('" + x + "', '" + y + "', '" + i + "');");
        } catch(Exception e){
            if(getDebugMsg) System.out.println("Grass nicht hinzugefügt");
        }
    }

    private void dropAll(){
        try{
            stmt.execute("DROP TABLE JA_Farmer;");
        }catch(Exception e){
            if(getDebugMsg) System.out.println("Tabelle Farmer nicht gelöscht");
        }
        try{
            stmt.execute("DROP TABLE JA_Baum;");
        }catch (Exception e) {
            if(getDebugMsg) System.out.println("Tabelle Baum nicht gelöscht");
        }
        try{
            stmt.execute("DROP TABLE JA_Grass;");
        }catch (Exception e) {
            if(getDebugMsg) System.out.println("Tabelle Grass nicht gelöscht");
        }
        try{
            stmt.execute("DROP TABLE JA_Tier;");
        }catch (Exception e) {
            if(getDebugMsg) System.out.println("Tabelle Tier nicht gelöscht");
        }


    }

    private void loadDatabase(){
        try {
            mapBuilder.loadGrassFromDatabase(stmt.executeQuery("SELECT * FROM JA_Grass;"));
            mapBuilder.loadTreesFromDatabase(stmt.executeQuery("SELECT * FROM JA_Baum;"));
            ResultSet results = stmt.executeQuery("SELECT * FROM JA_Farmer;");
            while(results.next()) {
                userInterface.addCash(results.getInt(2));
                userInterface.addWood(results.getInt(3));
                userInterface.addHarmony(results.getInt(4));
            }
        }catch(Exception e){

        }
    }

    public void removeTreeFromDatabase(int x, int y){
        try {
            stmt.execute("DELETE FROM JA_Baum WHERE x = " + x + " AND y = " + y + ";");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
