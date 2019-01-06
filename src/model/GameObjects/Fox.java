package model.GameObjects;

import control.ProgramController;
import control.framework.UIController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class Fox extends Animal  {

    //Atribute

    //Referenzen


    public Fox(double x, double y, ProgramController pc, String t, int ID, UIController ui){
        super(x,y,pc,t,ID,ui);
        pathToImageLeft = "assets/images/fuchs_links.png";
        pathToImageRight = "assets/images/Animals/fuchs.png";
        price = 120;
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
    }


    public void update(double dt){
        super.update(dt);
    }
}

