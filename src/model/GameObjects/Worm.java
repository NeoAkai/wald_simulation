package model.GameObjects;

import control.ProgramController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class Worm extends Animal  {

    //Atribute

    //Referenzen


    public Worm(double x, double y, ProgramController pc, String t,int ID){
        super(x,y,pc,t,ID);
        pathToImageLeft = "assets/images/cooler_wurm_links.png";
        pathToImageRight = "assets/images/Animals/cooler_wurm.png";
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
    }


    public void update(double dt){
        super.update(dt);
    }
}

