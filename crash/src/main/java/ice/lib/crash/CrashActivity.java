package ice.lib.crash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

public class CrashActivity extends Activity {

    private float sp2px(Float spValue) {
        return spValue * getResources().getDisplayMetrics().scaledDensity + 0.5f;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String errorData = intent.getStringExtra("error");
        if (errorData == null) {
            errorData = "Error Data is null";
        }
        loadView(errorData);
    }

    private void loadView(String data) {
        ScrollView scrollView = new ScrollView(this);
        scrollView.setOverScrollMode(2);
        TextView textView = new TextView(this);
        textView.setText(data);
        textView.setTextSize(sp2px(3f));
        HorizontalScrollView hScrollView = new HorizontalScrollView(this);
        hScrollView.addView(textView);
        scrollView.addView(hScrollView);
        setContentView(scrollView);
    }

}
