package Dino;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;

public class LittleDino {
    private double posX;
    private double posY;
    private Image image;
    private Image runOne;
    private Image runTwo;
    private Image dead;
    private ImageView imageView;
    private double yVel = 0;
    private int count = 0;
    private boolean isRunOne = true;
    private boolean die = false;
    private boolean fast = false;
    private int jumpCount;

    public LittleDino() throws MalformedURLException {
        File file = new File("src/main/resources/dino_sample.png");
        String localUrl = file.toURI().toURL().toString();
        this.image = new Image(localUrl, false);

        this.imageView = new ImageView();
        this.posX = 50;
        this.posY = 245;
        this.imageView.setX(this.posX);
        this.imageView.setY(this.posY);
        this.imageView.setImage(this.image);

        File runOne = new File("src/main/resources/dino_run_1.png");
        File runTwo = new File("src/main/resources/dino_run_2.png");
        File dead = new File("src/main/resources/dino_die.png");
        String runOneUrl = runOne.toURI().toURL().toString();
        String runTwoUrl = runTwo.toURI().toURL().toString();
        String dieUrl = dead.toURI().toURL().toString();

        this.runOne = new Image(runOneUrl);
        this.runTwo = new Image(runTwoUrl);
        this.dead = new Image(dieUrl);
        this.jumpCount = 0;

    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
        this.imageView.setY(this.posY);
    }

    public void addToRoot(Group root) {
        root.getChildren().add(this.imageView);
    }
    public void start() {
        this.imageView.setImage(this.runOne);
    }

    public void move() {
        if (!die) {
            count++;
            if (this.jumpCount == 2 && (this.getPosY() >= 265 || this.getPosY() >= 245)) {
                this.jumpCount = 0;
            }
            if (count % 10 == 0) {
                if (isRunOne) {
                    this.imageView.setImage(this.runTwo);
                    this.isRunOne = false;
                } else {
                    this.imageView.setImage(this.runOne);
                    this.isRunOne = true;
                }
            }
            this.setPosY(this.getPosY() + this.yVel);
            if (this.getPosY() >= 245 && !this.fast) {
                this.setPosY(245);
                this.yVel = 0;
            }else if (this.getPosY() >= 265 && this.fast) {
                this.setPosY(265);
                this.yVel = 0;
            } else {
                this.imageView.setImage(this.image);
                this.yVel += 1;
            }
        } else {
            if (this.fast) {
                this.setPosY(this.getPosY() - 20);
                this.fast = false;
            }
            this.imageView.setImage(this.dead);
        }
    }

    public void jump() {
        if (!die) {
            if (this.jumpCount < 2) {
                this.imageView.setImage(this.image);
                this.yVel = -22;
                this.jumpCount ++;
            }
        } else {
            this.imageView.setImage(this.dead);
        }
    }

    public void die() {
        this.die = true;
    }

    public void fast() throws MalformedURLException {
        this.setPosY(this.getPosY() + 20);
        File fast1 = new File("src/main/resources/dino_fast_1.png");
        File fast2 = new File("src/main/resources/dino_fast_2.png");
        String fast1Url = fast1.toURI().toURL().toString();
        String fast2Url = fast2.toURI().toURL().toString();

        this.runOne = new Image(fast1Url);
        this.runTwo = new Image(fast2Url);
        this.fast = true;
    }

    public void slow() throws MalformedURLException {
        this.setPosY(this.getPosY() - 20);
        File runOne = new File("src/main/resources/dino_run_1.png");
        File runTwo = new File("src/main/resources/dino_run_2.png");
        String runOneUrl = runOne.toURI().toURL().toString();
        String runTwoUrl = runTwo.toURI().toURL().toString();

        this.runOne = new Image(runOneUrl);
        this.runTwo = new Image(runTwoUrl);

        this.fast = false;

    }

    public double getyVel() {
        return yVel;
    }
}
