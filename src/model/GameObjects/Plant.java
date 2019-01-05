package model.GameObjects;

import control.Config;
import control.ProgramController;

import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Plant extends ProducingObject{

    private String type;
    private BufferedImage growingImage, doneImage;
    private boolean grown = false;
    private ProgramController pc;
    private boolean klicked = false;

    public Plant(String type, int x, int y, ProgramController pc){
        this.x = x;
        this.y = y;
        this.type = type;
        this.pc = pc;
        itemCooldown = 25;
        setImage();
    }

    private void setImage(){
        try {
            if(type.equals("cherry")){
                growingImage = ImageIO.read(new File("assets/images/MapObjectImages/bush.png"));
                doneImage = ImageIO.read(new File("assets/images/MapObjectImages/cherryBush.png"));
            }else if(type.equals("carrot")){
                growingImage = ImageIO.read(new File("assets/images/MapObjectImages/field.png"));
                doneImage = ImageIO.read(new File("assets/images/MapObjectImages/carotField.png"));
            }else if(type.equals("wheat")){
                growingImage = ImageIO.read(new File("assets/images/MapObjectImages/field.png"));
                doneImage = ImageIO.read(new File("assets/images/MapObjectImages/wheatField.png"));
            }
        } catch (IOException e) {
            if (Config.INFO_MESSAGES) System.out.println("Laden eines Bildes fehlgeschlagen");
        }
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        if(itemCooldown <= 0){
            setMyImage(doneImage);
        }else{
            setMyImage(growingImage);
        }
    }

    public void mouseReleased(MouseEvent e){
        if(e.getX()>x&&e.getX()<x+50&&e.getY()>y&&e.getY()<y+50&&itemCooldown<=0){
            if(klicked) {

                pc.removePlant(this);
            }
            klicked = !klicked;
        }
    }

    public int getx(){
        return (int)x;
    }

    public int gety(){
        return (int)y;
    }

}
