package ua.yurezcv.stoic.ui.authors;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ua.yurezcv.stoic.R;
import ua.yurezcv.stoic.data.model.Author;

public class AuthorDetailsFragment extends Fragment {

    private static final String ARG_PARAM_AUTHOR = "fragment_param_author";
    private static final String KEY_PARAM_AUTHOR = "key_fragment_param_author";

    private Author mAuthor;

    @BindView(R.id.iv_author_circle_image)
    CircleImageView mAuthorImageView;

    @BindView(R.id.tv_author_details_name)
    TextView mNameTextView;

    @BindView(R.id.tv_author_details_years)
    TextView mYearsTextView;

    @BindView(R.id.tv_author_details_bio)
    TextView mBioTextView;

    public AuthorDetailsFragment() {
        // Required empty public constructor
    }

    public static AuthorDetailsFragment newInstance(Author author) {
        AuthorDetailsFragment fragment = new AuthorDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM_AUTHOR, author);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAuthor = getArguments().getParcelable(ARG_PARAM_AUTHOR);
        }
        if (savedInstanceState != null) {
            mAuthor = savedInstanceState.getParcelable(KEY_PARAM_AUTHOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_author_details, container, false);
        ButterKnife.bind(this, view);

        populateViews(mAuthor);

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // save RecyclerView state
        outState.putParcelable(KEY_PARAM_AUTHOR, mAuthor);
    }

    private void populateViews(Author author) {
        mNameTextView.setText(author.getName());
        mYearsTextView.setText(author.getYearsOfLive());
        mBioTextView.setText(author.getBio());
        mAuthorImageView.setImageResource(mAuthor.getId() == 1 ?
                R.drawable.ic_sample_author_1 : R.drawable.ic_sample_author_2);
    }

    @OnClick(R.id.btn_author_details_wiki)
    void openWikiPage() {
        Intent i = new Intent(Intent.ACTION_VIEW,
                Uri.parse(mAuthor.getWikiLink()));
        startActivity(i);
    }

}
