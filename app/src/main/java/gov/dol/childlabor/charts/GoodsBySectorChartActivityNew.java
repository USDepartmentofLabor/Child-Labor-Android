package gov.dol.childlabor.charts;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gov.dol.childlabor.CountryGood;
import gov.dol.childlabor.Good;
import gov.dol.childlabor.GoodXmlParser;
import gov.dol.childlabor.R;

public class GoodsBySectorChartActivityNew extends AppCompatActivity implements
        OnChartValueSelectedListener {

    private PieChart chart;
    String country = "Country";
    TextView agriculture,manufacturing,mining,other;
    boolean isGoodsByRegion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
          //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_goods_by_sector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        isGoodsByRegion = getIntent().getBooleanExtra("IS_GOODS_BY_REGION",false);
        if(isGoodsByRegion){
            findViewById(R.id.sector_group).setVisibility(View.GONE);
        }
        agriculture = findViewById(R.id.agri);
        manufacturing = findViewById(R.id.manufacture);
        mining = findViewById(R.id.mining);
        other = findViewById(R.id.other);
        agriculture.setOnClickListener(view -> {
            resetColors();
            agriculture.setBackgroundColor(getResources().getColor(R.color.orange));
            setData("Agriculture");
            country = "Agriculture";
            chart.setCenterText(generateCenterSpannableText());
        });
        mining.setOnClickListener(view -> {
            resetColors();
            mining.setBackgroundColor(getResources().getColor(R.color.orange));
            setData("Mining");
            country = "Mining";
            chart.setCenterText(generateCenterSpannableText());
        });
        manufacturing.setOnClickListener(view -> {
            resetColors();
            manufacturing.setBackgroundColor(getResources().getColor(R.color.orange));
            setData("Manufacturing");
            country = "Manufacturing";
            chart.setCenterText(generateCenterSpannableText());
        });
        other.setOnClickListener(view -> {
            resetColors();
            other.setBackgroundColor(getResources().getColor(R.color.orange));
            setData("Other");
            country = "Other";
            chart.setCenterText(generateCenterSpannableText());
        });

        if(isGoodsByRegion){
            setTitle("Goods By Region");
            country = "All Categories";
        }else {
            setTitle("Goods By Sector");
            country = "Agriculture";
        }
        chart = findViewById(R.id.chart1);
        chart.setUsePercentValues(false);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setCenterText(generateCenterSpannableText());

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(50f);
        chart.setTransparentCircleRadius(0f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        chart.setOnChartValueSelectedListener(this);


        chart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE);
        chart.setEntryLabelTextSize(16f);
        chart.setDrawEntryLabels(false);
        setData("Agriculture");
    }

    private void resetColors() {
        agriculture.setBackground(null);
        mining.setBackground(null);
        other.setBackground(null);
        manufacturing.setBackground(null);
    }

    private void setData(String sector) {
        //ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        GoodXmlParser gParser = GoodXmlParser.fromContext(this);
        Map<String,Integer> map = new HashMap<>();
        ArrayList<Good> goodListBySector = gParser.getGoodListNew("");
        for (Good good :
                goodListBySector) {
            if(isGoodsByRegion){
                for (CountryGood country : good.getCountries()) {
                    //Log.e("Region",country.getCountryRegion());
                    if (map.containsKey(country.getCountryRegion())) {
                        map.put(country.getCountryRegion(), map.get(country.getCountryRegion()) + 1);
                    } else {
                        map.put(country.getCountryRegion(), 1);
                    }
                }
            }else {
                if (good.getSector().equals(sector)) {
                    for (CountryGood country : good.getCountries()) {
                        //Log.e("Region",country.getCountryRegion());
                        if (map.containsKey(country.getCountryRegion())) {
                            map.put(country.getCountryRegion(), map.get(country.getCountryRegion()) + 1);
                        } else {
                            map.put(country.getCountryRegion(), 1);
                        }
                    }
                }
            }
        }
        ArrayList<PieEntry> values = new ArrayList<>();
        map.remove("");
        for (String key :
                map.keySet()) {
            values.add(new PieEntry(map.get(key), key));
        }


        PieDataSet dataSet = new PieDataSet(values, country+" By Region");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        /*for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);*/

        /*for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);*/

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        /*for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);*/

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new DefaultValueFormatter(0));
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.WHITE);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }




    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString(country+"\nBy Region");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, country.length(), 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), country.length(), s.length() - country.length()+5, 0);
        //s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        //s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        //s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        //s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
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
