package model.GameObjects;

import control.ProgramController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;

public class InventoryButton extends MenuButton {

    //Atribute

    //Referenzen


    public InventoryButton(double x, double y, ProgramController pc) {
        super(x,y,40,40,pc);
        this.x = x;
        this.y = y;

        createAndSetNewImage("assets/images/UiImages/invButton.png");

    }

    @Override
    protected void doButtonFunction(MouseEvent e) {
    }

    @Override
    protected void onButtonActivation() {
        pc.setInventoryVisible(true);
    }

    @Override
    protected void onButtonDeactivation() {

    }

    public void setVisibility(boolean b){
        visible = b;
    }
    }




