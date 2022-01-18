package ice.lib.crash;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
        LinearLayout baseLayout = new LinearLayout(this);
        baseLayout.setOrientation(LinearLayout.VERTICAL);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setOverScrollMode(2);

        TextView textView = new TextView(this);
        textView.setText(data);
        textView.setTextSize(sp2px(3f));

        HorizontalScrollView hScrollView = new HorizontalScrollView(this);
        hScrollView.addView(textView);
        scrollView.addView(hScrollView);

        Button copyButton = new Button(this);
        copyButton.setText("Copy to Clipboard");
        copyButton.setOnClickListener(view -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("error data", data);
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show();
        });

        baseLayout.addView(scrollView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f));
        baseLayout.addView(copyButton);

        setContentView(baseLayout);
    }

}
