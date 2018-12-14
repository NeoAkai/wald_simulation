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



    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getX()>x&&e.getX()<x+26&&e.getY()>y&&e.getY()<y+15) {
            if (visible) {
                if (klicked) {
                    switch (product) {
                        case 0:

                            break;
                        case 1:

                            break;
                        case 2:

                            break;
                        case 3:

                            break;

                        case 4:

                            break;

                        case 5:
                            if(pc.getCash()>=200){
                                pc.addAnimal(1,200);
                            }
                            break;

                    }


                }
            }
            klicked = !klicked;
        }
    }

    public boolean isVisible() {
        return visible;
    }



    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
