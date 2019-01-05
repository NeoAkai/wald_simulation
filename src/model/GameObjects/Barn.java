package model.GameObjects;

import mapObjects.CoveringObject;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class Barn extends ProducingObject {

    private String type;
    private Animal[] animals;

    public Barn(String type, double x, double y){
        this.x = x;
        this.y = y;
        this.type = type;
        createAndSetNewImage("assets/images/barns/" + type + ".png");
        animals = new Animal[5];
        maxItemCooldown = 61;
    }

    /*@Override
    public void draw(DrawTool drawTool) {

    }*/

    @Override
    public void update(double dt) {
        super.update(dt);
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
}
