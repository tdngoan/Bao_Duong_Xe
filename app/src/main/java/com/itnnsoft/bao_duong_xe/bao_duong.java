package com.itnnsoft.bao_duong_xe;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.itnnsoft.bao_duong_xe.adapter.Bao_Duong_Adapter;
import com.itnnsoft.bao_duong_xe.model.Bao_Duong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class bao_duong extends AppCompatActivity {
    ArrayList<Bao_Duong> list_baoduong = new ArrayList<Bao_Duong>();
    Bao_Duong_Adapter bao_duong_adapter;
    ListView lst_bao_duong;
    Button btn_add_bao_duong;
    String id_xe,id_bao_duong;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_duong);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = FirebaseFirestore.getInstance();
        FloatingActionButton fab = findViewById(R.id.fab);

        lst_bao_duong = findViewById(R.id.lst_bao_duong);
        btn_add_bao_duong=findViewById(R.id.btn_add_bao_duong);
        bao_duong_adapter = new Bao_Duong_Adapter(this,R.layout.item_bao_duong,list_baoduong);
        lst_bao_duong.setAdapter(bao_duong_adapter);

        id_xe = getIntent().getStringExtra("id");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        btn_add_bao_duong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list_baoduong.size()==0){
                    id_bao_duong="1";
                }else {
                    id_bao_duong=String.valueOf(Integer.valueOf(list_baoduong.get(list_baoduong.size()-1).getId())+1);
                }
                Intent add_bao_duong = new Intent(getApplicationContext(), add_bao_duong.class);
                add_bao_duong.putExtra("id_xe","User/tdngoan/xe/"+id_xe+"/bao_duong");
                add_bao_duong.putExtra("id_bao_duong",id_bao_duong);
                startActivity(add_bao_duong);
            }
        });
        lst_bao_duong.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Integer id =i;
                final Dialog dialog = new Dialog(bao_duong.this);
                dialog.setContentView(R.layout.menu_bao_duong);
                Button btn_sua = dialog.findViewById(R.id.btn_sua);
                Button btn_xoa = dialog.findViewById(R.id.btn_xoa);
                Button btn_dong = dialog.findViewById(R.id.btn_dong);
                btn_xoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.collection("User/tdngoan/xe/"+id_xe+"/bao_duong").document(list_baoduong.get(id).getId())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(bao_duong.this,"Đã xoá bảo dưỡng thành công",Toast.LENGTH_LONG).show();
                                        load_bao_duong();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(bao_duong.this,"Lỗi khi xoá bảo dưỡng: " +e.toString(),Toast.LENGTH_LONG).show();
                                    }
                                });
                        dialog.cancel();
                    }
                });
                btn_dong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                btn_sua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent update_bao_duong = new Intent(bao_duong.this, update_bao_duong.class);
                        update_bao_duong.putExtra("id_bao_duong",list_baoduong.get(id).getId());
                        update_bao_duong.putExtra("id_xe",id_xe);
                        startActivity(update_bao_duong);
                        dialog.cancel();
                    }
                });
                dialog.show();
                return false;
            }
        });
    }

    public void load_bao_duong(){
        list_baoduong.clear();
        db.collection("/User/tdngoan/xe/"+id_xe+"/bao_duong")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Bao_Duong bao_duong = document.toObject(Bao_Duong.class);
                                list_baoduong.add(bao_duong);
                            }
                            bao_duong_adapter.notifyDataSetChanged();
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        load_bao_duong();
    }
}
