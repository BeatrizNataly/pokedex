package com.projeto.pokedex.ui.activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.projeto.pokedex.R;
import com.projeto.pokedex.pokedex.api.PokedexApiService;
import com.projeto.pokedex.pokedex.api.PokedexRetrofitResponse;
import com.projeto.pokedex.pokedex.api.PokemonRetrofit;
import com.projeto.pokedex.pokedex.database.PokedexViewModel;
import com.projeto.pokedex.pokedex.database.pokeinfo.tables.Ability;
import com.projeto.pokedex.pokedex.database.pokeinfo.tables.PokeInfo;
import com.projeto.pokedex.pokedex.database.pokeinfo.tables.Pokemon_Favorite;
import com.projeto.pokedex.pokedex.database.pokeinfo.tables.Type;
import com.projeto.pokedex.pokedex.entities.Pokemon;
import com.projeto.pokedex.pokedex.entities.PokemonInfo;
import com.projeto.pokedex.recyclerview.adapter.PokemonAbilityAdapter;
import com.projeto.pokedex.recyclerview.adapter.PokemonTypeAdapter;
import com.projeto.pokedex.util.ColorUtil;
import com.projeto.pokedex.util.ConstantUtil;
import com.projeto.pokedex.util.FormatUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoPokemonActivity extends AppCompatActivity {

    private static final String APP_TITLE_INFO = "Sobre o pokémon";
    private ProgressBar spinner;
    private TextView nomePokemon, pesoAlturaPokemon, noConnection;
    private RecyclerView abilitiesRecyclerview, typeRecyclerview;
    private ImageView imagemPokemon, backgroundTheme;
    private ToggleButton iconeFavorito;
    private PokemonRetrofit retrofit;
    private int pokemonId;
    private PokemonAbilityAdapter adapterAbility;
    private PokemonTypeAdapter adapterType;
    private PokedexViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pokemon);
        setTitle(APP_TITLE_INFO);
        createLoadingAnimation();
        getViews();
        getRetrofit();
        getPokemon();
    }

    private void createLoadingAnimation() {
        spinner = findViewById(R.id.spinner);
        spinner.setVisibility(View.VISIBLE);
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> spinner.setVisibility(View.GONE), 300);
    }

    private void getRetrofit() {
        viewModel = new PokedexViewModel(getApplication());
        retrofit = new PokemonRetrofit();
        new PokedexRetrofitResponse(this);
        retrofit.createRetrofit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_item_arrow_back) finish();
        return super.onOptionsItemSelected(item);
    }

    private void getPokemon() {
        if (getIntent().getSerializableExtra("extra_pokemon") != null) {
            Pokemon pokemon = (Pokemon) getIntent().getSerializableExtra("extra_pokemon");
            nomePokemon.setText(pokemon.getName().toUpperCase());
            pokemonId = pokemon.getNumber();
            setFavoriteStatus();
            setFotoPokemon();
            getInfosDaApi();
            toggleFavoriteIcon();
        } else
            Toast.makeText(this, ConstantUtil.POKEMON_NOT_FOUND, Toast.LENGTH_SHORT).show();
    }

    private void setBackgroundTheme(int number) {
        int backgroundColor = ColorUtil.getBackgroundColor(number);
        backgroundTheme.getBackground().setColorFilter(backgroundColor, PorterDuff.Mode.DARKEN);
    }

    private void setFavoriteStatus() {
        try {
            if (viewModel.getIsPokemonFavorite(pokemonId) == 1)
                iconeFavorito.setChecked(true);
        } catch (IndexOutOfBoundsException ie) {
            viewModel.addRelationPokemonFavorite(new Pokemon_Favorite(pokemonId, 0));
            iconeFavorito.setChecked(false);
        }
    }

    private void setFotoPokemon() {
        Glide.with(this)
                .load(ConstantUtil.IMAGE_BASE_URL + pokemonId + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imagemPokemon);
    }

    private void getViews() {
        backgroundTheme = findViewById(R.id.info_background_theme);

        noConnection = findViewById(R.id.info_sem_conexao);
        noConnection.setVisibility(View.GONE);

        nomePokemon = findViewById(R.id.placeholder_nome_pokemon);
        pesoAlturaPokemon = findViewById(R.id.info_peso_altura);
        imagemPokemon = findViewById(R.id.info_imagem_pokemon);

        iconeFavorito = findViewById(R.id.info_favorito);
        createRecyclerViews();
    }

    private void createRecyclerViews() {
        LinearLayoutManager linearLayoutManager;
        GridLayoutManager gridLayoutManager;

        adapterAbility = new PokemonAbilityAdapter(this);
        abilitiesRecyclerview = findViewById(R.id.info_recyclerview_ability);
        abilitiesRecyclerview.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        abilitiesRecyclerview.setLayoutManager(linearLayoutManager);
        abilitiesRecyclerview.setAdapter(adapterAbility);

        adapterType = new PokemonTypeAdapter();
        typeRecyclerview = findViewById(R.id.info_pokemon_type);
        typeRecyclerview.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(this, 2);
        typeRecyclerview.setLayoutManager(gridLayoutManager);
        typeRecyclerview.setAdapter(adapterType);
    }

    private void getInfosDaApi() {
        PokedexApiService apiService = retrofit.getRetrofit().create(PokedexApiService.class);
        Call<PokemonInfo> callback = apiService.getInfoPokemon(pokemonId);
        callback.enqueue(new Callback<PokemonInfo>() {
            @Override
            public void onResponse(Call<PokemonInfo> call, Response<PokemonInfo> response) {
                if (response.isSuccessful()) {//insere dados com base na API
                    PokemonInfo body = setPokemonInfo(response);
                    PokedexRetrofitResponse.setInfoResponseSuccess(body);
                    setBackgroundTheme(body.getTypes().get(0).getType().getNumber());

                } else {
                    PokeInfo info = getPokeInfo();
                    setInfoFromDatabase(info);
                }
            }

            @Override
            public void onFailure(Call<PokemonInfo> call, Throwable t) {
                PokeInfo info = getPokeInfo();
                setInfoFromDatabase(info);
            }
        });
        spinner.setVisibility(View.GONE);
    }

    private PokeInfo getPokeInfo() {
        Toast.makeText(InfoPokemonActivity.this, ConstantUtil.NO_CONNECTION, Toast.LENGTH_SHORT).show();
        noConnection.setVisibility(View.VISIBLE);
        return viewModel.getPokeInfoFromDatabase(pokemonId);
    }

    @NonNull
    private PokemonInfo setPokemonInfo(Response<PokemonInfo> response) {
        PokemonInfo body = response.body();
        String pesoAltura = "Peso: " + FormatUtil.formatMedida(body.getWeight(), "kg")
                + " - Altura: " + FormatUtil.formatMedida(body.getHeight(), "m");
        adapterAbility.adicionaListaAbilities((ArrayList<PokemonInfo.Ability>) body.getAbilities());
        adapterType.adicionaLista((ArrayList<PokemonInfo.Types>) body.getTypes());
        pesoAlturaPokemon.setText(pesoAltura);
        return body;
    }

    private void setInfoFromDatabase(PokeInfo info) {
        try {
            String pesoAltura = "Peso: " + FormatUtil.formatMedida(info.getWeight(), "kg")
                    + " - Altura: " + FormatUtil.formatMedida(info.getHeight(), "m");
            pesoAlturaPokemon.setText(pesoAltura);

            viewModel.getPokemonTypesFromDatabase(info.getId()).observe(this, lista -> {
                adapterType.adicionaListaDoDatabase((ArrayList<Type>) lista);
                setBackgroundTheme(lista.get(0).getIdTipo());
            });

            viewModel.getPokemonAbilitiesFromDatabase(info.getId()).observe(this, lista -> {
                adapterAbility.adicionaListaDoDatabase((ArrayList<Ability>) lista);
            });
        } catch (NullPointerException nu) {
            Log.i("PokedexDB Null", "setInfoPesoAltura: Dado não existe no DB para pokémon " + nomePokemon.getText());
            Toast.makeText(this, ConstantUtil.ERRO_AO_CARREGAR, Toast.LENGTH_SHORT).show();
        }
    }

    public void toggleFavoriteIcon() {
        iconeFavorito.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                viewModel.addRelationPokemonFavorite(new Pokemon_Favorite(pokemonId, 1));
            } else {
                viewModel.addRelationPokemonFavorite(new Pokemon_Favorite(pokemonId, 0));
            }
        });
    }

    public void finishBtn(View view) {
        finish();
    }
}