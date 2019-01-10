package model.GameObjects;

import control.Config;
import control.ProgramController;
import model.framework.GraphicalObject;
import view.framework.DrawTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BarnInfo extends GraphicalObject {

    private ProgramController pc;
    private boolean klicked, visible = false;
    private Barn b;
    private BufferedImage food, point1, point2, point3, point4, point5, barn;
    private Rectangle2D.Double hitbox;

    public BarnInfo(double x, double y, ProgramController pc, Barn b){
        this.x = x;
        this.y = y;
        this.pc = pc;
        this.b = b;
        createAndSetNewImage("assets/images/UiImages/BarnInfo.png");


        setImage();
    }

    private void setImage(){
        try {
            if(b.getFood().equals("cherry"))food = ImageIO.read(new File("assets/images/UiImages/cherry.png"));
            if(b.getFood().equals("carrot"))food = ImageIO.read(new File("assets/images/UiImages/carrot.png"));
            if(b.getFood().equals("wheat"))food = ImageIO.read(new File("assets/images/UiImages/wheat.png"));
            point1 = ImageIO.read(new File("assets/images/UiImages/point.png"));
            point2 = ImageIO.read(new File("assets/images/UiImages/point.png"));
            point3 = ImageIO.read(new File("assets/images/UiImages/point.png"));
            point4 = ImageIO.read(new File("assets/images/UiImages/point.png"));
            point5 = ImageIO.read(new File("assets/images/UiImages/point.png"));

            barn = b.getBarnImage();


        } catch (IOException e) {
            if (Config.INFO_MESSAGES) System.out.println("Laden eines Bildes fehlgeschlagen");
        }
    }

    @Override
    public void draw(DrawTool drawTool) {

        if(visible){
            drawTool.drawImage(getMyImage(),x, y);


            drawTool.drawImage(barn,x+250,y+90);

            if(b.getAllAnimals()>=1)drawTool.drawImage(point1,x+44,y+300);
            if(b.getAllAnimals()>=2)drawTool.drawImage(point1,x+105,y+300);
            if(b.getAllAnimals()>=3)drawTool.drawImage(point1,x+165,y+300);
            if(b.getAllAnimals()>=4)drawTool.drawImage(point1,x+226,y+300);
            if(b.getAllAnimals()>=5)drawTool.drawImage(point1,x+286,y+300);

            drawTool.drawImage(food,x+260,y+495);

            if(b.getStarving())drawTool.setCurrentColor(255,0,0,255);
            if(!b.getStarving())drawTool.setCurrentColor(0,0,0,255);

            if(b.getMultiStarving()!=0) {
                if(b.getStarvingTime()%60>=10){
                    drawTool.drawText(x + 333, y + 385, "" + (int) ((b.getStarvingTime() / b.getMultiStarving())/60)+":"+(int)((b.getStarvingTime() / b.getMultiStarving())%60));
                }else{
                    drawTool.drawText(x + 333, y + 385, "" + (int) ((b.getStarvingTime() / b.getMultiStarving())/60)+":0"+(int)((b.getStarvingTime() / b.getMultiStarving())%60));
                }
            }else{
                if(b.getStarvingTime()%60>=10){
                    drawTool.drawText(x + 333, y + 385, "" + (int) (b.getStarvingTime()/60)+":"+(int)(b.getStarvingTime()%60));
                }else{
                    drawTool.drawText(x + 333, y + 385, "" + (int) (b.getStarvingTime()/60)+":0"+(int)(b.getStarvingTime()%60));
                }

            }


        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(klicked){
            if(visible) {
                if (e.getX() > x + 100 && e.getX() < x + 450 && e.getY() > y + 440 && e.getY() < y + 560) {
                    System.out.println("Fütterungszeit");
                    switch (b.getFood()){
                        case "cherry":
                            if(pc.getSqlHandler().getCherries() >= 5){
                                b.addStarvingTime(300);
                                pc.getSqlHandler().changeCherriesByAmount(-5);
                            }
                            break;
                        case "wheat":
                            if(pc.getSqlHandler().getWheat() >= 5){
                                b.addStarvingTime(300);
                                pc.getSqlHandler().changeWheatByAmount(-5);
                            }
                            break;
                        case "carrot":
                            if(pc.getSqlHandler().getCarrots() >= 5){
                                b.addStarvingTime(300);
                                pc.getSqlHandler().changeCarrotsByAmount(-5);
                            }
                            break;
                    }

                }


                if (e.getX() > x + 522 && e.getX() < x + 569 && e.getY() > y + 11 && e.getY() < y + 54) {
                    setVisible(false);
                }

                if (e.getX() > x + 406 && e.getX() < x + 469 && e.getY() > y + 280 && e.getY() < y + 342) {
                    if (pc.checkFreeAnimalPlaces()&&b.getAllAnimals()>0) {
                        pc.freeAnimal(b.removeAnimal(),this);
                    }
                }
                if(e.getX()>x+495&&e.getX()<x+560&&e.getY()>y+280&&e.getY()<y+342&&b.getAllAnimals()>0){
                    b.removeAnimal();
                    pc.addCash(200);
                }
                if(e.getX()>x+32&&e.getY()>y+72&&e.getX()<x+95&&e.getY()<y+135){
                    b.removeThisBarn();
                    pc.addCash(1000);
                    pc.addWood(800);
                }
            }
        }
        klicked =! klicked;
    }

    public void setVisible(boolean b){
        visible = b;
        pc.setBarnsKlickable(!b);

    }
}
