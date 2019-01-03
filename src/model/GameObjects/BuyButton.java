package model.GameObjects;

import control.ProgramController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import java.awt.event.MouseEvent;

public class BuyButton extends GraphicalObject {

    private boolean visible;
    private int product;
    private ProgramController pc;
    private boolean klicked = false;
    private ShopButton sB;

    public BuyButton(double x, double y,int product, ProgramController pc, ShopButton sB){
        this.x = x;
        this.y = y;
        this.pc = pc;
        this.sB = sB;
        createAndSetNewImage("assets/images/UiImages/buy.png");
        this.product = product;
    }

    @Override
    public void draw(DrawTool drawTool) {
        if(visible) drawTool.drawImage(getMyImage(),x,y);
    }



    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getX()>x&&e.getX()<x+26&&e.getY()>y&&e.getY()<y+15) {
            if (visible) {
                if (klicked) {
                    switch (product) {
                        case 0:

                            break;
                        case 1:

                            break;
                        case 2:

                            break;
                        case 3:

                            break;

                        case 4:

                            break;

                        case 5:
                            if(pc.getCash()>=200){
                                pc.addAnimal(1,200);
                            }
                            break;
                        case 6:
                            if(pc.getCash()>=500){
                                pc.addAnimal(2,500);
                            }
                            break;
                        case 7:
                            if(pc.getCash()>=600){
                                pc.addAnimal(3,600);
                            }
                            break;
                        case 8:
                            if(pc.getCash()>=600){
                                pc.addAnimal(4,600);
                            }
                            break;
                        case 9:
                            if(pc.getCash()>=1000){
                                pc.addAnimal(5,1000);
                            }
                            break;
                        case 10:
                            if(pc.getCash()>=800){
                                pc.addAnimal(6,800);
                            }
                            break;
                        case 11:
                            if(pc.getCash()>=350){
                                pc.addAnimal(7,350);
                            }
                            break;
                        case 12:
                            if(pc.getCash()>=1200){
                                pc.addAnimal(8,1200);
                            }
                            break;
                        case 13:
                            if(pc.getCash()>=2000){
                                pc.addAnimal(9,2000);
                            }
                            break;
                        case 14:
                            if(pc.getCash()>=3000&&pc.getWood()>=1337){
                                pc.build(6,3000,1337,false);
                            }
                            break;
                        case 15:
                            if(pc.getCash()>=3000&&pc.getWood()>=1337){
                                pc.build(7,3500,2200,false);
                            }
                            break;
                        case 16:
                            if(pc.getCash()>=3000&&pc.getWood()>=1337){
                                pc.build(8,4000,2400,false);
                            }
                            break;
                        case 17:
                            if(pc.getCash()>=3000&&pc.getWood()>=1337){
                                pc.build(9,4000,2400,false);
                            }
                            break;
                        case 18:
                            if(pc.getCash()>=3000&&pc.getWood()>=1337){
                                pc.build(10,6500,4000,false);
                            }
                            break;
                        case 19:
                            if(pc.getCash()>=3000&&pc.getWood()>=1337){
                                pc.build(11,4800,2500,false);
                            }
                            break;
                        case 20:
                            if(pc.getCash()>=3000&&pc.getWood()>=1337){
                                pc.build(12,2000,2600,false);
                            }
                            break;
                        case 21:
                            if(pc.getCash()>=3000&&pc.getWood()>=1337){
                                pc.build(13,8500,5200,false);
                            }
                            break;
                        case 22:
                            if(pc.getCash()>=3000&&pc.getWood()>=1337){
                                pc.build(14,12000,7350,false);
                            }
                            break;
                    }

                    sB.onButtonDeactivation();
                    pc.deactivateButton();

                }
            }
            klicked = !klicked;
        }
    }

    public boolean isVisible() {
        return visible;
    }



    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
