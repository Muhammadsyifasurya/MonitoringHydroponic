package id.mentoring.monitoringhidroponik.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.mentoring.monitoringhidroponik.R;
import id.mentoring.monitoringhidroponik.adapter.MahasiswaAdapter;
import id.mentoring.monitoringhidroponik.model.MahasiswaResponse;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton mFabAdd;
    private ImageView mIvHome, mIvUser;
    private SearchView mSvNotif;
    MahasiswaAdapter mahasiswaAdapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    ArrayList<MahasiswaResponse> ListMahasiswa;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        mIvHome.setOnClickListener(this);
        mFabAdd.setOnClickListener(this);
        mIvUser.setOnClickListener(this);

        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayout);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        initPresenter();
    }

    private void initPresenter() {
        databaseReference.child("Mahasiswa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ListMahasiswa = new ArrayList<>();
                for(DataSnapshot item: snapshot.getChildren()){
                    MahasiswaResponse mhs = item.getValue(MahasiswaResponse.class);
                    mhs.setKey(item.getKey());
                    ListMahasiswa.add(mhs);
                }
                mahasiswaAdapter = new MahasiswaAdapter(ListMahasiswa, MainActivity.this);
                recyclerView.setAdapter(mahasiswaAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initView() {
        mFabAdd = findViewById(R.id.fab_add);
        recyclerView = findViewById(R.id.RvTampilan);
        mIvHome = findViewById(R.id.IvHome);
        mIvUser = findViewById(R.id.IvUser);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_add:
                startActivity(new Intent(MainActivity.this, AddActivity.class));

                break;
            case R.id.IvHome:
                startActivity((new Intent(MainActivity.this, HomeMonitorActivity.class)));

                break;
            case R.id.IvUser:
                startActivity(new Intent(MainActivity.this, UserActivity.class));
        }
    }
}