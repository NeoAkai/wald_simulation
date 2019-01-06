package model.GameObjects;

import control.Config;
import control.ProgramController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BarnInfo extends GraphicalObject {

    private ProgramController pc;
    private boolean klicked, visible;
    private Barn b;
    private BufferedImage food, point, barn;
    private Rectangle2D.Double hitbox;

    public BarnInfo(double x, double y, ProgramController pc, Barn b){
        this.x = x;
        this.y = y;
        this.pc = pc;
        this.b = b;
        createAndSetNewImage("assets/images/UiImages/BarnInfo.png");
        visible = false;
        hitbox = new Rectangle2D.Double(x+100,y+545,350,120);
    }

    private void setImage(){
        try {
            if(b.getFood().equals("cherry"))food = ImageIO.read(new File("assets/images/UiImages/cherry.png"));
            if(b.getFood().equals("carrot"))food = ImageIO.read(new File("assets/images/UiImages/carrot.png"));
            if(b.getFood().equals("wheat"))food = ImageIO.read(new File("assets/images/UiImages/wheat.png"));
            point = ImageIO.read(new File("assets/images/UiImages/cherry.png"));
            barn = b.getBarnImage();


        } catch (IOException e) {
            if (Config.INFO_MESSAGES) System.out.println("Laden eines Bildes fehlgeschlagen");
        }
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
                    System.out.println("Fütterungszeit");
                }
            }
        }
        klicked =! klicked;
    }

    public void setVisible(boolean b){
        visible = b;
    }
}
