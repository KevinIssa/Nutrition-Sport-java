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
import java.util.function.Function;
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
		this.graphType.getItems().addAll("moyenne", "consommée/brûlée");
		this.graphType.setValue("moyenne");
	}

	private void setupBarChart() {
		barChart.setTitle("Moyenne des calories");
		barChart.setLegendVisible(false);
		barChart.setAnimated(false); // to avoid the collapse bug on the chart
		barChart.getXAxis().setLabel("Date");
		barChart.getYAxis().setLabel("Calories");
	}

	private void addDataToBarChart() {
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		for (DateCalorieDTO dateCalorie : data) {
			series.getData().add(new XYChart.Data<>(dateCalorie.date().toString(), dateCalorie.getCalorieDifference()));
		}
		barChart.getData().add(series);
	}

	private void initBarChart() {
		setupBarChart();
		addDataToBarChart();
		onGraphTypeChange();
	}

	private void setupAxes() {
		lineChart.getXAxis().setLabel("Date");
		lineChart.getYAxis().setLabel("Calories");
	}

	private void setupSeries() {
		lineChart.setTitle("Calories brûlées et consommées");
		addSeries("Brûlées", DateCalorieDTO::calorieBurned);
		addSeries("Consommées", DateCalorieDTO::calorieIntake);
		addSeries("Différence", DateCalorieDTO::getCalorieDifference);
	}

	private void addSeries(String name, Function<DateCalorieDTO, Number> valueFunction) {
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		series.setName(name);
		for (DateCalorieDTO dateCalorie : data) {
			series.getData().add(new XYChart.Data<>(dateCalorie.date().toString(), valueFunction.apply(dateCalorie)));
		}
		lineChart.getData().add(series);
	}

	private void initLineChart() {
		setupAxes();
		setupSeries();
		lineChart.setCreateSymbols(false);
		lineChart.setAnimated(false);
		onGraphTypeChange();
	}

	public double getLastCalorieDifference() {
		return data.getLast().getCalorieDifference();
	}

	@FXML
	private void onGraphTypeChange() {
		if (graphType.getValue().equals("moyenne")) {
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
