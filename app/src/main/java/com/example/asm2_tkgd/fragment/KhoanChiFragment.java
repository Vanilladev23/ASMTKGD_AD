package com.example.asm2_tkgd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm2_tkgd.R;
import com.example.asm2_tkgd.adapter.KhoanChiAdapter;
import com.example.asm2_tkgd.dao.KhoanThuChiDAO;
import com.example.asm2_tkgd.model.KhoanThuChi;
import com.example.asm2_tkgd.model.Loai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class KhoanChiFragment extends Fragment {
    RecyclerView listViewKhoanChi;
    KhoanThuChiDAO khoanThuChiDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.khoanchi_fragment, container, false);

        // Giao diện item
        listViewKhoanChi = view.findViewById(R.id.listViewKhoanChi);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);

        // Data
        khoanThuChiDAO = new KhoanThuChiDAO(getContext());
        loadData();

        floatAdd.setOnClickListener(view1 -> {
            showDialogThemKhoanThu();
        });

        return view;
    }

    private void loadData() {
        ArrayList<KhoanThuChi> list = khoanThuChiDAO.getDSKhoanThuChi("chi");

        // Adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        listViewKhoanChi.setLayoutManager(linearLayoutManager);
        KhoanChiAdapter adapter = new KhoanChiAdapter(list, getContext(), khoanThuChiDAO, getListSpinner());
        listViewKhoanChi.setAdapter(adapter);
    }

    private ArrayList<HashMap<String, Object>> getListSpinner() {
        ArrayList<HashMap<String, Object>> listSpinner;
        listSpinner = new ArrayList<>();
        ArrayList<Loai> listLoai = khoanThuChiDAO.getDSLoai("chi");
        for(Loai loai : listLoai) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maloai", loai.getMaloai());
            hashMap.put("tenloai", loai.getTenloai());
            listSpinner.add(hashMap);
        }
        return listSpinner;
    }

    private void showDialogThemKhoanThu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themkhoanchi, null);
        Spinner spnLoaiChi = view.findViewById(R.id.spnLoaiChi);
        EditText edtTien = view.findViewById(R.id.edtTien);
        builder.setView(view);

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), getListSpinner(), android.R.layout.simple_list_item_1, new String[]{"tenloai"}, new int[]{android.R.id.text1});
        spnLoaiChi.setAdapter(simpleAdapter);

        builder.setPositiveButton("Thêm", (dialog, which) -> {
            String tien = edtTien.getText().toString();
            HashMap<String, Object> selected = (HashMap<String, Object>) spnLoaiChi.getSelectedItem();
            int maloai = (int) selected.get("maloai");
            KhoanThuChi khoanThuChi = new KhoanThuChi(Integer.parseInt(tien), maloai);
            if (khoanThuChiDAO.themMoiKhoanThuChi(khoanThuChi)) {
                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                loadData();
            } else {
                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> {

        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
