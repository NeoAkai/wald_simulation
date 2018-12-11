package model.GameObjects;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class UserInterface extends GraphicalObject  {

    //Atribute
    private int width;
    private int heigth;
    private int wood = 0;
    private int cash = 500;
    private int food = 0;


    //Referenzen


    public UserInterface(double x, double y){
        this.x = x;
        this.y = y;
        createAndSetNewImage("assets/images/UiImages/interface.png");

    }
    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.drawImage(getMyImage(),x,y);
        drawTool.setCurrentColor(255,255,255,255);
        drawTool.drawText(120, 32, cash+"$");
        drawTool.drawText(340,32,wood+"");
        drawTool.drawText(560,32,food+"");
    }

    public void addCash(int amount){
        cash = cash + amount;
    }

    public void addWood(int amount){
        wood = wood + amount;
    }
}

