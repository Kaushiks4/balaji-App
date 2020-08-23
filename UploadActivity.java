package my.app.vehicle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UploadActivity extends AppCompatActivity {
    TextView date, session;
    EditText vehicleNumber,description,driverName;
    String[] mechanics;
    Spinner mechanic;
    ImageView mPhoto, vPhoto,sparePhoto;
    Button submit,vehicle,mech,oldPhoto,newPhoto,submit2;
    String d,s,mechanicName,mimg,time,oimg,nimg,name;
    ArrayList<String> vimgs;
    Uri vuri,mUri,oUri,nUri;
    DatabaseReference data;
    StorageReference storage;
    int k;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Intent i = getIntent();
        d = i.getStringExtra("Date");
        s = i.getStringExtra("Session");
        name = i.getStringExtra("Name");

        date = (TextView) findViewById(R.id.textView4);
        date.setText(d);
        session = (TextView) findViewById(R.id.textView2);
        session.setText(s);
        k = 0;

        data = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance().getReference();

        vehicleNumber = (EditText) findViewById(R.id.vehiclenumber);
        description = (EditText) findViewById(R.id.description);
        mechanic = (Spinner) findViewById(R.id.spinner);
        vehicle = (Button) findViewById(R.id.button4);
        mech = (Button) findViewById(R.id.button5);
        vPhoto = (ImageView) findViewById(R.id.imageView);
        vPhoto.setVisibility(View.GONE);
        mPhoto = (ImageView) findViewById(R.id.imageView2);
        mPhoto.setVisibility(View.GONE);
        oldPhoto = (Button) findViewById(R.id.button8);
        newPhoto = (Button) findViewById(R.id.button7);
        submit2 = (Button) findViewById(R.id.button9);
        submit2.setVisibility(View.GONE);
        sparePhoto = (ImageView) findViewById(R.id.imageView3);
        sparePhoto.setVisibility(View.GONE);
        driverName = (EditText) findViewById(R.id.editTextTextPersonName);
        submit = (Button) findViewById(R.id.button6);
        submit.setVisibility(View.GONE);

        oimg = "";
        nimg = "";

        Date t = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
        time = simpleDateFormat.format(t);

        data.child("Mechanics").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                long n = dataSnapshot.getChildrenCount();
                mechanics = new String[(int) n];
                for(DataSnapshot names: dataSnapshot.getChildren()){
                    mechanics[i] = names.child("Mechanic_name").getValue().toString();
                    i++;
                }

                ArrayAdapter aa = new ArrayAdapter(UploadActivity.this, android.R.layout.simple_spinner_item, mechanics);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mechanic.setAdapter(aa);

                mechanic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mechanicName = parent.getItemAtPosition(position).toString();
                        return;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        vimgs = new ArrayList<>();
        vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vehicleNumber.getText().toString().isEmpty()) {
                    vehicleNumber.setError("Required");
                    vehicleNumber.requestFocus();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                            String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            requestPermissions(permission, 10);
                        } else {
                            openCamera(1);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Not granted", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        oldPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vehicleNumber.getText().toString().isEmpty()) {
                    vehicleNumber.setError("Required");
                    vehicleNumber.requestFocus();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                            String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            requestPermissions(permission, 10);
                        } else {
                            openCamera(2);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Not granted", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        newPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vehicleNumber.getText().toString().isEmpty()) {
                    vehicleNumber.setError("Required");
                    vehicleNumber.requestFocus();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                            String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            requestPermissions(permission, 10);
                        } else {
                            openCamera(3);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Not granted", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        mech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vehicleNumber.getText().toString().isEmpty()) {
                    vehicleNumber.setError("Required");
                    vehicleNumber.requestFocus();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                            String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            requestPermissions(permission, 10);
                        } else {
                            openCamera(0);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Not granted", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }

    private void openCamera(int m) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From this camera");
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
        if(m == 0) {
            mUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            i.putExtra(MediaStore.EXTRA_OUTPUT,mUri);
            startActivityForResult(i,101);
        }
        else if(m == 1){
            vuri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            i.putExtra(MediaStore.EXTRA_OUTPUT,vuri);
            startActivityForResult(i,100);
        }
        else if(m == 2){
            oUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            i.putExtra(MediaStore.EXTRA_OUTPUT,oUri);
            startActivityForResult(i,102);
        }
        else {
            nUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            i.putExtra(MediaStore.EXTRA_OUTPUT,nUri);
            startActivityForResult(i,103);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();
        if(requestCode == 100 && resultCode == RESULT_OK){
            final StorageReference image = storage.child(d + s + vehicleNumber.getText().toString()+ k +"vehicle.jpg");
            image.putFile(vuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    vPhoto.setVisibility(View.VISIBLE);
                    vPhoto.setImageURI(vuri);
                    image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            k++;
                            vimgs.add(uri.toString());
                        }
                    });
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                }
            });
        }
        else if(requestCode == 101 && resultCode == RESULT_OK) {
            final StorageReference image = storage.child(d + s + vehicleNumber.getText().toString()+"mechanic.jpg");
            image.putFile(mUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    mPhoto.setVisibility(View.VISIBLE);
                    mPhoto.setImageURI(mUri);
                    image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            mimg = uri.toString();
                            submit2.setVisibility(View.VISIBLE);
                        }
                    });
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                }
            });
        }
        else if(requestCode == 103 && resultCode == RESULT_OK) {
            final StorageReference image = storage.child(d + s + vehicleNumber.getText().toString()+"newPhoto.jpg");
            image.putFile(nUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    sparePhoto.setVisibility(View.VISIBLE);
                    sparePhoto.setImageURI(nUri);
                    image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            nimg = uri.toString();
                            submit2.setVisibility(View.GONE);
                            submit.setVisibility(View.VISIBLE);
                        }
                    });
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                }
            });
        }
        else if(requestCode == 102 && resultCode == RESULT_OK) {
            final StorageReference image = storage.child(d + s + vehicleNumber.getText().toString()+"oldPhoto.jpg");
            image.putFile(oUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    sparePhoto.setVisibility(View.VISIBLE);
                    sparePhoto.setImageURI(oUri);
                    image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            oimg = uri.toString();
                            submit2.setVisibility(View.GONE);
                            submit.setVisibility(View.VISIBLE);
                        }
                    });
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"No image found",Toast.LENGTH_LONG).show();
        }
    }

    public void submitForm(){
        if(vehicleNumber.getText().toString().trim().isEmpty()){
            vehicleNumber.setError("Required");
            vehicleNumber.requestFocus();
        }
        if(description.getText().toString().trim().isEmpty()){
            description.setError("Required");
            description.requestFocus();
        }
        if(driverName.getText().toString().trim().isEmpty()){
            driverName.setError("Required");
            driverName.requestFocus();
        }
        if(vimgs.size() == 0){
            Toast.makeText(getApplicationContext(),"Vehicle photo not uploaded",Toast.LENGTH_LONG).show();
            return;
        }
        if(mUri == null){
            Toast.makeText(getApplicationContext(),"Mechanic photo not uploaded",Toast.LENGTH_LONG).show();
            return;
        }
        if(!vehicleNumber.getText().toString().trim().isEmpty() && !description.getText().toString().trim().isEmpty() && vimgs != null && mUri != null && !driverName.getText().toString().trim().isEmpty()){
            System.out.println(name);
            DailyDetails dailyDetails = new DailyDetails(vehicleNumber.getText().toString().trim(),description.getText().toString().trim(),mechanicName,mimg,time,name,driverName.getText().toString().trim());
            data.child("Daily").child(d).child(vehicleNumber.getText().toString().toLowerCase()).child(s).setValue(dailyDetails);
            for(int i=0;i<vimgs.size();i++){
                data.child("Daily").child(d).child(vehicleNumber.getText().toString().toLowerCase()).child(s).child("Images").child(String.valueOf(i)).setValue(vimgs.get(i));
            }
            data.child("Daily").child(d).child(vehicleNumber.getText().toString().toLowerCase()).child(s).child("Old_Photo").setValue(oimg);
            data.child("Daily").child(d).child(vehicleNumber.getText().toString().toLowerCase()).child(s).child("New_Photo").setValue(nimg);
            Toast.makeText(getApplicationContext(),"Uploaded Successfully",Toast.LENGTH_LONG).show();
            Intent i = new Intent(UploadActivity.this,HomeActivity.class);
            startActivity(i);
            return;
        }
    }
}