package ice.lib.crash;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class CrashActivity extends Activity {

    private static class AndroidDeviceMetadata {
        public static String getCPU() {
            final String[] supportedABIs = Build.SUPPORTED_ABIS;
            if (supportedABIs != null && supportedABIs.length > 0) {
                return supportedABIs[0];
            }
            return "UNKNOWN";
        }
    }

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

    private String getOthersData() {
        return "Android: " + Build.VERSION.RELEASE + "\n" +
                "SDK: " + Build.VERSION.SDK_INT + "\n" +
                "ABI: " + AndroidDeviceMetadata.getCPU() + "\n" +
                "Brand: " + Build.BRAND + "\n" +
                "Model: " + Build.MODEL + "\n" +
                Config.extraDataWhenShowingLog + "\n";
    }

    private void loadView(String data) {
        LinearLayout baseLayout = new LinearLayout(this);
        baseLayout.setOrientation(LinearLayout.VERTICAL);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setOverScrollMode(2);

        TextView logTextView = new TextView(this);
        logTextView.setText(data);
        logTextView.setTextSize(sp2px(3f));

        TextView deviceTextView = new TextView(this);
        deviceTextView.setText(getOthersData());
        deviceTextView.setTextSize(sp2px(3.5f));

        LinearLayout tLinearLayout = new LinearLayout(this);
        tLinearLayout.setOrientation(LinearLayout.VERTICAL);
        tLinearLayout.addView(deviceTextView);
        tLinearLayout.addView(logTextView);

        HorizontalScrollView hScrollView = new HorizontalScrollView(this);
        hScrollView.addView(tLinearLayout);
        scrollView.addView(hScrollView);

        Button copyButton = new Button(this);
        copyButton.setText("Copy to Clipboard");
        copyButton.setOnClickListener(view -> {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("error data", getOthersData() + "\n" + data);
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show();
        });

        baseLayout.addView(scrollView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f));
        baseLayout.addView(copyButton);

        setContentView(baseLayout);
    }

}
