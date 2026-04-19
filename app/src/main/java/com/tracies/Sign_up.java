package com.tracies;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Sign_up extends AppCompatActivity {

    Button SignUp;
    ImageView apple_sign, close,Login_Gmail;
    EditText edit_name, edit_email, edit_phone,edit_adress;
    TextInputEditText edit_password,confirm_password;
    TextView tosign_up;
    FirebaseAuth firebaseAuth;
    users userModel;


    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            Intent intent = new Intent(getApplicationContext(),Bottom_nav.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // form fields
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_email = (EditText) findViewById(R.id.edit_email);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        edit_password = (TextInputEditText) findViewById(R.id.edit_password);
        confirm_password = (TextInputEditText) findViewById(R.id.confirm_password);
        Login_Gmail = (ImageView) findViewById(R.id.Login_Gmail);
        tosign_up = (TextView) findViewById(R.id.tosign_up);
        SignUp = (Button) findViewById(R.id.Sign_up);
        close = (ImageView) findViewById(R.id.close);

        userModel = new users();

        createRequest();


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_up.this, MainActivity.class);
                startActivity(intent);
            }
        });

        tosign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_up.this, Log_in.class);
                startActivity(intent);
            }
        });



        Login_Gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();

            }
        });




        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_name.getText().toString().trim();
                String email=edit_email.getText().toString().trim();
                String phoneNumber = edit_phone.getText().toString().trim();
                String password=edit_password.getText().toString().trim();
                String confirmPass = confirm_password.getText().toString().trim();


                if (name.isEmpty()){
                    edit_name.setError("Enter Name");
                    edit_name.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    edit_email.setError("Enter Email");
                    edit_email.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    edit_password.setError("Enter Password");
                    edit_password.requestFocus();
                    return;
                }
                if (phoneNumber.isEmpty()) {
                    edit_phone.setError("Enter Phone");
                    edit_phone.requestFocus();
                    return;
                }


                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    edit_email.setError("Please Provide Valid Email");
                    edit_email.requestFocus();
                    return;
                }

                if(password.equals(confirmPass)){
                    mAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(Sign_up.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        users user = new users(name,email,phoneNumber);

                                        FirebaseDatabase.getInstance().getReference("users")
                                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()){
                                                    Toast.makeText(Sign_up.this,"User has been registered successfully!",Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(Sign_up.this,Bottom_nav.class));
                                                }
                                                else{
                                                    Toast.makeText(Sign_up.this,"Failed to Register ! Try again!",Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(Sign_up.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }else{
                    Toast.makeText(Sign_up.this, "You must fill in all the fields",
                            Toast.LENGTH_SHORT).show();
                    if(!password.equals(confirmPass)){
                        Toast.makeText(Sign_up.this, "Confirm pass don't match password",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }



    private void createRequest(){
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()){
                                                startActivity(new Intent(Sign_up.this,Bottom_nav.class));
                                                finish();
                                            }else {
                                                userModel.setName(user.getDisplayName());
                                                Log.e("userDisplayName",""+user.getDisplayName());
                                                userModel.setEmail(user.getEmail());
                                                if (user.getPhoneNumber() != null) {
                                                    userModel.setPhone(user.getPhoneNumber());
                                                }
                                                FirebaseDatabase.getInstance().getReference("users")
                                                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                                        .setValue(userModel)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(Sign_up.this, "User has been registered successfully!", Toast.LENGTH_SHORT).show();
                                                                    startActivity(new Intent(Sign_up.this, Bottom_nav.class));
                                                                } else {
                                                                    Toast.makeText(Sign_up.this, "Failed to Register ! Try again!", Toast.LENGTH_SHORT).show();

                                                                }
                                                            }
                                                        });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });



                        }
                    }
                });
    }
}