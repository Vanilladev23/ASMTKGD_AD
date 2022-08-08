package com.example.asm2_tkgd.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm2_tkgd.R;
import com.example.asm2_tkgd.dao.KhoanThuChiDAO;
import com.example.asm2_tkgd.model.Loai;

import java.util.ArrayList;

public class LoaiChiAdapter extends RecyclerView.Adapter<LoaiChiAdapter.ViewHolder> {
    private ArrayList<Loai> list;
    private Context context;
    private KhoanThuChiDAO khoanThuChiDAO;

    public LoaiChiAdapter(ArrayList<Loai> list, Context context, KhoanThuChiDAO khoanThuChiDAO) {
        this.list = list;
        this.context = context;
        this.khoanThuChiDAO = khoanThuChiDAO;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen;
        ImageView ivSua, ivXoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTen);
            ivSua = itemView.findViewById(R.id.ivSua);
            ivXoa = itemView.findViewById(R.id.ivXoa);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loaichi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtTen.setText(list.get(holder.getAdapterPosition()).getTenloai());

        holder.ivSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSuaLoaiChi(list.get(holder.getAdapterPosition()));
            }
        });

        holder.ivXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idCanXoa = list.get(holder.getAdapterPosition()).getMaloai();
                if (khoanThuChiDAO.xoaLoaiThuChi(idCanXoa)) {
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

    private void showDialogSuaLoaiChi(Loai loai) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sualoaichi, null);
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
        list = khoanThuChiDAO.getDSLoai("chi");
        notifyDataSetChanged();
    }
}
