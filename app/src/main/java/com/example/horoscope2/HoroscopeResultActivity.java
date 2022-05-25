package com.example.horoscope2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import static com.example.horoscope2.MainActivity.EXTRA_MESSAGE;

public class HoroscopeResultActivity extends AppCompatActivity {

  String message, sign, interval, interval1, signNumber;

  Button obtenerBtn;

  TextView intervalT, periodT, titleT, signT, horoscopeT;

  ImageButton goBack, btnShare;

  ImageView signV;

  String period = "daily-today", topic = "general";
  String TAG;
  String[] date;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_horoscope_result);

    // Get the Intent that started this activity and extract the string
    Intent intent = getIntent();
    message = intent.getStringExtra(EXTRA_MESSAGE);
    interval1 = intent.getStringExtra(interval);

    setLayout();
    setHoroscope(message,interval1);
    setPeriod(interval1);

    obtenerBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        obtenerWeb();
      }
    });

  }

  public void setLayout(){
    intervalT=(TextView)findViewById(R.id.textView31);
    periodT=(TextView)findViewById(R.id.textView32);
    titleT=(TextView)findViewById(R.id.textView33);
    signT=(TextView)findViewById(R.id.textView34);
    horoscopeT=(TextView)findViewById(R.id.textView35);

    goBack=(ImageButton) findViewById(R.id.imageButton15);
    btnShare=(ImageButton)findViewById(R.id.imageButton16);

    signV=(ImageView)findViewById(R.id.imageView2);

    obtenerBtn=findViewById(R.id.obtenerBtn);

    SharedPreferences userInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    sign= userInfo.getString("sign",sign);
    signT.setText(sign);

    goBack.setImageResource(R.drawable.btn_pressed2);
    btnShare.setImageResource(R.drawable.btn_pressed2);

    switch (sign) {

      case "0":
        Log.i("myApp","sign sobreescrito");

      case "Aries":
        signV.setImageResource(R.drawable.aries);
        signNumber="1";
        break;

      case "Gemini":
        signV.setImageResource(R.drawable.gemini);
        signNumber="3";
        break;

      case "Taurus":
        signV.setImageResource(R.drawable.taurus);
        signNumber="2";
        break;

      case "Virgo":
        signV.setImageResource(R.drawable.virgo);
        signNumber="6";
        break;

      case "Leo":
        signV.setImageResource(R.drawable.leo);
        signNumber="5";
        break;

      case "Cancer":
        signV.setImageResource(R.drawable.cancer);
        signNumber="4";
        break;

      case "Sagittarius":
        signV.setImageResource(R.drawable.sagitarius);
        signNumber="9";
        break;

      case "Scorpio":
        signV.setImageResource(R.drawable.scorpio);
        signNumber="8";
        break;

      case "Libra":
        signV.setImageResource(R.drawable.libra);
        signNumber="7";
        break;

      case "Pisces":
        signV.setImageResource(R.drawable.pisces);
        signNumber="12";
        break;

      case "Aquarius":
        signV.setImageResource(R.drawable.aquarius);
        signNumber="11";
        break;

      case "Capricorn":
        signV.setImageResource(R.drawable.capricorn);
        signNumber="10";
        break;

      default:
        break;
    }

  }

  void setHoroscope(String message, String interval){

    horoscopeT.setText("Press the button!");
    switch (message) {

      case "Health Horoscope":
        titleT.setText(message);
        intervalT.setText(interval);
        topic="wellness";
        break;

      case "Career Horoscope":
        titleT.setText(message);
        intervalT.setText(interval);
        topic="career";
        break;

      case "Love Horoscope":
        titleT.setText(message);
        intervalT.setText(interval);
        topic="love";
        break;

      case "Money Horoscope":
        titleT.setText(message);
        intervalT.setText(interval);
        topic="money";
        break;

      case "Daily Horoscope":
        intervalT.setText(message);
        break;

      case "Weekly Horoscope":
        intervalT.setText(message);
        break;

      case "Monthly Horoscope":
        intervalT.setText(message);
        break;

      case "Yearly Horoscope":
        intervalT.setText(message);
        break;

      default:
        break;
    }
  }

  void setPeriod(String interval){

    Date d = new Date();
    //s contiene la fecha actual en el formato descrito
    CharSequence s = DateFormat.format("MMM d yyyy ", d.getTime());

    Calendar calendar = Calendar.getInstance();
    while (calendar.get(Calendar.DAY_OF_WEEK) > calendar.getFirstDayOfWeek()) {
      calendar.add(Calendar.DATE, -1); // Substract 1 day until first day of week.
    }
    CharSequence firstDay = DateFormat.format("d MMM yyyy", calendar.getTime());
    date = firstDay.toString().split(" ");
    int lastDay = Integer.parseInt(date[0])+6;
    String month = date[1];

    int lastDayM =calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

    CharSequence year = date[2];



    switch (interval) {

      case "Daily Horoscope":
        periodT.setText(s);
        break;

      case "Weekly Horoscope":
        periodT.setText(firstDay+" - "+lastDay+" "+month);
        period="weekly";
        break;

      case "Monthly Horoscope":
        periodT.setText("01 "+month+" - "+lastDayM+" "+month);
        period="monthly";
        break;

      case "Yearly Horoscope":
        periodT.setText(year);
        period="yearly";
        break;

      default:
        break;
    }
  }

  public void goBack(View view){
    /** Creo un nuevo Intent*/
    Intent intent = new Intent(this, HoroscopeActivity.class);
    startActivity(intent);
  }

  //Scraper de la info en la web
  private void obtenerWeb(){
    Log.d("horosweb","https://www.horoscope.com/us/horoscopes/"+topic+"/horoscope-"+topic+"-"+period+".aspx?sign="+signNumber);
    new Thread(new Runnable() {
      @Override
      public void run() {
        final StringBuilder builder = new StringBuilder();
        try {
          Document document = Jsoup.connect("https://www.horoscope.com/us/horoscopes/"+topic+"/horoscope-"+topic+"-"+period+".aspx?sign="+signNumber)
                  .timeout(0).get();

          String title = document.title();

          //Elements links = document.select("a[href]"); Para extraer los links
          Elements entradas = document.select("div.main-horoscope");
          builder.append(title).append("\n");

          for(Element texto: entradas){
            builder.append("\n\n").append("").append(texto.text().split("- ")[1].split("Get")[0])
                    .append("\n");

          }
          /** Para extraer los links
           *for(Element link: links){
           *                      builder.append("\n\n").append("Link: ").append(link.attr("href"))
           *                                 .append("\n").append("Text: ").append(link.text())
           *                                 .append("\n");
           *                     }
           */

        }catch (IOException e){
          builder.append("Error: ").append(e.getMessage()).append("\n");
        }
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            horoscopeT.setText(builder.toString());
          }
        });
      }
    }).start();
  }

  public void share(View view){

    Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
    compartir.setType("text/plain");
    String mensaje = "I looked through my horoscope last week, and frankly, didn't pay much attention to it. Turned out, I should have! Some predictions came true word-for-word! Check yours in HorosopeApp";
    compartir.putExtra(android.content.Intent.EXTRA_SUBJECT, "Horoscope App");
    compartir.putExtra(android.content.Intent.EXTRA_TEXT, mensaje);
    startActivity(Intent.createChooser(compartir, "Share via"));
  }
}
