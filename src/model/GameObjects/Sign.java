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

public class Sign extends GraphicalObject {

    private ProgramController pc;
    private boolean visible = false;
    private Animal animal;
    private Barn b;

    public Sign(double x, double y, ProgramController pc,Animal animal){
        this.x = x;
        this.y = y;
        this.pc = pc;
        this.animal = animal;
        createAndSetNewImage("assets/images/UiImages/schild.png");


    }

    public Sign(double x, double y, ProgramController pc,Barn b){
        this.x = x;
        this.y = y;
        this.pc = pc;
        this.b = b;
        createAndSetNewImage("assets/images/UiImages/schild.png");


    }

    @Override
    public void draw(DrawTool drawTool) {

        if(visible){
            drawTool.drawImage(getMyImage(),x,y);

        }
    }

    public void setVisibility(boolean a){
        visible = a;
    }

    public void mouseReleased(MouseEvent e){
        if(e.getX()>x&&e.getX()<x+50&&e.getY()>y&&e.getY()<y+60&&visible){
            setVisibility(false);
            if(animal!=null)animal.setTime(250);
            if(b!=null) b.setnewTime(250);
            if(animal!=null)pc.addCash(animal.getPrice());
            if(b!=null)pc.addCash(b.getPrice());

        }
    }

    public void setnewX(double a){
        x = a;
    }
    public void setnewY(double a){
        y = a - 60;
    }
}
