package ua.yurezcv.stoic.ui.quotes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
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
import ua.yurezcv.stoic.data.model.QuoteDisplay;


public class QuotesFragment extends Fragment {

    private static final String KEY_RECYCLER_VIEW_STATE = "rv-quotes-state";

    // UI elements
    @BindView(R.id.rv_quotes)
    RecyclerView mQuotesGridRecycleView;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar mProgressBar;

    @BindView(R.id.tv_error_message)
    TextView mErrorTextView;

    // private OnFragmentInteractionListener mListener;

    private QuotesRecyclerViewAdapter mQuotesListAdapter;

    public QuotesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mQuotesListAdapter = new QuotesRecyclerViewAdapter(new ArrayList<QuoteDisplay>());

        setupViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quotes, container, false);
        ButterKnife.bind(this, view);


        // init the recycler view
        mQuotesGridRecycleView.setLayoutManager(
                new StaggeredGridLayoutManager(calcNumberOfColumns(),
                        StaggeredGridLayoutManager.VERTICAL));
        mQuotesGridRecycleView.setAdapter(mQuotesListAdapter);

        // restore RecyclerView state if the bundle exists
        if (savedInstanceState != null) {
            Parcelable recyclerViewState = savedInstanceState.getParcelable(KEY_RECYCLER_VIEW_STATE);
            mQuotesGridRecycleView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }

        return view;
    }

/*    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

/*    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    private void setupViewModel() {
        QuotesViewModel viewModel = ViewModelProviders.of(this).get(QuotesViewModel.class);
        viewModel.getQuotes().observe(this, new Observer<List<QuoteDisplay>>() {
            @Override
            public void onChanged(@Nullable List<QuoteDisplay> quoteList) {

                mQuotesListAdapter.setData(quoteList);

                if (!mQuotesListAdapter.isEmpty()) {
                    hideProgressBar();
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // save RecyclerView state
        outState.putParcelable(KEY_RECYCLER_VIEW_STATE, mQuotesGridRecycleView.getLayoutManager().onSaveInstanceState());
    }

    private int calcNumberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 600;
        int width = displayMetrics.widthPixels;
        return width / widthDivider;
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        mQuotesGridRecycleView.setVisibility(View.VISIBLE);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
 /*   public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }*/
}
