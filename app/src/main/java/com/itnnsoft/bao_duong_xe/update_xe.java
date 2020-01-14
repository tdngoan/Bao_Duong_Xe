package com.itnnsoft.bao_duong_xe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.itnnsoft.bao_duong_xe.model.Xe;

public class update_xe extends AppCompatActivity {
    EditText edt_hang,edt_ten,edt_mau,edt_nam_sx;
    Button btn_update_xe,btn_close_update_xe;
    FirebaseFirestore db;
    Xe xe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_xe);
        edt_hang=findViewById(R.id.edt_ngay);
        edt_ten=findViewById(R.id.edt_noi_dung);
        edt_mau=findViewById(R.id.edt_gia);
        edt_nam_sx=findViewById(R.id.edt_noi_bao_duong);
        btn_update_xe=findViewById(R.id.btn_update_xe);
        btn_close_update_xe=findViewById(R.id.btn_close_update_xe);
        db = FirebaseFirestore.getInstance();
        final String id_xe = getIntent().getStringExtra("id");

        DocumentReference document = db.collection("User/tdngoan/xe").document(id_xe);
        document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        xe=document.toObject(Xe.class);
                        edt_hang.setText(xe.getHang());
                        edt_ten.setText(xe.getTen());
                        edt_mau.setText(xe.getMau());
                        edt_nam_sx.setText(xe.getNam_sx().toString());
                    } else {
                        Log.d("Cập nhật xe: ", "Không có xe");
                    }
                } else {
                    Log.d("Cập nhật xe", "lỗi đọc xe ", task.getException());
                }
            }
        });

        btn_close_update_xe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_update_xe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("User/tdngoan/xe").document(id_xe)
                        .update("hang",edt_hang.getText().toString(),
                                "ten",edt_ten.getText().toString(),
                                "mau",edt_mau.getText().toString(),
                                "nam_sx",Integer.valueOf(edt_nam_sx.getText().toString()))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Đã cập nhật thông tin xe thành công",Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Đã xảy ra lôi khi cập nhật: "+e.toString(),Toast.LENGTH_LONG).show();
                            }
                        });
                finish();
            }
        });

    }
}
