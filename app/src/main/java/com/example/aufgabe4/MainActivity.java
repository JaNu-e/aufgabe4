package com.example.aufgabe4;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String pfad;
    File files[];

    ArrayList<ID3v2> tagList; //neu
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onSuchen(View v){
        EditText etPfad = (EditText) findViewById(R.id.idPfad);
        pfad= etPfad.getText().toString();
        File dir = new File(pfad);
        files = dir.listFiles();
        if(files == null)
        {
            Toast toto= Toast.makeText(this, pfad,Toast.LENGTH_LONG);
            toto.show();//message
            return;
        }
        String datArray[] = new String[files.length];
        tagList = new ArrayList<ID3v2>(files.length);
        for(int i=0; i < files.length; i++) {
            datArray[i] = files[i].getName();
            if(files[i].isDirectory())
            {datArray[i] += " - DIR";
           continue;
            }else {


                Mp3File song = null;
                try {
                    song = new Mp3File(pfad + "/" + files[i].getName());
                    if (song.hasId3v2Tag()) {
                       ID3v2 myTag = song.getId3v2Tag();
                        String Album = myTag.getAlbum();
                        datArray[i] += Album + ": ";
                        //ihre variante
                        ID3v2 songtag = song.getId3v2Tag();
                        tagList.add(songtag);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (UnsupportedTagException e) {
                    e.printStackTrace();
                } catch (InvalidDataException e) {
                    e.printStackTrace();
                }
            }

        }


        MusikAdapter adapter= new MusikAdapter(this,tagList);
       //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datArray);
        ((ListView)findViewById(R.id.idTvListe)).setAdapter(adapter);

    //media player erstellen der auch abspielt yay.

    }
}