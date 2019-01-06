package model.GameObjects;

import control.ProgramController;
import control.framework.UIController;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Barn extends ProducingObject {

    private String type;
    private Animal[] animals;
    private String food;
    private BarnInfo barnInfo;
    private UIController ui;
    private ProgramController pc;
    private boolean klicked = false;

    //Starving-Attribute
    private double starvingTime = 10000;
    private boolean starving = false;
    private int multiStarving;
    

    public Barn(String type, double x, double y, UIController ui,ProgramController pc){
        this.x = x;
        this.y = y;
        this.type = type;
        this.pc = pc;
        this.ui = ui;



        createAndSetNewImage("assets/images/barns/" + type + ".png");
        animals = new Animal[5];
        maxItemCooldown = 61;

        if(type.equals("hase")||type.equals("hirsch")){
            food = "carrot";
        }
        if(type.equals("schnecke")||type.equals("wurm")||type.equals("ziege")){
            food = "wheat";
        }
        if(type.equals("eichhoernchen")||type.equals("fuchs")||type.equals("vogel")){
            food = "cherry";
        }
        barnInfo = new BarnInfo(400,50,pc,this);
        ui.drawObject(barnInfo);
        multiStarving = getAllAnimals();

    }

    /*@Override
    public void draw(DrawTool drawTool) {

    }*/

    @Override
    public void update(double dt) {
        multiStarving = getAllAnimals();

        if(multiStarving>0){
            starving = true;
            starvingTime = starvingTime - dt*multiStarving;
        }else{
            starving = false;
        }

        if(starvingTime<=0){
            //HIER!
        }
    }

    public boolean addAnimal(Animal a){
        if(!isArrayFull()) {
            if (type.equals("eichhoernchen") && a instanceof Eichhörnchen) {
                addToArray(a);
            }else if(type.equals("fuchs") && a instanceof Fox){
                addToArray(a);
            }else if(type.equals("hase") && a instanceof Rabbit){
                addToArray(a);
            }else if(type.equals("hirsch") && a instanceof Hirsch){
                addToArray(a);
            }else if(type.equals("schnecke") && a instanceof Schnecke){
                addToArray(a);
            }else if(type.equals("vogel") && a instanceof Bird){
                addToArray(a);
            }else if(type.equals("wurm") && a instanceof Worm){
                addToArray(a);
            }else if(type.equals("ziege") && a instanceof Goat){
                addToArray(a);
            }else{
                return false;
            }
        }else{
            return false;
        }
        recalculateItemCooldown();
        return true;
    }

    private void addToArray(Animal a){
        boolean filled = false;
        for(int i = 0; i < animals.length && !filled; i++){
            if(animals[i] == null){
                animals[i] = a;
                filled = true;
            }
        }
    }

    public boolean deleteAnimal(Animal a){
        boolean deleted = false;
        for(int i = 0; i < animals.length && !deleted; i++){
            if(animals[i] == a){
                animals[i] = null;
                deleted = true;
                recalculateItemCooldown();
            }
        }
        return deleted;
    }

    private void recalculateItemCooldown(){
        int i = 0;
        for(int j = 0; j < animals.length; j++){
            if(animals[j] != null){
                i++;
            }
        }
        if(i == 0){
            maxItemCooldown = 61;
        }else{
            maxItemCooldown = 60.0 / i;
        }
    }

    private boolean isArrayFull(){
        boolean b = true;
        for(int i = 0; i < animals.length && b; i++){
            if(animals[i] == null){
                b = false;
            }
        }
        return b;
    }
    public boolean checkAnimal(String a){
        if(type.equals(a)){
            return true;
        }
        return false;
    }
    public String getArt(){
        return type;
    }

    public Animal[] getAnimals() {
        return animals;
    }

    public String getFood(){
        return food;
    }
    public BufferedImage getBarnImage(){
        return getMyImage();
    }
    public void mouseReleased(MouseEvent e){
        if(e.getX()>x&&e.getX()<x+50&&e.getY()>y&&e.getY()<y+50){
          if(!klicked) {
              barnInfo.setVisible(true);
              pc.redrawBarnInfo(barnInfo);
          }
          klicked = !klicked;
        }
    }

    public int getAllAnimals(){
       int animalCounter = 0;
        for(int i = 0; i < animals.length; i++){
            if(animals[i]!=null){
                animalCounter++;
            }
        }
        return animalCounter;
    }

    public double getStarvingTime(){
        return starvingTime;
    }
    public int getMultiStarving(){
        return multiStarving;
    }
    public boolean getStarving(){
        return starving;
    }

    public void addStarvingTime(int amount){
        starvingTime = starvingTime + amount;
    }

}
