package com.itnnsoft.bao_duong_xe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itnnsoft.bao_duong_xe.R;
import com.itnnsoft.bao_duong_xe.model.Xe;

import java.util.List;

public class Xe_Adapter extends ArrayAdapter<Xe> {
    private Context context;
    private int resoure;
    private List<Xe> listxe;

    public Xe_Adapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Xe> objects) {
        super(context, resource, objects);
        this.context= context;
        this.resoure=resource;
        this.listxe =objects;
    }

    public class ViewHolder{

        private TextView txt_hang;
        private TextView txt_ten;
        private TextView txt_mau;
        private TextView txt_nam_sx;
        private ImageView img_hinh;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_xe,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.txt_hang = (TextView)convertView.findViewById(R.id.txt_hang);
            viewHolder.txt_ten = (TextView)convertView.findViewById(R.id.txt_ten);
            viewHolder.txt_mau = (TextView)convertView.findViewById(R.id.txt_mau);
            viewHolder.txt_nam_sx = (TextView)convertView.findViewById(R.id.txt_nam_sx);
            viewHolder.img_hinh = (ImageView) convertView.findViewById(R.id.img_hinh);


            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Xe xe = listxe.get(position);
        viewHolder.txt_hang.setText("Hãng:"+ xe.getHang());
        viewHolder.txt_ten.setText("Tên: "+xe.getTen());
        viewHolder.txt_mau.setText("Màu: "+xe.getMau());
        viewHolder.txt_nam_sx.setText("Năm sản xuất: "+xe.getNam_sx().toString());
        viewHolder.img_hinh.setImageDrawable(context.getResources().getDrawable(R.drawable.xe01));
        return convertView;
    }
}
