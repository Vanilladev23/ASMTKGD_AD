package com.example.asm2_tkgd.model;

public class Loai {
    private int maloai;
    private String tenloai;
    private String trangthai;

    public Loai(int maloai, String tenloai, String trangthai) {
        this.maloai = maloai;
        this.tenloai = tenloai;
        this.trangthai = trangthai;
    }

    public Loai(String tenloai, String trangthai) {
        this.tenloai = tenloai;
        this.trangthai = trangthai;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
