package mapObjects;

import control.ProgramController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class Tree extends CoveringObject  {

    //Atribute
    private int width=50;
    private int heigth=50;
    private String art;
    private boolean axeable = false;
    private boolean realAxeable = false;
    private double axeCounter = 0.5;
    private boolean imgload =false;
    private ProgramController pc;
    private boolean klicked = false;
    private boolean living = true;

    private boolean parasiten = false;
    private double parasitenCounter = 300;
    private double parasitenSpawnCounter = 500;
    //Referenzen


    public Tree(double x, double y,String art, ProgramController pc){
        this.x = x;
        this.y = y;
        this.pc = pc;
        this.art = art;

        Hitbox = new Rectangle2D.Double(x,y-50,width,heigth);

        if(art.equals("T")) {
            createAndSetNewImage("assets/images/MapObjectImages/tree.png");
        }else if(art.equals("B")){
            createAndSetNewImage("assets/images/MapObjectImages/birke.png");
        }

    }
    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        if(!imgload){
            drawTool.drawImage(getMyImage(),x,y-50);
        }else if(imgload){
            drawTool.drawImage(getMyImage(),x,y);
        }

    }

    public void setAxeable(boolean b){
        axeable = b;
    }

    /*public void mouseReleased(MouseEvent e){
        if(klicked) {
            if (realAxeable && living) {
                if (e.getX() > x && e.getX() < x + width && e.getY() > y - 50 && e.getY() < y + heigth - 50) {
                    if(!parasiten) pc.updateWood((int) (Math.random() * 12 + 8));
                    //living = false;
                    pc.removeTree(this);
                    //createAndSetNewImage("assets/images/grass.png"); //wichtig, da sonst Grafikfehler auftritt!
                    //imgload = true;
                }
            }
        }
        klicked = !klicked;
    }*/

    public void real(boolean b){
        realAxeable = b;
    }

    public void update(double dt){
        if(axeable){
            axeCounter = axeCounter - dt;
        }

        parasitenSpawnCounter = parasitenSpawnCounter - dt;

        if(parasitenSpawnCounter <= 0){

        }

        if(parasiten){
            parasitenCounter = parasitenCounter -dt;
        }
        if(parasitenCounter <=0 && parasiten){
            pc.removeTree(this);
        }

        if(axeCounter <= 0){
            axeable = false;
            realAxeable = true;
            axeCounter = 0.5;
        }
    }

    public void parasiten(boolean b){
        parasiten = b;
    }

    public boolean isParasiten() {
        return parasiten;
    }

    public void relive(String newArt){
        art = newArt;
        living = true;
    }
}

