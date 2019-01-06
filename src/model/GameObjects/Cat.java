package model.GameObjects;

import control.ProgramController;
import control.framework.UIController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class Cat extends Animal  {

    //Atribute

    //Referenzen


    public Cat(double x, double y, ProgramController pc, String t, int ID, UIController ui){
        super(x,y,pc,t,ID, ui);
        pathToImageLeft = "assets/images/Animals/cat_black_left.png";
        pathToImageRight = "assets/images/Animals/cat_black_right.png";
        price = 20;
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
    }


    public void update(double dt){
        super.update(dt);
    }
}

