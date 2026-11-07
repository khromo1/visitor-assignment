package streaming.catalog;

public class AudiobookChapter implements CatalogItem {
    private final String title;
    private final String bookTitle;
    private final int durationSeconds;
    private final int playCount;

    public AudiobookChapter(String title, String bookTitle, int durationSeconds, int playCount) {
        this.title = title;
        this.bookTitle = bookTitle;
        this.durationSeconds = durationSeconds;
        this.playCount = playCount;
    }

    public String getTitle() { return title; }
    public String getBookTitle() { return bookTitle; }
    public int getDurationSeconds() { return durationSeconds; }
    public int getPlayCount() { return playCount; }

    @Override
    public void accept(CatalogVisitor visitor) {
        visitor.visitAudiobookChapter(this);
    }
}
