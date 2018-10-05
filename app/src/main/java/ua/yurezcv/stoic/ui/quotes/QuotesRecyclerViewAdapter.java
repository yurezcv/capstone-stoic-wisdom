package ua.yurezcv.stoic.ui.quotes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.yurezcv.stoic.R;
import ua.yurezcv.stoic.data.model.QuoteDisplay;

public class QuotesRecyclerViewAdapter extends RecyclerView.Adapter<QuotesRecyclerViewAdapter.ViewHolder> {

    private final List<QuoteDisplay> mValues;
    private final QuotesFragment.OnQuotesFragmentInteractionListener mListener;

    QuotesRecyclerViewAdapter(List<QuoteDisplay> mValues,
                              QuotesFragment.OnQuotesFragmentInteractionListener listener) {
        this.mValues = mValues;
        mListener = listener;
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final View mView;

        @BindView(R.id.tv_quote)
        TextView mQuoteTextView;

        @BindView(R.id.tv_author)
        TextView mAuthorTextView;

        @BindView(R.id.tv_source)
        TextView mSourceTextView;

        @BindView(R.id.btn_like_quote)
        ImageButton mLikeButton;

        @BindView(R.id.btn_share_quote)
        ImageButton mShareButton;

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
            toggleLikeIcon();

            mLikeButton.setOnClickListener(this);
            mShareButton.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mQuoteTextView.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // gets item position
            // Check if an item was deleted, but the user clicked it before the UI removed it
            if (position != RecyclerView.NO_POSITION) {
                switch (v.getId()) {
                    case R.id.btn_like_quote:
                        mQuote.setFavorite(!mQuote.isFavorite());
                        toggleLikeIcon();
                        mListener.onQuoteLike(mQuote.getId(), mQuote.isFavorite());
                        break;
                    case R.id.btn_share_quote:
                        mListener.onQuoteShare(mQuote);
                        break;
                }
            }
        }

        private void toggleLikeIcon() {
            mLikeButton.setImageResource(mQuote.isFavorite() ?
                    R.drawable.ic_favorites : R.drawable.ic_favorites_outline);
        }
    }
}
