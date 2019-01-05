package model.GameObjects;

import control.ProgramController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;

public class Animal extends GraphicalObject {

    protected String pathToImageLeft, pathToImageRight;
    private double moveCounter = (int)(Math.random()*12 + 3);
    private int direction = 0;
    private boolean moving = false, imageSet;
    protected boolean klickable = false;
    protected boolean klicked = false;
    protected ProgramController pc;
    protected String type;
    private int databaseID;

    public Animal(double x, double y, ProgramController pc, String type, int ID){
        this.x = x;
        this.y = y;
        this.pc = pc;
        this.type = type;
        imageSet = false;
        this.databaseID = ID;
    }


    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.drawImage(getMyImage(),x,y);
        if(!imageSet){
            createAndSetNewImage(pathToImageRight);
            width = getMyImage().getWidth();
            height = getMyImage().getHeight();
            imageSet = true;
        }
    }

    public void update(double dt){
        moveCounter = moveCounter - dt;
        if(moveCounter<=0){
            moveCounter = (int)(Math.random()*12 + 3);
            direction = (int)(Math.random()*4+1);
            if(direction==1)createAndSetNewImage(pathToImageRight);
            //if(direction==2)createAndSetNewImage("assets/images/cat_black_down.png");
            if(direction==3)createAndSetNewImage(pathToImageLeft);
            //if(direction==4)createAndSetNewImage("assets/images/cat_black_up.png");

            moving = !moving;
        }
        if(moving){
            if(direction == 1){
                x = x + dt * 20;
            }
            if(direction == 2){
                y = y + dt * 20;
            }
            if(direction == 3){
                x = x - dt * 20;
            }
            if(direction == 4){
                y = y - dt * 20;
            }
            if(x<=150){
                direction=1;
                createAndSetNewImage(pathToImageRight);
            }
            if(x>=1225){
                direction=3;
                createAndSetNewImage(pathToImageLeft);
            }
            if(y<=225){
                direction=2;
                //if(direction==2)createAndSetNewImage("assets/images/cat_black_down.png");
            }
            if(y>=600){
                direction=4;
                //createAndSetNewImage("assets/images/cat_black_up.png");
            }



        }
    }

    public void setKlickable(boolean a){
        klickable = a;
    }

    public void mouseReleased(MouseEvent e){
        if(klickable){
            if(!klicked){
                if(e.getX()>x&&e.getX()<x+30&&e.getY()>y&&e.getY()<y+30){
                    pc.putAnimalIntoBarn(this);
                }
            }
            klicked = !klicked;
        }
    }

    public String getArt(){
        return type;
    }

    public int getDatabaseID() {
        return databaseID;
    }

    public void setDatabaseID(int databaseID) {
        this.databaseID = databaseID;
    }
}
