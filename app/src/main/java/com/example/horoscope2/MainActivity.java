package com.example.horoscope2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageButton i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13;

    TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13;

    Button btn1;

    String sign = "0";

    //private int[] imageResult = {R.drawable.10,R.drawable.11};

    public static final String EXTRA_MESSAGE = "Hello";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences userInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        setLayout();
        if(userInfo.getBoolean("loggedIn",true)){
            sign=userInfo.getString("sign",sign);
            switch (sign) {

                case "0":
                    Log.i("myApp","sign sobreescrito");

                case "Aries":
                    i3.setImageResource(R.drawable.btn_pressed);
                    break;

                case "Gemini":
                    clearBtn();
                    i1.setImageResource(R.drawable.btn_pressed);
                    break;

                case "Taurus":
                    i2.setImageResource(R.drawable.btn_pressed);
                    break;

                case "Virgo":
                    i4.setImageResource(R.drawable.btn_pressed);
                    break;

                case "Leo":
                    i5.setImageResource(R.drawable.btn_pressed);
                    break;

                case "Cancer":
                    i6.setImageResource(R.drawable.btn_pressed);
                    break;

                case "Sagittarius":
                    i7.setImageResource(R.drawable.btn_pressed);
                    break;

                case "Scorpio":
                    i8.setImageResource(R.drawable.btn_pressed);
                    break;

                case "Libra":
                    i9.setImageResource(R.drawable.btn_pressed);
                    break;

                case "Pisces":
                    i10.setImageResource(R.drawable.btn_pressed);
                    break;

                case "Aquarius":
                    i11.setImageResource(R.drawable.btn_pressed);
                    break;

                case "Capricorn":
                    i12.setImageResource(R.drawable.btn_pressed);;
                    break;


                default:
                    break;
            }

            i13 = (ImageButton) findViewById(R.id.imageButton15);
        }

    }

    void setLayout () {

        t1 = (TextView) findViewById(R.id.textView);
        t2 = (TextView) findViewById(R.id.textView2);
        t3 = (TextView) findViewById(R.id.textView3);
        t4 = (TextView) findViewById(R.id.textView4);
        t5 = (TextView) findViewById(R.id.textView5);
        t6 = (TextView) findViewById(R.id.textView6);
        t7 = (TextView) findViewById(R.id.textView7);
        t8 = (TextView) findViewById(R.id.textView8);
        t9 = (TextView) findViewById(R.id.textView9);
        t10 = (TextView) findViewById(R.id.textView10);
        t11 = (TextView) findViewById(R.id.textView11);
        t12 = (TextView) findViewById(R.id.textView12);
        t13 = (TextView) findViewById(R.id.textView13);


        i1 = (ImageButton) findViewById(R.id.imageButton);
        i2= (ImageButton) findViewById(R.id.imageButton2);
        i3 = (ImageButton) findViewById(R.id.imageButton3);
        i4 = (ImageButton) findViewById(R.id.imageButton4);
        i5 = (ImageButton) findViewById(R.id.imageButton5);
        i6 = (ImageButton) findViewById(R.id.imageButton6);
        i7 = (ImageButton) findViewById(R.id.imageButton7);
        i8 = (ImageButton) findViewById(R.id.imageButton8);
        i9 = (ImageButton) findViewById(R.id.imageButton9);
        i10 = (ImageButton) findViewById(R.id.imageButton10);
        i11 = (ImageButton) findViewById(R.id.imageButton11);
        i12 = (ImageButton) findViewById(R.id.imageButton12);

        btn1 = (Button) findViewById(R.id.button);

    }

    public void clearBtn(){
        i1.setImageResource(R.drawable.btn_pressed1);
        i2.setImageResource(R.drawable.btn_pressed1);
        i3.setImageResource(R.drawable.btn_pressed1);
        i4.setImageResource(R.drawable.btn_pressed1);
        i5.setImageResource(R.drawable.btn_pressed1);
        i6.setImageResource(R.drawable.btn_pressed1);
        i7.setImageResource(R.drawable.btn_pressed1);
        i8.setImageResource(R.drawable.btn_pressed1);
        i9.setImageResource(R.drawable.btn_pressed1);
        i10.setImageResource(R.drawable.btn_pressed1);
        i11.setImageResource(R.drawable.btn_pressed1);
        i12.setImageResource(R.drawable.btn_pressed1);

    }

    public void selectSign (View view){

        switch (view.getId()) {

            case R.id.button:
                Toast toast13 = Toast.makeText(getApplicationContext(),
                        "Funciona el beta", Toast.LENGTH_LONG);
                toast13.show();
                break;

                case R.id.imageButton:
                    clearBtn();
                    i1.setImageResource(R.drawable.btn_pressed);
                Toast toast1 = Toast.makeText(getApplicationContext(),
                        "Gemini", Toast.LENGTH_LONG);
                toast1.show();
                sign = "Gemini";
                break;

            case R.id.imageButton2:
                clearBtn();
                i2.setImageResource(R.drawable.btn_pressed);

                Toast toast3 = Toast.makeText(getApplicationContext(),
                        "Taurus", Toast.LENGTH_LONG);
                toast3.show();
                sign = "Taurus";
                break;

            case R.id.imageButton3:
                clearBtn();
                i3.setImageResource(R.drawable.btn_pressed);
                Toast toast2 = Toast.makeText(getApplicationContext(),
                        "Aries", Toast.LENGTH_LONG);
                toast2.show();
                sign = "Aries";
                break;

            case R.id.imageButton4:
                clearBtn();
                i4.setImageResource(R.drawable.btn_pressed);
                Toast toast4 = Toast.makeText(getApplicationContext(),
                        "Virgo", Toast.LENGTH_LONG);
                toast4.show();
                sign = "Virgo";
                break;

            case R.id.imageButton5:
                clearBtn();
                i5.setImageResource(R.drawable.btn_pressed);
                Toast toast5 = Toast.makeText(getApplicationContext(),
                        "Leo", Toast.LENGTH_LONG);
                toast5.show();
                sign = "Leo";
                break;

            case R.id.imageButton6:
                clearBtn();
                i6.setImageResource(R.drawable.btn_pressed);
                Toast toast6 = Toast.makeText(getApplicationContext(),
                        "Cancer", Toast.LENGTH_LONG);
                toast6.show();
                sign = "Cancer";
                break;

            case R.id.imageButton7:
                clearBtn();
                i7.setImageResource(R.drawable.btn_pressed);
                Toast toast7 = Toast.makeText(getApplicationContext(),
                        "Sagittarius", Toast.LENGTH_LONG);
                toast7.show();
                sign = "Sagittarius";
                break;

            case R.id.imageButton8:
                clearBtn();
                i8.setImageResource(R.drawable.btn_pressed);
                Toast toast8 = Toast.makeText(getApplicationContext(),
                        "Scorpio", Toast.LENGTH_LONG);
                toast8.show();
                sign = "Scorpio";
                break;

            case R.id.imageButton9:
                clearBtn();
                i9.setImageResource(R.drawable.btn_pressed);
                Toast toast9 = Toast.makeText(getApplicationContext(),
                        "Libra", Toast.LENGTH_LONG);
                toast9.show();
                sign = "Libra";
                break;

            case R.id.imageButton10:
                clearBtn();
                i10.setImageResource(R.drawable.btn_pressed);
                Toast toast10 = Toast.makeText(getApplicationContext(),
                        "Pisces", Toast.LENGTH_LONG);
                toast10.show();
                sign = "Pisces";
                break;

            case R.id.imageButton11:
                clearBtn();
                i11.setImageResource(R.drawable.btn_pressed);
                Toast toast11 = Toast.makeText(getApplicationContext(),
                        "Aquarius", Toast.LENGTH_LONG);
                toast11.show();
                sign = "Aquarius";
                break;

            case R.id.imageButton12:
                clearBtn();
                i12.setImageResource(R.drawable.btn_pressed);
                Toast toast12 = Toast.makeText(getApplicationContext(),
                        "Capricorn", Toast.LENGTH_LONG);
                toast12.show();
                sign = "Capricorn";
                break;


            default:
                break;
        }
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {

        SharedPreferences userInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor  editor = userInfo.edit();
        editor.putBoolean("loggedIn",false);
        editor.commit();

        if(sign=="0"){

            Toast toast14 = Toast.makeText(getApplicationContext(),
                    "Please select your sign", Toast.LENGTH_LONG);
            toast14.show();

        } else {


            /** Creo un nuevo Intent*/
            Intent intent = new Intent(this, PrincipalActivity.class);
            /**Coloco en el intent el signo seleccionado*/
            intent.putExtra(EXTRA_MESSAGE, sign);
            startActivity(intent);

        }

    }

    public void goBack(View view){
        /** Creo un nuevo Intent*/
        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
    }
}
