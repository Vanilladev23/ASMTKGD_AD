package com.example.asm2_tkgd.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.asm2_tkgd.fragment.KhoanChiFragment;
import com.example.asm2_tkgd.fragment.LoaiChiFragment;

public class ChiAdapter extends FragmentStateAdapter {
    public ChiAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return new LoaiChiFragment();
        return new KhoanChiFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
