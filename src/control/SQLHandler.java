package control;

import model.GameObjects.Barn;
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
                        "isGrown BIT NOT NULL," +
                        "growTimer int NOT NULL," +
                        "PRIMARY KEY (ID)," +
                        "FOREIGN KEY (x,y) REFERENCES JA_Grass(x, y)" +
                        ");");
                stmt.execute("CREATE TABLE JA_Stall (" +
                        "ID int NOT NULL AUTO_INCREMENT," +
                        "typ varchar(255) NOT NULL," +
                        "x int NOT NULL," +
                        "y int NOT NULL," +
                        "PRIMARY KEY (ID)," +
                        "FOREIGN KEY (x,y) REFERENCES JA_Grass(x, y)" +
                        ");");
                stmt.execute("CREATE TABLE JA_Tier (" +
                        "ID int NOT NULL AUTO_INCREMENT," +
                        "art varchar(255) NOT NULL," +
                        "inStall BIT NOT NULL," +
                        "stallID int," +
                        "PRIMARY KEY (ID)," +
                        "FOREIGN KEY (stallID) REFERENCES JA_Stall(ID)" +
                        ");");
                stmt.execute("CREATE TABLE JA_Pflanze (" +
                        "ID int NOT NULL AUTO_INCREMENT," +
                        "typ varchar(255) NOT NULL," +
                        "x int NOT NULL," +
                        "y int NOT NULL," +
                        "isGrown BIT NOT NULL," +
                        "growTimer int NOT NULL," +
                        "PRIMARY KEY (ID)," +
                        "FOREIGN KEY (x,y) REFERENCES JA_Grass(x,y)" +
                        ");");
                mapBuilder.loadFromTxt();
            }catch (Exception e){
                try{
                    loadDatabase();
                }catch(Exception e2){
                    if(getDebugMsg) System.out.println("ResultSets nicht erstellt");
                }

            }//*/
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

    public void addTree(int x, int y, String art, boolean isGrown, int growTimer){
        int i = 0;
        if(isGrown) i = 1;
        try {
            String s = "INSERT INTO JA_Baum (art, x, y, isGrown, growTimer)" +
                    "VALUES ('" + art + "', " + x + ", " + y + ", " + i + ", " + growTimer + ");";
            System.out.println(s);
            stmt.execute(s);
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

    public void addBarn(int x, int y, String type){
        try{
            stmt.execute("INSERT INTO JA_Stall (x, y, typ)" +
                    "VALUES ('" + x + "', '" + y + "', '" + type + "');");
        }catch (Exception e){
            if(getDebugMsg) System.out.println("Stall nicht hinzugefügt");
        }
    }

    public void addAnimal(String type, Barn barn){
        try{
            if(barn == null){
               stmt.execute("INSERT INTO JA_Tier (art, inStall, stallID)" +
                       "VALUES  ('" + type + "', 0, null);");
            }else{
                stmt.execute("INSERT INTO JA_Tier (art, inStall, stallID)" +
                        "VALUES ('" + type + "', 1, '" + getBarnID(barn) + "');");
            }
        }catch (Exception e){
            if(getDebugMsg) System.out.println("Tier nicht hinzugefügt");
        }
    }

    public void addPlant(String type, int x, int y){
        try{
            String s = "INSERT INTO JA_Pflanze (typ, x, y, isGrown, growTimer)" +
                    "VALUES('" + type + "'," + x +"," + y + ", 0, 25 );";
            System.out.println(s);
            stmt.execute(s);
        }catch(Exception e){
            if(getDebugMsg) System.out.println("Pflanze nicht hinzugefügt");
        }
    }

    private int getBarnID(Barn barn){
        try {
            ResultSet results = stmt.executeQuery("SELECT ID FROM JA_Stall WHERE x = " + barn.getY() / 50 + " AND y = " + barn.getX() / 50 + ");");
            while (results.next()){
                return results.getInt(1);
            }
        }catch(Exception e){

        }
        return 0;
    }

    public void updateAnimalBarn(int animalID, Barn barn){
        try{
            stmt.execute("UPDATE JA_Tier " +
                    "SET stallID = " + getBarnID(barn) + " " +
                    "WHERE ID = " + animalID + ";"
                    );
        }catch(Exception e){

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
            stmt.execute("DROP TABLE JA_Tier;");
        }catch (Exception e) {
            if(getDebugMsg) System.out.println("Tabelle Tier nicht gelöscht");
        }
        try{
            stmt.execute("DROP TABLE JA_Stall;");
        }catch (Exception e) {
            if(getDebugMsg) System.out.println("Tabelle Tier nicht gelöscht");
        }
        try{
            stmt.execute("DROP TABLE JA_Pflanze;");
        }catch (Exception e) {
            if(getDebugMsg) System.out.println("Tabelle Pflanze nicht gelöscht");
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
            mapBuilder.loadProducingObjectsFromDatabase(stmt.executeQuery("SELECT * FROM JA_Stall;"),0);
            mapBuilder.loadProducingObjectsFromDatabase(stmt.executeQuery("SELECT * FROM JA_Pflanze;"),1);
            loadAnimals();
            ResultSet results = stmt.executeQuery("SELECT * FROM JA_Farmer;");
            while(results.next()) {
                userInterface.addCash(results.getInt(2));
                userInterface.addWood(results.getInt(3));
                userInterface.addHarmony(results.getInt(4));
            }
        }catch(Exception e){

        }
    }

    private void loadAnimals(){
        try {
            mapBuilder.loadAnimalsToPC(stmt.executeQuery("SELECT art FROM JA_Tier WHERE inStall = 0;"));
            ResultSet barns = stmt.executeQuery("SELECT ID, x, y FROM JA_Stall;");
            while (barns.next()) {
                mapBuilder.loadAnimalsToBarn(stmt.executeQuery("SELECT ID, art FROM JA_Tier WHERE inStall = 1 AND stallID = " + barns.getInt(1) + ";"), barns.getInt(2), barns.getInt(3));
            }
        }catch (Exception e){
            System.out.println("Tiere nicht geladen");
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
