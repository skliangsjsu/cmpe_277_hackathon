package com.example.cmpe_277_hackathon_project;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner countrySpinner;
    private Spinner indicatorSpinner;
    private Button fetchDataButton;
    private LineChart lineChart;
    private EditText promptInput;
    private Button promptButton;
    private TextView promptResponse;

    private DataPointDao dataPointDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the database
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "macroeconomic-db").allowMainThreadQueries().build();
        dataPointDao = db.dataPointDao();

        // Insert sample data for testing
        insertSampleData();

        // Initialize UI components
        countrySpinner = findViewById(R.id.country_spinner);
        indicatorSpinner = findViewById(R.id.indicator_spinner);
        fetchDataButton = findViewById(R.id.fetch_data_button);
        lineChart = findViewById(R.id.line_chart);
        promptInput = findViewById(R.id.prompt_input);
        promptButton = findViewById(R.id.prompt_button);
        promptResponse = findViewById(R.id.prompt_response);

        setupSpinners();

        // Fetch data and display in chart when button is clicked
        fetchDataButton.setOnClickListener(v -> {
            String country = countrySpinner.getSelectedItem().toString();
            String indicator = indicatorSpinner.getSelectedItem().toString();
            displayData(country, indicator);
        });

        // Handle prompt submission for text-based insights
        promptButton.setOnClickListener(v -> handlePrompt(promptInput.getText().toString()));
    }

    private void setupSpinners() {
        // Set up country selection spinner
        String[] countries = {"China", "India", "USA"};
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countries);
        countrySpinner.setAdapter(countryAdapter);

        // Set up indicator selection spinner
        String[] indicators = {"GDP Growth Rate", "Food Insecurity", "FDI Inflows", "Agricultural Contribution"};
        ArrayAdapter<String> indicatorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, indicators);
        indicatorSpinner.setAdapter(indicatorAdapter);
    }

    private void insertSampleData() {
        // Insert sample data points into the database
        dataPointDao.insert(new DataPoint("India", "GDP Growth Rate", 2020, 5.0f));
        dataPointDao.insert(new DataPoint("India", "GDP Growth Rate", 2021, 4.5f));
        dataPointDao.insert(new DataPoint("India", "GDP Growth Rate", 2022, 6.2f));
        dataPointDao.insert(new DataPoint("USA", "Food Insecurity", 2020, 8.0f));
        dataPointDao.insert(new DataPoint("USA", "Food Insecurity", 2021, 7.8f));
        dataPointDao.insert(new DataPoint("USA", "Food Insecurity", 2022, 7.6f));
        dataPointDao.insert(new DataPoint("China", "FDI Inflows", 2020, 2.3f));
        dataPointDao.insert(new DataPoint("China", "FDI Inflows", 2021, 3.5f));
        dataPointDao.insert(new DataPoint("China", "FDI Inflows", 2022, 4.1f));
    }

    private void displayData(String country, String indicator) {
        // Retrieve data points from the database based on the selected country and indicator
        List<DataPoint> dataPoints = dataPointDao.getDataPoints(country, indicator);

        if (dataPoints.isEmpty()) {
            Toast.makeText(this, "No data available for selection", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare data entries for the chart
        List<Entry> entries = new ArrayList<>();
        for (DataPoint dataPoint : dataPoints) {
            entries.add(new Entry(dataPoint.year, dataPoint.value));
        }

        // Set up the chart data set
        LineDataSet lineDataSet = new LineDataSet(entries, indicator + " Data");
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.invalidate(); // Refresh the chart
    }

    private void handlePrompt(String prompt) {
        // Simple mock response handling based on prompt keywords
        String response;
        if (prompt.toLowerCase().contains("food insecurity")) {
            response = "In 2024, food insecurity increased due to global inflation and conflicts.";
        } else if (prompt.toLowerCase().contains("malnutrition")) {
            response = "Malnutrition in conflict zones remains high due to disrupted supply chains.";
        } else if (prompt.toLowerCase().contains("gdp growth")) {
            response = "GDP growth rates have fluctuated due to economic slowdowns and policy changes.";
        } else {
            response = "Data not available for the entered prompt.";
        }
        promptResponse.setText(response);
    }
}
