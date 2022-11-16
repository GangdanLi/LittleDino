package Dino;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;

import static Dino.App.SPEED;

public class ThreeObstacles implements Obstacle{

    private double posX;
    private double posY;
    private Image image;
    private ImageView imageView;

    public ThreeObstacles() throws MalformedURLException {
        File file = new File("src/main/resources/obstacle3.png");
        String localUrl = file.toURI().toURL().toString();
        this.image = new Image(localUrl, false);
        this.imageView = new ImageView();
        this.posX = -150;
        this.posY = 253;
        this.imageView.setX(this.posX);
        this.imageView.setY(this.posY);
        this.imageView.setImage(this.image);
    }

    @Override
    public double getXPos() {
        return this.posX;
    }

    @Override
    public double getYPos() {
        return this.posY;
    }

    @Override
    public void setXPos(double posX) {
        this.posX = posX;
        this.imageView.setX(this.posX);
    }

    @Override
    public void setYPos(double posY) {
        this.posY = posY;
        this.imageView.setY(this.posY);
    }

    @Override
    public ImageView getImageView() {
        return this.imageView;
    }

    @Override
    public void addToRoot(Group root) {
        root.getChildren().add(this.imageView);
    }

    @Override
    public void readyToAppear() {
        if (this.getXPos() <= -10) {
            this.setXPos(730);
        }
    }

    @Override
    public void move() {
        this.setXPos(this.getXPos() - SPEED);
    }
}
