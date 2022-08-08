package com.example.asm2_tkgd.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asm2_tkgd.R;
import com.example.asm2_tkgd.dao.KhoanThuChiDAO;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class ThongkeFragment extends Fragment implements OnChartValueSelectedListener {
    KhoanThuChiDAO khoanThuChiDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thongke_fragment, container, false);

        khoanThuChiDAO = new KhoanThuChiDAO(getContext());

        PieChart mChart = view.findViewById(R.id.piechart);
        mChart.setRotationEnabled(true);
        mChart.setDescription(new Description());
        mChart.setHoleRadius(35f);
        mChart.setTransparentCircleAlpha(0);
        mChart.setCenterText("Thống kê");
        mChart.setCenterTextSize(10);
        mChart.setDrawEntryLabels(true);
        addDataSet(mChart);
        mChart.setOnChartValueSelectedListener(this);

        return view;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private void addDataSet(PieChart pieChart) {
        float[] yData = khoanThuChiDAO.getThongTinThuChi();
        String[] xData = { "Khoản thu", "Khoản chi" };

        ArrayList<PieEntry> entrys = new ArrayList<>();
        for (int i = 0; i < yData.length; i++) {
            entrys.add(new PieEntry(yData[i], xData[i]));
        }
        PieDataSet pieDataSet = new PieDataSet(entrys, " ");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.CYAN);
        colors.add(Color.GREEN);
        pieDataSet.setColors(colors);
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
