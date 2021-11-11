package com.example.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.function.LongToIntFunction;

public class MainActivity extends AppCompatActivity {

    long input = 0;
    int count = 0;
    TextView txtPercentage;
    EditText txtInput;
    Button btnDoAgain;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtPercentage = (TextView) findViewById(R.id.txtPercentage);
        txtInput = (EditText) findViewById(R.id.editext1);
        btnDoAgain = (Button) findViewById(R.id.btnDoAgain);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnDoAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    input = Integer.parseInt(txtInput.getText().toString());
                    new VerySlowTask().execute();
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private class VerySlowTask extends AsyncTask<String, Long, Void> {
        protected void onPreExecute() {
            txtPercentage.setText("0%");
            progressBar.setProgress(0);
            progressBar.setMax(100);
            btnDoAgain.setEnabled(false);
            count = 0;
        }
        protected Void doInBackground(final String... args) {
            try {
                for (Long i = 0L; i <= input; i++) {
                    Thread.sleep(0);
                    float percent = ((float)i/(float)input)*100f;
                    if ((int)percent > count) {
                        count = (int)percent;
                        publishProgress((long) count);
                    }
                }
            }
            catch (InterruptedException e) { Log.e("slow-job interrupted", e.getMessage()); }
            return null;
        }
        @Override
        protected void onProgressUpdate(Long... value) {
            super.onProgressUpdate(value);
            txtPercentage.setText(value[0] + "%");
            progressBar.setProgress(value[0].intValue());
        }
        protected void onPostExecute(final Void unused) {
            btnDoAgain.setEnabled(true);
            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
        }
    }
}