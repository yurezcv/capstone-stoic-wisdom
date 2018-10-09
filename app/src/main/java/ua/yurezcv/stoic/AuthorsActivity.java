package ua.yurezcv.stoic;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import ua.yurezcv.stoic.data.model.Author;
import ua.yurezcv.stoic.ui.authors.AuthorDetailsFragment;
import ua.yurezcv.stoic.ui.authors.AuthorsFragment;

public class AuthorsActivity extends AppCompatActivity implements AuthorsFragment.OnAuthorsListFragmentInteractionListener {

    final static String TAG_FRAGMENT_AUTHORS = "fragment_authors";
    final static String TAG_FRAGMENT_AUTHOR_DETAILS = "fragment_author_details";

    public static Intent createIntent(Context context) {
        return new Intent(context, AuthorsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authors);
        setupActionBar();

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fl_authors, new AuthorsFragment(), TAG_FRAGMENT_AUTHORS);
            transaction.commitNow();
        }
    }

    @Override
    public void onListFragmentInteraction(Author author) {
        AuthorDetailsFragment detailsFragment = AuthorDetailsFragment.newInstance(author);
        FragmentManager manager = getSupportFragmentManager();
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                // .addSharedElement(, transitionName)
                .replace(R.id.fl_authors, detailsFragment, TAG_FRAGMENT_AUTHOR_DETAILS)
                .addToBackStack(TAG_FRAGMENT_AUTHORS)
                .commit();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
