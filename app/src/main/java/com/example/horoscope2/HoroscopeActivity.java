package com.example.horoscope2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.horoscope2.MainActivity.EXTRA_MESSAGE;

public class HoroscopeActivity extends AppCompatActivity {

    ImageButton health, career, love, money, daily, goBack;

    TextView thealth, tcareer, tlove, tmoney, tdaily, tready;

    Button dbtn, wbtn, mbtn;

    String interval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horoscope);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView19);
        textView.setText(message);

        setLayout2();

    }

    void setLayout2 () {

        thealth = (TextView) findViewById(R.id.textView20);
        tcareer = (TextView) findViewById(R.id.textView21);
        tlove = (TextView) findViewById(R.id.textView22);
        tmoney = (TextView) findViewById(R.id.textView23);
        tdaily = (TextView) findViewById(R.id.textView24);
        tready = (TextView) findViewById(R.id.textView25);

        health = (ImageButton) findViewById(R.id.imageButton20);
        career= (ImageButton) findViewById(R.id.imageButton21);
        love = (ImageButton) findViewById(R.id.imageButton22);
        money= (ImageButton) findViewById(R.id.imageButton23);
        daily = (ImageButton) findViewById(R.id.imageButton24);
        goBack = (ImageButton) findViewById(R.id.imageButton15);

        dbtn=(Button)findViewById(R.id.button3);
        //dbtn.setPressed(true);
        wbtn=(Button)findViewById(R.id.button4);
        mbtn=(Button)findViewById(R.id.button5);
        //ybtn=(Button)findViewById(R.id.button6);

        health.setImageResource(R.drawable.btn_pressed2);
        career.setImageResource(R.drawable.btn_pressed2);
        love.setImageResource(R.drawable.btn_pressed2);
        money.setImageResource(R.drawable.btn_pressed2);
        daily.setImageResource(R.drawable.btn_pressed2);
        goBack.setImageResource(R.drawable.btn_pressed2);

    }

    public void selectFunction2 (View view){

        switch (view.getId()) {

            case R.id.imageButton20:
                /**Toast toast1 = Toast.makeText(getApplicationContext(),
                        "health", Toast.LENGTH_LONG);
                toast1.show();
                 Creo un nuevo Intent*/
                Intent intent = new Intent(this, TryFullAccessActivity.class);
                /**Coloco en el intent el signo seleccionado*/
                intent.putExtra(EXTRA_MESSAGE, "Health Horoscope");
                intent.putExtra(interval,tdaily.getText());
                startActivity(intent);
                break;

            case R.id.imageButton21:
                /** Toast toast3 = Toast.makeText(getApplicationContext(),
                        "career", Toast.LENGTH_LONG);
                toast3.show();
               Creo un nuevo Intent*/
                Intent intent2 = new Intent(this, TryFullAccessActivity.class);
                /**Coloco en el intent el signo seleccionado*/
                intent2.putExtra(EXTRA_MESSAGE, "Career Horoscope");
                intent2.putExtra(interval,tdaily.getText());
                startActivity(intent2);
                break;

            case R.id.imageButton22:
                /**Toast toast2 = Toast.makeText(getApplicationContext(),
                        "love", Toast.LENGTH_LONG);
                toast2.show();
                 Creo un nuevo Intent*/
                Intent intent3 = new Intent(this, TryFullAccessActivity.class);
                /**Coloco en el intent el signo seleccionado*/
                intent3.putExtra(EXTRA_MESSAGE, "Love Horoscope");
                intent3.putExtra(interval,tdaily.getText());
                startActivity(intent3);
                break;

            case R.id.imageButton23:
                /**Toast toast4 = Toast.makeText(getApplicationContext(),
                        "money", Toast.LENGTH_LONG);
                toast4.show();
                 Creo un nuevo Intent*/
                Intent intent6 = new Intent(this, TryFullAccessActivity.class);
                /**Coloco en el intent el signo seleccionado*/
                intent6.putExtra(EXTRA_MESSAGE, "Money Horoscope");
                intent6.putExtra(interval,tdaily.getText());
                startActivity(intent6);
                break;

            case R.id.imageButton24:
                /**oast toast5 = Toast.makeText(getApplicationContext(),
                        "daily", Toast.LENGTH_LONG);
                toast5.show();
                 Creo un nuevo Intent*/
                Intent intent4 = new Intent(this, TryFullAccessActivity.class);
                /**Coloco en el intent el signo seleccionado*/
                intent4.putExtra(EXTRA_MESSAGE, tdaily.getText());
                intent4.putExtra(interval,tdaily.getText());
                startActivity(intent4);
                break;

            case R.id.button3:
                dbtn.setPressed(true);
                tdaily.setText("Daily Horoscope");
                wbtn.setPressed(false);
                mbtn.setPressed(false);
                break;

                case R.id.button4:
                    wbtn.setPressed(true);
                    tdaily.setText("Weekly Horoscope");
                    dbtn.setPressed(false);
                    mbtn.setPressed(false);
                break;

            case R.id.button5:
                mbtn.setPressed(true);
                tdaily.setText("Monthly Horoscope");
                dbtn.setPressed(false);
                wbtn.setPressed(false);

                break;

            default:
                break;
        }
    }

    public void goBack(View view){
        /** Creo un nuevo Intent*/
        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
    }

}
