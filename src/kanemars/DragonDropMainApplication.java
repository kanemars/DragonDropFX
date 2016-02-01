package kanemars;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class DragonDropMainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Dragon Drop FX");
        primaryStage.setScene(new Scene(root, 300, 275));

        final List<String> parameters = getParameters().getRaw();
        final String cutAndPasteFile = parameters.get(0);

        primaryStage.show();
    }


    public static void main(String[] args) {
        if (args.length > 0) {
            launch(args);
        }
    }
}
