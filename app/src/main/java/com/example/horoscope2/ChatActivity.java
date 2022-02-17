package com.example.horoscope2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.horoscope2.recycler.AdapterDatos;
import com.example.horoscope2.recycler.PalmPhotoItem;
import com.example.horoscope2.recycler.TextItem;
import com.example.horoscope2.recycler.TimelineItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.example.horoscope2.PrincipalActivity.capt;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private AdapterDatos adapter;
    private List<TimelineItem> mData = new ArrayList<>();
    Uri outputFileUri=null;
    private ImageView imageView;
    Handler handler, handler2, handler3, handler4, handler5, handler1;

    ImageButton goBack;

    MediaPlayer notificacion;

    ArrayList<Integer> arrayBarajado;

    String[] predict= {"Your behavior and feelings often depend upon the moods, your appearance may not correspond to your internal condition. «The book may not be judged by its cover» -this is right about you. You are subject to the quick change of moods, sometimes you are ridden by the self-excluding desires. Your palm testifies to the perfectionism and attention to the details. You are hard to satisfy completely – you will always have some new goal. Your «nicety» with a correct approach may become the feature of character helping to achieve success in life." +
            "\n\nYou are quite sensitive to your appearance especially to the external criticism. You have a good intuition and, most frequently, your first impression from anyone hits the nail on the head. Quite frequently you squander energy, but serious relationships or any profession will allow you becoming very productive and creative." +
            "\n\nYou avoid direct confrontation and prefer harmonious family and business relations. Quite often you are a mediator, peacemaker and diplomatist." +
            "\n\nQuite frequently you mistrust your own powers and the chosen profession. You will be able to achieve much in creative work unless you give too much attention to any critical remark." +
            "\n\nYou are amorous and a little naïve in relations with the other people. You have a sophisticated taste, you are often sentimental, romantic, idealist in life. You’d better avoid unfriendly and critical people and communicate with friendly and happy ones." +
            "\n\nApparently you have quite a critical view of the future. Quite often you lack self-confidence; you are uncertain about success in any undertakings of yours. Due to this you may get stuck at the stage of planning without passing to realization of your projects. Preferring the bird in the hand quite often you get immersed in new or progressive ideas." +
            "\n\nYou languish for love and are ready for anything for love. Middle level of such a phenomenon is expressed in an internal shyness and strong affection to your friends. Extreme case characterizes a person who completely relies on others, depends on them in economic and emotional factors."
            ,
            "You could make a good judge – your sense of justice and tact allows being the peacemaker and solve the toughest situations." +
            "\n\nIt’s hard to you to change your point of view. You are consistent in the issues of morale and religion, although, in some basic issues the people around may characterize such quality as some level of “bullheadedness». Try to view the issue from different points of view in order to understand your friends better." +
            "\n\nYou should pay more attention to the condition of your body, make physical exercises, and it will become more strong and energetic" +
            "\n\nYou don’t like making spontaneous decisions, you always use logics, analyze the situation and solve the problems with sobriety." +
            "\n\nUnfortunately you are not the one for whom the welfare falls from the sky. You will have to be persistent in your undertakings to achieve a success. You will have multiple returns to your start point." +
            "\n\nYou have a streak of adequate care. You are careful in your communication with others, you often inhibit yourself. You are sensible to the miseries of others. You should stand off those who pour their problems out to you." +
            "\n\nYou believe in honesty and you can’t stand liars. If someone deceives you at least once you don’t deal with them anymore." +
            "\n\nYou combine the idealism in love and realism of modern relations in you. You strike a sensible balance in order to achieve harmony in love. If you let yourself love, you depend on your amorous relations. You do everything to achieve what you want." +
            "\n\nYour lines speak about your predisposition to material success. Your close relatives help you as they completely trust your abilities. You are able to cope with any work if the remuneration lives up to your expectations." +
            "\n\nProblematic marriage or relationship is probable in your life, which will bring you disappointments, suffering and non-satisfaction with your partner."
            ,
            "Your palm shows the emotionality quite often developing into the impatience. Such type of hand characterizes passionate and hot-blooded people. You like changes, boring work tires you. You try to find your own way in solving any problem, you don’t like the limits, frames and follow the trodden path." +
            "\n\nYou may be called the big pioneer, generator of ideas, but you can finish what you started only if you don’t lose interest to this. Quite frequently you come up with some idea, but you don’t have enough patience to realize it. In business you need the reliable assistants or partners who will accomplish your bright ideas." +
            "\n\nSurface impression is very important to you. You are quite self-respecting and try to keep everything under control. You may look passionless but the load of duties taken up may cause strong internal nervousness." +
            "\n\nYou not always welcome the criticism even if it is constructive. You like and can give instructions and sensible pieces of advice, but, unfortunately you not always give an ear to your own good instructions. You have a good rapport with kids. If you remark them they will obey. You are self-respecting and hate to ask for help." +
            "\n\nYou are predisposed to the depression. You are your own most merciless and heavy critic. Your excessive prejudice to self-analysis may only strengthen the feeling of inner agitation." +
            "\n\nYou are predisposed to the desire to stand out from the crowd. Such people starve for independence. You’d rather ask for forgiveness than for permit. You are quite easy-going, charismatic and able to controvert, therefore you hate limitations, prohibitions, boundaries. Most probably, you are a perfect inventive lover. The people like you make perfect businesspeople, realizing their aspiration to decide how much they should earn and how much time to spend for this on their own." +
            "\n\nYou have a strong will, helping to keep dignity in any non-standard situations. Sometimes you may be reproached for excessive emotionality and irritation."
            ,
            "You try to control the life of people close to you with good intent, but they may not always like it. Think more about charity than about pure control – such tactics will lead to better results." +
            "\n\nYou are the one who loves life much. You are loved by your friends. Obviously you possess an excessive sexuality and use it subconsciously to present yourself to the people around in the most advantageous light" +
            "\n\nYou strive for balance in life. Family values are important to you, at the same time you are aware of the necessity to professional growth and material independence." +
            "\n\nAt defined period the usual values will be reconsidered. May be you will indulge in serious hobby, you will dramatically change your type of activity or place of residence" +
            "\n\nLines on your hand are characteristic of the artists or for the people who are great on arts. If you have not unlocked your creative talent, it has all chances to get unlocked at any stage of your life." +
            "\n\nRobustness is predominant in your character. For any problem you have multiple practical solutions. You are a perfect watcher and analyst." +
            "\n\nIn personal life you are romantic and choose the partner hoping for the serious relationships. You believe in strong, pure, idealistic love – love without any concealed motives." +
            "\n\nYou understand that you move up the ranks not as quick as you could have done. It’s high time you think what you will spend your time for and find a possibility to develop your skills." +
            "\n\nProblematic marriage or relationship is probable in your life, which will bring you disappointments, suffering and non-satisfaction with your partner."
            ,
            "You have the acute mind, you are smart. Your main drawback (or may be advantage:)) – is the aspiration to manipulate the other people. You have the perfect self-motivation abilities and infect the others with your own example. You are subconsciously predisposed to the leadership, the acknowledgment of the others is very important to you. Your character is very multi-sided – sometimes you may be quick-tempered, sometimes absolutely phlegmatic." +
            "\n\nYou bloom from the complex brain activity and work perfectly under pressure of time. Sometimes it seems that you can’t live without stress and melodramatic situations. You are a great disputant and can engage in a dispute rather “for love of the game” than for the very subject of dispute." +
            "\n\nYou avoid direct confrontation and prefer harmonious family and business relations. Quite often you are a mediator, peacemaker and diplomatist." +
            "\n\nQuite frequently you mistrust your own powers and the chosen profession. You will be able to achieve much in creative work unless you give too much attention to any critical remark." +
            "\n\nYou are amorous and a little naïve in relations with the other people. You have a sophisticated taste, you are often sentimental, romantic, idealist in life. You’d better avoid unfriendly and critical people and communicate with friendly and happy ones." +
            "\n\nApparently you have quite a critical view of the future. Quite often you lack self-confidence; you are uncertain about success in any undertakings of yours. Due to this you may get stuck at the stage of planning without passing to realization of your projects. Preferring the bird in the hand quite often you get immersed in new or progressive ideas."
            ,
            "You languish for love and are ready for anything for love. Middle level of such a phenomenon is expressed in an internal shyness and strong affection to your friends. Extreme case characterizes a person who completely relies on others, depends on them in economic and emotional factors." +
            "\n\nYou could make a good judge – your sense of justice and tact allows being the peacemaker and solve the toughest situations." +
            "\n\nYour aspiration to help the people around is respectable, but you shouldn’t forget about yourself. Your altruism and humanity were repeatedly the reason of your own deprivations and failures." +
            "\n\nYou are destined to face the event which will change your life dramatically – be it divorce or emigration." +
            "\n\nYou don’t like making spontaneous decisions, you always use logics, analyze the situation and solve the problems with sobriety." +
            "\n\nQuite often you take much efforts to fulfill some task, but you forget to demand an appropriate remuneration for your work." +
            "\n\nYou are able to double thinking and possess probably higher than average level of intelligence. On the one hand – thoughtfulness and caution, on the other – moderate sought after risk. Such people always keep the lights on and achieve a success in any sphere." +
            "\n\nYou possess excessive passion. Sometimes love and desire to give yourself to someone overwhelm you so that there is no one to accept them. Therefore, your amorousness may be referred not to the right person and be undivided." +
            "\n\nYour lines speak about your predisposition to material success. Your close relatives help you as they completely trust your abilities. You are able to cope with any work if the remuneration lives up to your expectations." +
            "\n\nApparently, some of your amorous adventures will end scandalously and you will be the initiator."
            ,
            "Such hand characterizes a tiffany nature. You are compassionate and sensitive, you have a good intuition. You value beauty and love surrounding yourselves with beautiful things. You are kind and generous by nature. You friends and family are your life priorities rather than work. Lively imagination, pure taste, developed esthetic perception make you especially prone to the arts and creativity." +
            "\n\nYou avoid direct confrontation and prefer harmonious family and business relations. Quite often you are a mediator, peacemaker and diplomatist." +
            "\n\nQuite frequently you mistrust your own powers and the chosen profession. You will be able to achieve much in creative work unless you give too much attention to any critical remark." +
            "\n\nYou are amorous and a little naïve in relations with the other people. You have a sophisticated taste, you are often sentimental, romantic, idealist in life. You’d better avoid unfriendly and critical people and communicate with friendly and happy ones." +
            "\n\nYou are open and sensitive to new ideas and able to change your beliefs under the influence of new conceptions. The people like you make perfect parents encouraging their kids to the independent decision-making." +
            "\n\nYou languish for love and are ready for anything for love. Middle level of such a phenomenon is expressed in an internal shyness and strong affection to your friends. Extreme case characterizes a person who completely relies on others, depends on them in economic and emotional factors."
            ,
            "You could make a good judge – your sense of justice and tact allows being the peacemaker and solve the toughest situations." +
            "\n\nYou try to control the life of people close to you with good intent, but they may not always like it. Think more about charity than about pure control – such tactics will lead to better results." +
            "\n\nSeemingly you don’t care about your body at all. This is the main reason of your fatigue and reluctance in work. Get yourself into shape and you will have more energy both for professional activity and for your personal life." +
            "\n\nYou don’t like making spontaneous decisions, you always use logics, analyze the situation and solve the problems with sobriety." +
            "\n\nUnfortunately you are not the one for whom the welfare falls from the sky. You will have to be persistent in your undertakings to achieve a success. You will have multiple returns to your start point." +
            "\n\nYou have a streak of adequate care. You are careful in your communication with others, you often inhibit yourself. You are sensible to the miseries of others. You should stand off those who pour their problems out to you." +
            "\n\nYou believe in honesty and you can’t stand liars. If someone deceives you at least once you don’t deal with them anymore." +
            "\n\nIn personal life you are romantic and choose the partner hoping for the serious relationships. You believe in strong, pure, idealistic love – love without any concealed motives." +
            "\n\nDuring your whole life you will feel the support. This can be parents, spouse or even loyal friend." +
            "\n\nProblematic marriage or relationship is probable in your life, which will bring you disappointments, suffering and non-satisfaction with your partner."
            ,
            "You have a streak of adequate care. You are careful in your communication with others, you often inhibit yourself. You are sensible to the miseries of others. You should stand off those who pour their problems out to you." +
            "\n\nYou are able to double thinking and possess probably higher than average level of intelligence. On the one hand – thoughtfulness and caution, on the other – moderate sought after risk. Such people always keep the lights on and achieve a success in any sphere." +
            "\n\nIn your feelings you allow them dominate over the logics and therefore you often renounce your own interests. Beyond all the reasons you are ready to believe in partner’s infallibility. Excessive expectations and naivety may lead to early disappointments." +
            "\n\nIndecisiveness always hinders reaching the success. Caution and suspicion are important in today’s world, but if they are excessive, this will hardly open any new perspectives in front of you." +
            "\n\nApparently, some of your amorous adventures will end scandalously and you will be the initiator."
            ,
            "You have huge reserve of inner energy and are quick off the mark. Sometimes you are nervous, inclined to dream and remote from reality. The people with your type of hand make good actors as you can be quite convincing. Routine is boring for you, you need constant changes and challenges. Sometimes you may be precipitated and too tender-minded. Usually you coddle those whom you love and do not notice the disadvantages of others." +
            "\n\nYou avoid direct confrontation and prefer harmonious family and business relations. Quite often you are a mediator, peacemaker and diplomatist." +
            "\n\nQuite frequently you mistrust your own powers and the chosen profession. You will be able to achieve much in creative work unless you give too much attention to any critical remark." +
            "\n\nYou are amorous and a little naïve in relations with the other people. You have a sophisticated taste, you are often sentimental, romantic, idealist in life. You’d better avoid unfriendly and critical people and communicate with friendly and happy ones." +
            "\n\nApparently you have quite a critical view of the future. Quite often you lack self-confidence; you are uncertain about success in any undertakings of yours. Due to this you may get stuck at the stage of planning without passing to realization of your projects. Preferring the bird in the hand quite often you get immersed in new or progressive ideas." +
            "\n\nYou languish for love and are ready for anything for love. Middle level of such a phenomenon is expressed in an internal shyness and strong affection to your friends. Extreme case characterizes a person who completely relies on others, depends on them in economic and emotional factors." +
            "\n\nYou have a strong will, helping to keep dignity in any non-standard situations. Sometimes you may be reproached for excessive emotionality and irritation." +
            "\n\nYour inner energy reserve may only be envied! Channel it a right direction and the result will not be long in coming." +
            "\n\nProbably you noticed many times that the necessary help comes just in time. It protects you during tough times marked on your lifeline." +
            "\n\nTo overcome the life difficulties you will need the help of your relatives and friends, but don’t reject it – for you moral support of your friends is most important." +
            "\n\nAt defined period the usual values will be reconsidered. May be you will indulge in serious hobby, you will dramatically change your type of activity or place of residence."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //Declaro el SharedPreferences
        SharedPreferences userInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Declaro el editor del SharedPreferences
        SharedPreferences.Editor  editor = userInfo.edit();
        //Obtengo un Arraylist del tamaño total de predicciones restantes con indices aleatorios.
        arrayBarajado = barajar(10);

        //notificacion = MediaPlayer.create(this,R.);


        final Intent intent = getIntent();
        outputFileUri = intent.getParcelableExtra("imageUri");

        //imageView=findViewById(R.id.palmPhoto);
        //imageView.setImageURI(outputFileUri);

        iniRv();
        setupAdapter();

        if(!userInfo.getBoolean("palmPhotoReceived",false)){
            insertItem();
        }else {
            getListData();
        }
        recycler.smoothScrollToPosition(adapter.getItemCount());

    }

    private void insertItem() {
        //Declaro el SharedPreferences
        SharedPreferences userInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Declaro el editor del SharedPreferences
        SharedPreferences.Editor  editor = userInfo.edit();

        //Create palm photo item
        PalmPhotoItem itemPhoto = new PalmPhotoItem(outputFileUri);
        TimelineItem photoTimelineItem = new TimelineItem(itemPhoto);
        mData.add(photoTimelineItem);
        adapter.notifyItemInserted(1);

        //Inicio thread
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextItem itemText1 = new TextItem("Hello! I am Andry. I practice the technique of palm reading.");
                TimelineItem textTimelineItem2 = new TimelineItem(itemText1);
                mData.add(textTimelineItem2);
                adapter.notifyItemInserted(2);

            }
        },1000);

        //Inicio thread
        handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextItem itemText2 = new TextItem("This technique is traditional in my family and I will gladly share the results of my reading with you.");
                TimelineItem textTimelineItem3 = new TimelineItem(itemText2);
                mData.add(textTimelineItem3);
                adapter.notifyItemInserted(3);

            }
        },4000);

        //Inicio thread
        handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextItem itemText3 = new TextItem("Please, wait while I´m getting your results ready.");
                TimelineItem textTimelineItem4 = new TimelineItem(itemText3);
                mData.add(textTimelineItem4);
                adapter.notifyItemInserted(4);

            }
        },5000);

        //Inicio thread
        handler4 = new Handler();
        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextItem itemText4 = new TextItem("Here is what your future holds:");
                TimelineItem textTimelineItem5 = new TimelineItem(itemText4);
                mData.add(textTimelineItem5);
                adapter.notifyItemInserted(5);

            }
        },6000);

        //Inicio thread
        handler5 = new Handler();
        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextItem itemText4 = new TextItem(predict[arrayBarajado.get(0)]);
                guardarIdex(0);
                TimelineItem textTimelineItem5 = new TimelineItem(itemText4);
                mData.add(textTimelineItem5);
                adapter.notifyItemInserted(6);
                recycler.smoothScrollToPosition(adapter.getItemCount());


            }
        },10000);

        editor.putBoolean("palmPhotoReceived",true);
        editor.putString("palmPhotoUri",outputFileUri.toString());
        editor.apply();
        Map<String, ?> allEntries = userInfo.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }
    }

    private void setupAdapter() {

        adapter = new AdapterDatos(this,mData);
        recycler.setAdapter(adapter);
    }

    private void getListData() {

        //Declaro el SharedPreferences
        SharedPreferences userInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        outputFileUri= Uri.parse(userInfo.getString("palmPhotoUri"," "));

        PalmPhotoItem itemPhoto = new PalmPhotoItem(outputFileUri);
        TimelineItem photoTimelineItem = new TimelineItem(itemPhoto);
        mData.add(photoTimelineItem);

        TextItem itemText1 = new TextItem("Hello! I am Andry. I practice the technique of palm reading.");
        TimelineItem textTimelineItem2 = new TimelineItem(itemText1);
        mData.add(textTimelineItem2);

        TextItem itemText2 = new TextItem("This technique is traditional in my family and I will gladly share the results of my reading with you.");
        TimelineItem textTimelineItem3 = new TimelineItem(itemText2);
        mData.add(textTimelineItem3);

        TextItem itemText3 = new TextItem("Please, wait while I´m getting your results ready.");
        TimelineItem textTimelineItem4 = new TimelineItem(itemText3);
        mData.add(textTimelineItem4);

        TextItem itemText4 = new TextItem("Here is what your future holds:");
        TimelineItem textTimelineItem5 = new TimelineItem(itemText4);
        mData.add(textTimelineItem5);

        String[] indexUsado= recuperarIdexUsados();
        TextItem itemText5 = new TextItem(predict[Integer.parseInt(indexUsado[0])]);
        TimelineItem textTimelineItem6 = new TimelineItem(itemText5);
        mData.add(textTimelineItem6);
/**
        //mData = DataSource.getTimelineData();
        List<TimelineItem> mdata = new ArrayList<>();


        //Create text item
        TextItem itemText = new TextItem("Yesterday");
        TimelineItem textTimelineItem = new TimelineItem(itemText);

        //Create palm photo item
        PalmPhotoItem itemPhoto = new PalmPhotoItem(outputFileUri);

        mdata.add(textTimelineItem);*/
    }

    private void iniRv() {

        recycler = findViewById(R.id.reyclerId);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        goBack = (ImageButton) findViewById(R.id.imageButton15);
        goBack.setImageResource(R.drawable.btn_pressed2);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

    }

    public void goBack(){
        /** Creo un nuevo Intent*/
        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
    }

    public void getMyPalmReading(View view){

        //Declaro el SharedPreferences
        SharedPreferences userInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Declaro el editor del SharedPreferences
        SharedPreferences.Editor  editor = userInfo.edit();


        //Primero que nada obtenemos la fecha actual para validar que no se haya pedido un horoscopo el dia de hoy
        Date d = new Date();
        //s contiene la fecha actual en el formato descrito
        CharSequence s = DateFormat.format("MMM d yyyy", d.getTime());
        //Valido si no ha pasado un dia desde la ultima prediccion
        if((userInfo.getString("todayDate"," ")).equals(s)){
            //Inicio thread
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    TextItem itemText1 = new TextItem("I see you're anxious for your next prediction. See you tomorrow!");
                    TimelineItem textTimelineItem2 = new TimelineItem(itemText1);
                    mData.add(textTimelineItem2);
                    adapter.notifyItemInserted(adapter.getItemCount());
                    recycler.smoothScrollToPosition(adapter.getItemCount());

                }
            },1000);
        }else //en caso contrario
        {
            //Inicio thread
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    TextItem itemText1 = new TextItem("Hi! I'm glad to help you!");
                    TimelineItem textTimelineItem2 = new TimelineItem(itemText1);
                    mData.add(textTimelineItem2);
                    adapter.notifyItemInserted(adapter.getItemCount());
                    recycler.smoothScrollToPosition(adapter.getItemCount());

                }
            },1000);
            //Inicio thread
            handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    TextItem itemText2 = new TextItem("Here is what your future holds:");
                    TimelineItem textTimelineItem3 = new TimelineItem(itemText2);
                    mData.add(textTimelineItem3);
                    adapter.notifyItemInserted(adapter.getItemCount());
                    recycler.smoothScrollToPosition(adapter.getItemCount());

                }
            },2000);
            //Inicio thread
            handler5 = new Handler();
            handler5.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int indexNoUsado= checkIndexUsados();
                    TextItem itemText4 = new TextItem(predict[arrayBarajado.get(indexNoUsado)]);
                    TimelineItem textTimelineItem5 = new TimelineItem(itemText4);
                    mData.add(textTimelineItem5);
                    adapter.notifyItemInserted(adapter.getItemCount());
                    recycler.smoothScrollToPosition(adapter.getItemCount());
                    guardarIdex(indexNoUsado);

                }
            },5000);


            editor.putString("todayDate",s.toString());
            editor.apply();

        }

    }

    private void guardarIdex(int indexUsado){
        //Declaro el SharedPreferences
        SharedPreferences userInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //Declaro el editor del SharedPreferences
        SharedPreferences.Editor  editor = userInfo.edit();
        //Rescato los index usados anteriormente
        String indexes= userInfo.getString("indexUsados","");
        //Rescato el index recién usado
        int indexx = arrayBarajado.get(indexUsado);
        //Guardo el index usado
        editor.putString("indexUsados", indexes+indexx + " ");
        editor.apply();

        Map<String, ?> allEntries = userInfo.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
              Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }

    }

    private  String[] recuperarIdexUsados(){
        //Declaro el SharedPreferences
        SharedPreferences userInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String index= userInfo.getString("indexUsados","");

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
        Log.d("index barajados", resultadoA.get(0)+" "+resultadoA.get(1)+" "+resultadoA.get(2)+" "+resultadoA.get(3)+" "+resultadoA.get(4)+" "+resultadoA.get(5)+" "+resultadoA.get(6)+" "+resultadoA.get(7)+" "+resultadoA.get(8)+" "+resultadoA.get(9));
        return  resultadoA;
    }

    /**
     * //Algoritmo para chequear en el log los valores de cada key
     *     Map<String, ?> allEntries = userInfo.getAll();
     *             for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
     *         Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
     */

}
