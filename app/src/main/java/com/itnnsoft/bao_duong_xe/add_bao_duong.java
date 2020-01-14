package com.itnnsoft.bao_duong_xe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class add_bao_duong extends AppCompatActivity {
    EditText edt_ngay,edt_noi_dung,edt_gia,edt_noi_bao_duong;
    Button btn_add,btn_close;
    FirebaseFirestore db;
    String id_xe,id_bao_duong;
    Calendar calendar;
    SimpleDateFormat dinhdangngay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bao_duong);
        db = FirebaseFirestore.getInstance();
        edt_ngay=findViewById(R.id.edt_ngay);
        edt_noi_dung=findViewById(R.id.edt_noi_dung);
        edt_gia=findViewById(R.id.edt_gia);
        edt_noi_bao_duong=findViewById(R.id.edt_noi_bao_duong);
        btn_add=findViewById(R.id.btn_add_bao_duong);
        btn_close=findViewById(R.id.btn_close_add_bao_duong);
        calendar = Calendar.getInstance();
        dinhdangngay = new SimpleDateFormat("dd/MM/yyyy");
        edt_ngay.setText(dinhdangngay.format(calendar.getTime()));
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
                new DatePickerDialog(add_bao_duong.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        id_xe = getIntent().getStringExtra("id_xe");
        id_bao_duong=getIntent().getStringExtra("id_bao_duong");
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> bao_duong = new HashMap<>();
                bao_duong.put("id",id_bao_duong);
                bao_duong.put("ngay", edt_ngay.getText().toString());
                bao_duong.put("noi_dung", edt_noi_dung.getText().toString());
                bao_duong.put("gia", Integer.valueOf(edt_gia.getText().toString()));
                bao_duong.put("noi_bao_duong",edt_noi_bao_duong.getText().toString());

                db.collection(id_xe).document(id_bao_duong).set(bao_duong)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Đã thêm bảo dưỡng mới thành công",Toast.LENGTH_LONG).show();
                                finish();
                                // Log.d("add xe", "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Đã xảy ra lỗi khi thêm bảo dưỡng mới",Toast.LENGTH_LONG).show();
                                // Log.w("add xe", "Error writing document", e);
                            }
                        });;
            }
        });
    }
}
