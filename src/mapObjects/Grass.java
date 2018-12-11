package mapObjects;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class Grass extends GraphicalObject  {

    //Atribute
    private int width;
    private int heigth;
    private boolean plantable;

    //Referenzen
    private CoveringObject coveringObject;


    public Grass(double x, double y){
        this.x = x;
        this.y = y;
        createAndSetNewImage("assets/images/MapObjectImages/grass.png");

    }
    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.drawImage(getMyImage(),x,y);
    }

    public CoveringObject getCoveringObject() {
        return coveringObject;
    }

    public void setCoveringObject(CoveringObject coveringObject) {
        this.coveringObject = coveringObject;
    }
}

