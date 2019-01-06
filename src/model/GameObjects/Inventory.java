package model.GameObjects;

import control.Config;
import control.ProgramController;
import control.SQLHandler;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Inventory extends GraphicalObject {

    private ProgramController pc;
    private boolean visible = false;
    private Rectangle2D.Double hitbox;
    private SQLHandler sqlHandler;

    public Inventory(double x, double y, ProgramController pc,SQLHandler sqlHandler){
        this.x = x;
        this.y = y;
        this.pc = pc;
        this.sqlHandler = sqlHandler;
        createAndSetNewImage("assets/images/UiImages/inventory.png");


        }


    @Override
    public void draw(DrawTool drawTool) {

       if(visible){
           drawTool.drawImage(getMyImage(),x,y);
           drawTool.drawText(x+150,y+130,""+sqlHandler.getCherries());
           drawTool.drawText(x+150,y+255,""+sqlHandler.getCarrots());
           drawTool.drawText(x+150,y+390,""+sqlHandler.getWheat());
       }
    }

    public void setVisibility(boolean a){
        visible = a;
    }

}
