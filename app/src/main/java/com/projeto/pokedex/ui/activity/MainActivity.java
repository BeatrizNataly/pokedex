package com.projeto.pokedex.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.projeto.pokedex.R;
import com.projeto.pokedex.recyclerview.adapter.viewPager.ViewPagerAdapter;
import com.projeto.pokedex.ui.fragment.ListaFavoritosFragment;
import com.projeto.pokedex.ui.fragment.ListaPokemonFragment;
import com.projeto.pokedex.util.ConstantUtil;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private EditText pesquisa;
    private TextView arrowBack;
    private ListaPokemonFragment listaPokemonFragment;
    private ListaFavoritosFragment listaFavoritosFragment;
    private int tabIcons[] = {R.drawable.pokeball, R.drawable.ic_favorite};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Pokédex");
        setViews();
        setLayoutManager();
        setArrowBackToFragments();
    }

    private void setViews() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.main_view_pager);
        listaPokemonFragment = new ListaPokemonFragment();
        listaFavoritosFragment = new ListaFavoritosFragment();
    }

    private void setLayoutManager() {
        setupViewPagerAdapter();
        setupTabIcons();
    }

    private void setupViewPagerAdapter() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPagerAdapter.adicionaFragmento(listaPokemonFragment, "Pokémons");
        viewPagerAdapter.adicionaFragmento(listaFavoritosFragment, "Favoritos");
        viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
                tab.setText(viewPagerAdapter.getTituloFragment(position))).attach();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setArrowBackToFragments() {
        arrowBack = findViewById(R.id.back_to_list);
        listaPokemonFragment.setArrowBack(arrowBack);
        listaFavoritosFragment.setArrowBack(arrowBack);
    }


    public void buscaPokemon(View view) {
        pesquisa = findViewById(R.id.editTextPesquisaNomePokemon);
        String busca = pesquisa.getText().toString();
        if (!busca.isEmpty()) {
            buscaPokemonNosFragments(busca);
            return;
        }
        Toast.makeText(this, ConstantUtil.POKEMON_NOT_FOUND, Toast.LENGTH_SHORT).show();
    }

    private void buscaPokemonNosFragments(String busca) {
        switch (viewPager.getCurrentItem()) {
            case 0:
                listaPokemonFragment.buscaPokemon(busca);
                break;
            case 1:
                listaFavoritosFragment.buscaNosFavoritos(busca);
                break;
            default:
                Toast.makeText(this, ConstantUtil.POKEMON_NOT_FOUND, Toast.LENGTH_SHORT);
                arrowBack.setVisibility(View.GONE);
        }
    }

    public void scrollUp(View view) {
        switch (viewPager.getCurrentItem()) {
            case 0:
                listaPokemonFragment.scrollUp();
                break;
            case 1:
                listaFavoritosFragment.scrollUp();
                break;
            default:
                Toast.makeText(this, ConstantUtil.ERRO_AO_CARREGAR, Toast.LENGTH_SHORT);
        }
    }

    public void retornaParaListaPrincipal(View view) {
        arrowBack.setVisibility(View.GONE);
        pesquisa.setText("");
        retornaListaPrincipalDoFragment();
        Toast.makeText(this, ConstantUtil.RETURN, Toast.LENGTH_SHORT).show();
    }

    private void retornaListaPrincipalDoFragment() {
        if (listaPokemonFragment.isSearchingPokemons())
            listaPokemonFragment.retornaParaListaPrincipal();
        if (listaFavoritosFragment.isSearchingPokemons())
            listaFavoritosFragment.retornaParaListaPrincipal();
    }
}