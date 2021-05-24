package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class MainWindowController {
//    @FXML
//    BorderPane mainBorderPane;
    @FXML
    private ToggleGroup filter;
    @FXML
    private Button buttonAddHersteller;
    @FXML
    private Button buttonAddKuchen;
    @FXML
    private Button buttonRemoveKuchen;
    @FXML
    private Button buttonRemoveHersteller;
    @FXML
    private Button buttonSetInspectionDate;
    @FXML
    private TextField herstellerField;
    @FXML
    private  TextField fachnummerField;

    public void initialize(){



        //let this field only accept numbers source: https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
        fachnummerField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    fachnummerField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    public void showNewItemsDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        //dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add new Todo Item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));

        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e){
            System.out.println("couldnt load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

    }
}
