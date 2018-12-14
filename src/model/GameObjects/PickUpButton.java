package model.GameObjects;

import control.ProgramController;

import java.awt.event.MouseEvent;

public class PickUpButton extends MenuButton{


    public PickUpButton(double x, double y, int width, int height, ProgramController pc) {
        super(x, y, width, height, pc);

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
