package com.example.asm2_tkgd.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.asm2_tkgd.R;
import com.example.asm2_tkgd.dao.KhoanThuChiDAO;
import com.example.asm2_tkgd.model.Loai;

import java.util.ArrayList;

public class LoaiThuAdapter extends BaseAdapter {
    private ArrayList<Loai> list;
    private Context context;
    private KhoanThuChiDAO khoanThuChiDAO;

    public LoaiThuAdapter(ArrayList<Loai> list, Context context, KhoanThuChiDAO khoanThuChiDAO) {
        this.list = list;
        this.context = context;
        this.khoanThuChiDAO = khoanThuChiDAO;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewOfItem {
        TextView txtTen;
        ImageView ivSua, ivXoa;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        ViewOfItem viewOfItem;

        if (view == null) {
            viewOfItem = new ViewOfItem();
            view = inflater.inflate(R.layout.item_loaithu, viewGroup, false);
            viewOfItem.txtTen = view.findViewById(R.id.txtTen);
            viewOfItem.ivSua = view.findViewById(R.id.ivSua);
            viewOfItem.ivXoa = view.findViewById(R.id.ivXoa);
            view.setTag(viewOfItem);
        } else {
            viewOfItem = (ViewOfItem) view.getTag();
        }

        viewOfItem.txtTen.setText(list.get(i).getTenloai());

        viewOfItem.ivSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSuaLoaiThu(list.get(i));
            }
        });

        viewOfItem.ivXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int idCanXoa = list.get(i).getMaloai();
               if (khoanThuChiDAO.xoaLoaiThuChi(idCanXoa)) {
                   Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                   reLoadData();
               } else {
                   Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
               }
            }
        });

        return view;
    }
    private void showDialogSuaLoaiThu(Loai loai) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sualoaithu, null);
        EditText edtInput = view.findViewById(R.id.edtInput);
        builder.setView(view);

        edtInput.setText(loai.getTenloai());

        builder.setPositiveButton("Cập nhật", ((dialogInterface, i) -> {
            String tenloai = edtInput.getText().toString();
            loai.setTenloai(tenloai);
            if (khoanThuChiDAO.capnhatLoaiThuChi(loai)) {
                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                reLoadData();
            } else {
                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        }));

        builder.setNegativeButton("Hủy", ((dialogInterface, i) -> {

        }));

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void reLoadData() {
        list.clear();
        list = khoanThuChiDAO.getDSLoai("thu");
        notifyDataSetChanged();
    }
}
