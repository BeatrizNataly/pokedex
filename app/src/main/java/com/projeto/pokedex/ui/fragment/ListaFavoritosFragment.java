package com.projeto.pokedex.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.projeto.pokedex.R;
import com.projeto.pokedex.pokedex.database.PokedexViewModel;
import com.projeto.pokedex.pokedex.entities.Pokemon;
import com.projeto.pokedex.recyclerview.adapter.ListaPokemonAdapter;
import com.projeto.pokedex.util.ConstantUtil;
import com.projeto.pokedex.util.animation.LoadingAnimation;

import java.util.ArrayList;

public class ListaFavoritosFragment extends Fragment {
    private View vi;
    private RecyclerView listaPokemonsRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private ListaPokemonAdapter adapter;
    private PokedexViewModel viewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isSearchingPokemons = false;
    private String ultimaBusca = "";
    private TextView arrowBack;

    public ListaFavoritosFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vi = inflater.inflate(R.layout.fragment_lista_pokemon, container, false);
        adapter = new ListaPokemonAdapter(getActivity());
        viewModel = ViewModelProviders.of(this).get(PokedexViewModel.class);

        createLoadingAnimation();
        buscaListaFavoritos();
        createRecyclerView();
        createRefreshLayout();
        return vi;
    }

    @Override
    public void onResume() {
        super.onResume();
        buscaListaFavoritos();
    }

    private void createRefreshLayout() { //RECARREGA A LISTA DO ZERO E EXIBE O RESULTADO CARREGADO
        swipeRefreshLayout = vi.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            listaPokemonsRecyclerView.setVisibility(View.INVISIBLE);
            scrollUp();
            createLoadingAnimation();
            listaPokemonsRecyclerView.setVisibility(View.VISIBLE);
            if (isSearchingPokemons)
                buscaNosFavoritos(ultimaBusca);
            else
                buscaListaFavoritos();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void createLoadingAnimation() {
        LoadingAnimation.createAnimation(vi);
    }

    private void buscaListaFavoritos() {
        isSearchingPokemons = false;
        adapter.limpaLista();
        viewModel.getFavoritePokemons().observe(getActivity(), lista -> {
            adapter.adicionaLista((ArrayList<Pokemon>) lista);
        });
    }

    private void createRecyclerView() {
        listaPokemonsRecyclerView = vi.findViewById(R.id.pokemon_recyclerview);
        listaPokemonsRecyclerView.setAdapter(adapter);
        listaPokemonsRecyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        listaPokemonsRecyclerView.setLayoutManager(gridLayoutManager);
    }

    public void buscaNosFavoritos(String busca) {
        ultimaBusca = busca;
        viewModel.searchFavoritePokemons(busca).observe(getActivity(), lista -> {
            adapter.limpaLista();
            isSearchingPokemons = true;
            if (lista.size() > 0) {
                adapter.adicionaListaDeBusca((ArrayList<Pokemon>) lista);
                arrowBack.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getActivity(), ConstantUtil.POKEMON_NOT_FOUND, Toast.LENGTH_SHORT).show();
                arrowBack.setVisibility(View.GONE);
            }
        });
    }

    public void retornaParaListaPrincipal() {
        adapter.limpaLista();
        buscaListaFavoritos();
        ultimaBusca = "";
        Toast.makeText(getContext(), ConstantUtil.RETURN, Toast.LENGTH_SHORT).show();
    }

    public void scrollUp() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) listaPokemonsRecyclerView.getLayoutManager();
        layoutManager.smoothScrollToPosition(listaPokemonsRecyclerView, null, 0);
    }

    public boolean isSearchingPokemons() {
        return isSearchingPokemons;
    }

    public void setArrowBack(TextView arrowBack) {
        this.arrowBack = arrowBack;
    }
}