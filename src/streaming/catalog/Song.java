package streaming.catalog;

public class Song implements CatalogItem {
    private final String title;
    private final String artist;
    private final int durationSeconds;
    private final int playCount;

    public Song(String title, String artist, int durationSeconds, int playCount) {
        this.title = title;
        this.artist = artist;
        this.durationSeconds = durationSeconds;
        this.playCount = playCount;
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public int getDurationSeconds() { return durationSeconds; }
    public int getPlayCount() { return playCount; }

    @Override
    public void accept(CatalogVisitor visitor) {
        visitor.visitSong(this);   // double dispatch: the element tells the visitor what it is
    }
}
