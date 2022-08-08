package com.example.asm2_tkgd.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm2_tkgd.R;
import com.example.asm2_tkgd.dao.KhoanThuChiDAO;
import com.example.asm2_tkgd.model.KhoanThuChi;

import java.util.ArrayList;
import java.util.HashMap;

public class KhoanThuAdapter extends RecyclerView.Adapter<KhoanThuAdapter.ViewHolder> {
    private ArrayList<KhoanThuChi> list;
    private Context context;
    private KhoanThuChiDAO khoanThuChiDAO;
    private ArrayList<HashMap<String, Object>> listSpinner;

    public KhoanThuAdapter(ArrayList<KhoanThuChi> list, Context context, KhoanThuChiDAO khoanThuChiDAO, ArrayList<HashMap<String, Object>> listSpinner) {
        this.list = list;
        this.context = context;
        this.khoanThuChiDAO = khoanThuChiDAO;
        this.listSpinner = listSpinner;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtTien;
        ImageView ivSua, ivXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTen);
            txtTien = itemView.findViewById(R.id.txtTien);
            ivSua = itemView.findViewById(R.id.ivSua);
            ivXoa = itemView.findViewById(R.id.ivXoa);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_khoanthu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtTen.setText(list.get(holder.getAdapterPosition()).getTenloai());
        holder.txtTien.setText(String.valueOf(list.get(holder.getAdapterPosition()).getTien()));

        holder.ivSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSuaKhoanThuChi(list.get(holder.getAdapterPosition()));
            }
        });

        holder.ivXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int makhoan = list.get(holder.getAdapterPosition()).getMakhoan();
                if (khoanThuChiDAO.xoaKhoanThuChi(makhoan)) {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    reLoadData();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void showDialogSuaKhoanThuChi(KhoanThuChi khoanThuChi) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_suakhoanthu, null);
        Spinner spnLoaiThuChi = view.findViewById(R.id.spnLoaiThu);
        EditText edtTien = view.findViewById(R.id.edtTien);
        builder.setView(view);

        SimpleAdapter adapter = new SimpleAdapter(context, listSpinner, android.R.layout.simple_list_item_1, new String[]{"tenloai"}, new int[]{android.R.id.text1});
        spnLoaiThuChi.setAdapter(adapter);

        int index = 0;
        int position = -1;
        for(HashMap<String, Object> item : listSpinner) {
            if ((int) item.get("maloai") == khoanThuChi.getMaloai()) {
                position = index;
            }
            index++;
        }
        spnLoaiThuChi.setSelection(position);

        edtTien.setText(String.valueOf(khoanThuChi.getTien()));

        builder.setPositiveButton("Cập nhật", ((dialogInterface, i) -> {
            String tien = edtTien.getText().toString();
            HashMap<String, Object> selected = (HashMap<String, Object>) spnLoaiThuChi.getSelectedItem();
            int maloai = (int) selected.get("maloai");
            khoanThuChi.setMaloai(maloai);
            khoanThuChi.setTien(Integer.parseInt(tien));
            if (khoanThuChiDAO.capNhatKhoanThuChi(khoanThuChi)) {
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
       list= khoanThuChiDAO.getDSKhoanThuChi("thu");
       notifyDataSetChanged();
    }
}
