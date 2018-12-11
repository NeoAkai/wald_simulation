package mapObjects;

import model.framework.GraphicalObject;

import java.awt.geom.Rectangle2D;

public class CoveringObject extends GraphicalObject {

    protected Rectangle2D Hitbox;

    public Rectangle2D getHitbox() {
        return Hitbox;
    }

    public void setHitbox(Rectangle2D hitbox) {
        Hitbox = hitbox;
    }
}
