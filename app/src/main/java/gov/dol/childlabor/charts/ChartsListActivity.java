package gov.dol.childlabor.charts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import gov.dol.childlabor.R;

public class ChartsListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.proportional_chart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChartsListActivity.this,DataVisualizationActivity.class));
            }
        });
        findViewById(R.id.piechart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChartsListActivity.this, GoodsBySectorChartActivityNew.class);
                intent.putExtra("IS_GOODS_BY_REGION",false);
                startActivity(intent);
            }
        });
        findViewById(R.id.goods_by_region).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChartsListActivity.this, GoodsBySectorChartActivityNew.class);
                intent.putExtra("IS_GOODS_BY_REGION",true);
                startActivity(intent);
            }
        });
        findViewById(R.id.assessment_levels).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChartsListActivity.this, AssessmentLevelsChart.class);
                intent.putExtra("IS_GOODS_BY_REGION",true);
                intent.putExtra("IS_ASSESSMENT_LEVELS_BY_REGION",true);
                startActivity(intent);
            }
        });



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
