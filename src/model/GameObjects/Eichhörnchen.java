package model.GameObjects;

import control.ProgramController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class Eichhörnchen extends Animal  {

    //Atribute

    //Referenzen


    public Eichhörnchen(double x, double y, ProgramController pc,String t, int ID){
        super(x,y,pc,t,ID);
        pathToImageLeft = "assets/images/eichhörnchen_links.png";
        pathToImageRight = "assets/images/Animals/eichhörnchen.png";
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
    }


    public void update(double dt){
        super.update(dt);
    }
}

