package Dino;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static Dino.App.SPEED;

public class Game {

    private LittleDino dino;
    private Ground ground;
    private Ground ground2;
    private List<Obstacle> obstacles = new ArrayList<>();
    private Bird bird;
    private int count;
    private int[] obstacleAppear = new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110};
    private Random ran;
    private List<Cloud> clouds = new ArrayList<>();
    private Text scoreToShow;
    private Text tryAgain;
    private Text hiScore;
    private int high;
    private boolean dead = false;
    private boolean fast = false;
    private boolean isJump = false;
    private boolean formerSpeed = false;

    public Game() throws MalformedURLException {this.setUp();}

    public void setUp() throws MalformedURLException {
        this.obstacles.add(new OneObstacle());
        this.obstacles.add(new TwoObstacles());
        this.obstacles.add(new ThreeObstacles());
        this.dino = new LittleDino();
        this.ground = new Ground();
        this.ground2 = new Ground(725);
        this.bird = new Bird();
        this.ran = new Random();
        this.clouds.add(new Cloud());
        this.clouds.add(new Cloud());
        this.clouds.add(new Cloud());
        this.scoreToShow = new Text("SCORE: 000000");
        this.scoreToShow.setX(500);
        this.scoreToShow.setY(100);
        this.scoreToShow.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        this.tryAgain = new Text("");
        this.tryAgain.setX(730);
        this.tryAgain.setY(180);
        this.tryAgain.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        this.dead = false;
        this.count = 0;
        File highScore = new File("src/main/resources/high.txt");
        try {
            InputStreamReader read = new InputStreamReader(new FileInputStream(highScore));
            BufferedReader br = new BufferedReader(read);
            this.high = Integer.parseInt(br.readLine());
            this.hiScore = new Text("HI:  " + this.high);
            this.hiScore.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            this.hiScore.setX(500);
            this.hiScore.setY(120);
            read.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void move() {
        this.ground.move();
        this.ground2.move();
        this.dino.move();
        for (Obstacle o : obstacles) {
            o.move();
        }
        this.bird.move();
        for (Cloud c : clouds) {
            c.move();
        }
    }

    public void updateScore() {
        String text = "SCORE: ";
        int score = this.count / 6;
        if (score / 100000 > 0) {
            text += "" + score;
        } else if (score / 10000 > 0) {
            text += "0" + score;
        } else if (score / 1000 > 0) {
            text += "00" + score;
        } else if (score / 100 > 0) {
            text += "000" + score;
        } else if (score / 10 > 0) {
            text += "0000" + score;
        } else {
            text += "00000" + score;
        }
        this.scoreToShow.setText(text);
    }
    public void tick(){
        if (!dead) {
            count++;
            int i = ran.nextInt(10);
            if (count % 120 == obstacleAppear[i]) {
                obstacles.get(ran.nextInt(2)).readyToAppear();
            }
            if (count % 500 == 0) {
                bird.readyToAppear();
            }
            if (count % 50 == 0) {
                clouds.get(0).readyToAppear();
            }
            if (count % 100 == 0) {
                clouds.get(1).readyToAppear();
            }
            if (count % 150 == 0) {
                clouds.get(2).readyToAppear();
            }
            if (count % 300 == 0) {
                for (Cloud c : clouds) {
                    c.readyToAppear();
                }
            }

            if (isJump && this.dino.getyVel() == 0) {
                this.fast = this.formerSpeed;
                this.isJump = false;
            }

            this.move();
            this.handleCollision();

        } else {
            this.dino.die();
            this.dino.move();
            this.tryAgain.setText("Dino's dead... Please try again.");
            this.tryAgain.setX(this.tryAgain.getX() - 2);
            if (tryAgain.getX() < -330) {
                tryAgain.setX(730);
            }
            if (count / 6 > this.high) {
                File toWrite = new File("src/main/resources/high.txt");
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(toWrite));
                    bw.write(String.valueOf(this.count / 6));
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    public void addToRoot(Group root) {
        for (Cloud c : clouds) {
            c.addToRoot(root);
        }
        this.ground.addToRoot(root);
        this.ground2.addToRoot(root);
        for (Obstacle o : obstacles){
            o.addToRoot(root);
        }
        this.bird.addToRoot(root);
        this.dino.addToRoot(root);
        root.getChildren().add(this.scoreToShow);
        root.getChildren().add(this.hiScore);
        root.getChildren().add(this.tryAgain);
        root.getChildren().add(this.speedButton());
    }
    public void jump() {
        this.formerSpeed = this.fast;
        this.fast = false;
        this.isJump = true;
        this.dino.jump();
    }
    public void start() {
        this.dino.start();
    }
    public Button speedButton() {
        Button newButton = new Button("FAST!!!!");
        newButton.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        newButton.setOnAction(event -> {
            if (SPEED == 5) {
                this.fast = true;
                SPEED = 10;
                newButton.setText("SLOW____");
                try {
                    this.dino.fast();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                this.fast = false;
                SPEED = 5;
                newButton.setText("FAST!!!!");
                try {
                    this.dino.slow();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return newButton;
    }
    public void handleCollision() {
        double dinoHalfX = 0;
        double dinoHalfY = 0;
        if (fast) {
            dinoHalfX = 43.5;
            dinoHalfY = 20;
        } else {
            dinoHalfX = 28;
            dinoHalfY = 29;
        }

        double dinoCenterX = this.dino.getPosX() + dinoHalfX;
        double dinoCenterY = this.dino.getPosY() + dinoHalfY;

        double obstacleOneCenterX = this.obstacles.get(0).getXPos() + 27;
        double obstacleOneCenterY = this.obstacles.get(0).getYPos() + 52.5;
        if (Math.abs(dinoCenterX - obstacleOneCenterX) <= 27 + dinoHalfX && Math.abs(dinoCenterY - obstacleOneCenterY) <= 52.5 + dinoHalfY) {
            this.dead = true;
        }

        double obstacleTwoCenterX = this.obstacles.get(1).getXPos() + 37;
        double obstacleTwoCenterY = this.obstacles.get(1).getYPos() + 36.5;
        if (Math.abs(dinoCenterX - obstacleTwoCenterX) <= 37 + dinoHalfX && Math.abs(dinoCenterY - obstacleTwoCenterY) <= 36.5 + dinoHalfY) {
            this.dead = true;
        }

        double obstacleThreeCenterX = this.obstacles.get(2).getXPos() + 54.5;
        double obstacleThreeCenterY = this.obstacles.get(2).getYPos() + 35;
        if (Math.abs(dinoCenterX - obstacleThreeCenterX) <= 54.5 + dinoHalfX && Math.abs(dinoCenterY - obstacleThreeCenterY) <= 35 + dinoHalfY) {
            this.dead = true;
        }

        double birdCenterX = this.bird.getPosX() + 38.5;
        double birdCenterY = this.bird.getPosY() + 27;
        if (Math.abs(dinoCenterX - birdCenterX) <= 38.5 + dinoHalfX && Math.abs(dinoCenterY - birdCenterY) <= 27 + dinoHalfY) {
            this.dead = true;
        }


    }

}
