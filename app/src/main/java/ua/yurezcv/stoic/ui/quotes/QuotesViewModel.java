package ua.yurezcv.stoic.ui.quotes;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import ua.yurezcv.stoic.data.model.Quote;

public class QuotesViewModel extends AndroidViewModel {

    private static final String TAG = "QuotesViewModel";

    private MutableLiveData<List<Quote>> mQuotes;

    // TODO finish getting data from the Repository
    public QuotesViewModel(@NonNull Application application) {
        super(application);

        mQuotes = new MutableLiveData<>();

        /*AppExecutors appExecutors = AppExecutors.getInstance(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(AppExecutors.THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());

        DataRepository dataRepository = DataRepository.getInstance(this.getApplication(),
                appExecutors);

        dataRepository.getRecipes(new DataSourceContract.GetRecipesCallback() {
            @Override
            public void onSuccess(List<Recipe> recipes) {
                mRecipes.setValue(recipes);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
            }
        });*/
    }

    public LiveData<List<Quote>> getQuotes() {
        return mQuotes;
    }
}
