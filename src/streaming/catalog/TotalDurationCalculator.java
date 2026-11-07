package streaming.catalog;

// Concrete Visitor #3: added later to show that no existing class has to change.
public class TotalDurationCalculator implements CatalogVisitor {
    private int totalSeconds = 0;

    @Override
    public void visitSong(Song song) {
        totalSeconds += song.getDurationSeconds();
    }

    @Override
    public void visitPodcastEpisode(PodcastEpisode episode) {
        totalSeconds += episode.getDurationSeconds();
    }

    @Override
    public void visitAudiobookChapter(AudiobookChapter chapter) {
        totalSeconds += chapter.getDurationSeconds();
    }

    public int getTotalSeconds() {
        return totalSeconds;
    }
}