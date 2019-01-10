package model.GameObjects;

import control.Config;
import control.ProgramController;
import control.framework.UIController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Knebi extends GraphicalObject {

    private boolean klicked = false;
    private BufferedImage info;
    private boolean visible = false;
    private ProgramController pc;


    public Knebi(double x, double y, ProgramController pc){
        this.x = x;
        this.y = y;
        this.pc = pc;

        createAndSetNewImage("assets/images/UiImages/knebi.png");
        try{
        info = ImageIO.read(new File("assets/images/UiImages/StarvingText.png"));
        } catch (IOException e) {
            if (Config.INFO_MESSAGES) System.out.println("Laden eines Bildes fehlgeschlagen");
        }
    }


    public void draw(DrawTool drawTool) {
        drawTool.drawImage(getMyImage(),x,y);
        if(visible)drawTool.drawImage(info,x-448,y+50);
    }

    @Override
    public void update(double dt) {

    }


    public void mouseReleased(MouseEvent e){

        if(e.getX()>x&&e.getX()<x+35&&e.getY()>y&&e.getY()<y+49){
            if(!klicked&&visible) {
                setVisible(false);
            }
            klicked = !klicked;
        }
    }

    public void setVisible(boolean a){
        visible = a;
    }
}
