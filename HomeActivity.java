package my.app.vehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {
    Button morning, afternoon, evening;
    DatabaseReference data;
    String date,session,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent i = getIntent();
        name = i.getStringExtra("Name");

        morning = (Button) findViewById(R.id.button);
        afternoon = (Button) findViewById(R.id.button2);
        evening = (Button) findViewById(R.id.button3);

        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        date = simpleDateFormat.format(d);

        morning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session = "Morning";
                Intent i = new Intent(getApplicationContext(),UploadActivity.class);
                i.putExtra("Date",date);
                i.putExtra("Session", session);
                i.putExtra("Name",name);
                startActivity(i);
            }
        });

        afternoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session = "Afternoon";
                Intent i = new Intent(getApplicationContext(),UploadActivity.class);
                i.putExtra("Date",date);
                i.putExtra("Session", session);
                i.putExtra("Name",name);
                startActivity(i);
            }
        });
        evening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session = "Evening";
                Intent i = new Intent(getApplicationContext(),UploadActivity.class);
                i.putExtra("Date",date);
                i.putExtra("Session", session);
                i.putExtra("Name",name);
                startActivity(i);
            }
        });
    }
}