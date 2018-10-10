package ua.yurezcv.stoic.data.model;

public class QuoteDisplay {

    private int id;
    private String quote;
    private String author;
    private String source;
    private boolean isFavorite;

    private boolean isSelected;

    public QuoteDisplay(int id, String quote, String author, String source, boolean isFavorite) {
        this.id = id;
        this.quote = quote;
        this.author = author;
        this.source = source;
        this.isFavorite = isFavorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuote() {
        // return "\u201C"+quote+"\u201D";
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getSharableContent() {
        return "\u201C"+quote+"\u201D " + getAuthor();
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
