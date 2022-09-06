package id.mentoring.monitoringhidroponik.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import id.mentoring.monitoringhidroponik.R;

public class UserActivity extends AppCompatActivity {
    private MaterialButton mBtnLogut;
    private FirebaseUser firebaseUser;
    private TextView mTvYourname;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        initView();

        if(firebaseUser!=null){
            mTvYourname.setText(firebaseUser.getDisplayName());
        } else {
            mTvYourname.setText("Nama tidak terinput");
        }
        mBtnLogut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

    }

    private void initView() {
        mBtnLogut = findViewById(R.id.btn_Logout);
        mTvYourname =findViewById(R.id.tv_Yourname);
    }
}