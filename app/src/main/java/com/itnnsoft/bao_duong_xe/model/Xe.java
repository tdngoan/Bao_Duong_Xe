package com.itnnsoft.bao_duong_xe.model;

public class Xe {
    String hang;
    String ten;
    String mau;
    Integer nam_sx;
    String id;

    public Xe() {
    }

    public Xe(String hang, String ten, String mau, Integer nam_sx) {
        this.hang = hang;
        this.ten = ten;
        this.mau = mau;
        this.nam_sx = nam_sx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMau() {
        return mau;
    }

    public void setMau(String mau) {
        this.mau = mau;
    }

    public Integer getNam_sx() {
        return nam_sx;
    }

    public void setNam_sx(Integer nam_sx) {
        this.nam_sx = nam_sx;
    }
}
