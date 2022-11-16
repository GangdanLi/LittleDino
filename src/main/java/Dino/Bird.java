package Dino;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Random;

import static Dino.App.SPEED;

public class Bird {

    private double posX;
    private double posY;
    private Image imageStateOne;
    private Image imageStateTwo;
    private boolean ifStateOne = true;
    private ImageView imageView;
    private Random ran;

    public Bird() throws MalformedURLException {
        File file = new File("src/main/resources/bird1.png");
        String localUrl = file.toURI().toURL().toString();
        this.imageStateOne = new Image(localUrl, false);
        File file2 = new File("src/main/resources/bird2.png");
        String localUrl2 = file2.toURI().toURL().toString();
        this.imageStateTwo = new Image(localUrl2, false);
        this.imageView = new ImageView();
        this.posX = -100;
        this.posY = 100;
        this.imageView.setX(this.posX);
        this.imageView.setY(this.posY);
        this.imageView.setImage(this.imageStateOne);
        this.ran = new Random();
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
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

    public void changeState() {
        if (ifStateOne) {
            this.imageView.setImage(this.imageStateTwo);
            ifStateOne = false;
        } else {
            this.imageView.setImage(this.imageStateOne);
            ifStateOne = true;
        }
    }

    public void addToRoot(Group root) {
        root.getChildren().add(this.imageView);
    }

    public void readyToAppear() {
        if (this.getPosX() <= -10) {
            this.setPosX(730);
            this.setPosY(120 + ran.nextDouble(140));
        }
    }

    public void move() {
        this.setPosX(this.getPosX() - SPEED);
        if (this.getPosX() > -50 && this.getPosX() % 30 == 0) {
            this.changeState();
        }
    }
}

