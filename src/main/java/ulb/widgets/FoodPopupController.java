package ulb.widgets;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Objects;

public class FoodPopupController {
    @FXML TextField gramme;
    @FXML TextField serving;
    @FXML Label servinglabel;
    public int getGramme(){
        if (!gramme.isDisable()){
            return Integer.parseInt(gramme.getText());
        }
        return 0;
    }
    public int getServing(){
        if (!serving.isDisable()){
            return Integer.parseInt(serving.getText());
        }
        return 0;
    }
    public void setServinglabel(String label){
        this.servinglabel.setText(label);
    }
    @FXML
    void textboxHandler(Event event){
        if (!Objects.equals(serving.getText(), "")){
            gramme.setDisable(true);
        }else{
            gramme.setDisable(false);
        }
        if (!Objects.equals(gramme.getText(), "")){
            serving.setDisable(true);
        }else{
            serving.setDisable(false);
        }
    }
}
