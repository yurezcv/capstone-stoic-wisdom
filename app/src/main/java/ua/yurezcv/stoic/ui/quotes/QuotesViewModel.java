package ua.yurezcv.stoic.ui.quotes;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Executors;

import ua.yurezcv.stoic.data.DataRepository;
import ua.yurezcv.stoic.data.DataSource;
import ua.yurezcv.stoic.data.model.Quote;
import ua.yurezcv.stoic.utils.threading.AppExecutors;
import ua.yurezcv.stoic.utils.threading.DiskIOThreadExecutor;

public class QuotesViewModel extends AndroidViewModel {

    private static final String TAG = "QuotesViewModel";

    private MutableLiveData<List<Quote>> mQuotes;

    // TODO finish getting data from the Repository
    public QuotesViewModel(@NonNull Application application) {
        super(application);

        mQuotes = new MutableLiveData<>();

        AppExecutors appExecutors = AppExecutors.getInstance(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(AppExecutors.THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());

        DataRepository dataRepository = DataRepository.getInstance(this.getApplication(),
                appExecutors);

        dataRepository.getQuotes(new DataSource.GetQuotesCallback() {
            @Override
            public void onSuccess(List<Quote> quotes) {
                Log.d(TAG, "onSuccess");
                mQuotes.setValue(quotes);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d(TAG, "onFailure");
                Log.e(TAG, throwable.getMessage());
            }

            @Override
            public void onNetworkFailure() {

            }
        });
    }

    public LiveData<List<Quote>> getQuotes() {
        return mQuotes;
    }
}
