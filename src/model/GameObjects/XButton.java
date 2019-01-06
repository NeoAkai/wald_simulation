package model.GameObjects;

import control.ProgramController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class XButton extends GraphicalObject {

    private ProgramController pc;
    private boolean klicked, visible;

    private Rectangle2D.Double hitbox;

    public XButton(double x, double y, ProgramController pc){
        this.x = x;
        this.y = y;
        this.pc = pc;
        createAndSetNewImage("assets/images/UiImages/ix.png");
        visible = false;
        hitbox = new Rectangle2D.Double(x,y,40,40);
    }

    @Override
    public void draw(DrawTool drawTool) {
        if(visible) drawTool.drawImage(getMyImage(),x, y);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(klicked){
            if(visible){
                if(hitbox.contains(e.getPoint())){
                    pc.deactivateButton();
                    pc.setInventoryVisible(false);
                }
            }
        }
        klicked =! klicked;
    }

    public void setVisible(boolean b){
        visible = b;
    }
}
