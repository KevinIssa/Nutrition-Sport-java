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
package ulb.views;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ulb.dtos.DateCalorieDTO;

public class GraphComponentViewController extends AnchorPane {
	private static final Logger logger =
			LoggerFactory.getLogger(GraphComponentViewController.class);
	@FXML private BarChart<String, Number> barChart;
	@FXML private LineChart<String, Number> lineChart;
	@FXML private ChoiceBox<String> graphType;

	private List<DateCalorieDTO> data;
	private Listener listener;

	public void setListener(Object listener) {
		if (listener == null) {
			logger.error("Listener is null");
			System.exit(1);
		}
		this.listener = (GraphComponentViewController.Listener) listener;
		this.setupGraph();
	}

	private void
			setupGraph() { // not put in the Initialize method because need the listener to be set
		this.data = this.listener.getGraphData();
		this.setButton();
		this.initBarChart();
		this.initLineChart();
	}

	private void setButton() {
		this.graphType.getItems().addAll("average", "intake/burned");
		this.graphType.setValue("average");
	}

	private void initBarChart() {
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		barChart.setTitle("Calorie average");
		barChart.setLegendVisible(false);
		xAxis.setLabel("Date");
		yAxis.setLabel("Calories");
		for (DateCalorieDTO dateCalorie : data) {
			series.getData()
					.add(
							new XYChart.Data<>(
									dateCalorie.date().toString(),
									dateCalorie.getCalorieDifference()));
		}
		barChart.getData().add(series);
		this.onGraphTypeChange();
	}

	private void initLineChart() {
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		XYChart.Series<String, Number> series2 = new XYChart.Series<>();
		lineChart.setTitle("Calorie intake and burned");
		barChart.setLegendVisible(false);
		xAxis.setLabel("Date");
		yAxis.setLabel("Calories");
		for (DateCalorieDTO dateCalorie : data) {
			series.getData()
					.add(
							new XYChart.Data<>(
									dateCalorie.date().toString(), dateCalorie.calorieIntake()));
			series2.getData()
					.add(
							new XYChart.Data<>(
									dateCalorie.date().toString(), dateCalorie.calorieBurned()));
		}
		lineChart.getData().add(series);
		lineChart.getData().add(series2);
		this.onGraphTypeChange();
	}

	@FXML
	private void onGraphTypeChange() {
		if (graphType.getValue().equals("average")) {
			barChart.setVisible(true);
			lineChart.setVisible(false);
		} else {
			barChart.setVisible(false);
			lineChart.setVisible(true);
		}
	}

	public interface Listener {
		List<DateCalorieDTO> getGraphData();
	}
}
