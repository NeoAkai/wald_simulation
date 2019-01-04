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
    private double growTime = 1;
    private boolean young = true;

    private boolean parasiten = false;
    private double parasitenCounter = 300;
    private double parasitenSpawnCounter = 500;
    //Referenzen


    public Tree(double x, double y,String art, ProgramController pc, double growTime){
        this.x = x;
        this.y = y;
        this.pc = pc;
        this.art = art;
        this.growTime = growTime;

        Hitbox = new Rectangle2D.Double(x,y-50,width,heigth);

        checkYoungness();
    }

    public void checkYoungness(){
        if(!young) {
            if (art.equals("T")) {
                createAndSetNewImage("assets/images/MapObjectImages/tree.png");
            } else if (art.equals("B")) {
                createAndSetNewImage("assets/images/MapObjectImages/birke.png");
            }
        }else if(young){
            if (art.equals("T")) {
                createAndSetNewImage("assets/images/MapObjectImages/sapling.png");
            } else if (art.equals("B")) {
                createAndSetNewImage("assets/images/MapObjectImages/birk_sapling.png");
            }
        }
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        if(!young ) {
            if (!imgload) {
                drawTool.drawImage(getMyImage(), x, y - 50);
            } else if (imgload) {
                drawTool.drawImage(getMyImage(), x, y);
            }
        }else if(young){
            drawTool.drawImage(getMyImage(), x, y);
        }

    }

    public void setAxeable(boolean b){
        axeable = b;
    }


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


        if(young){
            growTime = growTime - dt;
        }

        if(growTime <= 0 && young){
            young = false;
            checkYoungness();
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

