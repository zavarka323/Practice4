package ru.mirea.zavarzin.practice4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    int counter = 0;
    TextView infoTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoTextView = findViewById(R.id.textView);
        Thread mainThread = Thread.currentThread();
        infoTextView.append("\n\nВВОДИТЕ ЧИСЛА!!!\n\nТекущий поток: " + mainThread.getName());
        // Меняем имя и выводим в текстовом поле
        mainThread.setName("MireaThread");
        infoTextView.append("\nНовое имя потока: " + mainThread.getName());
    }
    public void onClick(View view) {
        Runnable runnable = new Runnable() {
            public void run() {
                int numberThread = counter++;
                Log.i("ThreadProject", "Запущен поток № " + numberThread);
                long endTime = System.currentTimeMillis() + 10 * 1000;

                String couples=((EditText)findViewById(R.id.editTextCouples)).getText().toString();
                String days=((EditText)findViewById(R.id.editTextTextDays)).getText().toString();
                if (!couples.equals("") && !days.equals(""))
                {
                    infoTextView.setText("Количество пар в среднем за день: "+Integer.parseInt(couples)/Integer.parseInt(days));
                }

                while (System.currentTimeMillis() < endTime) {
                    synchronized (this) {
                        try { wait(endTime - System.currentTimeMillis()); }
                        catch (Exception e) {}
                    }
                }
                Log.i("ThreadProject", "Выполнен поток № " + numberThread);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
