package com.itnnsoft.bao_duong_xe.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.itnnsoft.bao_duong_xe.R;
import com.itnnsoft.bao_duong_xe.adapter.Xe_Adapter;
import com.itnnsoft.bao_duong_xe.bao_duong;
import com.itnnsoft.bao_duong_xe.update_xe;
import com.itnnsoft.bao_duong_xe.model.Xe;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Button btn_add_xe;
    TextView textView;
    ListView lst_xe;
    ArrayList<Xe> list_xe=new ArrayList<Xe>();
    Xe_Adapter xe_adapter;
    String TAG ="Home";
    FirebaseFirestore db;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textView = root.findViewById(R.id.text_home);
        btn_add_xe = root.findViewById(R.id.btn_add_xe);
        lst_xe = root.findViewById(R.id.lst_xe);
        xe_adapter = new Xe_Adapter(getContext(),R.layout.item_xe,list_xe);
        lst_xe.setAdapter(xe_adapter);
        db = FirebaseFirestore.getInstance();
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });



        lst_xe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent baoduong = new Intent(getContext(), bao_duong.class);
//                baoduong.putExtra("id",list_xe.get(i).getId());
//                startActivity(baoduong);
            }
        });
        lst_xe.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Integer id = i;
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.menu_xe);
                Button btn_sua = dialog.findViewById(R.id.btn_sua);
                Button btn_xoa = dialog.findViewById(R.id.btn_xoa);
                Button btn_baoduong = dialog.findViewById(R.id.btn_baoduong);

                btn_baoduong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent baoduong = new Intent(getContext(), bao_duong.class);
                        baoduong.putExtra("id",list_xe.get(id).getId());
                        startActivity(baoduong);
                        dialog.cancel();
                    }
                });
                btn_sua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent update_xe = new Intent(getContext(), update_xe.class);
                        update_xe.putExtra("id",list_xe.get(id).getId());
                        startActivity(update_xe);
                        dialog.cancel();
                    }
                });
                btn_xoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.collection("User/tdngoan/xe").document(list_xe.get(id).getId())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getContext(),"Đã xoá xe thành công",Toast.LENGTH_LONG).show();
                                        load_xe();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(),"Lỗi khi xoá xe: " +e.toString(),Toast.LENGTH_LONG).show();
                                    }
                                });
                        dialog.cancel();
                    }
                });
                dialog.show();
                return false;
            }
        });
        btn_add_xe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String id;
                if(list_xe.size()==0){
                    id="1";
                }else {
                    id=String.valueOf(Integer.valueOf(list_xe.get(list_xe.size()-1).getId())+1);
                }
                Intent add_xe = new Intent(getContext(), com.itnnsoft.bao_duong_xe.add_xe.class);
                add_xe.putExtra("id",id);
                startActivity(add_xe);
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        load_xe();
    }

    public void load_xe(){
        list_xe.clear();

        db.collection("User/tdngoan/xe")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Xe xe = document.toObject(Xe.class);
                                list_xe.add(xe);
                            }
                            xe_adapter.notifyDataSetChanged();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}