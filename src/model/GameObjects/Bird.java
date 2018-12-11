package model.GameObjects;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class Bird extends Animal  {

    //Atribute

    //Referenzen


    public Bird(double x, double y){
        super(x,y);
        pathToImageLeft = "assets/images/vogel_links.png";
        pathToImageRight = "assets/images/Animals/vogel.png";
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
    }


    public void update(double dt){
        super.update(dt);
    }
}

