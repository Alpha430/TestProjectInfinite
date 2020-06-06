package com.example.testprojectinfinite;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testprojectinfinite.CustaAdapter.CustomGrid;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    List<Integer> dataLis;

    GridView gridView;
    CustomGrid customGrid;
    TextView txtselNo,txtPrevNumber;
    final int min = 1;
    final int max = 90;
    Integer[] namesArr;

    public List<Integer> selList=new ArrayList<>();

    TextView txtNewGame;
    public static String LastSelectedNumber= "__";
    Boolean startPrev =false;
    ArrayList<String> saveArr;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView=findViewById(R.id.grid);
        txtselNo=findViewById(R.id.txtselNo);
        txtPrevNumber=findViewById(R.id.txtPrevNumber);
        txtNewGame=findViewById(R.id.txtNewGame);
        saveArr=new ArrayList<>();
         sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);


        //Collections.shuffle(dataLis);
        refreshDta();
        readData();





        customGrid=new CustomGrid(MainActivity.this,dataLis);
        gridView.setAdapter(customGrid);







        txtselNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if(selList.size()<=0){
                        Toast.makeText(MainActivity.this,"Winner Winner Chicken Dinner",Toast.LENGTH_LONG).show();
                        txtNewGame.setVisibility(View.VISIBLE);
                    }else {
                        if(startPrev){
                            txtPrevNumber.setText(LastSelectedNumber);
                        }
                        namesArr = selList.toArray(new Integer[selList.size()]);
                        Integer ik = namesArr[new Random().nextInt(namesArr.length)];
                        txtselNo.setText(ik.toString());
                        LastSelectedNumber=ik.toString();
                        selList.remove(ik);
                        startPrev =true;

                        int position = dataLis.indexOf(ik);

                        customGrid.setSelectedPosition(position);

                        customGrid.notifyDataSetChanged();

                        //save data
                        saveArr.add(String.valueOf(position));
                        Gson gson = new Gson();
                        String json = gson.toJson(saveArr);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Set",json );
                        editor.commit();

                    }
                }catch (Exception e){
                    e.getMessage();
                }


            }
        });


        txtNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNewGame.setVisibility(View.GONE);
                refreshDta();

            }
        });








    }


    private void readData() {


        Gson gson = new Gson();
        String json = sharedPreferences.getString("Set", "");
        if (json.isEmpty()) {
            Toast.makeText(MainActivity.this, "There is something error", Toast.LENGTH_LONG).show();
        } else {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            saveArr = gson.fromJson(json, type);
        }
        if(saveArr.size()>0){

            for(String data:saveArr) {
                int position = Integer.valueOf(data);
                customGrid.setSelectedPosition(position);
                customGrid.notifyDataSetChanged();
            }

        }else{
            refreshDta();
        }
    }


        public void refreshDta () {
            txtNewGame.setVisibility(View.GONE);
            txtselNo.setText("__");
            startPrev = false;
            txtPrevNumber.setText("__");
            dataLis = new ArrayList<>();
            for (int i = 1; i <= 90; i++) {
                dataLis.add(i);
                selList.add(i);
            }

            customGrid = new CustomGrid(MainActivity.this, dataLis);
            gridView.setAdapter(customGrid);
        }


}
