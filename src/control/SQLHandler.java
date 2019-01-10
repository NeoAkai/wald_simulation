package control;

import model.GameObjects.Barn;
import model.GameObjects.Plant;
import model.GameObjects.ProducingObject;
import model.GameObjects.UserInterface;
import model.MapBuildingObject.MapBuilder;
import model.abitur.datenstrukturen.List;

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

            dropAll();

            try{
                stmt.execute("CREATE TABLE JA_Farmer(" +
                        "ID int NOT NULL AUTO_INCREMENT," +
                        "geld int," +
                        "holz int," +
                        "harmonie int," +
                        "heu int," +
                        "karotten int," +
                        "kirschen int," +
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
                        "productionTimer int NOT NULL," +
                        "starvingTimer int NOT NULL," +
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
            stmt.execute("INSERT INTO JA_Stall (x, y, typ, productionTimer, starvingTimer)" +
                         "VALUES ('" + x + "', '" + y + "', '" + type + "', 61, 10000);");
        }catch (Exception e){
            if(getDebugMsg) System.out.println("Stall nicht hinzugefügt");
        }
    }

    public int addAnimal(String type, Barn barn){
        try{
            if(barn == null){
               stmt.execute("INSERT INTO JA_Tier (art, inStall, stallID)" +
                            "VALUES  ('" + type + "', 0, null);");
            }else{
                stmt.execute("INSERT INTO JA_Tier (art, inStall, stallID)" +
                             "VALUES ('" + type + "', 1, '" + getBarnID(barn) + "');");
            }
            ResultSet s = stmt.executeQuery("SELECT MAX(ID) FROM JA_Tier");
            s.next();
            return s.getInt(1);
        }catch (Exception e){
            //if(getDebugMsg) System.out.println("Tier nicht hinzugefügt");
            e.printStackTrace();
        }
        return -1;
    }

    public void addPlant(String type, int x, int y){
        try{
            String s = "INSERT INTO JA_Pflanze (typ, x, y, isGrown, growTimer)" +
                       "VALUES('" + type + "'," + x +"," + y + ", 0, 25 );";
            stmt.execute(s);
        }catch(Exception e){
            if(getDebugMsg) System.out.println("Pflanze nicht hinzugefügt");
        }
    }

    public void removeBarn(Barn barn){
        try{
            int barnID = getBarnID(barn);
            stmt.execute("DELETE FROM JA_Tier WHERE stallID = " + barnID + ";");
            stmt.execute("DELETE FROM JA_Stall WHERE ID = " + barnID + ";");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private int getBarnID(Barn barn){
        try {
            String s = "SELECT ID " +
                       "FROM JA_Stall " +
                       "WHERE x = " + ((int)barn.getY()/50) + " AND y = " + ((int)barn.getX()) / 50 + ";";
            ResultSet results = stmt.executeQuery(s);
            while (results.next()){
                return results.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public void updateAnimalBarn(int animalID, Barn barn){
        try{
            String s = "UPDATE JA_Tier " +
                       "SET stallID = " + getBarnID(barn) + ", inStall = 1 " +
                       "WHERE ID = " + animalID + ";";
            stmt.execute(s);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void freeAnimal(int animalID){
        try{
            stmt.execute("UPDATE JA_Tier " +
                         "SET stallID = NULL, inStall = 0 " +
                         "WHERE ID = " + animalID + ";");
        }catch (Exception e){
            e.printStackTrace();
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
            mapBuilder.loadTreesFromDatabase(stmt.executeQuery("SELECT art,x,y,isGrown,growTimer " +
                                                               "FROM JA_Baum;"));
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
            mapBuilder.loadAnimalsToPC(stmt.executeQuery("SELECT ID, art FROM JA_Tier WHERE inStall = 0;"));
            ResultSet barns = stmt.executeQuery("SELECT ID, x, y FROM JA_Stall;");
            /*while (barns.next()) {
            ResultSet s = stmt.executeQuery("SELECT ID, art " +
                                            "FROM JA_Tier " +
                                            "WHERE inStall = 1 AND stallID = " + barns.getInt(1) + ";");
            mapBuilder.loadAnimalsToBarn(s, barns.getInt(2), barns.getInt(3));
            }*/
            List<int[]> barnDataList = new List();
            while(barns.next()){
                barnDataList.append(new int[3]);
                barnDataList.toLast();
                barnDataList.getContent()[0] = barns.getInt(1);
                barnDataList.getContent()[1] = barns.getInt(2);
                barnDataList.getContent()[2] = barns.getInt(3);
            }
            barnDataList.toFirst();
            while (barnDataList.hasAccess()){
                ResultSet s = stmt.executeQuery("SELECT ID, art " +
                                                "FROM JA_Tier " +
                                                "WHERE inStall = 1 AND stallID = " + barnDataList.getContent()[0] + ";");
                mapBuilder.loadAnimalsToBarn(s,barnDataList.getContent()[1], barnDataList.getContent()[2]);
                barnDataList.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void removeTreeFromDatabase(int x, int y){
        try {
            stmt.execute("DELETE " +
                         "FROM JA_Baum " +
                         "WHERE x = " + x + " AND y = " + y + ";");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void removePlantFromDatabase(int x, int y){
        try{
            stmt.execute("DELETE " +
                    "FROM JA_Pflanze " +
                    "WHERE x = " + x + " AND y = " + y + ";");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getCarrots(){
        try {
            ResultSet s = stmt.executeQuery("SELECT karotten FROM JA_Farmer");
            while(s.next()){
                return s.getInt(1);
            }
        }catch(Exception e){
            System.out.println("kann nicht karotten getten");
        }
        return -1;
    }

    public void changeCarrotsByAmount(int amount){
        try{
            stmt.execute("UPDATE JA_Farmer SET karotten = " + (getCarrots()+amount) + ";");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getWheat(){
        try {
            ResultSet s = stmt.executeQuery("SELECT heu FROM JA_Farmer");
            while(s.next()){
                return s.getInt(1);
            }
        }catch(Exception e){
            System.out.println("kann nicht karotten getten");
        }
        return -1;
    }

    public void changeWheatByAmount(int amount){
        try{
            stmt.execute("UPDATE JA_Farmer SET heu = " + (getWheat()+amount) + ";");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getCherries(){
        try {
            ResultSet s = stmt.executeQuery("SELECT kirschen FROM JA_Farmer");
            while(s.next()){
                return s.getInt(1);
            }
        }catch(Exception e){
            System.out.println("kann nicht karotten getten");
        }
        return -1;
    }

    public void changeCherriesByAmount(int amount){
        try{
            stmt.execute("UPDATE JA_Farmer SET kirschen = " + (getCherries()+amount) + ";");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateTreeGrowTimer(int x, int y, int timer){
        try{
            stmt.execute("UPDATE JA_Baum " +
                         "SET growTimer = " + timer + " " +
                         "WHERE x = " + x + " AND y = " + y + ";");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void growTree(int x, int y){
        try{
            stmt.execute("UPDATE JA_Baum " +
                         "SET growTimer = 0, isGrown = 1 " +
                         "WHERE x = " + x + " AND y = " + y + ";");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateProductionTimer(ProducingObject p, int timer){
        try{
            if(p instanceof Plant){
                String s = "UPDATE JA_Pflanze " +
                        "SET growTimer = " + timer + " " +
                        "WHERE x = " + (int)p.getY()/50 + " AND y = " + (int)p.getX()/50 + ";";
                stmt.execute(s);
            }else if(p instanceof Barn){
                String s = "UPDATE JA_Stall " +
                        "SET productionTimer = " + timer + ", starvingTimer = " + ((int)((Barn) p).getStarvingTime()) + " " +
                        "WHERE x = " + (int)p.getY()/50 + " AND y = " + (int)p.getX()/50 + ";";
                stmt.execute(s);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
