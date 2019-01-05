package model.GameObjects;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class UserInterface extends GraphicalObject  {

    //Atribute
    private int wood = 500000;
    private int cash = 500000;
    private int harmony;


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
        drawTool.drawText(560,32, harmony +"");
    }

    public void addCash(int amount){
        cash = cash + amount;
    }

    public void addWood(int amount){
        wood = wood + amount;
    }

    public void addHarmony(int amount) { harmony = harmony + amount; }

    public int getWood(){
        return wood;
    }

    public int getCash(){
        return cash;
    }

    public int getHarmony(){
        return harmony;
    }
}

