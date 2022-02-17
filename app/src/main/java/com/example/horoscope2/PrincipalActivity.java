package com.example.horoscope2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

import static com.example.horoscope2.MainActivity.EXTRA_MESSAGE;

public class PrincipalActivity extends AppCompatActivity {

    ImageButton horoscope, faceScan, palmReading;

    TextView tHoroscope, tFaceScan, tPalmReading, tHoroscopeApp;

    Button goBack;

    public static final String capt="";

    String sign;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //Declaro el SharedPreferences
        SharedPreferences userInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Declaro el editor del SharedPreferences
        SharedPreferences.Editor  editor = userInfo.edit();
        // Get the Intent that started this activity
        Intent intent = getIntent();

        setLayout2();

        TextView textView = findViewById(R.id.textView14);
        //Verificamos si ya se había logeado antes
        if(userInfo.getBoolean("loggedIn",true)){
            //De ser así, muestro el signo correspondiente a la sesion actual
            textView.setText(userInfo.getString("sign",sign));
        }else{
            //Si no, ahora sí está loggeado. Edito el SharedPreferences
            editor.putBoolean("loggedIn",true);
            //Rescato el signo seleccionado en el activity anterior.
            String message = intent.getStringExtra(EXTRA_MESSAGE);
            //Lo muestro
            textView.setText(message);
            //Guardo el signo en Shared preferences
            sign = message;
            editor.putString("sign",sign);
            editor.apply();
        }
    }

    void setLayout2 () {

        tHoroscope = (TextView) findViewById(R.id.textView15);
        tPalmReading = (TextView) findViewById(R.id.textView16);
        tFaceScan = (TextView) findViewById(R.id.textView17);
        tHoroscopeApp = (TextView) findViewById(R.id.textView18);

        horoscope = (ImageButton) findViewById(R.id.imageButton13);
        faceScan= (ImageButton) findViewById(R.id.imageButton14);
        palmReading = (ImageButton) findViewById(R.id.imageButton16);


        goBack = (Button) findViewById(R.id.button2);

        horoscope.setImageResource(R.drawable.btn_pressed2);
        faceScan.setImageResource(R.drawable.btn_pressed2);
        palmReading.setImageResource(R.drawable.btn_pressed2);


    }

    public void selectFunction (View view){

        switch (view.getId()) {

            case R.id.imageButton13:
                /**Toast toast1 = Toast.makeText(getApplicationContext(),
                        "Horoscope", Toast.LENGTH_LONG);
                toast1.show();
                 Creo un nuevo Intent*/
                Intent intent = new Intent(this, HoroscopeActivity.class);
                /**Coloco en el intent el signo seleccionado*/
                intent.putExtra(EXTRA_MESSAGE, sign);
                startActivity(intent);
                break;

            case R.id.imageButton14:
                /**Toast toast3 = Toast.makeText(getApplicationContext(),
                        "facescan", Toast.LENGTH_LONG);
                toast3.show();
                 Creo un nuevo Intent*/
                Intent intent3 = new Intent(this, FaceScanActivity.class);
                intent3.putExtra(capt, "Face Capture");
                startActivity(intent3);
                break;

            case R.id.imageButton16:
                //Declaro el SharedPreferences
                SharedPreferences userInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                //Declaro el editor del SharedPreferences
                SharedPreferences.Editor  editor = userInfo.edit();
                boolean palmPhotoReceived =  userInfo.getBoolean("palmPhotoReceived", false);

                /** Algoritmo para chequear en el log los valores de cada key
                 * Map<String, ?> allEntries = userInfo.getAll();
                 * for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                 *        Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
                 * }
                 */


                if(!palmPhotoReceived){
                    //Creo un nuevo Intent
                    Intent intent1 = new Intent(this, FaceScanActivity.class);
                    /**Coloco en el intent el signo seleccionado*/
                    intent1.putExtra(capt, "Hand Capture");
                    startActivity(intent1);
                }else{
                    //Creo un nuevo Intent
                    Intent intent7 = new Intent(this, ChatActivity.class);
                    startActivity(intent7);

                }

                break;

            case R.id.button2:
                /** Creo un nuevo Intent*/
                Intent intent4 = new Intent(this, MainActivity.class);
                startActivity(intent4);
                break;

            default:
                break;
        }
    }
}
