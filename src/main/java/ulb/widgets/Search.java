/*
 * Ce projet est une application de santé et de bien-être développée dans le cadre du cours INFO-F-307 à l'ULB.
 *
 * Groupe : 06
 * Étudiants :
 * - Kevin ISSA
 * - Hamza CAEYMAN
 * - Alexandru MELNIC
 * - Ze-Xuan XU
 * - Bao TRAN
 * - Hà Uyên TRAN
 * - Hugo CHARELS
 * - Hodo SOULEIMAN AHMED
 * - Kevin VANDERVAEREN
 * - Arthur INSTALLÉ
 *
 * Date : 2024
 */
package ulb.widgets;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class Search implements Initializable {
	@FXML private TextField searchField;
	@FXML private ListView<String> searchList;
	private Listener listener;

	public interface Listener {
		void onClick(String search);

		ObservableList<String> getContent(String search);
	}

	public void setListener(Search.Listener listener) {
		this.listener = listener;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {}

	@FXML
	public void onListClick(){
		if (this.listener != null) {
			this.listener.onClick(this.searchList.getSelectionModel().getSelectedItem());
		}
	}

	@FXML
	private void loadContent(){
		if (this.listener != null) {
			String search = this.searchField.getText();
			this.searchList.setItems(this.listener.getContent(search));
			this.searchList.getSelectionModel().selectFirst();
		}
	}

	@FXML
	public void keyPress(KeyEvent event) {
		switch(event.getCode()){
			case ENTER:
				this.onListClick();
				break;
			case UP:
				this.onUpPress();
				break;
			case DOWN:
				this.onDownPress();
		}
	}

	public String getText() {
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

	public String getSelectedItem() {
		return searchList.getSelectionModel().getSelectedItem();
	}

	public int getSelectedIndex() {
		return searchList.getSelectionModel().getSelectedIndex();
	}

	public void selectPrevious() {
		searchList.getSelectionModel().selectPrevious();
	}

	public void selectNext() {
		searchList.getSelectionModel().selectNext();
	}

	public void scrollTo(int index) {
		searchList.scrollTo(index);
	}

	public void selectFirst() {
		searchList.getSelectionModel().selectFirst();
	}

	public int size() {
		return searchList.getItems().size();
	}

	public boolean isEmpty() {
		return searchList.getItems().isEmpty();
	}

	public ObservableList<String> getItems() {
		return searchList.getItems();
	}

	public void setResults(ObservableList<String> results) {
		searchList.setItems(results);
	}

	public String getFirst(){
		return searchList.getItems().getFirst();
	}
}
