package my.app.vehicle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText username,password;
    Button signin;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signin = (Button) findViewById(R.id.signin);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        int flag = 0;
                        for(DataSnapshot names: dataSnapshot.getChildren()) {
                            String name = names.child("username").getValue().toString();
                            if (name.equals(username.getText().toString().trim())) {
                                String pwd = names.child("password").getValue().toString();
                                if (pwd.equals(password.getText().toString().trim())) {
                                    flag = 1;
                                    Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                                    i.putExtra("Name", name);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                            if(flag == 0){
                                Toast.makeText(getApplicationContext(), "Invalid Username", Toast.LENGTH_SHORT).show();
                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(),"Internet connection error!",Toast.LENGTH_LONG).show();
                        return;
                    }
                });
            }
        });
    }
}