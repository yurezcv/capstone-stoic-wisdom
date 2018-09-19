package ua.yurezcv.stoic.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ua.yurezcv.stoic.data.model.Author;
import ua.yurezcv.stoic.data.model.QuoteJson;

public final class Utils {
    private Utils() {}

    /* Create a type quote for gson */
    private static Type getQuoteType() {
        return new TypeToken<ArrayList<QuoteJson>>(){}.getType();
    }

    /* Create a type author for gson */
    private static Type getAuthorType() {
        return new TypeToken<ArrayList<Author>>(){}.getType();
    }

    /* Parse JSON with quotes with Google gson library */
    public static List<QuoteJson> parseQuotesJson(String inputJson) {
        Gson gson = new Gson();
        return gson.fromJson(inputJson, getQuoteType());
    }

    /* Parse JSON with quotes with Google gson library */
    public static List<Author> parseAuthorsJson(String inputJson) {
        Gson gson = new Gson();
        return gson.fromJson(inputJson, getAuthorType());
    }
}
