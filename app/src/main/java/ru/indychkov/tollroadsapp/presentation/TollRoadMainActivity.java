package ru.indychkov.tollroadsapp.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import ru.indychkov.tollroadsapp.R;

public class TollRoadMainActivity extends AppCompatActivity {
    private Spinner spinnerRoad;
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private Switch switchIsFromMoscow;
    private CheckBox checkIsAvtodor;
    private Button buttonCalculate;
    private TollRoadViewModel tollRoadViewModel;
    private RecyclerView pathRecyclerView;
    private CheckBox checkIs1Cat;
    private CheckBox checkIs2Cat;
    private CheckBox checkIs3Cat;
    private CheckBox checkIs4Cat;
    private String selectedRoad;
    RecyclerViewAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setup();

    }

    private void setup() {
        ViewModelProvider viewModelProvider = ViewModelProviders.of(this, new TollRoadViewModelFactory(this));
        tollRoadViewModel = viewModelProvider.get(TollRoadViewModel.class);
        tollRoadViewModel.getRoadNames().observe(this, roadNames -> {
            spinnerRoad.setAdapter(new CustomSpinnerAdapter(roadNames));
        });

        tollRoadViewModel.getRoadPartsFrom().observe(this, roadFrom -> {
            spinnerFrom.setAdapter(new CustomSpinnerAdapter(roadFrom));
            spinnerTo.setAdapter(null);
        });

        tollRoadViewModel.getRoadPartsTo().observe(this, roadTo -> spinnerTo.setAdapter(new CustomSpinnerAdapter(roadTo)));

        tollRoadViewModel.getErrors().observe(this, error ->
                Toast.makeText(this, error, Toast.LENGTH_LONG).show());

        tollRoadViewModel.getSectionFrom().observe(this, sectionFrom -> {
            tollRoadViewModel.loadToRoadParts(selectedRoad);
        });

        tollRoadViewModel.getFinalPath().observe(this, roadTo -> {
            recyclerViewAdapter.setTasks(roadTo);
            pathRecyclerView.setAdapter(recyclerViewAdapter);
        });

        tollRoadViewModel.loadRoadNames();
    }

    private void initViews() {
        pathRecyclerView = findViewById(R.id.recycler_view);
        selectedRoad = "M-3";
        buttonCalculate = findViewById(R.id.button_calculate);
        spinnerRoad = findViewById(R.id.roads_spinner);
        spinnerFrom = findViewById(R.id.from_spinner);
        spinnerTo = findViewById(R.id.to_spinner);
        switchIsFromMoscow = findViewById(R.id.fromMoscow_switch);
        switchIsFromMoscow.setEnabled(false);
        checkIsAvtodor = findViewById(R.id.check_avtodor);
        checkIs1Cat = findViewById(R.id.check_1_cat);
        checkIs1Cat.setChecked(true);
        checkIs1Cat.setEnabled(false);
        checkIs2Cat = findViewById(R.id.check_2_cat);
        checkIs3Cat = findViewById(R.id.check_3_cat);
        checkIs4Cat = findViewById(R.id.check_4_cat);
        //checkIsCustomTime = findViewById(R.id.check_custom_time);
        pathRecyclerView = findViewById(R.id.recycler_view);
        pathRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(this);


        switchIsFromMoscow.setOnCheckedChangeListener((buttonView, isChecked) -> {

            tollRoadViewModel.setIsFromMoscow(switchIsFromMoscow.isChecked());

            tollRoadViewModel.loadFromRoadParts(selectedRoad);
        });
        checkIs1Cat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkIs1Cat.setEnabled(false);
                setUnChecked(checkIs2Cat, checkIs3Cat, checkIs4Cat, 1);
            }
        });
        checkIs2Cat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkIs2Cat.setEnabled(false);
                setUnChecked(checkIs1Cat, checkIs3Cat, checkIs4Cat, 2);
            }
        });
        checkIs3Cat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkIs3Cat.setEnabled(false);
                setUnChecked(checkIs1Cat, checkIs2Cat, checkIs4Cat, 3);
            }
        });
        checkIs4Cat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkIs4Cat.setEnabled(false);
                setUnChecked(checkIs1Cat, checkIs2Cat, checkIs3Cat, 4);
            }
        });

        spinnerRoad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switchIsFromMoscow.setEnabled(true);
                selectedRoad = spinnerRoad.getSelectedItem().toString();
                tollRoadViewModel.loadFromRoadParts(selectedRoad);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tollRoadViewModel.setFromSection(spinnerFrom.getSelectedItem().toString(), position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                buttonCalculate.setEnabled(true);
                tollRoadViewModel.setToSection(spinnerTo.getSelectedItem().toString(), position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tollRoadViewModel.loadFinalPath(selectedRoad);
            }
        });
    }

    private void setUnChecked(CheckBox one, CheckBox two, CheckBox three, int category) {
        one.setChecked(false);
        two.setChecked(false);
        three.setChecked(false);
        one.setEnabled(true);
        two.setEnabled(true);
        three.setEnabled(true);
        tollRoadViewModel.setCategory(category);
    }
}
