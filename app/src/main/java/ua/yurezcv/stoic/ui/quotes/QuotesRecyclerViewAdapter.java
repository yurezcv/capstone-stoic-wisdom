package ua.yurezcv.stoic.ui.quotes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.yurezcv.stoic.R;
import ua.yurezcv.stoic.data.model.Quote;
import ua.yurezcv.stoic.data.model.QuoteDisplay;

public class QuotesRecyclerViewAdapter extends RecyclerView.Adapter<QuotesRecyclerViewAdapter.ViewHolder> {

    private final List<QuoteDisplay> mValues;
    // private final OnListFragmentInteractionListener mListener;

    QuotesRecyclerViewAdapter(List<QuoteDisplay> mValues) {
        this.mValues = mValues;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_quote, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuoteDisplay quote = mValues.get(position);
        holder.bind(quote);

        /*holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mRecipe);
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setData(List<QuoteDisplay> quotes) {
        if (mValues != null) {
            mValues.addAll(quotes);
            notifyDataSetChanged();
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;

        @BindView(R.id.tv_quote)
        TextView mQuoteTextView;

        @BindView(R.id.tv_author)
        TextView mAuthorTextView;

        @BindView(R.id.tv_source)
        TextView mSourceTextView;

        QuoteDisplay mQuote;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        void bind(QuoteDisplay quote) {
            mQuote = quote;
            mQuoteTextView.setText(mQuote.getQuote());
            mAuthorTextView.setText(mQuote.getAuthor());
            mSourceTextView.setText(mQuote.getSource());
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mQuoteTextView.getText() + "'";
        }
    }
}
