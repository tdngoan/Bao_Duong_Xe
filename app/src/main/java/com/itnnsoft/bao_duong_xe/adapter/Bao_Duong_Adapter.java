package com.itnnsoft.bao_duong_xe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itnnsoft.bao_duong_xe.R;
import com.itnnsoft.bao_duong_xe.model.Bao_Duong;
import com.itnnsoft.bao_duong_xe.model.Xe;

import java.util.List;

public class Bao_Duong_Adapter extends ArrayAdapter<Bao_Duong> {
    private Context context;
    private int resoure;
    private List<Bao_Duong> listbaoduong;

    public Bao_Duong_Adapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Bao_Duong> objects) {
        super(context, resource, objects);
        this.context= context;
        this.resoure=resource;
        this.listbaoduong =objects;
    }

    public class ViewHolder{

        private TextView txt_ngay;
        private TextView txt_noi_dung;
        private TextView txt_gia;
        private TextView txt_noi_bao_duong;
        private TextView id;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Bao_Duong_Adapter.ViewHolder viewHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bao_duong,parent,false);
            viewHolder = new Bao_Duong_Adapter.ViewHolder();
            viewHolder.txt_ngay = (TextView)convertView.findViewById(R.id.txt_ngay);
            viewHolder.txt_noi_dung = (TextView)convertView.findViewById(R.id.txt_noi_dung);
            viewHolder.txt_gia = (TextView)convertView.findViewById(R.id.txt_gia);
            viewHolder.txt_noi_bao_duong = (TextView)convertView.findViewById(R.id.txt_noi_bao_duong);
            viewHolder.id = (TextView) convertView.findViewById(R.id.txt_id);


            convertView.setTag(viewHolder);
        }else{
            viewHolder = (Bao_Duong_Adapter.ViewHolder) convertView.getTag();
        }
        Bao_Duong bao_duong = listbaoduong.get(position);
        viewHolder.txt_ngay.setText("Ngày:"+ bao_duong.getNgay().toString());
        viewHolder.txt_noi_dung.setText("Nội dung: "+bao_duong.getNoi_dung());
        viewHolder.txt_gia.setText("Chi phí: "+String.format("%,d",bao_duong.getGia())+" đ");
        viewHolder.txt_noi_bao_duong.setText("Nơi bảo dưỡng: "+bao_duong.getNoi_bao_duong());
        viewHolder.id.setText(String.valueOf(position+1));
        return convertView;
    }
}
