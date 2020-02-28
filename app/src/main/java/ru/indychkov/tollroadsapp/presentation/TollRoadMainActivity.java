package ru.indychkov.tollroadsapp.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.indychkov.tollroadsapp.R;

public class TollRoadMainActivity extends AppCompatActivity {
    private Spinner spinnerRoad;
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private TextView testTextView;
    private Switch switchIsFromMoscow;
    private CheckBox checkIsAvtodor;
    private CheckBox checkIsCustomTime;
    private Button buttonCalculate;
    private TollRoadViewModel tollRoadViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setup();
        switchIsFromMoscow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tollRoadViewModel.loadRoadPartsFrom(spinnerRoad.getSelectedItem().toString(),
                        switchIsFromMoscow.isChecked());
            }
        });
    }

    private void setup() {
        ViewModelProvider viewModelProvider = ViewModelProviders.of(this, new TollRoadViewModelFactory(this));
        tollRoadViewModel = viewModelProvider.get(TollRoadViewModel.class);
        tollRoadViewModel.getRoadNames().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> roadNames) {
                spinnerRoad.setAdapter(new CustomSpinnerAdapter(roadNames));
            }
        });
        tollRoadViewModel.getRoadPartsFrom().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> roadFrom) {
                spinnerFrom.setAdapter(new CustomSpinnerAdapter(roadFrom));
            }
        });
        tollRoadViewModel.getRoadPartsTo().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> roadTo) {
                spinnerTo.setAdapter(new CustomSpinnerAdapter(roadTo));
            }
        });
        tollRoadViewModel.getErrors().observe(this, error ->
                Toast.makeText(this, error, Toast.LENGTH_LONG).show());
        tollRoadViewModel.loadRoadNames();
    }

    private void initViews() {
        spinnerRoad = findViewById(R.id.roads_spinner);
        spinnerFrom = findViewById(R.id.from_spinner);
        spinnerTo = findViewById(R.id.to_spinner);
        testTextView = findViewById(R.id.test_view);
        switchIsFromMoscow = findViewById(R.id.fromMoscow_switch);
        switchIsFromMoscow.setEnabled(false);
        checkIsAvtodor = findViewById(R.id.check_avtodor);
        checkIsCustomTime = findViewById(R.id.check_custom_time);
        buttonCalculate = findViewById(R.id.calculate_button);
        spinnerRoad.setOnItemSelectedListener(new OnRoadSelectedListener());
    }
    private class OnRoadSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switchIsFromMoscow.setEnabled(true);
            tollRoadViewModel.loadRoadPartsFrom(spinnerRoad.getSelectedItem().toString(),
                    switchIsFromMoscow.isChecked());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
