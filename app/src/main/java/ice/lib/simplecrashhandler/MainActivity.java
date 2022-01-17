package ice.lib.simplecrashhandler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import ice.lib.crash.CrashHandler;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout baseLayout = new LinearLayout(this);

        Button errorButton1 = new Button(this);
        errorButton1.setText("Null Pointer Error");
        errorButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = null;
                s.equals(null);
            }
        });

        Button errorButton2 = new Button(this);
        errorButton2.setText("Throwing An Error");
        errorButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                throw new RuntimeException("Test");
            }
        });

        Button errorButton3 = new Button(this);
        errorButton3.setText("Error In Other Thread");
        errorButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrashHandler.getSafeThread(getApplicationContext(), () -> {
                    throw new RuntimeException("Test in other threads");
                }).start();
            }
        });

        baseLayout.setOrientation(LinearLayout.VERTICAL);
        baseLayout.addView(errorButton1);
        baseLayout.addView(errorButton2);
        baseLayout.addView(errorButton3);

        setContentView(baseLayout);

    }
}
