package ua.yurezcv.stoic.ui.authors;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.yurezcv.stoic.R;
import ua.yurezcv.stoic.data.model.Author;
import ua.yurezcv.stoic.utils.EmptyLocalDataException;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnAuthorsListFragmentInteractionListener}
 * interface.
 */
public class AuthorsFragment extends Fragment {

    private static final String KEY_RECYCLER_VIEW_STATE = "rv-authors-state";

    private OnAuthorsListFragmentInteractionListener mListener;

    private AuthorsRecyclerViewAdapter mAdapter;

    // UI elements
    @BindView(R.id.rv_authors)
    RecyclerView mAuthorsRecycleView;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar mProgressBar;

    @BindView(R.id.tv_error_message)
    TextView mErrorTextView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AuthorsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authors, container, false);
        ButterKnife.bind(this, view);

        mAdapter = new AuthorsRecyclerViewAdapter(new ArrayList<Author>(), mListener);
        mAuthorsRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuthorsRecycleView.setAdapter(mAdapter);

        setupViewModel();

        // restore RecyclerView state if the bundle exists
        if (savedInstanceState != null) {
            Parcelable recyclerViewState = savedInstanceState.getParcelable(KEY_RECYCLER_VIEW_STATE);
            mAuthorsRecycleView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAuthorsListFragmentInteractionListener) {
            mListener = (OnAuthorsListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAuthorsListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // save RecyclerView state
        outState.putParcelable(KEY_RECYCLER_VIEW_STATE, mAuthorsRecycleView.getLayoutManager().onSaveInstanceState());
    }

    private void setupViewModel() {
        final AuthorsViewModel viewModel = ViewModelProviders.of(this).get(AuthorsViewModel.class);
        viewModel.getAuthors().observe(this, new Observer<List<Author>>() {

            @Override
            public void onChanged(@Nullable List<Author> authors) {
                if (authors != null && !authors.isEmpty()) {
                    mAdapter.setData(authors);
                    hideProgressBar();
                } else if (viewModel.isError()) {
                    String error;
                    Throwable throwable = viewModel.getError();
                    if(throwable instanceof EmptyLocalDataException) {
                        error = getString(R.string.error_no_author);
                        showErrorMessage(error);
                    }
                }
            }
        });
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        mAuthorsRecycleView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(String message) {
        mErrorTextView.setText(message);
        mErrorTextView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnAuthorsListFragmentInteractionListener {
        void onListFragmentInteraction(Author item);
    }
}
