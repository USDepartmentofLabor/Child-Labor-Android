package gov.dol.childlabor.charts;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.Gravity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import gov.dol.childlabor.R;

public class DataVisualizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proportional_area_chart);
        BubbleLayout layout = findViewById(R.id.bubble_layout);
        Map<String,Pair<Float,Integer>> labels = new HashMap<>();
        labels.put("GOLD",new Pair(2.4f,R.color.yellow));
        labels.put("BRICKS",new Pair(2.0f,R.color.fuchsia));
        labels.put("SUGARCANE",new Pair(1.9f,R.color.silver));
        labels.put("COFFEE",new Pair(1.7f,R.color.purple));
        labels.put("COTTON",new Pair(1.7f,R.color.light_white));
        labels.put("TOBACCO",new Pair(1.7f,R.color.teal));
        labels.put("CATTLE",new Pair(1.4f,R.color.green));
        labels.put("FISH",new Pair(1.4f,R.color.aqua));
        labels.put("GARMENTS",new Pair(1.1f,R.color.red));
        labels.put("RICE",new Pair(0.9f,R.color.light_green));
        labels.put("COAL",new Pair(0.7f,R.color.light_yellow));



        Iterator<String> iterator = labels.keySet().iterator();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        while (iterator.hasNext()){
            String label = iterator.next();
            Float value = labels.get(label).first*width*0.2f;
            BubbleView bubbleView = new BubbleView(this);
            bubbleView.setCircleColor(labels.get(label).second);
            bubbleView.setText(label);
            bubbleView.setGravity(Gravity.CENTER);
            bubbleView.setPadding(10, 10, 10, 10);
            bubbleView.setTextColor(Color.parseColor("#000000"));
            layout.addView(bubbleView,value.intValue(),value.intValue());
        }

    }
}