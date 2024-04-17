package ulb.widgets;

import javafx.collections.ObservableList;

public class Search {
    //TODO Doit auto-remettre l'item choisi dans la searchbar : this.searchField.setText(this.suggestionsList.getSelectionModel().getSelectedItem());


    public String getText(){};
    public String getSelectedItem(){}
    public int getSelectedIndex(){}
    public void selectPrevious(){}
    public void selectNext(){}
    public void scrollTo(int index){}
    public void selectFirst(){}
    public int size(){}
    public boolean isEmpty(){}
    public ObservableList<String> getItems(){}
    public void setResults(ObservableList<String> results) {}
}
