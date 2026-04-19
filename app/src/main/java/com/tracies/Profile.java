package com.tracies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    ImageView userDP,editDP,sign_out;
    TextView Uemail,paddress;
    EditText Uname,Uphone;
    DatabaseReference mdatabase;
    StorageReference storageReference;
    Button update;
    Uri imageUri;
    Bitmap bitmap;
    String UserID = "";
    private static final String USER = "users";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserID = user.getUid();

        userDP = (ImageView) findViewById(R.id.userDP);
        editDP = (ImageView) findViewById(R.id.editDP);
        paddress = (TextView) findViewById(R.id.p_adress);
        Uname = (EditText) findViewById(R.id.p_name);
        sign_out = (ImageView) findViewById(R.id.sign_out);
        Uphone = (EditText) findViewById(R.id.p_phone);
        update = (Button) findViewById(R.id.update);
        Uemail = (TextView) findViewById(R.id.email);

        storageReference = FirebaseStorage.getInstance().getReference();

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),Log_in.class);
                startActivity(intent);
            }
        });
        mdatabase = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getUid());

        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){

                        users user = snapshot.getValue(users.class);
                            String name = user.name;
                            String phone = user.phoneNumber;
                            String email = user.email;
                            String address = user.address;
                            Uname.setText(name);
                            Uphone.setText(phone);
                            Uemail.setText(email);
                            paddress.setText(address);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Something wrong", Toast.LENGTH_SHORT).show();

            }
        });



        editDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("khan123","called");

                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                pickPhoto.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(pickPhoto,"Please Select File"),101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                           permissionToken.continuePermissionRequest();
                             }
                        }).check();


            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null){
                uploadUserImage();
                }
                mdatabase.child("name").setValue(Uname.getEditableText().toString());

                mdatabase.child("phoneNumber").setValue(Uphone.getEditableText().toString());

                mdatabase.child("address").setValue(paddress.getEditableText().toString());


            }
        });





    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 101 && resultCode == RESULT_OK) {

                    imageUri = data.getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        userDP.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }


            }
        }



    public void uploadUserImage() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Updating");
        pd.show();

        Log.e("cleed","called");


        final StorageReference uploader = storageReference.child("image/"+"img"+System.currentTimeMillis());
        uploader.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final Map<String,Object> map = new HashMap<>();
                                map.put("imageUrl",uri.toString());
//                                map.put("name",Uname.getText().toString());
//                                map.put("phone",Uphone.getText().toString());

                                mdatabase.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists())
                                            mdatabase.updateChildren(map);
                                        else
                                            mdatabase.setValue(map);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                pd.dismiss();
                                Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                float percent = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                pd.setMessage("Uploaded :"+(int)percent+"%");
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("imageUrl").exists()){

                    Glide.with(getApplicationContext()).load(snapshot.child("imageUrl").getValue().toString()).into(userDP);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}