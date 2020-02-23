package com.team6.rideshare.Activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.team6.rideshare.R;
import com.team6.rideshare.data.Driver;
import com.team6.rideshare.data.Passenger;
import com.team6.rideshare.network.DriverRoute;
import com.team6.rideshare.network.PassengerAssignment;
import com.team6.rideshare.network.RideShareREST;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.Collections;
import java.util.Comparator;

@EActivity
public class SeeAssignmentActivity extends AppCompatActivity {

    @RestService
    RideShareREST rideShareREST;

    private boolean isDriver=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_assignment);
    }

    public void radioChecked(View view){
        switch (view.getId()){
            case R.id.driver_radio:
                isDriver = true;
            case R.id.passenger_radio:
                isDriver = false;
        }

    }

    private void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    @UiThread
    void showDriverStuff(DriverRoute route) {
        Collections.sort(route.passengers, new Comparator<DriverRoute.RouteStop>() {
            @Override
            public int compare(DriverRoute.RouteStop stop1, DriverRoute.RouteStop stop2) {
                return stop1.order - stop2.order;
            }
        });

    }

    @UiThread
    void showPassengerStuff(PassengerAssignment passenger){
        TableLayout tableLayout = (TableLayout) findViewById(R.id.result_table);
        TableRow tbRow1 = new TableRow(this);
        TextView driverTextView = (TextView) LayoutInflater.from(this).inflate(R.layout.route_stop_textview, tbRow1, false);
        driverTextView.setText(R.string.driver);
        tbRow1.addView(driverTextView);
        TextView nameTextView = (TextView) LayoutInflater.from(this).inflate(R.layout.route_stop_textview, tbRow1, false);
        nameTextView.setText(passenger.driver);
        tbRow1.addView(nameTextView);
        tableLayout.addView(tbRow1);

        TableRow tbRow2 = new TableRow(this);
        TextView timeLabelTextView = (TextView) LayoutInflater.from(this).inflate(R.layout.route_stop_textview, tbRow2, false);
        timeLabelTextView.setText(R.string.pick_up);
        tbRow2.addView(timeLabelTextView);
        TextView timeTextView = (TextView) LayoutInflater.from(this).inflate(R.layout.route_stop_textview, tbRow2, false);
        String timeString = getResources().getStringArray(R.array.driver_leave_times)[passenger.time];
        timeTextView.setText(timeString);
        tbRow2.addView(timeTextView);
        tableLayout.addView(tbRow2);
    }

    @Background
    @Click(R.id.see_assignment)
    public void handleClick(){
        EditText editName = (EditText) findViewById(R.id.name);
        String name = editName.getText().toString();
        final Activity act = this;
        if(name.equals("")) {
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast(act,"Please enter your name!" );
                }
            });
            return;
        }
        if(isDriver){

        } else {
            showPassengerStuff(rideShareREST.getPassengerDriver(name));
        }

    }
}