package model.GameObjects;

import control.Config;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Plant extends ProducingObject{

    private String type;
    private BufferedImage growingImage, doneImage;

    public Plant(String type, int x, int y){
        this.x = x;
        this.y = y;
        this.type = type;
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

}
