package ua.yurezcv.stoic.ui.authors;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import ua.yurezcv.stoic.StoicWisdomApp;
import ua.yurezcv.stoic.data.DataRepository;
import ua.yurezcv.stoic.data.DataSource;
import ua.yurezcv.stoic.data.model.Author;
import ua.yurezcv.stoic.utils.EmptyLocalDataException;
import ua.yurezcv.stoic.utils.threading.AppExecutors;

class AuthorsViewModel extends AndroidViewModel {

    private MutableLiveData<List<Author>> mAuthors;

    private boolean isError;
    private Throwable mErrorThrowable;

    public AuthorsViewModel(@NonNull Application application) {
        super(application);

        mAuthors = new MutableLiveData<>();

        loadData();
    }

    private void loadData() {
        AppExecutors appExecutors = StoicWisdomApp.getExecutors();
        DataRepository dataRepository = DataRepository.getInstance(this.getApplication(),
                appExecutors);

        dataRepository.getAuthors(new DataSource.GetAuthorsCallback() {
            @Override
            public void onSuccess(List<Author> authors) {
                mAuthors.postValue(authors);
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (throwable instanceof EmptyLocalDataException) {
                    mAuthors.setValue(null);
                }
                isError = true;
                mErrorThrowable = throwable;
            }
        });
    }

    public LiveData<List<Author>> getAuthors() {
        return mAuthors;
    }

    public boolean isError() {
        return isError;
    }

    public Throwable getError() {
        return mErrorThrowable;
    }
}
