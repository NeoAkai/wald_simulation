package model.GameObjects;

import mapObjects.Grass;
import control.ProgramController;
import mapObjects.Tree;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;

public class AxeButton extends MenuButton {

    //Atribute
    private Grass[][] grasses;
    private boolean klicked = false;
    private boolean visible = true;

    //Referenzen


    public AxeButton(double x, double y, Grass[][] grasses, ProgramController pc) {
        super(x,y,40,40,pc);
        this.grasses = grasses;
        createAndSetNewImage("assets/images/UiImages/axeButton.png");
    }

    @Override
    protected void doButtonFunction(MouseEvent e) {

            for (int i = 0; i < grasses.length; i++) {
                for (int f = 0; f < grasses[0].length; f++) {
                    if(grasses[i][f] != null) {
                        if (grasses[i][f].getCoveringObject() != null) {
                            if(grasses[i][f].getCoveringObject().getHitbox() != null) {
                                if (grasses[i][f].getCoveringObject().getHitbox().contains(e.getPoint())) {
                                    if (!((Tree) grasses[i][f].getCoveringObject()).isYoung()) pc.addWood((int) (Math.random() * 12 + 8));
                                    pc.removeTree((Tree) grasses[i][f].getCoveringObject());
                                }
                            }
                        }
                    }
                }

        }
    }

    @Override
    protected void onButtonActivation() {

    }

    @Override
    protected void onButtonDeactivation() {

    }

    public void setVisibility(boolean b){
        visible = b;
    }
}

