package ulb.widgets;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.awt.*;

public class Search extends VBox {
    //TODO Doit auto-remettre l'item choisi dans la searchbar : this.searchField.setText(this.suggestionsList.getSelectionModel().getSelectedItem());

    @FXML private TextField searchField;
    @FXML private ListView<String> suggestionsList;

    public String getText(){
        return searchField.getText();
    }

    //TODO onClickEvent

    private void onUpPress() {
		if (this.searchController.getSelectedIndex() != 0) {
			this.searchController.selectPrevious();
		}
		int index = this.searchController.getSelectedIndex();
		if (index - 3 <= this.searchController.size() - 1) {
			this.searchController.scrollTo(index - 3);
		}
	}

	private void onDownPress() {
		if (this.searchController.getSelectedIndex()
				!= this.searchController.size() - 1) {
			this.searchController.selectNext();
		}
		int index = this.searchController.getSelectedIndex();
		if (index - 4 >= 0) {
			this.searchController.scrollTo(index - 4);
		}
	}

    public String getSelectedItem(){
        return suggestionsList.getSelectionModel().getSelectedItem();
    }
    public int getSelectedIndex(){
        return suggestionsList.getSelectionModel().getSelectedIndex();
    }
    public void selectPrevious(){
        suggestionsList.getSelectionModel().selectPrevious();
    }
    public void selectNext(){
        suggestionsList.getSelectionModel().selectNext();
    }
    public void scrollTo(int index){
        suggestionsList.scrollTo(index);
    }
    public void selectFirst(){
        suggestionsList.getSelectionModel().selectFirst();
    }
    public int size(){
        return suggestionsList.getItems().size();
    }
    public boolean isEmpty(){
        return suggestionsList.getItems().isEmpty();
    }
    public ObservableList<String> getItems(){
        return suggestionsList.getItems();
    }
    public void setResults(ObservableList<String> results) {
        suggestionsList.setItems(results);
    }
}
