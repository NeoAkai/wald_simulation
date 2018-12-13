package model.GameObjects;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;

public class BuyButton extends GraphicalObject {

    private boolean visible;
    private String product;

    public BuyButton(double x, double y, String product){
        this.x = x;
        this.y = y;
        createAndSetNewImage("assets/images/UiImages/buy.png");
        this.product = product;
    }

    @Override
    public void draw(DrawTool drawTool) {
        if(visible) drawTool.drawImage(getMyImage(),x,y);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
