package id.mentoring.monitoringhidroponik.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import id.mentoring.monitoringhidroponik.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEtUsername;
    private EditText mEtPassword;
    private EditText mEtYourName;
    private Button mBtnRegister;
    private TextView mTvLogin;
    private String mUsername, mPassword, mYourName;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private CheckBox mCbShowPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerr);
        initView();
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Loading.....");
        progressDialog.setMessage("Silahkan Tunggu!");
        progressDialog.setCancelable(false);

        mBtnRegister.setOnClickListener(this);
        mTvLogin.setOnClickListener(this);
        mCbShowPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegister:
                mUsername = mEtUsername.getText().toString();
                mPassword = mEtPassword.getText().toString();
                mYourName = mEtYourName.getText().toString();

                if (mUsername.isEmpty()){
                    mEtUsername.setError("Username belum terisi !");
                }
                else if (mPassword.isEmpty()){
                    mEtPassword.setError("Password belum terisi !");
                }
                else if (mYourName.isEmpty()){
                    mEtYourName.setError("Nama belum terisi !");
                }
                else {
                    register(mUsername, mPassword, mYourName);
                }
                break;

            case R.id.tvLoginHere:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.CbRegisterShowP:
                if (mCbShowPassword.isChecked()){
                    mEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mEtPassword.setTransformationMethod((PasswordTransformationMethod.getInstance()));
                }
        }
    }

    private void register(String username, String password, String yourName) {
        //progressDialog.show();
        mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult()!=null){
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    if (firebaseUser!=null){
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setDisplayName(yourName)
                                .build();
                        firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                reload();
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Register gagal", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    private void reload() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    private void initView() {
        mEtUsername = findViewById(R.id.etRegisterUsername);
        mEtPassword = findViewById(R.id.etRegisterPassword);
        mEtYourName = findViewById(R.id.etRegisterName);
        mBtnRegister = findViewById(R.id.btnRegister);
        mTvLogin = findViewById(R.id.tvLoginHere);
        mAuth = FirebaseAuth.getInstance();
        mCbShowPassword = findViewById(R.id.CbRegisterShowP);
    }

}