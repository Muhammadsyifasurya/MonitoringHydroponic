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
import com.google.firebase.auth.FirebaseUser;

import id.mentoring.monitoringhidroponik.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private TextView mTvCreateAccount;
    private String mUsername, mPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private CheckBox mCbShowPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Loading.....");
        progressDialog.setMessage("Silahkan Tunggu!");
        progressDialog.setCancelable(false);

        mBtnLogin.setOnClickListener(this);
        mTvCreateAccount.setOnClickListener(this);
        mCbShowPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                mUsername = mEtUsername.getText().toString();
                mPassword = mEtPassword.getText().toString();

                if(mUsername.isEmpty()){
                    mEtUsername.setError("Mohon masukan username!");
                }
                else if(mPassword.isEmpty()){
                    mEtPassword.setError("Password belum terisi");
                }
                else {
                    login(mUsername, mPassword);
                }
                break;

            case R.id.tvCreateAccount:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                finish();

                break;
            case R.id.CbShowPassword:
                if (mCbShowPassword.isChecked()){
                    mEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mEtPassword.setTransformationMethod((PasswordTransformationMethod.getInstance()));
                }

                break;
        }
    }

    private void login(String mUsername, String mPassword) {
        mAuth.signInWithEmailAndPassword(mUsername, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult()!=null){
                    if (task.getResult().getUser()!=null){
                        reload();
                    } else {
                        Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Username atau Password salah", Toast.LENGTH_SHORT).show();
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
        mEtUsername = findViewById(R.id.etUsername);
        mEtPassword = findViewById(R.id.etPassword);
        mBtnLogin = findViewById(R.id.btnLogin);
        mTvCreateAccount = findViewById(R.id.tvCreateAccount);
        mAuth = FirebaseAuth.getInstance();
        mCbShowPassword = findViewById(R.id.CbShowPassword);
    }


}