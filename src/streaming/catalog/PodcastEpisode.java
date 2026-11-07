package streaming.catalog;

public class PodcastEpisode implements CatalogItem {
    private final String title;
    private final String showName;
    private final int durationSeconds;
    private final int playCount;

    public PodcastEpisode(String title, String showName, int durationSeconds, int playCount) {
        this.title = title;
        this.showName = showName;
        this.durationSeconds = durationSeconds;
        this.playCount = playCount;
    }

    public String getTitle() { return title; }
    public String getShowName() { return showName; }
    public int getDurationSeconds() { return durationSeconds; }
    public int getPlayCount() { return playCount; }

    @Override
    public void accept(CatalogVisitor visitor) {
        visitor.visitPodcastEpisode(this);
    }
}
