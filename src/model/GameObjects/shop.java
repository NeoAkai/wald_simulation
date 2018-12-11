package model.GameObjects;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class shop extends GraphicalObject  {

    //Atribute
    private int width;
    private int heigth;
    private boolean visible = false;
    //Referenzen


    public shop(double x, double y){
        this.x = x;
        this.y = y;
        createAndSetNewImage("assets/images/shop.png");


    }
    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        if(visible)drawTool.drawImage(getMyImage(),x,y);
    }


    public void setVisible(boolean b){
        visible = b;
    }

}

