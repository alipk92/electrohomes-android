package me.sheharyar.alisparkrelay;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends Activity {
    public static ProgressBar  progressbar;
    public static ArrayList<Switch> relays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressbar = (ProgressBar) findViewById (R.id.progressBar);

        relays = new ArrayList<Switch>();

        relays.add((Switch)findViewById(R.id.switch1));
        relays.add((Switch)findViewById(R.id.switch2));
        relays.add((Switch)findViewById(R.id.switch3));
        relays.add((Switch)findViewById(R.id.switch4));
    }

    protected void performRelayAction(final int relay) {
        final boolean status = relays.get(relay-1).isChecked();

        SparkRestClient.switchRelay(relay, status, new JsonHttpResponseHandler() {
            public void onStart() {
                disableAll();
                progressbar.setVisibility(View.VISIBLE);

                String text = "OFF";
                if (relays.get(relay-1).isChecked())
                    text = "ON";

                Toast.makeText(getApplicationContext(), "Turning Relay " + relay + " " + text, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressbar.setVisibility(View.INVISIBLE);
                enableAll();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progressbar.setVisibility(View.INVISIBLE);
                enableAll();
                relays.get(relay-1).setChecked(!status);
                Toast.makeText(getApplicationContext(), "ERROR!", Toast.LENGTH_LONG).show();
            }

        });

    }

    public static void enableAll()  { for (Switch r : relays) r.setEnabled(true);  }
    public static void disableAll() { for (Switch r : relays) r.setEnabled(false); }

    public void s1(View view) { performRelayAction(1); }
    public void s2(View view) { performRelayAction(2); }
    public void s3(View view) { performRelayAction(3); }
    public void s4(View view) { performRelayAction(4); }

}
