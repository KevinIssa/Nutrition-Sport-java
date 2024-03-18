package ulb.views.utils;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class CustomListCell extends ListCell<Image>{
    private HBox hbox;
    private Label label;
    private ImageView imageView;

    public CustomListCell() {
        super();
        hbox = new HBox();
        label = new Label("aaaaaaaaaaa");
        imageView = new ImageView();
        hbox.getChildren().addAll(imageView, label);
    }

    @Override
    public void updateItem(Image item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            imageView.setImage(item);
            setGraphic(imageView);
        }
    }
}
