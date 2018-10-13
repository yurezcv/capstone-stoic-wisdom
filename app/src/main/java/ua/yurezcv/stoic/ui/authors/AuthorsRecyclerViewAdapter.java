package ua.yurezcv.stoic.ui.authors;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.yurezcv.stoic.R;
import ua.yurezcv.stoic.data.model.Author;
import ua.yurezcv.stoic.ui.authors.AuthorsFragment.OnAuthorsListFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Author} and makes a call to the
 * specified {@link OnAuthorsListFragmentInteractionListener}.
 */
public class AuthorsRecyclerViewAdapter extends RecyclerView.Adapter<AuthorsRecyclerViewAdapter.ViewHolder> {

    private final List<Author> mValues;
    private final OnAuthorsListFragmentInteractionListener mListener;

    AuthorsRecyclerViewAdapter(List<Author> items, OnAuthorsListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_author, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Author author = mValues.get(position);
        holder.bind(author);
    }

    public void setData(List<Author> authors) {
        if (mValues != null) {
            int currentSize = mValues.size();
            mValues.addAll(authors);
            notifyItemRangeInserted(currentSize, authors.size());
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final View mView;

        @BindView(R.id.tv_author_name)
        TextView mAuthorNameTextView;

        @BindView(R.id.tv_author_dates)
        TextView mAuthorDatesTextView;

        @BindView(R.id.iv_author_image)
        ImageView mAuthorImageView;

        Author mAuthor;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        void bind(Author author) {
            mAuthor = author;
            mAuthorNameTextView.setText(mAuthor.getName());
            mAuthorDatesTextView.setText(mAuthor.getYearsOfLive());
            mAuthorImageView.setImageResource(mAuthor.getId() == 1 ?
                    R.drawable.ic_sample_author_1 : R.drawable.ic_sample_author_2);

            mView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != mListener) {
                mListener.onListFragmentInteraction(mAuthor);
            }
        }
    }
}
