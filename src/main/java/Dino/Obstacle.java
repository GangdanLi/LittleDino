package Dino;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

public interface Obstacle {
    public double getXPos();
    public double getYPos();
    public void setXPos(double posX);
    public void setYPos(double posY);
    public ImageView getImageView();
    public void addToRoot(Group root);
    public void readyToAppear();
    public void move();
}
