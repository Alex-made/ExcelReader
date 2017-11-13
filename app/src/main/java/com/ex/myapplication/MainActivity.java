package com.ex.myapplication;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public Spinner spinner1;
    public Spinner spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "Ошибка в on create");

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);


        Button Count = (Button) findViewById(R.id.button); //Рассчитать
        Count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finds();
            }
        });

        Button Graph = (Button) findViewById(R.id.button2); //вывести график
        Graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GraphActivity.class);
                startActivity(intent);
            }
        });

        //Флешка читаема?
        String s = Environment.getExternalStorageState();
        Toast toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        toast.show();


        //Заполнить спиннеры и вернуть значения
        fill();

    }

    public  void fill() //Получение данных из excel для выпадающего списка
    {

        try {

            File path = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "example.xls");

            FileInputStream in = new FileInputStream(path);

            Workbook wb = Workbook.getWorkbook(in);
            Sheet s1 = wb.getSheet(0); //Получаем первую страницу
            Sheet s2 = wb.getSheet(1); //Получаем вторую страницу


            //Данные для выпадающих списков
            final String[] spindata1 = new String[s1.getRows() - 1];
            final String[] spindata2 = new String[s2.getRows() - 1];


            for (int i = 1; i < s1.getRows(); i++) //заполняем первый массив для второго спиннера
            {
                spindata1[i - 1] = s1.getCell(0, i).getContents();
            }

            for (int i = 1; i < s2.getRows(); i++) //заполняем второй массив для второго спиннера
            {
                spindata2[i - 1] = s2.getCell(0, i).getContents();
            }

            //Спиннеры
            //Адаптер
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spindata1);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
            spinner1.setAdapter(adapter1);

            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spindata2);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
            spinner2.setAdapter(adapter2);


            in.close();


        }

        catch (Exception var9) {
            Toast toast = Toast.makeText(getApplicationContext(), "Ошибка " + var9.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    public void finds() {

        try {

            //String[] dd = fill();  //получаем выбранные значения, чтобы найти соответствия им
            TextView res = (TextView) findViewById(R.id.textView);
            String selected1 = spinner1.getSelectedItem().toString();
            String selected2 = spinner2.getSelectedItem().toString();

            //String e1 = edt1.getText().toString();
            //String e2 = edt2.getText().toString();


            //Строчки не пусты?
            //if (e1.length() == 0 || e2.length() == 0) {
               // Toast toast = Toast.makeText(getApplicationContext(), "Введите значение!", Toast.LENGTH_SHORT);
               // toast.show();

            //}
            // else
            //{
            File path = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "example.xls");
                FileInputStream in = new FileInputStream(path);
                Workbook wb = Workbook.getWorkbook(in);
                Sheet s1 = wb.getSheet(0); //Получаем первую страницу
                Sheet s2 = wb.getSheet(1); //Получаем вторую страницу
                Cell z1 = null; //Ячейка 1 страницы
                Cell z2 = null; //Ячейка 2 страницы

                String answer1 = "";
                String answer2 = "";

            /* spinner1=Spinner.OnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int selectedPosition, long selectedId) {
                    dd[0]= (String) adapterView.getItemAtPosition(selectedPosition);


                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {}
            });*/
            for (int i = 1; i <s1.getRows(); i++) {
                    z1 = s1.getCell(0, i); //берем первую ячейку

                    if (z1.getContents().equals(selected1)) {
                        answer1 = s1.getCell(1, i).getContents();  //если она равна запросу, то НОВОЙ ячейке присваиваем значение ответа на запрос
                    }
                }


            for (int i = 1; i < s2.getRows(); i++) {
                    z2 = s2.getCell(0, i);
                    if (z2.getContents().equals(selected2)) {
                        answer2 = s2.getCell(1, i).getContents();
                    }
            }

            //Результат расчета в textView  "res"
            res.setText("Вес = " + answer1 + " " + "Вес троса= " + answer2);

            in.close(); //закрывем файл



        }

        catch(Exception var10){
            Toast toast = Toast.makeText(getApplicationContext(), "Ошибка " + var10.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }



    }


}



