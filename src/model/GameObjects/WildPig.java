package model.GameObjects;

import control.ProgramController;
import control.framework.UIController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class WildPig extends Animal  {

    //Atribute

    //Referenzen


    public WildPig(double x, double y, ProgramController pc, String t, int ID, UIController ui){
        super(x,y,pc,t,ID,ui);
        pathToImageLeft = "assets/images/wildschwein_links.png";
        pathToImageRight = "assets/images/Animals/wildschwein.png";
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
    }


    public void update(double dt){
        super.update(dt);
    }
}

