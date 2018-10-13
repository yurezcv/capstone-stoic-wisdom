package ua.yurezcv.stoic.ui.quotes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import ua.yurezcv.stoic.data.remote.FetchDataService;
import ua.yurezcv.stoic.data.remote.FetchResultReceiver;

import static android.app.Activity.RESULT_OK;


public class QuotesFragment extends Fragment {

    private static final String KEY_RECYCLER_VIEW_STATE = "rv-quotes-state";

    // UI elements
    @BindView(R.id.rv_quotes)
    RecyclerView mQuotesGridRecycleView;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar mProgressBar;

    @BindView(R.id.tv_error_message)
    TextView mErrorTextView;

    private FetchResultReceiver mReceiver;

    private OnQuotesFragmentInteractionListener mListener;

    private QuotesRecyclerViewAdapter mQuotesListAdapter;

    public QuotesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mQuotesListAdapter = new QuotesRecyclerViewAdapter(new ArrayList<QuoteDisplay>(), mListener);

        setupViewModel(false);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnQuotesFragmentInteractionListener) {
            mListener = (OnQuotesFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnQuotesFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setupViewModel(boolean isRefresh) {
        QuotesViewModel viewModel = ViewModelProviders.of(this).get(QuotesViewModel.class);
        if (isRefresh) {
            viewModel.loadData();
        } else {
            viewModel.getQuotes().observe(this, new Observer<List<QuoteDisplay>>() {

                @Override
                public void onChanged(@Nullable List<QuoteDisplay> quoteList) {

                    if (quoteList != null) {
                        mQuotesListAdapter.setData(quoteList);
                    } else {
                        setupServiceReceiver();
                        fetchAuthors();
                        fetchQuotes();
                    }

                    if (!mQuotesListAdapter.isEmpty()) {
                        hideProgressBar();
                    }
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // save RecyclerView state
        outState.putParcelable(KEY_RECYCLER_VIEW_STATE, mQuotesGridRecycleView.getLayoutManager().onSaveInstanceState());
    }

    private void fetchQuotes() {
        startFetchService(FetchDataService.CODE_QUOTES);
    }

    private void fetchAuthors() {
        startFetchService(FetchDataService.CODE_AUTHORS);
    }

    private void startFetchService(int code) {
        Intent i = FetchDataService.createIntent(getActivity());
        i.putExtra(FetchDataService.KEY_REQUEST, code);
        if (mReceiver != null) {
            i.putExtra(FetchDataService.KEY_RECEIVER, mReceiver);
        }
        getActivity().startService(i);
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

    // Setup the callback for when data is received from the service
    private void setupServiceReceiver() {
        mReceiver = new FetchResultReceiver(new Handler());
        // This is where we specify what happens when data is received from the service
        mReceiver.setReceiver(new FetchResultReceiver.Receiver() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == RESULT_OK) {
                    setupViewModel(true);
                }
            }
        });
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
    public interface OnQuotesFragmentInteractionListener {
        void onQuoteLike(int quoteId, boolean isInFavorites);
        void onQuoteShare(QuoteDisplay quoteDisplay);
    }
}
