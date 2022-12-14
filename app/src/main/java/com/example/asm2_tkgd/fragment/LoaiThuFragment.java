package com.example.asm2_tkgd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm2_tkgd.R;
import com.example.asm2_tkgd.adapter.LoaiThuAdapter;
import com.example.asm2_tkgd.dao.KhoanThuChiDAO;
import com.example.asm2_tkgd.model.Loai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LoaiThuFragment extends Fragment {
    RecyclerView listViewLoaiThu;
    FloatingActionButton floatAdd;
    LoaiThuAdapter adapter;
    ArrayList<Loai> list;
    KhoanThuChiDAO khoanThuChiDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loaithu_fragment, container, false);

        listViewLoaiThu = view.findViewById(R.id.listViewLoaiThu);
        floatAdd = view.findViewById(R.id.floatAdd);

        khoanThuChiDAO = new KhoanThuChiDAO(getContext());
        loadData();

        floatAdd.setOnClickListener(view1 -> {
            showDialogThem();
        });

        return view;
    }

    private void loadData() {
        list = khoanThuChiDAO.getDSLoai("thu");

        // Adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        listViewLoaiThu.setLayoutManager(linearLayoutManager);
        adapter = new LoaiThuAdapter(list, getContext(), khoanThuChiDAO);
        listViewLoaiThu.setAdapter(adapter);
    }

    private void showDialogThem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themloaithu, null);
        EditText edtInput = view.findViewById(R.id.edtInput);
        builder.setView(view);

        builder.setPositiveButton("Th??m", ((dialogInterface, i) -> {
           String tenloai = edtInput.getText().toString();
           Loai loaiThem = new Loai(tenloai, "thu");
           if (khoanThuChiDAO.themMoiLoaiThuChi(loaiThem)) {
               Toast.makeText(getContext(), "Th??m th??nh c??ng", Toast.LENGTH_SHORT).show();
               loadData();
           } else {
                Toast.makeText(getContext(), "Th??m th???t b???i", Toast.LENGTH_SHORT).show();
           }
        }));

        builder.setNegativeButton("H???y", ((dialogInterface, i) -> {

        }));

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
