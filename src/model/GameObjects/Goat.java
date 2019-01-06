package model.GameObjects;

import control.ProgramController;
import control.framework.UIController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class Goat extends Animal  {

    //Atribute

    //Referenzen


    public Goat(double x, double y, ProgramController pc, String t, int ID, UIController ui){
        super(x,y, pc,t,ID,ui);
        pathToImageLeft = "assets/images/ziege_links.png";
        pathToImageRight = "assets/images/Animals/ziege.png";
        price = 250;
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
    }


    public void update(double dt){
        super.update(dt);
    }
}

