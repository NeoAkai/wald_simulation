package model.GameObjects;

import control.ProgramController;

import java.awt.event.MouseEvent;

public class PickUpButton extends MenuButton{


    public PickUpButton(double x, double y, ProgramController pc) {
        super(x, y, 40, 40, pc);
        createAndSetNewImage("assets/images/UiImages/pickUpButton.png");
    }

    @Override
    protected void doButtonFunction(MouseEvent e) {

    }

    @Override
    protected void onButtonActivation() {

    }

    @Override
    protected void onButtonDeactivation() {

    }
}
