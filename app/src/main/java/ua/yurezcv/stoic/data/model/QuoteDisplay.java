package ua.yurezcv.stoic.data.model;

public class QuoteDisplay {

    private int id;
    private String quote;
    private String author;
    private String source;

    public QuoteDisplay(int id, String quote, String author, String source) {
        this.id = id;
        this.quote = quote;
        this.author = author;
        this.source = source;
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
}
