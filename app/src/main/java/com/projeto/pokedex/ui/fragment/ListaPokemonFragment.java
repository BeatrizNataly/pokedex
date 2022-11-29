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
import com.projeto.pokedex.pokedex.api.PokedexRetrofitResponse;
import com.projeto.pokedex.pokedex.api.PokemonRetrofit;
import com.projeto.pokedex.pokedex.database.PokedexViewModel;
import com.projeto.pokedex.recyclerview.adapter.ListaPokemonAdapter;
import com.projeto.pokedex.util.ConstantUtil;
import com.projeto.pokedex.util.animation.LoadingAnimation;

public class ListaPokemonFragment extends Fragment {
    private View vi;
    private RecyclerView listaPokemonsRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private PokemonRetrofit retrofit;
    private ListaPokemonAdapter adapter;
    private PokedexViewModel viewModel;
    private boolean isScrolledToEnd;
    private boolean isSearchingPokemons = false;
    private int offset;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String ultimaBusca = "";
    private TextView arrowBack;

    public ListaPokemonFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vi = inflater.inflate(R.layout.fragment_lista_pokemon, container, false);
        createLoadingAnimation();
        createRetrofit();
        createRecyclerView();
        createScrollListener();
        createRefreshLayout();
        return vi;
    }

    private void createLoadingAnimation() {
        LoadingAnimation.createAnimation(vi);
    }

    private void createRefreshLayout() { //RECARREGA A LISTA DO ZERO E EXIBE O RESULTADO CARREGADO
        swipeRefreshLayout = vi.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            listaPokemonsRecyclerView.setVisibility(View.INVISIBLE);
            scrollUp();
            createLoadingAnimation();
            adapter.limpaLista();
            listaPokemonsRecyclerView.setVisibility(View.VISIBLE);
            offset = 0;
            if (isSearchingPokemons)
                buscaPokemon(ultimaBusca);
            else
                getInformacao(offset); //se ele ñ conseguir pegar a informação ele pega dnv do db
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void createRetrofit() {
        adapter = new ListaPokemonAdapter(getActivity());
        viewModel = ViewModelProviders.of(this).get(PokedexViewModel.class);
        retrofit = new PokemonRetrofit();
        retrofit.createRetrofit();
        new PokedexRetrofitResponse(viewModel, getActivity());
        isScrolledToEnd = true;
        offset = 0;
        getInformacao(offset);
        // offset += 20;
    }

    private void createRecyclerView() {
        listaPokemonsRecyclerView = vi.findViewById(R.id.pokemon_recyclerview);
        listaPokemonsRecyclerView.setAdapter(adapter);
        listaPokemonsRecyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        listaPokemonsRecyclerView.setLayoutManager(gridLayoutManager);
    }

    private void createScrollListener() {
        listaPokemonsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int itemVisivelCount = gridLayoutManager.getChildCount();
                    int totalItemCount = gridLayoutManager.getItemCount();
                    int ultimoItemVisivel = gridLayoutManager.findFirstVisibleItemPosition();
                    if ((itemVisivelCount + ultimoItemVisivel) >= totalItemCount) {
                        isScrolledToEnd = false;
//                        offset += 20;
                        if (!isSearchingPokemons && retrofit.isConnected()) {
                            offset += 20;
                            getInformacao(offset);
                        }
                    }
                }
            }
        });
    }

    private void getInformacao(int offset) {
        isScrolledToEnd = true;
        retrofit.getListaDaAPI(offset, adapter);
        isSearchingPokemons = false;
    }

    public void buscaPokemon(String busca) {
        createLoadingAnimation();
        ultimaBusca = busca;
        retrofit.getBusca(offset, adapter, busca, arrowBack);
        isSearchingPokemons = true;
    }

    public void retornaParaListaPrincipal() {
        adapter.limpaLista();
        offset = 0;
        getInformacao(offset);
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