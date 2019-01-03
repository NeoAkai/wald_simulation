package model.GameObjects;

import mapObjects.CoveringObject;

public class ProducingObject extends CoveringObject {

    protected double itemCooldown, maxItemCooldown, progressBarColorR, progressBarWidth, progressBarPercentage;

    @Override
    public void update(double dt) {
        if(itemCooldown > 0 && maxItemCooldown != 61){
            itemCooldown -= dt;
        }else{
            itemCooldown = maxItemCooldown;
        }
        progressBarPercentage = itemCooldown / maxItemCooldown;
        progressBarWidth = -0.5 * progressBarPercentage * 100 + 50;
        progressBarColorR = 2.55 * progressBarPercentage * 100;
    }
}
