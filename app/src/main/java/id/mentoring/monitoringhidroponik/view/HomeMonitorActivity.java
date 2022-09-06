package id.mentoring.monitoringhidroponik.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import id.mentoring.monitoringhidroponik.R;

public class HomeMonitorActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvTdsValue;
    private TextView mTvPhValue;
    private TextView mTvNotifikasi;
    private MaterialButton mBtnAddTds;
    private MaterialButton mBtnAddAsam;
    private MaterialButton mBtnAddBasa;
    private String mTdsValue;
    private String mPhValue;
    private Switch Otomatis;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_monitor);
        initView();

        Otomatis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                if(Otomatis.isChecked() == true){
                    Toast.makeText(HomeMonitorActivity.this, "Penyiraman Manual", Toast.LENGTH_SHORT).show();
                    mBtnAddAsam.setEnabled(true);
                    mBtnAddTds.setEnabled(true);
                    mBtnAddBasa.setEnabled(true);
                    mBtnAddTds.setOnClickListener(HomeMonitorActivity.this);
                    mBtnAddAsam.setOnClickListener(HomeMonitorActivity.this);
                    mBtnAddBasa.setOnClickListener(HomeMonitorActivity.this);
                    Otomatis.setText("Manual");
                    DatabaseReference perintah = database.getReference("Data/Perintah");
                    perintah.setValue(1);
                } else {
                    mBtnAddAsam.setEnabled(false);
                    mBtnAddTds.setEnabled(false);
                    mBtnAddBasa.setEnabled(false);
                    Toast.makeText(HomeMonitorActivity.this, "Penyiraman Otomatis", Toast.LENGTH_SHORT).show();
                    Otomatis.setText("Otomatis");
                    DatabaseReference perintah = database.getReference("Data/Perintah");
                    perintah.setValue(0);
                }
            }
        });

        initPresenter();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        switch (view.getId()){
            case R.id.Btn_addAsam:
                DatabaseReference PumpAsam = database.getReference("Relay/Pompa1");
                String BtnAsamTxt = mBtnAddAsam.getText().toString();
                if(BtnAsamTxt.equals("Add Asam")){
                    mBtnAddAsam.setText("Stop");
                    PumpAsam.setValue(1);

                } else {
                    PumpAsam.setValue(0);
                    mBtnAddAsam.setText("Add Asam"); }
                break;


            case R.id.Btn_addBasa:
                DatabaseReference PumpBasa = database.getReference("Relay/Pompa2");
                String BtnBasaTxt = mBtnAddBasa.getText().toString();
                if(BtnBasaTxt.equals("Add Basa")){
                    mBtnAddBasa.setText("Stop");
                    PumpBasa.setValue(1);

                } else {
                    PumpBasa.setValue(0);
                    mBtnAddBasa.setText("Add Basa"); }
                break;

            case R.id.Btn_addTds:
                String BtnTdsTxt = mBtnAddTds.getText().toString();
                DatabaseReference PumpAbMixA = database.getReference("Relay/Pompa3");

                if(BtnTdsTxt.equals("Add Tds")){
                    PumpAbMixA.setValue(1);
                    mBtnAddTds.setText("Stop");

                } else {
                    PumpAbMixA.setValue(0);
                    mBtnAddTds.setText("Add Tds"); }

                break;
        }
    }

    private void initPresenter() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mTdsValue = snapshot.child("Data/TdsValue").getValue().toString();
                mTvTdsValue.setText(mTdsValue);

                mPhValue = snapshot.child("Data/PhValue").getValue().toString();
                mTvPhValue.setText(mPhValue);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initView() {
        mTvPhValue = findViewById(R.id.Tv_PhValue);
        mTvTdsValue = findViewById(R.id.Tv_TdsValue);
        mTvNotifikasi = findViewById(R.id.Tv_Notify);
        mBtnAddAsam = findViewById(R.id.Btn_addAsam);
        mBtnAddTds = findViewById(R.id.Btn_addTds);
        mBtnAddBasa = findViewById(R.id.Btn_addBasa);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Otomatis = findViewById(R.id.Sw_Otomatis);
    }


}