package View;

import Model.Model;
import ViewModel.ViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ViewRun extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try{
            FXMLLoader fxl= new FXMLLoader();
            BorderPane root= fxl.load(getClass().getResource("MainWindow1.fxml").openStream());
            Model m=new Model();
            m.startHost(3080);
            MainWindowController wc=fxl.getController();
            ViewModel vm=new ViewModel(m);
            wc.init(vm);
            vm.setView(wc);

            Scene scene=new Scene(root,1200,900);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){ e.printStackTrace();}

//        StackPane layout= new StackPane();
//
//        javafx.scene.control.Button button= new javafx.scene.control.Button("Hello World");
//        layout.getChildren().add(button);
//        Scene scene = new Scene(layout,300,300);
//        stage.setScene(scene);
//        stage.setTitle("JavaFX 19");
//        stage.show();
    }
}