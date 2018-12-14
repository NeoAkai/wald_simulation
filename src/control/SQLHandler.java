package control;

import mapObjects.Tree;
import model.MapBuildingObject.MapBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLHandler {

    private boolean getDebugMsg = true;
    private Statement stmt;

    private MapBuilder mapBuilder;

    public SQLHandler(MapBuilder mapBuilder){
        this.mapBuilder = mapBuilder;
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
                        "PRIMARY KEY(ID)" +
                        ")");
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
                mapBuilder.loadFromTxt();
            }catch (Exception e){
                try{
                    loadDatabase();
                }catch(Exception e2){
                    if(getDebugMsg) System.out.println("Baum ResultSet nicht erstellt");
                }

            }
        }catch(Exception e){
            if(getDebugMsg) e.printStackTrace();
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
        System.out.println(1);
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


    }

    private void loadDatabase(){
        try {
            mapBuilder.loadGrassFromDatabase(stmt.executeQuery("SELECT * FROM JA_Grass;"));
            mapBuilder.loadTreesFromDatabase(stmt.executeQuery("SELECT * FROM JA_Baum;"));
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
