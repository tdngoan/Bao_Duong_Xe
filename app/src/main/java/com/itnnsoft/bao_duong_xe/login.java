package com.itnnsoft.bao_duong_xe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.itnnsoft.bao_duong_xe.model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class login extends AppCompatActivity {
    EditText edt_username,edt_password;
    Button btn_login;
    CheckBox chk_nho;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_username=findViewById(R.id.edt_username);
        edt_password=findViewById(R.id.edt_password);
        btn_login=findViewById(R.id.btn_login);
        chk_nho=findViewById(R.id.chk_nho);

        db=FirebaseFirestore.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference docRef = db.collection("User").document(edt_username.getText().toString());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                               User user = document.toObject(User.class);
                               if(user.getPassword().equals(edt_password.getText().toString())){
                                   Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_LONG).show();
                                   if(chk_nho.isChecked()){
                                       SaveUser(user);
                                   }
                                   finish();
                               }else {
                                   //Sai mật khẩu
                                   Toast.makeText(getApplicationContext(),"Sai mật khẩu",Toast.LENGTH_LONG).show();
                               }
                            } else {
                                Toast.makeText(getApplicationContext(),"Tên đăng nhập không tồn tại",Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"Không thể kết nối với cơ sở dữ liệu vui lòng kiểm tra lại internet",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }

    public void SaveUser(User user) {
        String filepath =  "/data/data/" + getPackageName() + "/currentuser.itnn";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filepath);
            String dulieu = "";
            dulieu = user.getUsername()+"\t" + user.getPassword();
            byte[] buffer = dulieu.getBytes();
            fos.write(buffer, 0, buffer.length);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null)
                try {
                    fos.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
        }

    }

    public String ReadUser(){
        String filepath =  "/data/data/" + getPackageName() + "/currentuser.itnn";
        FileInputStream fis = null;
        String ketqua="";
        try {
            fis = new FileInputStream(filepath);
            int length = (int) new File(filepath).length();
            byte[] buffer = new byte[length];
            fis.read(buffer, 0, length);
            ketqua = new String(buffer);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(fis != null)
                try {
                    fis.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
        }
        return  ketqua;
    }
}
