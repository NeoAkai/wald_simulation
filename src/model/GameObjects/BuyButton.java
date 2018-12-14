package model.GameObjects;

import control.ProgramController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;

public class BuyButton extends GraphicalObject {

    private boolean visible;
    private int product;
    private ProgramController pc;
    private boolean klicked = false;

    public BuyButton(double x, double y,int product, ProgramController pc){
        this.x = x;
        this.y = y;
        this.pc = pc;
        createAndSetNewImage("assets/images/UiImages/buy.png");
        this.product = product;
    }

    @Override
    public void draw(DrawTool drawTool) {
        if(visible) drawTool.drawImage(getMyImage(),x,y);
    }

    //lol

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getX()>x&&e.getX()<x+26&&e.getY()>y&&e.getY()<y+15){
            if(klicked){

            }
        }
        klicked = !klicked;
    }

    public boolean isVisible() {
        return visible;
    }

    //lol

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
