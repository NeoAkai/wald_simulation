package model.GameObjects;

import control.ProgramController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public abstract class MenuButton extends GraphicalObject {

    protected Rectangle2D.Double hitbox;
    protected boolean klicked, visible, active;
    protected ProgramController pc;

    public MenuButton(double x, double y, int width, int height, ProgramController pc){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        hitbox = new Rectangle2D.Double(x,y,width,height);
        this.pc = pc;
        visible = true;
    }

    @Override
    public void draw(DrawTool drawTool) {
        if(visible) drawTool.drawImage(getMyImage(),x,y);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(klicked){
            if(visible){
                if(hitbox.contains(e.getPoint())){
                    activateButton();
                }
            }else if(active){
                doButtonFunction(e);
            }
        }
        klicked = !klicked;
    }

    protected void activateButton(){
      pc.activateButton(this);
    }

    protected abstract void doButtonFunction(MouseEvent e);

    public Rectangle2D.Double getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle2D.Double hitbox) {
        this.hitbox = hitbox;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
