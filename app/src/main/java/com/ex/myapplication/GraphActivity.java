package com.ex.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.Layout;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.io.FileInputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Created by Алексей on 01.11.2017.
 */

public class GraphActivity extends MainActivity {

    LineGraphSeries<DataPoint> series;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        GraphView graph1 = (GraphView) findViewById(R.id.graph1); //создали объект класса GraphView
        series = new LineGraphSeries<DataPoint>();
        graph1.addSeries(series);

        graph();

    }

    public  void graph() {
        try {
            File path = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "example.xls");
          FileInputStream in = new FileInputStream(path);

          Workbook wb = Workbook.getWorkbook(in);
          Sheet s1 = wb.getSheet(0); //Получаем первую страницу
          Sheet s2 = wb.getSheet(1); //Получаем вторую страницу

          DataPoint[] points = new DataPoint[s2.getRows()]; //Массив точек графика

          //В цикле читаем значения пары ячеек и строим очередную точку
          for (int i=1; i<s2.getRows(); i++)   //начинаем с 1, т.к. шапку не считаем
          {
              Short x = Short.valueOf(s2.getCell(0, i).getContents()); //Получили строки из листа и преобразовали их в числа
              Short y = Short.valueOf(s2.getCell(1, i).getContents());
              points[i] = new DataPoint(x, y);
              series.appendData(points[i], true, 125);

          }

          in.close();

        }
        catch (Exception var11) {
            Toast toast = Toast.makeText(getApplicationContext(), "Ошибка " + var11.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }


    }
}
