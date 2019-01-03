package mapObjects;

import control.ProgramController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;

public class Grass extends GraphicalObject  {

    //Atribute
    private boolean treePlantable;
    private boolean klickable = false;
    private boolean klicked = true;
    private ProgramController pc;
    private int index;

    //Referenzen
    private CoveringObject coveringObject;


    public Grass(double x, double y, boolean treePlantable, ProgramController pc){
        this.x = x;
        this.y = y;
        this.treePlantable = treePlantable;
        this.pc = pc;
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

    public boolean getTreePlantable(){
        return treePlantable;
    }

    public void mouseReleased(MouseEvent e) {
        if (klicked) {
            if (klickable) {
                if (e.getX() >= x && e.getX() <= x + 50 && e.getY() >= y && e.getY() <= y + 50) {

                    System.out.println(x + "   "+ y);
                    pc.addBarn(this, index);
                    pc.removeKlickableGrass();
                }
            }
        }
        klicked = !klicked;
    }
    public void setKlickable(boolean a, int index){
        klickable = a;
       if(klickable)this.index = index;
    }
    public int getx(){
        return (int)x;
    }
    public int gety(){
        return (int)y;
    }
}

