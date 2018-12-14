package mapObjects;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class Grass extends GraphicalObject  {

    //Atribute
    private boolean treePlantable;

    //Referenzen
    private CoveringObject coveringObject;


    public Grass(double x, double y, boolean treePlantable){
        this.x = x;
        this.y = y;
        this.treePlantable = treePlantable;
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

    public boolean plant(CoveringObject c){
        boolean b = false;

        if(coveringObject == null) {
            if (c instanceof Tree) {
                if (treePlantable) {
                    coveringObject = c;
                    b = true;
                }
            } else {
                coveringObject = c;
                b = true;
            }
        }

        return b;
    }
}

