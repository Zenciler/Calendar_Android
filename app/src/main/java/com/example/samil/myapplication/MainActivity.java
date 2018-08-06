package com.example.samil.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button appointment;
    private MultiAutoCompleteTextView description;
    private EditText title;
    private EditText date;

    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);

        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        //Set an event for Teachers' Professional Day 2016 which is 21st of October




        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();
                dateClicked.setYear(dateClicked.getYear()+1900);
                dateClicked.setMonth(dateClicked.getMonth()+1);
               // Date b=new Date(2018,8,6,0,0);
                List<Event> events=compactCalendar.getEvents(dateClicked);
                System.out.println(dateClicked.getYear()+"  "+dateClicked.getMonth());
                System.out.println(dateClicked);
                System.out.println(events.size()+"   "+dateClicked.getTime());

                if (events.size()>0 && dateClicked.getTime()==events.get(0).getTimeInMillis()) {
                    String data=((String)events.get(0).getData());
                    String minute=data.substring(0,data.indexOf("\n"));
                    String a[]=minute.split("/");
                    dateClicked.setHours(Integer.parseInt(a[0]));
                    dateClicked.setMinutes(Integer.parseInt(a[01]));
                    dateClicked.setYear(dateClicked.getYear()-1900);
                    dateClicked.setMonth(dateClicked.getMonth()-1);
                    data=data.substring(data.indexOf("\n")+1);

                    Toast.makeText(context,dateClicked.toString()+"\n"+data , Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(context, "No Events Planned for that day", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
        title=(EditText)findViewById(R.id.editText6);
        description=(MultiAutoCompleteTextView)findViewById(R.id.multiAutoCompleteTextView2);
        date=(EditText)findViewById(R.id.editText8);

        appointment = (Button) findViewById(R.id.button4);
        appointment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
            String titlesave=title.getText().toString();
            String descriptionsave=description.getText().toString();
            String datesave=date.getText().toString();
            title.setText("");
            description.setText("");
            date.setText("");
            System.out.println(titlesave+descriptionsave+datesave);
            String times[]=datesave.split("/");
            System.out.println(Integer.parseInt(times[0]));
            Date time=new Date(Integer.parseInt(times[0]),Integer.parseInt(times[1]),Integer.parseInt(times[2]),0,0);
            System.out.println(time.getTime());
            System.out.println(time.getYear()+"  "+time.getMonth());
            Event ev1 = new Event(Color.RED,time.getTime(),Integer.parseInt(times[3])+"/"+Integer.parseInt(times[4])+"\n"+titlesave+"\n"+descriptionsave);
            compactCalendar.addEvent(ev1);



            }
        });

    }
}