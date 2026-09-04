import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {
    private Stage stage;
    private Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        BorderPane borderPane = new BorderPane();
        scene = new Scene(borderPane);
        stage.setScene(scene);
        scene.getStylesheets().add("styles.css");
        Font.loadFont(getClass().getResource("fonts/SourceCodePro.ttf").toExternalForm(), 10);

        stage.setTitle("teeheee");
        stage.setWidth(800);
        stage.setHeight(500);
        stage.setAlwaysOnTop(true);
        stage.setFullScreenExitHint("How dare you");
        borderPane.setTop(new HBox(new Text("This is my App")));
        Image logo = new Image("images/moveattack.png");
        Image shrek = new Image("images/emptymove.png");
        ImageView view = new ImageView(shrek);
        view.setPreserveRatio(true);
        view.setFitWidth(200);
        borderPane.setCenter(view);
        boolean atk = false;

       /* view.setOnMouseEntered(e->{
            view.setImage(logo);
            view.setPreserveRatio(true);
            view.setFitWidth(200);
        });
        view.setOnMouseExited(e->{
            view.setImage(shrek);
            view.setPreserveRatio(true);
            view.setFitWidth(200);
        });*/
        HBox box = new HBox();
        Button button = new Button("Click Me!");
        box.getChildren().add(button);
        borderPane.setBottom(box);
    //    Media song = new Media(getClass().getResource("sound/Chicken Nugget Song PARODY.mp3").toURI().toString());
    //    MediaPlayer player = new MediaPlayer(song);
        button.setOnAction(e->{
            if(view.getImage().equals(logo)){
                view.setImage(new Image("images/stinky.png"));
            }else view.setImage(logo);
        });

        stage.centerOnScreen();
        stage.show();
    }

   public static void main(String[] args) {
        launch();
    }
}
