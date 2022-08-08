package com.example.asm2_tkgd.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.asm2_tkgd.R;
import com.example.asm2_tkgd.adapter.ChiAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ChiFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chi_fragment, container, false);

        TabLayout  tabLayout = view.findViewById(R.id.tabLayoutChi);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager2Chi);

        ChiAdapter adapter = new ChiAdapter(getActivity());
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            if (position == 0)
                tab.setText("Loại chi");
            else
                tab.setText("Khoản chi");
        }).attach();

        return view;
    }
}
