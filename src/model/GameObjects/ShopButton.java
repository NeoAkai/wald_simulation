package model.GameObjects;

import control.ProgramController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ShopButton extends MenuButton {

    //Atribute

    //Referenzen
    private BuyButton[] buyButtons;
    private BufferedImage icon, userInterface;



    public ShopButton(double x, double y, ProgramController pc) {
        super(x,y,40,40,pc);
        buyButtons = new BuyButton[23];
        createButtons();

        icon = createNewImage("assets/images/UiImages/ShopButton.png");
        userInterface = createNewImage("assets/images/UiImages/Shop.png");
        setMyImage(icon);

    }

    @Override
    protected void doButtonFunction(MouseEvent e) {

    }

    @Override
    protected void onButtonActivation() {
        setMyImage(userInterface);
        this.setVisible(true);
        for(BuyButton b : buyButtons){
            if(b != null) b.setVisible(true);
        }
        x = 50;
        y = 50;
    }

    @Override
    protected void onButtonDeactivation() {
        setMyImage(icon);
        for(BuyButton b : buyButtons){
            if(b != null) b.setVisible(false);
        }
        x = 900;
        y = 5;
    }

    private void createButtons(){
        for(int i = 0; i < 5; i++){
            buyButtons[i] = new BuyButton(550,180 + i*28, i,pc);
        }
        for(int i = 5; i < 14; i++) {
            buyButtons[i] = new BuyButton(550, 400 + (i-5) * 28, i,pc);
        }
        for(int i = 14; i < 23; i++) {
            buyButtons[i] = new BuyButton(1200, 170 + (i-14) *29, i,pc);
        }

    }

    public void addBuyButtonsToDrawingPanel(){
        for(BuyButton b : buyButtons){
            if(b != null) pc.getUiController().drawObject(b);
        }
    }

    public void resetButtonsOnDrawingPanel(){
        for(BuyButton b : buyButtons){
            if(b != null){
                pc.getUiController().removeObject(b);
                pc.getUiController().drawObject(b);
            }
        }
    }

}


