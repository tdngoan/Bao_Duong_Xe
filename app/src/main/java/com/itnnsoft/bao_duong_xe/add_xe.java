package com.itnnsoft.bao_duong_xe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class add_xe extends AppCompatActivity {
    EditText edt_hang,edt_ten,edt_mau,edt_nam_sx;
    Button btn_add_xe,btn_close_add_xe;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_xe);
        edt_hang=findViewById(R.id.edt_hang);
        edt_ten=findViewById(R.id.edt_ten);
        edt_mau=findViewById(R.id.edt_mau);
        edt_nam_sx=findViewById(R.id.edt_nam_sx);
        btn_add_xe=findViewById(R.id.btn_add_xe);
        btn_close_add_xe=findViewById(R.id.btn_close_add_xe);
        db = FirebaseFirestore.getInstance();
        final String id_xe = getIntent().getStringExtra("id");
        btn_close_add_xe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_add_xe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> xe = new HashMap<>();
                    xe.put("id",id_xe);
                    xe.put("hang", edt_hang.getText().toString());
                    xe.put("ten", edt_ten.getText().toString());
                    xe.put("mau", edt_mau.getText().toString());
                    xe.put("nam_sx",Integer.valueOf(edt_nam_sx.getText().toString()));

                    db.collection("User/tdngoan/xe").document(id_xe).set(xe)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(),"Đã thêm xe mới thành công",Toast.LENGTH_LONG).show();
                                    finish();
                                   // Log.d("add xe", "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Đã xảy ra lỗi khi thêm xe mới",Toast.LENGTH_LONG).show();
                                   // Log.w("add xe", "Error writing document", e);
                                }
                            });;
            }
        });
    }
}
