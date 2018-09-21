package com.academy.fundamentals.list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Toast;

import com.academy.fundamentals.R;
import com.academy.fundamentals.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity implements OnMovieClickListener {

    private List<MovieModel> movies = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        RecyclerView recyclerView = findViewById(R.id.movies_rv_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadMovies();

        recyclerView.setAdapter(new MoviesViewAdapter(movies, this));
    }

    @Override
    public void onMovieClicked(int itemPosition) {
        if (itemPosition < 0 || itemPosition >= movies.size()) return;
        MovieModel movieModel = movies.get(itemPosition);
        if (movieModel == null || TextUtils.isEmpty(movieModel.getName())) return;
        Toast.makeText(this, movieModel.getName(), Toast.LENGTH_SHORT).show();
    }

    private void loadMovies() {
        movies = new ArrayList<>(9);

        MovieModel movie1 = new MovieModel();
        MovieModel movie2 = new MovieModel();
        MovieModel movie3 = new MovieModel();
        MovieModel movie4 = new MovieModel();
        MovieModel movie5 = new MovieModel();
        MovieModel movie6 = new MovieModel();
        MovieModel movie7 = new MovieModel();
        MovieModel movie8 = new MovieModel();
        MovieModel movie9 = new MovieModel();

        movie1.setName("Jurassic World - Fallen Kingdom");
        movie2.setName("The Meg");
        movie3.setName("The First Purge");
        movie4.setName("Deadpool 2");
        movie5.setName("Black Panther");
        movie6.setName("Ocean's Eight");
        movie7.setName("Interstellar");
        movie8.setName("Thor - Ragnarok");
        movie9.setName("Guardians of the Galaxy");

        movie1.setImageRes(R.drawable.jurassic_world_fallen_kingdom);
        movie2.setImageRes(R.drawable.the_meg);
        movie3.setImageRes(R.drawable.the_first_purge);
        movie4.setImageRes(R.drawable.deadpool_2);
        movie5.setImageRes(R.drawable.black_panther);
        movie6.setImageRes(R.drawable.ocean_eight);
        movie7.setImageRes(R.drawable.interstellar);
        movie8.setImageRes(R.drawable.thor_ragnarok);
        movie9.setImageRes(R.drawable.guardians_of_the_galaxy);

        movie1.setOverview("Three years after the demise of Jurassic World, a volcanic eruption threatens the remaining dinosaurs on the isla Nublar, so Claire Dearing, the former park manager, recruits Owen Grady to help prevent the extinction of the dinosaurs once again");
        movie2.setOverview("A deep sea submersible pilot revisits his past fears in the Mariana Trench, and accidentally unleashes the seventy foot ancestor of the Great White Shark believed to be extinct");
        movie3.setOverview("To push the crime rate below one percent for the rest of the year, the New Founding Fathers of America test a sociological theory that vents aggression for one night in one isolated community. But when the violence of oppressors meets the rage of the others, the contagion will explode from the trial-city borders and spread across the nation");
        movie4.setOverview("Wisecracking mercenary Deadpool battles the evil and powerful Cable and other bad guys to save a boy's life");
        movie5.setOverview("King T'Challa returns home from America to the reclusive, technologically advanced African nation of Wakanda to serve as his country's new leader. However, T'Challa soon finds that he is challenged for the throne by factions within his own country as well as without. Using powers reserved to Wakandan kings, T'Challa assumes the Black Panther mantel to join with girlfriend Nakia, the queen-mother, his princess-kid sister, members of the Dora Milaje (the Wakandan 'special forces') and an American secret agent, to prevent Wakanda from being dragged into a world war");
        movie6.setOverview("Debbie Ocean, a criminal mastermind, gathers a crew of female thieves to pull off the heist of the century at New York's annual Met Gala");
        movie7.setOverview("Interstellar chronicles the adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage");
        movie8.setOverview("Thor is on the other side of the universe and finds himself in a race against time to get back to Asgard to stop Ragnarok, the prophecy of destruction to his homeworld and the end of Asgardian civilization, at the hands of an all-powerful new threat, the ruthless Hela");
        movie9.setOverview("Light years from Earth, 26 years after being abducted, Peter Quill finds himself the prime target of a manhunt after discovering an orb wanted by Ronan the Accuser");

        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);
        movies.add(movie4);
        movies.add(movie5);
        movies.add(movie6);
        movies.add(movie7);
        movies.add(movie8);
        movies.add(movie9);
    }
}
