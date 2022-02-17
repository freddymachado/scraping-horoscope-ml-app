package com.example.horoscope2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.horoscope2.MainActivity.EXTRA_MESSAGE;

public class TryFullAccessActivity extends AppCompatActivity {

    ImageButton imagen;

    TextView full, text1, text2, text3;

    Button watch;
    String message, interval1, interval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_full_access);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        message = intent.getStringExtra(EXTRA_MESSAGE);
        interval1 = intent.getStringExtra(interval);

    setLayout3();

    }

    public void pressBtn(View view){
        /** Creo un nuevo Intent*/
        Intent intent = new Intent(this, HoroscopeResultActivity.class);
        /**Coloco en el intent el signo seleccionado*/
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(interval, interval1);
        startActivity(intent);
    }

    void setLayout3 () {

        full = (TextView) findViewById(R.id.textView20);
        text1 = (TextView) findViewById(R.id.textView21);
        text2 = (TextView) findViewById(R.id.textView22);
        text3 = (TextView) findViewById(R.id.textView23);

        imagen = (ImageButton) findViewById(R.id.imageButton25);

        watch = (Button) findViewById(R.id.button29);


    }

    public void goBack(View view){
        /** Creo un nuevo Intent*/
        Intent intent = new Intent(this, HoroscopeActivity.class);
        startActivity(intent);
    }


}
