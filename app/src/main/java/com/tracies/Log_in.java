package com.tracies;

import static android.content.ContentValues.TAG;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class Log_in extends AppCompatActivity {


    EditText password_feild, login_email;
    Button login1;
    ImageView gmail_login,apple_login,cross;
    TextView tosign;
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
        setContentView(R.layout.activity_log_in);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        tosign = (TextView) findViewById(R.id.tosign);
        cross = (ImageView) findViewById(R.id.cross);
        gmail_login = (ImageView) findViewById(R.id.gmail_login);

        login1 = (Button) findViewById(R.id.login_1);
        login_email = (EditText) findViewById(R.id.login_email);
        password_feild = (EditText) findViewById(R.id.password_feild);

        userModel = new users();

        createRequest();


        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Log_in.this,MainActivity.class);
                startActivity(intent);
            }
        });

        gmail_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();
            }

        });

        tosign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Log_in.this,Sign_up.class);
                startActivity(intent);
            }
        });



        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= login_email.getText().toString();
                String password=password_feild.getText().toString();
                if(!email.isEmpty() && !password.isEmpty() ){
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Log_in.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("TAG", "signInWithEmail:success");
                                        FirebaseUser currentUser = mAuth.getCurrentUser();
                                        Intent intent = new Intent(Log_in.this, Bottom_nav.class);
                                        startActivity(intent);
                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(Log_in.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });
                }else{
                    Toast.makeText(Log_in.this, "You must fill in all the fields",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });




    }


    private void createRequest(){
        // Configure Google Sign In
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
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
                Log.e("signin0015","why");
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("GOOGLE_SIGNIN", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.d("GOOGLE_SIGNIN", "firebaseAuthWithGoogleException:" + e.getMessage());
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
                                              startActivity(new Intent(Log_in.this,Bottom_nav.class));
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
                                                                    Toast.makeText(Log_in.this, "User has been registered successfully!", Toast.LENGTH_SHORT).show();
                                                                    startActivity(new Intent(Log_in.this, Bottom_nav.class));
                                                                } else {
                                                                    Toast.makeText(Log_in.this, "Failed to Register ! Try again!", Toast.LENGTH_SHORT).show();

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