package model.GameObjects;

import control.ProgramController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;

public class ShopButton extends MenuButton {

    //Atribute

    //Referenzen


    public ShopButton(double x, double y, ProgramController pc) {
        super(x,y,40,40,pc);
        this.x = x;
        this.y = y;

        createAndSetNewImage("assets/images/UiImages/ShopButton.png");

    }

    @Override
    protected void doButtonFunction(MouseEvent e) {

    }

    public void setVisibility(boolean b){
        visible = b;
    }
}


