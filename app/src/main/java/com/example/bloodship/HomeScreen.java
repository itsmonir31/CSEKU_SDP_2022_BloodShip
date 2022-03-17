package com.example.bloodship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    CardView cvbloodSearch, cvbloodRequest, cvbloodRequested, cvourPride, cvBloodteam, cvaboutClub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        //hooks
        cvbloodSearch = findViewById(R.id.cvbloodSearch);
        cvbloodRequest = findViewById(R.id.cvBloodRequest);
        cvbloodRequested = findViewById(R.id.cvRequestedBlood);
        cvourPride = findViewById(R.id.cvGuidelines);
        cvBloodteam = findViewById(R.id.cvBloodTeam);
        cvaboutClub = findViewById(R.id.cvAboutClub);


        //set on click listener
        cvbloodSearch.setOnClickListener(this);
        cvbloodRequest.setOnClickListener(this);
        cvbloodRequested.setOnClickListener(this);
        cvourPride.setOnClickListener(this);
        cvBloodteam.setOnClickListener(this);
        cvaboutClub.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.cvbloodSearch:
                Intent notification = new Intent(this, MainActivity.class);
                startActivity(notification);
                break;

            case R.id.cvBloodRequest:
                Intent requestBlood = new Intent(this, RequestBlood.class);
                startActivity(requestBlood);
                break;

            case R.id.cvRequestedBlood:
                Intent duhr = new Intent(this, RequestedBloodList.class);
                startActivity(duhr);
                break;

            case R.id.cvGuidelines:
                Intent asr = new Intent(this, Guidelines.class);
                startActivity(asr);
                break;

//            case R.id.cvBloodTeam:
//                Intent requested = new Intent(this, RequestedBloodList.class);
//                startActivity(requested);
//                break;

            case R.id.cvAboutClub:
                Intent club = new Intent(this, AboutApps.class);
                startActivity(club);
                break;
        }
    }

    public void goProfile(View view) {
        startActivity(new Intent(getApplicationContext(), Profile.class));
    }
}