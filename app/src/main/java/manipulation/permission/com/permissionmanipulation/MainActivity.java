package manipulation.permission.com.permissionmanipulation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    boolean popup = false;
    private TextView hitsNo;
    private int cnt = 0;
    private long prevClick = -1;
    private int displayCnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hitsNo = (TextView) findViewById(R.id.hits_no);

        displayCnt = rndNo(15, 30);

        setHits(cnt);
    }

    private boolean requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            return true;
        }
        return false;
    }

    private int rndNo(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    private void setHits(int hits) {
        hitsNo.setText(String.format(getString(R.string.hits_no), hits));
    }

    public void btnHit(View v) {
        if (popup ? prevClick + 400 < System.currentTimeMillis() : prevClick + 200 < System.currentTimeMillis()
                && prevClick != -1)
            cnt = 0;

        setHits(cnt++);

        if (cnt == displayCnt)
            popup = requestLocationPermission();

        prevClick = System.currentTimeMillis();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0)
            for (int i : grantResults) {
                if (i == PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(this.findViewById(android.R.id.content), R.string.permission_granted, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
    }
}
