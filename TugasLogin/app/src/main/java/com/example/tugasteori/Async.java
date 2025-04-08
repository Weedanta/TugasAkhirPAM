package com.example.tugasteori;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Async extends AppCompatActivity {

    TextView progressText;
    Button runTaskButton;
    Handler mainHandler = new Handler(Looper.getMainLooper()); // bisa run on main thread (Diekskusi secara langsung), kalau pakai post bisa pakai antrian
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);

        progressText = findViewById(R.id.textViewProgress);
        runTaskButton = findViewById(R.id.buttonRun);

        runTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runBackgroundTask();
        }
        });
    }

    private void runBackgroundTask() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    int progress = i * 10;
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressText.setText("Progress " + progress + "%");
                        }
                    });
                }
            }
        });
    }
}
