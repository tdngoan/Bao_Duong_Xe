package com.itnnsoft.bao_duong_xe.model;

public class Bao_Duong {
    String ngay;
    String noi_dung;
    Integer gia;
    String noi_bao_duong;
    String id;

    public Bao_Duong() {
    }

    public Bao_Duong(String ngay, String noi_dung, Integer gia, String noi_bao_duong) {
        this.ngay = ngay;
        this.noi_dung = noi_dung;
        this.gia = gia;
        this.noi_bao_duong = noi_bao_duong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getNoi_dung() {
        return noi_dung;
    }

    public void setNoi_dung(String noi_dung) {
        this.noi_dung = noi_dung;
    }

    public Integer getGia() {
        return gia;
    }

    public void setGia(Integer gia) {
        this.gia = gia;
    }

    public String getNoi_bao_duong() {
        return noi_bao_duong;
    }

    public void setNoi_bao_duong(String noi_bao_duong) {
        this.noi_bao_duong = noi_bao_duong;
    }
}
