package com.itnnsoft.bao_duong_xe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.itnnsoft.bao_duong_xe.model.Bao_Duong;
import com.itnnsoft.bao_duong_xe.model.Xe;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class update_bao_duong extends AppCompatActivity {
    EditText edt_ngay,edt_noi_dung,edt_gia,edt_noi_bao_duong;
    Button btn_update,btn_close;
    FirebaseFirestore db;
    String id_xe,id_bao_duong;
    Calendar calendar;
    SimpleDateFormat dinhdangngay;
    Bao_Duong bao_duong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bao_duong);

        db = FirebaseFirestore.getInstance();
        edt_ngay=findViewById(R.id.edt_ngay);
        edt_noi_dung=findViewById(R.id.edt_noi_dung);
        edt_gia=findViewById(R.id.edt_gia);
        edt_noi_bao_duong=findViewById(R.id.edt_noi_bao_duong);
        btn_update=findViewById(R.id.btn_update_bao_duong);
        btn_close=findViewById(R.id.btn_close_update_bao_duong);
        calendar = Calendar.getInstance();
        dinhdangngay = new SimpleDateFormat("dd/MM/yyyy");

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                edt_ngay.setText(dinhdangngay.format(calendar.getTime()));
            }
        };
        edt_ngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(update_bao_duong.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        id_xe = getIntent().getStringExtra("id_xe");
        id_bao_duong=getIntent().getStringExtra("id_bao_duong");

        DocumentReference document = db.collection("User/tdngoan/xe").document(id_xe).collection("bao_duong").document(id_bao_duong);
        document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        bao_duong=document.toObject(Bao_Duong.class);
                        edt_ngay.setText(bao_duong.getNgay());
                        edt_noi_dung.setText(bao_duong.getNoi_dung());
                        edt_gia.setText(String.valueOf(bao_duong.getGia()));
                        edt_noi_bao_duong.setText(bao_duong.getNoi_bao_duong());
                    } else {
                        Log.d("Cập nhật bao dưỡng: ", "Không có bảo dưỡng");
                    }
                } else {
                    Log.d("Cập nhật bảo dưỡng", "lỗi đọc bảo dưỡng ", task.getException());
                }
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("User/tdngoan/xe").document(id_xe).collection("bao_duong").document(id_bao_duong)
                        .update("ngay",edt_ngay.getText().toString(),
                                "noi_dung",edt_noi_dung.getText().toString(),
                                "gia",Integer.valueOf(edt_gia.getText().toString()),
                                "noi_bao_duong",edt_noi_bao_duong.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Đã cập nhật thông tin bảo dưỡng thành công",Toast.LENGTH_LONG).show();
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

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
