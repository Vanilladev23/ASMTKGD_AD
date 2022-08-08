package com.example.asm2_tkgd.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.asm2_tkgd.fragment.KhoanThuFragment;
import com.example.asm2_tkgd.fragment.LoaiThuFragment;

public class ThuAdapter extends FragmentStateAdapter {
    public ThuAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return new LoaiThuFragment();
        return new KhoanThuFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
