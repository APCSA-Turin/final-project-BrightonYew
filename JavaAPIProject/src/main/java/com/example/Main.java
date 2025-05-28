import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    } 

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("PLEASE WORK")
        Scene scene = new Scene(createContent());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Region createContent() {
        return new Label("Hello World");
    }
}