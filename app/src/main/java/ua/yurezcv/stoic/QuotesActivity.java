package ua.yurezcv.stoic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ua.yurezcv.stoic.data.remote.FetchDataService;

public class QuotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);
    }

    public void fetchQuotes(View view) {
        Intent i = FetchDataService.createIntent(this);
        i.putExtra(FetchDataService.KEY_REQUEST, FetchDataService.CODE_QUOTES);
        startService(i);
    }

    public void fetchAuthors(View view) {
        Intent i = FetchDataService.createIntent(this);
        i.putExtra(FetchDataService.KEY_REQUEST, FetchDataService.CODE_AUTHORS);
        startService(i);
    }
}
