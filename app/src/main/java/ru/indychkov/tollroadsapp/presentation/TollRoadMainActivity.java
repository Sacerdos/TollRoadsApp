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
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.indychkov.tollroadsapp.R;

public class TollRoadMainActivity extends AppCompatActivity {
    private Spinner spinnerRoad;
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private TextView testTextView;
    private Switch switchIsFromMoscow;
    private CheckBox checkIsAvtodor;
    //private CheckBox checkIsCustomTime;
    private Button buttonCalculate;
    private TollRoadViewModel tollRoadViewModel;
    private RecyclerView pathRecyclerView;
    private CheckBox checkIs1Cat;
    private CheckBox checkIs2Cat;
    private CheckBox checkIs3Cat;
    private CheckBox checkIs4Cat;

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
        tollRoadViewModel.getRoadNames().observe(this, roadNames -> spinnerRoad.setAdapter(new CustomSpinnerAdapter(roadNames)));

        tollRoadViewModel.getRoadPartsFrom().observe(this, roadFrom -> spinnerFrom.setAdapter(new CustomSpinnerAdapter(roadFrom)));

        tollRoadViewModel.getRoadPartsTo().observe(this, roadTo -> spinnerTo.setAdapter(new CustomSpinnerAdapter(roadTo)));

        tollRoadViewModel.getErrors().observe(this, error ->
                Toast.makeText(this, error, Toast.LENGTH_LONG).show());
        tollRoadViewModel.loadRoadNames();
    }

    private void initViews() {
        pathRecyclerView = findViewById(R.id.recycler_view);

        spinnerRoad = findViewById(R.id.roads_spinner);
        spinnerFrom = findViewById(R.id.from_spinner);
        spinnerTo = findViewById(R.id.to_spinner);
        testTextView = findViewById(R.id.test_view);
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
        buttonCalculate = findViewById(R.id.calculate_button);
        pathRecyclerView=findViewById(R.id.recycler_view);
        pathRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this);
        List<String> data = new ArrayList<>();
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        data.add("a");
        recyclerViewAdapter.setTasks(data);
        pathRecyclerView.setAdapter(recyclerViewAdapter);



        switchIsFromMoscow.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tollRoadViewModel.loadRoadPartsFrom(spinnerRoad.getSelectedItem().toString(),
                    switchIsFromMoscow.isChecked());
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
                tollRoadViewModel.loadRoadPartsFrom(spinnerRoad.getSelectedItem().toString(),
                        switchIsFromMoscow.isChecked());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
