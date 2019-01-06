package model.MapBuildingObject;

import mapObjects.*;
import control.ProgramController;
import control.framework.UIController;
import model.GameObjects.*;

import java.io.*;
import java.sql.ResultSet;

public class MapBuilder {

    //Attribute

    //Referenzen
    private Grass[][] grass;
    private Tree[][] tree;
    private UIController ui;
    private ProgramController pc;
    private BarnInfo barnInfo;


    public MapBuilder(Grass[][] grass, Tree[][] tree, UIController ui, ProgramController pc, BarnInfo barnInfo) {
        this.grass = grass;
        this.barnInfo = barnInfo;
        this.tree = tree;
        this.pc = pc;
        this.ui = ui;
    }

    public void loadFromTxt() {
        System.out.println(1);
        pc.addCash(500);


        BufferedReader reader;

        try {


            reader = new BufferedReader(new FileReader(new File("src/model/MapBuildingObject/Map.txt")));
            String line = reader.readLine();


            int amount = 20;  //Anzahl der Einstellungen + Leerzeilen +  Beschreibungen

            String[] lines = new String[amount];
            double tempX = 0;
            double tempY = 50;
            String currentLetter;
            for(int i = 0; i < lines.length; i++) {

                lines[i] = reader.readLine();

                if (lines[i] != null) {
                    char[] c = lines[i].toCharArray();
                    tempX = 0;
                    for (int a = 0; a < c.length; a++) {
                        currentLetter = "" + c[a];

                        if (currentLetter.equals("G")) {
                            grass[i][a] = new Grass(tempX, tempY, false,pc);
                            ui.drawObject(grass[i][a]);
                            pc.getSqlHandler().addGrass(i,a,false);
                        }

                        if (currentLetter.equals("T")||currentLetter.equals("B")) {
                            grass[i][a] = new Grass(tempX, tempY, true,pc);
                            ui.drawObject(grass[i][a]);
                            grass[i][a].plant(new Tree(tempX, tempY, currentLetter, pc,-1));
                            ui.drawObject(grass[i][a].getCoveringObject());
                            pc.getSqlHandler().addGrass(i,a,true);
                            pc.getSqlHandler().addTree(i,a,currentLetter,true,0);
                        }
                        tempX = tempX + 50;
                    }
                    tempY = tempY + 50;

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGrassFromDatabase(ResultSet results){
        try {
            while (results.next()) {
                int x = results.getInt(1);
                int y = results.getInt(2);
                grass[x][y] = new Grass(50*y,50*x+50, results.getBoolean(3),pc);
                ui.drawObject(grass[x][y]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadTreesFromDatabase(ResultSet results){
        try{
            while (results.next()){
                int x = results.getInt(3);
                int y = results.getInt(4);
                if(results.getInt(5) == 1) {
                    grass[x][y].setCoveringObject(new Tree(50 * y, 50 * x + 50, results.getString(2), pc, -1));
                }else{
                    grass[x][y].setCoveringObject(new Tree(50 * y, 50 * (x-1) + 50, results.getString(2), pc, results.getDouble(6)));
                }
                ui.drawObject(grass[x][y].getCoveringObject());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadProducingObjectsFromDatabase(ResultSet results, int i){
        try{
            while (results.next()){
                int x = results.getInt(3);
                int y = results.getInt(4);
                if(i == 0) {
                    grass[x][y].setCoveringObject(new Barn(results.getString(2), 50 * y, 50 * (x-1) + 50,ui,pc));
                }else if(i == 1){
                    grass[x][y].setCoveringObject(new Plant(results.getString(2), 50 * y, 50 * (x-1) + 50, pc));
                }
                ui.drawObject(grass[x][y].getCoveringObject());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadAnimalsToBarn(ResultSet animals, int barnX, int barnY){
        try {
            Barn barn = getBarnFromCoordinates(barnX, barnY);
            while (animals.next()) {
                pc.setAnimalCount(pc.getAnimalCount()+1);
                int ID = animals.getInt(1);
                String type = animals.getString(2);
                if(type.equals("eichhoernchen")){
                    barn.addAnimal(new Eichhörnchen(400,400,pc,"eichhoernchen",ID));
                }else if(type.equals("wurm")){
                    barn.addAnimal(new Worm(400,400,pc,"wurm",ID));
                }else if(type.equals("fuchs")){
                    barn.addAnimal(new Fox(400,400,pc,"fuchs",ID));
                }else if(type.equals("hase")){
                    barn.addAnimal(new Rabbit(400,400,pc,"hase",ID));
                }else if(type.equals("hirsch")){
                    barn.addAnimal(new Hirsch(400,400,pc,"hirsch",ID));
                }else if(type.equals("schnecke")){
                    barn.addAnimal(new Schnecke(400,400,pc,"schnecke",ID));
                }else if(type.equals("vogel")){
                    barn.addAnimal(new Bird(400,400,pc,"vogel",ID));
                }else if(type.equals("wildschwein")){
                    barn.addAnimal(new WildPig(400,400,pc,"wildschwein",ID));
                }else if(type.equals("ziege")){
                    barn.addAnimal(new Goat(400,400,pc,"ziege",ID));
                }
            }
        }catch(Exception e){

        }
    }

    private Barn getBarnFromCoordinates(int barnX, int barnY){
        return (Barn)grass[barnX][barnY].getCoveringObject();
    }

    public void loadAnimalsToPC(ResultSet results){
        try {
            while (results.next()) {
                pc.addAnimalFromDatabase(results.getString(2), results.getInt(1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}