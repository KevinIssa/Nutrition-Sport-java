package ulb.widgets;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;


public class Search extends VBox {
    @FXML private TextField searchField;
    @FXML private ListView<String> searchList;

    public void initialiaze(URL url, ResourceBundle resourceBundle) {}
    
    public String getText(){
        return searchField.getText();
    }

    private void onUpPress() {
		if (this.searchList.getSelectionModel().getSelectedIndex() != 0) {
			this.searchList.getSelectionModel().selectPrevious();
            this.searchField.setText(this.searchList.getSelectionModel().getSelectedItem());
		}
		int index = this.searchList.getSelectionModel().getSelectedIndex();
		if (index - 3 <= this.searchList.getItems().size() - 1) {
			this.searchList.scrollTo(index - 3);
		}
	}

	private void onDownPress() {
		if (this.searchList.getSelectionModel().getSelectedIndex()
				!= this.searchList.getItems().size() - 1) {
			this.searchList.getSelectionModel().selectNext();
            this.searchField.setText(this.searchList.getSelectionModel().getSelectedItem());
		}
		int index = this.searchList.getSelectionModel().getSelectedIndex();
		if (index - 4 >= 0) {
			this.searchList.scrollTo(index - 4);
		}
	}

    public String getSelectedItem(){
        return searchList.getSelectionModel().getSelectedItem();
    }

    public int getSelectedIndex(){
        return searchList.getSelectionModel().getSelectedIndex();
    }

    public void selectPrevious(){
        searchList.getSelectionModel().selectPrevious();
    }

    public void selectNext(){
        searchList.getSelectionModel().selectNext();
    }

    public void scrollTo(int index){
        searchList.scrollTo(index);
    }

    public void selectFirst(){
        searchList.getSelectionModel().selectFirst();
    }

    public int size(){
        return searchList.getItems().size();
    }

    public boolean isEmpty(){
        return searchList.getItems().isEmpty();
    }

    public ObservableList<String> getItems(){
        return searchList.getItems();
    }

    public void setResults(ObservableList<String> results) {
        searchList.setItems(results);
    }
}
