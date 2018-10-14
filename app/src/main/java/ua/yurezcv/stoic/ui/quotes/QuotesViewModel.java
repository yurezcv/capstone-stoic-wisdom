package ua.yurezcv.stoic.ui.quotes;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import ua.yurezcv.stoic.StoicWisdomApp;
import ua.yurezcv.stoic.data.DataRepository;
import ua.yurezcv.stoic.data.DataSource;
import ua.yurezcv.stoic.data.model.QuoteDisplay;
import ua.yurezcv.stoic.utils.EmptyLocalDataException;
import ua.yurezcv.stoic.utils.threading.AppExecutors;

public class QuotesViewModel extends AndroidViewModel {

    private MutableLiveData<List<QuoteDisplay>> mQuotes;

    public QuotesViewModel(@NonNull Application application) {
        super(application);

        mQuotes = new MutableLiveData<>();

        loadAll();
    }

    void loadAll() {
        loadQuotes(DataSource.FILTER_ALL);
    }

    void loadFavorites() {
        loadQuotes(DataSource.FILTER_FAVORITES);
    }

    private void loadQuotes(int filter) {
        AppExecutors appExecutors = StoicWisdomApp.getExecutors();
        DataRepository dataRepository = DataRepository.getInstance(this.getApplication(),
                appExecutors);

        dataRepository.getQuotes(filter, new DataSource.GetQuotesCallback() {
            @Override
            public void onSuccess(List<QuoteDisplay> quotes) {
                mQuotes.postValue(quotes);
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (throwable instanceof EmptyLocalDataException) {
                    mQuotes.setValue(null);
                }
            }
        });
    }

    public LiveData<List<QuoteDisplay>> getData() {
        return mQuotes;
    }
}
