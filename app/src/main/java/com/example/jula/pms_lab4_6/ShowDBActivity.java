package com.example.jula.pms_lab4_6;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jula.pms_lab4_6.db.DBHelper;
import com.example.jula.pms_lab4_6.db.data.DBConstants;
import com.example.jula.pms_lab4_6.db.data.InitialData;
import com.example.jula.pms_lab4_6.db.sort.SortOption;

import java.util.List;

public class ShowDBActivity extends AppCompatActivity implements DBConstants {
    private TableLayout tableLayout;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_db_activity);
        tableLayout = (TableLayout) findViewById(R.id.db_view);
        dbHelper = new DBHelper(this);
        initTableView(dbHelper.readAllDistricts());
    }

    private void initTableView(List<InitialData.District> districts) {
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
        tableLayout.addView(row);
    }

    private TextView withText(Object obj) {
        TextView tw = new TextView(this);
        tw.setText(obj.toString());
        return tw;
    }

    private void emptyTable() {
        int cnt = tableLayout.getChildCount();
        for (int i = cnt - 1; i > 0; i--) {
            tableLayout.removeViewAt(i);
        }
    }

    private SortOption distinctSort = SortOption.ASC;
    private SortOption populationSort = SortOption.ASC;
    private SortOption codeSort = SortOption.ASC;
    private SortOption regionSort = SortOption.ASC;

    public void sortByDistinct(View v) {
        distinctSort = distinctSort.revert();
        sort(distinctSort, DISTRICT_D_NAME);
    }

    public void sortByPopulation(View v) {
        populationSort = populationSort.revert();
        sort(populationSort, DISTRICT_D_POPULATION);
    }

    public void sortByRegion(View v) {
        regionSort = regionSort.revert();
        sort(regionSort, DISTRICT_REGION_ID);
    }

    public void sortByCode(View v) {
        codeSort = codeSort.revert();
        sort(codeSort, DISTRICT_CODE);
    }

    public void showDialogMin(View v) {
        InitialData.District district = dbHelper.findMinPopulation();
        openDialog("Minimum District", district.toString());
    }

    public void showDialogMax(View v) {
        InitialData.District district = dbHelper.findMaxPopulation();
        openDialog("Maximum District", district.toString());
    }

    public void showDialogSum(View v) {
        int sum = dbHelper.findSumPopulation();
        openDialog("Sum of population District", String.valueOf(sum));
    }

    private void openDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }

    private void sort(SortOption sortOption, String nameOfProperty) {
        emptyTable();
        List<InitialData.District> districts = dbHelper.readAllAndSort(nameOfProperty + " " + sortOption);
        initTableView(districts);
    }
}
