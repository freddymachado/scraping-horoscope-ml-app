package com.example.horoscope2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.horoscope2.recycler.TextItem;
import com.example.horoscope2.recycler.TimelineItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Collections;
import java.util.Map;

import static com.example.horoscope2.MainActivity.EXTRA_MESSAGE;
import static com.example.horoscope2.R.drawable.scorpio;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import static com.example.horoscope2.PrincipalActivity.capt;

public class ImageLabelingActivity extends AppCompatActivity {

    Button choose;
    TextView resultTv;
    ImageView imageView, imageView3;
    CharSequence imageLabels;
    String labels1;
    String palmOrHead, beard=" ", selfie=" ", smile=" ";
    String TAG;
    String[] labels;
    Boolean HandDetected=false;
    ProgressBar progressBar;
    int i=0;
    Handler h = new Handler();
    Uri outputFileUri;
    ArrayList<Integer> arrayBarajado;

    private boolean done=false;

    private int[] imageResults = {R.drawable.animal,R.drawable.animal2,R.drawable.animal3,R.drawable.animal4,R.drawable.animal5,R.drawable.animal6,R.drawable.animal7,R.drawable.animal8,R.drawable.animal9,R.drawable.animal10,R.drawable.animal11};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_labeling);

        // Get the Intent that started this activity and extract the string
        final Intent intent = getIntent();
        outputFileUri = intent.getParcelableExtra("imageUri");
        String message = intent.getStringExtra(capt);

        if(message.equals("Hand Capture")){
            palmOrHead="Hand";
        }else{
            palmOrHead="Head";
            beard="beard";
            selfie="selfie";
            smile="smile";
        }


        resultTv=findViewById(R.id.textView30);
        imageView=findViewById(R.id.imageView);
        choose=findViewById(R.id.choose);

        imageView.setImageURI(outputFileUri);

        progressBar=(ProgressBar)findViewById(R.id.progressBar2);


                FirebaseVisionImage image;
        try {
            image = FirebaseVisionImage.fromFilePath(getApplicationContext(), outputFileUri);
            FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance()
                    .getOnDeviceImageLabeler();

            labeler.processImage(image)
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionImageLabel> labels) {

                            for (FirebaseVisionImageLabel label: labels) {
                                String text = label.getText();
                                String entityId = label.getEntityId();
                                float confidence = label.getConfidence();

                                if(text.equalsIgnoreCase(palmOrHead)||text.equalsIgnoreCase(beard)||text.equalsIgnoreCase(selfie)||text.equalsIgnoreCase(smile)||text.equalsIgnoreCase("sky")){
                                    resultTv.setText(text+" Successfully Detected\n");
                                    Log.d("MyApp",palmOrHead+" Successfully Detected");
                                    HandDetected=true;
                                }else if(HandDetected==false){
                                    resultTv.setText(text+" Detected, "+palmOrHead+" Required. \nPlease try again");
                                    Log.d("MyApp","No Hand Detected");
                                }

                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            nothingDetected();
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }

        choose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                detecting();


                //progressBar=new ProgressBar(ImageLabelingActivity.this);


                /**Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"select images"),121);
                 */
            }
        });


    }

    private void detecting() {

        if(HandDetected){

            switch (palmOrHead) {

                case "Hand":
                    //Inicio thread
                    Thread hilo=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while(i<=100){

                                h.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        resultTv.setText(i+"%");
                                        progressBar.setProgress(i);

                                    }
                                });
                                try {
                                    Thread.sleep(20);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if(i==100){
                                    Intent intent = new Intent(ImageLabelingActivity.this,ChatActivity.class);
                                    /**Coloco en el intent el outputFileUri */
                                    intent.putExtra("imageUri", outputFileUri);
                                    startActivity(intent);
                                    finish();
                                }
                                i++;
                            }
                        }
                    });
                    hilo.start();
                    break;

                case "Head":
                    //Inicio thread
                    /**Thread hilo1=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while(i<=100){

                                h.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        resultTv.setText(i+"%");
                                        progressBar.setProgress(i);

                                    }
                                });
                                try {
                                    Thread.sleep(20);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if(i==100){
                                    done = true;
                                }
                                i++;
                            }
                        }
                    });
                    hilo1.start();*/
                    Log.d("index barajados","case works!");

                    showImage();

                    break;


                default:
                    break;
            }

        }else{
            resultTv.setText("Nothing detected, please return\n");
        }
/**
        labels = imageLabels.split("-");
        labels1 = labels[0];
        //labels2 = labels[1];// 123
        resultTv.setText(labels1);
        //resultTv.append(labels2);
        Log.i(TAG,resultTv.getText().toString());*/
    }

    private void showImage(){

        //Declaro el SharedPreferences
        SharedPreferences userInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Declaro el editor del SharedPreferences
        SharedPreferences.Editor  editor = userInfo.edit();


        //Primero que nada obtenemos la fecha actual para validar que no se haya pedido un horoscopo el dia de hoy
        Date d = new Date();
        //s contiene la fecha actual en el formato descrito
        CharSequence s = DateFormat.format("MMM d yyyy", d.getTime());
        //Valido si no ha pasado un dia desde la ultima prediccion
        if((userInfo.getString("todayDate2"," ")).equals(s)){
            Toast toast14 = Toast.makeText(getApplicationContext(),
                    "Next reading will be available tomorrow!", Toast.LENGTH_LONG);
            toast14.show();
        }
        //en caso contrario
        else{
            editor.putString("todayDate2", s.toString());
            editor.apply();
            arrayBarajado = barajar(11);
            //Valido si el indice arrayBarajado.get(0) se ha usado antes
            int indexNoUsado= checkIndexUsados();
            imageView3 = findViewById(R.id.imageView3);
            imageView3.setImageResource(imageResults[arrayBarajado.get(indexNoUsado)]);
            guardarIdex(indexNoUsado);
        }
    }

    private void guardarIdex(int indexUsado){
        //Declaro el SharedPreferences
        SharedPreferences userInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Declaro el editor del SharedPreferences
        SharedPreferences.Editor  editor = userInfo.edit();
        //Rescato los index usados anteriormente
        String indexes= userInfo.getString("indexUsados2","");
        //Rescato el index recién usado
        int indexx = arrayBarajado.get(indexUsado);
        //Guardo el index usado
        editor.putString("indexUsados2", indexes+indexx + " ");
        editor.apply();

        Map<String, ?> allEntries = userInfo.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }

    }

    private  String[] recuperarIdexUsados(){
        //Declaro el SharedPreferences
        SharedPreferences userInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String index= userInfo.getString("indexUsados2","");

        String[] indexS = index.split(" ");

        return indexS;
    }

    private int checkIndexUsados(){
        //Indica el indice del arrayBarajado que no se ha usado so far, si todos se han usado, mostrará el ultimo indice de dicho arreglo
        String[] indexUsados=recuperarIdexUsados();
        int n=0;
        for(int i=0 ; i<indexUsados.length;i++){
            if(arrayBarajado.get(n).toString().equals(indexUsados[i])){
                n++;
            }
        }
        return n;
    }

    public ArrayList<Integer> barajar(int longitud) {
        ArrayList resultadoA = new ArrayList<Integer>();
        for(int i=0; i<longitud; i++)
            resultadoA.add(i);
        Collections.shuffle(resultadoA);
        Log.d("index barajados", resultadoA.get(0)+" "+resultadoA.get(1)+" "+resultadoA.get(2)+" "+resultadoA.get(3)+" "+resultadoA.get(4)+" "+resultadoA.get(5)+" "+resultadoA.get(6)+" "+resultadoA.get(7)+" "+resultadoA.get(8));
        return  resultadoA;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==121){
            imageView.setImageURI(data.getData());

            FirebaseVisionImage image;
            try {
                image = FirebaseVisionImage.fromFilePath(getApplicationContext(), data.getData());
                FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance()
                        .getOnDeviceImageLabeler();

                labeler.processImage(image)
                        .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                                    @Override
                                    public void onSuccess(List<FirebaseVisionImageLabel> labels) {

                                        for (FirebaseVisionImageLabel label: labels) {
                                            String text = label.getText();
                                            String entityId = label.getEntityId();
                                            float confidence = label.getConfidence();
                                            resultTv.append(text+"    "+confidence+"\n");
                                        }

                                    }
                                })
                        .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        nothingDetected();
                                    }
                                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void nothingDetected() {
        Toast.makeText(this,"Nothing Detected",Toast.LENGTH_SHORT);
    }

    /**private void processDataResult(List<FirebaseVisionLabel> labels) {

     for (FirebaseVisionLabel label: labels) {
     Toast.makeText(this,"Label result: "+label.getLabel(),Toast.LENGTH_SHORT);
     }


     FirebaseVisionImage image = null;
     try {
     image = FirebaseVisionImage.fromFilePath(FaceScanActivity.this, Uri.fromFile(file));
     } catch (IOException e) {
     e.printStackTrace();
     }
     nothingDetected();

     FirebaseVisionLabelDetector detector = FirebaseVision.getInstance()
     .getVisionLabelDetector();

     nothingDetected();
     assert image != null;
     Task<List<FirebaseVisionLabel>> Result =
     detector.detectInImage(image)
     .addOnSuccessListener(
     new OnSuccessListener<List<FirebaseVisionLabel>>() {
    @Override
    public void onSuccess(List<FirebaseVisionLabel> labels) {
    processDataResult(labels);
    }
    })
     .addOnFailureListener(
     new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {
    nothingDetected();
    }
    });

     }*/
}
