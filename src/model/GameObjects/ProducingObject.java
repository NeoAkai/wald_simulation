package model.GameObjects;

import control.ProgramController;
import mapObjects.CoveringObject;
import view.framework.DrawTool;

public class ProducingObject extends CoveringObject {

    protected double itemCooldown, maxItemCooldown, progressBarColorR, progressBarWidth, progressBarPercentage, itemCooldownUpdateCooldown = 5;

    protected ProgramController pc;

    public ProducingObject(ProgramController pc) {
        this.pc = pc;
    }

    @Override
    public void update(double dt) {
        if(itemCooldown > 0 && maxItemCooldown != 61){
            itemCooldown -= dt;
        }else{
            //itemCooldown = maxItemCooldown;
        }
        progressBarPercentage = itemCooldown / maxItemCooldown;
        progressBarWidth = -0.5 * progressBarPercentage * 100 + 50;
        progressBarColorR = 2.55 * progressBarPercentage * 100;
        if(itemCooldownUpdateCooldown > 0){
            itemCooldownUpdateCooldown -= dt;
        }else{
            itemCooldownUpdateCooldown = 5;
            pc.getSqlHandler().updateProductionTimer(this,(int)itemCooldown);
        }
    }

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.drawImage(getMyImage(),x,y);
        drawTool.setCurrentColor(200,200,200,255);
        drawTool.drawFilledRectangle(x,y+50,50,3);
//        drawTool.setCurrentColor((int)progressBarColorR,255,0,255);
        drawTool.drawFilledRectangle(x,y+50,progressBarWidth,3);
    }


}
