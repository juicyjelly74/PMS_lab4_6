package com.example.jula.pms_lab4_6;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jula.pms_lab4_6.db.DBHelper;
import com.example.jula.pms_lab4_6.db.data.InitialData;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {
    private Spinner spinner;
    private DBHelper dbHelper;
    private EditText minPopulationEditText;
    private TableLayout tbl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_layout);
        dbHelper = new DBHelper(this);

        spinner = (Spinner) findViewById(R.id.region_chooser);
        tbl = (TableLayout) findViewById(R.id.db_view);
        minPopulationEditText = (EditText) findViewById(R.id.min_pop_edit_text);
        List<String> source = dbHelper.allRegions();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, source);
        spinner.setAdapter(adapter);
    }

    public void filter(View v) {
        List<InitialData.District> list = new ArrayList<>();
        String str = minPopulationEditText.getText().toString();
        try {
            String regionName = spinner.getSelectedItem().toString();
            if (str.length() > 0 && regionName != null) {
                Integer minPopulation = Integer.parseInt(str);
                Integer regionId = dbHelper.idByRegionName(regionName);
                list = dbHelper.findMoreThatMinPopulationAndRegion(minPopulation, regionId);
            } else if (str.length() > 0) {
                Integer minPopulation = Integer.parseInt(str);
                list = dbHelper.findMoreThatMinPopulation(minPopulation);
            } else {
                Integer regionId = dbHelper.idByRegionName(regionName);
                list = dbHelper.findWhereDistrict(regionId);
            }
        } catch (NumberFormatException e) {
            Log.e("ERROR", "ERROR", e);
        }
        initTableView(list);
    }

    private void emptyTable() {
        int cnt = tbl.getChildCount();
        for (int i = cnt - 1; i > 0; i--) {
            tbl.removeViewAt(i);
        }
    }

    private void initTableView(List<InitialData.District> districts) {
        emptyTable();
        for (InitialData.District district : districts) {
            addToTable(district);
        }
    }

    private void addToTable(InitialData.District district) {
        TableRow row = new TableRow(this);
        row.addView(withText(district.name));
        row.addView(withText(district.population));
        row.addView(withText(dbHelper.regionNameById(district.regionId)));
        row.addView(withText(district.code));
        tbl.addView(row);
    }

    private TextView withText(Object obj) {
        TextView tw = new TextView(this);
        tw.setText(obj.toString());
        return tw;
    }
}

