package com.academy.fundamentals.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.academy.fundamentals.R;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Button btnTrailer = findViewById(R.id.details_btn_trailer);
        btnTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trailerUrl = DetailsActivity.this.getString(R.string.infinity_war_trailer);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                startActivity(browserIntent);
            }
        });
    }
}
