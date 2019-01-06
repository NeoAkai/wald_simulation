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
    private boolean klicked, visible = false;
    private Barn b;
    private BufferedImage food, point1, point2, point3, point4, point5, barn;
    private Rectangle2D.Double hitbox;

    public BarnInfo(double x, double y, ProgramController pc, Barn b){
        this.x = x;
        this.y = y;
        this.pc = pc;
        this.b = b;
        createAndSetNewImage("assets/images/UiImages/BarnInfo.png");

        hitbox = new Rectangle2D.Double(x+100,y+445,350,120);

        setImage();
    }

    private void setImage(){
        try {
            if(b.getFood().equals("cherry"))food = ImageIO.read(new File("assets/images/UiImages/cherry.png"));
            if(b.getFood().equals("carrot"))food = ImageIO.read(new File("assets/images/UiImages/carrot.png"));
            if(b.getFood().equals("wheat"))food = ImageIO.read(new File("assets/images/UiImages/wheat.png"));
            point1 = ImageIO.read(new File("assets/images/UiImages/point.png"));
            point2 = ImageIO.read(new File("assets/images/UiImages/point.png"));
            point3 = ImageIO.read(new File("assets/images/UiImages/point.png"));
            point4 = ImageIO.read(new File("assets/images/UiImages/point.png"));
            point5 = ImageIO.read(new File("assets/images/UiImages/point.png"));

            barn = b.getBarnImage();


        } catch (IOException e) {
            if (Config.INFO_MESSAGES) System.out.println("Laden eines Bildes fehlgeschlagen");
        }
    }

    @Override
    public void draw(DrawTool drawTool) {

        if(visible){
            drawTool.drawImage(getMyImage(),x, y);


            drawTool.drawImage(barn,x+250,y+90);

            if(b.getAllAnimals()>=1)drawTool.drawImage(point1,x+44,y+300);
            if(b.getAllAnimals()>=2)drawTool.drawImage(point1,x+105,y+300);
            if(b.getAllAnimals()>=3)drawTool.drawImage(point1,x+165,y+300);
            if(b.getAllAnimals()>=4)drawTool.drawImage(point1,x+226,y+300);
            if(b.getAllAnimals()>=5)drawTool.drawImage(point1,x+286,y+300);

            drawTool.drawImage(food,x+260,y+495);


        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(klicked){
            if(visible){
                if(hitbox.contains(e.getPoint())){
                    System.out.println("Fütterungszeit");
                }
            }

            if(e.getX()>x+522&&e.getX()<x+569&&e.getY()>y+11&&e.getY()<y+54){
                visible = false;
            }

        }
        klicked =! klicked;
    }

    public void setVisible(boolean b){
        visible = b;

    }
}
