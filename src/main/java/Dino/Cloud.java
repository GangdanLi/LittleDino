package Dino;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Random;

import static Dino.App.SPEED;

public class Cloud {
    private double posX;
    private double posY;
    private Image image;
    private ImageView imageView;
    private Random ran = new Random();

    public Cloud() throws MalformedURLException {
        this.posX = ran.nextDouble(725);
        this.posY = ran.nextDouble(220);
        File file = new File("src/main/resources/cloud.png");
        String localUrl = file.toURI().toURL().toString();
        this.image = new Image(localUrl, false);
        this.imageView = new ImageView();
        this.imageView.setX(this.posX);
        this.imageView.setY(this.posY);
        this.imageView.setImage(this.image);
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosX(double posX) {
        this.posX = posX;
        this.imageView.setX(posX);
    }

    public void setPosY(double posY) {
        this.posY = posY;
        this.imageView.setY(posY);
    }

    public void readyToAppear() {
        if (this.getPosX() <= -80) {
            this.setPosX(730);
            this.setPosY(ran.nextDouble(220));
        }
    }

    public void move() {
        this.setPosX(this.getPosX() - SPEED);
    }

    public void addToRoot(Group root) {
        root.getChildren().add(this.imageView);
    }
}
