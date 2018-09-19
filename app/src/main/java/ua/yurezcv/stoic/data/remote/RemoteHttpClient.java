package ua.yurezcv.stoic.data.remote;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ua.yurezcv.stoic.data.model.Author;
import ua.yurezcv.stoic.data.model.QuoteJson;
import ua.yurezcv.stoic.utils.Utils;

public final class RemoteHttpClient {

    private static final String URL_QUOTES = "https://www.dropbox.com/s/jzmd70gsjr5quqd/sample-quotes.json?raw=1";
    private static final String URL_AUTHORS = "https://www.dropbox.com/s/bpfvkt8l4q5nz4w/sample-authors.json?raw=1";

    private final OkHttpClient mClient;

    RemoteHttpClient() {
        mClient = new OkHttpClient();
    }

    private Request prepareRequest(String url) {
        return new Request.Builder().url(url).build();
    }

    public List<Author> getAuthors() throws IOException {
        Request request = prepareRequest(URL_AUTHORS);

        Response response = mClient.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);

        return Utils.parseAuthorsJson(response.body().string());
    }

    public List<QuoteJson> getQuotes() throws IOException {
        Request request = prepareRequest(URL_QUOTES);

        Response response = mClient.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);

        return Utils.parseQuotesJson(response.body().string());
    }

}
