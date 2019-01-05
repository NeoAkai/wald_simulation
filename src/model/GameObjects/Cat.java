package model.GameObjects;

import control.ProgramController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class Cat extends Animal  {

    //Atribute

    //Referenzen


    public Cat(double x, double y, ProgramController pc,String t){
        super(x,y,pc,t);
        pathToImageLeft = "assets/images/Animals/cat_black_left.png";
        pathToImageRight = "assets/images/Animals/cat_black_right.png";
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
    }


    public void update(double dt){
        super.update(dt);
    }
}

