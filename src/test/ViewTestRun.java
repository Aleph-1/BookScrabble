package test;

import Model.Model;

import View.MainWindowController;
import ViewModel.ViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ViewTestRun extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try{

            FXMLLoader fxl= new FXMLLoader();
            BorderPane root= fxl.load(getClass().getResource("MainWindowCopy.fxml").openStream());//Loading the fxml
            Model m=new Model();
            m.startHost(8085);//Starting the model
            viewTest wc=fxl.getController();//Establishing wc as the controller
            ViewModel vm=new ViewModel(m);
            m.setViewModel(vm);
            wc.init(vm);
            vm.setView(wc);



            Scene scene=new Scene(root,1200,900);//Creating the size of the screen
            stage.setScene(scene);
            stage.show();//Showing the screen
            wc.testView();





        }catch (Exception e){ e.printStackTrace();}

    }

//        StackPane layout= new StackPane();
//
//        javafx.scene.control.Button button= new javafx.scene.control.Button("Hello World");
//        layout.getChildren().add(button);
//        Scene scene = new Scene(layout,300,300);
//        stage.setScene(scene);
//        stage.setTitle("JavaFX 19");
//        stage.show();
}
