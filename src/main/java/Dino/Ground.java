package Dino;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;

import static Dino.App.SPEED;

public class Ground {
    private double posX;
    private double posY;
    private Image image;
    private ImageView imageView;

    public Ground() throws MalformedURLException {
        File file = new File("src/main/resources/ground.png");
        String localUrl = file.toURI().toURL().toString();
        this.image = new Image(localUrl, false);
        this.imageView = new ImageView();
        this.posX = 0;
        this.posY = 300;
        this.imageView.setX(this.posX);
        this.imageView.setY(this.posY);
        this.imageView.setImage(this.image);
    }

    public Ground(double posX) throws MalformedURLException {
        File file = new File("src/main/resources/ground.png");
        String localUrl = file.toURI().toURL().toString();
        this.image = new Image(localUrl, false);
        this.imageView = new ImageView();
        this.posX = posX;
        this.posY = 300;
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

    public Image getImage() {
        return image;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setPosX(double posX) {
        this.posX = posX;
        this.imageView.setX(this.posX);
    }

    public void setPosY(double posY) {
        this.posY = posY;
        this.imageView.setY(this.posY);
    }

    public void addToRoot(Group root) {
        root.getChildren().add(this.imageView);
    }

    public void move() {
        this.setPosX(this.getPosX() - SPEED);
        if (this.getPosX() < -725) {
            this.setPosX(715);
        }
    }
}
