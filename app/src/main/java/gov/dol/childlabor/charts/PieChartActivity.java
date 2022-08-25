package gov.dol.childlabor.charts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

import gov.dol.childlabor.R;

public class PieChartActivity extends AppCompatActivity implements
        OnChartValueSelectedListener {

    private PieChart chart;
    Float ag,se,in;
    String country = "Country";
    private TextView hiddenContentDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_piechart_half);
        hiddenContentDescription = findViewById(R.id.content_description);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Working Children Statistics");

        country = getIntent().getStringExtra("Country");
        String agriculture = getIntent().getStringExtra("Agriculture");
        String services = getIntent().getStringExtra("Services");
        String industry = getIntent().getStringExtra("Industry");
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        chart = findViewById(R.id.chart1);
        try {
            ag = Float.parseFloat(agriculture);
            se = Float.parseFloat(services);
            in = Float.parseFloat(industry);
        }catch (Exception e){
            e.printStackTrace();
            ag = .333f;
            se = .333f;
            in = .333f;
            findViewById(R.id.toolbar_head).setVisibility(View.GONE);
            chart.setVisibility(View.GONE);
            findViewById(R.id.text).setVisibility(View.VISIBLE);
            findViewById(R.id.back).setVisibility(View.VISIBLE);
        }

        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        //chart.setCenterText(generateCenterSpannableText());

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(50f);
        chart.setTransparentCircleRadius(0f);

        chart.setDrawCenterText(false);

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

        chart.setExtraBottomOffset(100);
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(30f);
        l.setYEntrySpace(0f);
        l.setYOffset(-20f);

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE);
        chart.setEntryLabelTextSize(12f);
        chart.setDrawEntryLabels(false);
        setData();
    }

    private void setData() {
        //ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        ArrayList<PieEntry> values = new ArrayList<>();
        values.add(new PieEntry(ag*100, "Agriculture"));
        values.add(new PieEntry(se*100, "Services"));
        values.add(new PieEntry(in*100, "Industry"));

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < values.size(); i++) {
            buffer.append(values.get(i).getValue() + ", " +values.get(i).getLabel() + ", ");
        }
        hiddenContentDescription.setContentDescription(buffer.toString());
        PieDataSet dataSet = new PieDataSet(values, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(108,128,80));
        colors.add(Color.rgb(57,89,122));
        colors.add(Color.rgb(218,165,32));

        /*for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);*/

        /*for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);*/

        /*for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);*/

        //colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }




    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString(country+"\nWorking Statistics");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, country.length(), 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), country.length(), s.length() - country.length()-1, 0);
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
