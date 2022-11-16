package Dino;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.MalformedURLException;


public class App extends Application {

    static int SPEED = 5;
    private Game game;
    private boolean begin = false;
    private Text toStart = new Text("Click To Start.");
    private Stage currentStage;

    @Override
    public void start(Stage primaryStage) throws MalformedURLException {
        this.currentStage = primaryStage;

        this.game = new Game();

        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dino Test");
        primaryStage.show();

        Canvas canvas = new Canvas(725, 400);

        primaryStage.setWidth(725);
        primaryStage.setHeight(400);
        root.getChildren().add(canvas);

        this.toStart.setX(280);
        this.toStart.setY(180);
        this.toStart.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        this.game.addToRoot(root);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.seconds(1.0 / 60.0),
            (actionEvent) -> {
            scene.setOnMouseClicked(event -> {
                this.begin = true;
                this.game.start(); });
                if (this.begin) {
                    this.toStart.setText("");
                    this.game.tick();
                    scene.setOnMouseClicked((event -> this.game.jump()));
                    this.game.updateScore();
                }
            });

        timeline.getKeyFrames().add(frame);
        timeline.play();
        root.getChildren().add(this.toStart);
        root.getChildren().add(this.restart());
    }

    public Button restart() {
        Button restartButton = new Button("TRY AGAIN");
        restartButton.setLayoutX(550);
        restartButton.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        restartButton.setOnAction(event -> {
            SPEED = 5;
            this.currentStage.close();
            Platform.runLater( () -> {
                App newApp = new App();
                try {
                    newApp.start(new Stage());
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            } );
        });
        return restartButton;
    }

    public static void main(String[] args) {
        launch(args);
    }
}