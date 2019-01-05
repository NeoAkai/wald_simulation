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


    public MapBuilder(Grass[][] grass, Tree[][] tree, UIController ui, ProgramController pc) {
        this.grass = grass;
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
                            pc.getSqlHandler().addTree(i,a,currentLetter);
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
                grass[x][y].setCoveringObject(new Tree(50*y, 50*x+50, results.getString(2), pc, -1));
                ui.drawObject(grass[x][y].getCoveringObject());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadBarnsFromDatabase(ResultSet results){
        try{
            while (results.next()){
                int x = results.getInt(3);
                int y = results.getInt(4);
                grass[x][y].setCoveringObject(new Barn(results.getString(2), 50*y, 50*x+50));
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
                String type = animals.getString(1);
                if(type.equals("eichhoernchen")){
                    barn.addAnimal(new Eichhörnchen(400,400,pc,"eichhoernchen"));
                }else if(type.equals("wurm")){
                    barn.addAnimal(new Worm(400,400,pc,"wurm"));
                }else if(type.equals("fuchs")){
                    barn.addAnimal(new Fox(400,400,pc,"fuchs"));
                }else if(type.equals("hase")){
                    barn.addAnimal(new Rabbit(400,400,pc,"hase"));
                }else if(type.equals("hirsch")){
                    barn.addAnimal(new Hirsch(400,400,pc,"hirsch"));
                }else if(type.equals("schnecke")){
                    barn.addAnimal(new Schnecke(400,400,pc,"schnecke"));
                }else if(type.equals("vogel")){
                    barn.addAnimal(new Bird(400,400,pc,"vogel"));
                }else if(type.equals("wildschwein")){
                    barn.addAnimal(new WildPig(400,400,pc,"wildschwein"));
                }else if(type.equals("ziege")){
                    barn.addAnimal(new Goat(400,400,pc,"ziege"));
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
                String type = results.getString(1);
                if(type.equals("eichhoernchen")){
                    pc.addAnimalFromDatabase(2);
                }else if(type.equals("wurm")){
                    pc.addAnimalFromDatabase(1);
                }else if(type.equals("fuchs")){
                    pc.addAnimalFromDatabase(3);
                }else if(type.equals("hase")){
                    pc.addAnimalFromDatabase(4);
                }else if(type.equals("hirsch")){
                    pc.addAnimalFromDatabase(5);
                }else if(type.equals("schnecke")){
                    pc.addAnimalFromDatabase(6);
                }else if(type.equals("vogel")){
                    pc.addAnimalFromDatabase(7);
                }else if(type.equals("wildschwein")){
                    pc.addAnimalFromDatabase(8);
                }else if(type.equals("ziege")){
                    pc.addAnimalFromDatabase(9);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}