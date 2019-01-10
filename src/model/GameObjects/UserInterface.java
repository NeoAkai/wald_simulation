package model.GameObjects;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class UserInterface extends GraphicalObject  {

    //Atribute
    private int wood ;
    private int cash ;
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
        drawTool.drawText(470, 32, cash+"$");
        drawTool.drawText(690,32,wood+"");

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

