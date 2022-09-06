package id.mentoring.monitoringhidroponik.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import id.mentoring.monitoringhidroponik.R;
import id.mentoring.monitoringhidroponik.model.MahasiswaResponse;

public class AddActivity extends AppCompatActivity implements MaterialButton.OnCheckedChangeListener, View.OnClickListener {
    private MaterialButton mBtnSave;
    private MaterialButton mBtnUpdate;
    private EditText mEdtName;
    private EditText mEdtMatkul;
    private EditText mEdtPhMin;
    private EditText mEdtPhMax;
    private EditText mEdtTdsMin;
    private EditText mEdtTdsMax;
    private FirebaseUser firebaseUser;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mBtnUpdate.setOnClickListener(this);
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getNama = mEdtName.getText().toString();
                String getMatkul = mEdtMatkul.getText().toString();

                if(getNama.isEmpty()){
                    mEdtName.setError("Mohon Masukan Hari/Tanggal!");
                }
                else if (getMatkul.isEmpty()) {
                    mEdtMatkul.setError("Mohon Masukan Catatan");
                }
                else
                {
                    databaseReference.child("Mahasiswa").push().setValue(new MahasiswaResponse(getNama, getMatkul)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(AddActivity.this, "Data berhasil disimpan ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddActivity.this, MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddActivity.this, "Gagal menyimpan Data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void initView() {
        mBtnUpdate = findViewById(R.id.btnUpdate);
        mBtnSave = findViewById(R.id.btnSave);
        mEdtName = findViewById(R.id.ET_Name);
        mEdtMatkul = findViewById(R.id.ET_Matkul);
        mEdtPhMax = findViewById(R.id.EtPhMax);
        mEdtPhMin = findViewById(R.id.EtPhMin);
        mEdtTdsMax = findViewById(R.id.EtTdsMax);
        mEdtTdsMin = findViewById(R.id.EtTdsMin);
    }

    @Override
    public void onClick(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        switch (view.getId()){
            case R.id.btnUpdate :
                String TdsMinVal = mEdtTdsMin.getText().toString();
                String TdsMaxVal = mEdtTdsMax.getText().toString();
                String PhMinVal = mEdtPhMin.getText().toString();
                String PhMaxVal = mEdtPhMax.getText().toString();

                DatabaseReference PhMin = database.getReference("Target/PhMin");
                DatabaseReference PhMax = database.getReference("Target/PhMax");
                DatabaseReference TdsMin = database.getReference("Target/TdsMin");
                DatabaseReference TdsMax = database.getReference("Target/TdsMax");


                PhMin.setValue(PhMinVal);
                PhMax.setValue(PhMaxVal);
                TdsMin.setValue(TdsMinVal);
                TdsMax.setValue(TdsMaxVal);
        }
    }

    @Override
    public void onCheckedChanged(MaterialButton button, boolean isChecked) {

    }
}