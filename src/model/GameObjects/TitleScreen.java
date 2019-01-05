package model.GameObjects;

import control.ProgramController;
import mapObjects.CoveringObject;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class TitleScreen extends CoveringObject {

    //Atribute

    private ProgramController pc;
    private boolean klicked = false;



    public TitleScreen(double x, double y, ProgramController pc) {
        this.x = x;
        this.y = y;
        this.pc = pc;


        createAndSetNewImage("assets/images/UiImages/titlescreen.png");

    }

    public void mouseReleased(MouseEvent e){
        if(e.getX()>x+613&&e.getX()<x+763&&e.getY()>y+488&&e.getY()<y+588){
            if(klicked)pc.startGame();
            klicked = !klicked;
        }
    }


    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);

        drawTool.drawImage(getMyImage(), x, y);
    }
}