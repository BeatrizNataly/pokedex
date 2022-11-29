package com.projeto.pokedex.recyclerview.adapter.viewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private final ArrayList<Fragment> listaDeFragmentos = new ArrayList<>();
    private final ArrayList<String> titulosDoFragmentos = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public void adicionaFragmento(Fragment fragment, String tituloDoFragmento) {
        listaDeFragmentos.add(fragment);
        titulosDoFragmentos.add(tituloDoFragmento);
    }

    public String getTituloFragment(int position) {
        return titulosDoFragmentos.get(position);
    }

    public ArrayList<Fragment> getFragmentos() {
        return new ArrayList<>(listaDeFragmentos);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return listaDeFragmentos.get(position);
    }

    @Override
    public int getItemCount() {
        return listaDeFragmentos.size();
    }
}