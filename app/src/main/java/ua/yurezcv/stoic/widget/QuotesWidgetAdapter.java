package ua.yurezcv.stoic.widget;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.yurezcv.stoic.R;
import ua.yurezcv.stoic.data.model.QuoteDisplay;


public class QuotesWidgetAdapter extends RecyclerView.Adapter<QuotesWidgetAdapter.ViewHolder> {

    private final List<QuoteDisplay> mValues;

    private int selectedPosition = RecyclerView.NO_POSITION;

    QuotesWidgetAdapter(List<QuoteDisplay> quotes) {
        this.mValues = quotes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_widget_quote, parent, false);
        return new QuotesWidgetAdapter.ViewHolder(view);
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

    public int getSelectedQuoteId() {
        return mValues.get(selectedPosition).getId();
    }

    public void setData(List<QuoteDisplay> quotes) {
        if (mValues != null) {
            mValues.addAll(quotes);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final View mView;

        @BindView(R.id.tv_quote)
        TextView mQuoteTextView;

        @BindView(R.id.tv_author)
        TextView mAuthorTextView;

        @BindView(R.id.tv_source)
        TextView mSourceTextView;

        @BindView(R.id.ll_widget_quote)
        LinearLayout mLayout;

        QuoteDisplay mQuote;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        void bind(QuoteDisplay quote) {
            mQuote = quote;
            mView.setOnClickListener(this);

            updateBackground();

            mQuoteTextView.setText(mQuote.getQuote());
            mAuthorTextView.setText(mQuote.getAuthor());
            mSourceTextView.setText(mQuote.getSource());
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mQuoteTextView.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            // save selected position
            if(selectedPosition != RecyclerView.NO_POSITION) {
                mValues.get(selectedPosition).setSelected(false);
                notifyItemChanged(selectedPosition);
            }
            selectedPosition = getAdapterPosition();
            mValues.get(selectedPosition).setSelected(true);
            notifyItemChanged(selectedPosition);
        }

        private void updateBackground() {
            if(mQuote.isSelected()) {
                int color = mLayout.getResources().getColor(R.color.bg_selected);
                mLayout.setBackgroundColor(color);
            } else {
                mLayout.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }
}
