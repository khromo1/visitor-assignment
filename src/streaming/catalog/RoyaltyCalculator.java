package streaming.catalog;

public class RoyaltyCalculator implements CatalogVisitor {
    private static final double SONG_RATE_PER_PLAY = 0.004;
    private static final double PODCAST_RATE_PER_PLAY_MINUTE = 0.0005;
    private static final double AUDIOBOOK_RATE_PER_PLAY = 0.01;
    private static final int SECONDS_PER_MINUTE = 60;

    private double totalRoyalties = 0;

    @Override
    public void visitSong(Song song) {
        totalRoyalties += song.getPlayCount() * SONG_RATE_PER_PLAY;
    }

    @Override
    public void visitPodcastEpisode(PodcastEpisode episode) {
        double minutes = (double) episode.getDurationSeconds() / SECONDS_PER_MINUTE;
        totalRoyalties += episode.getPlayCount() * minutes * PODCAST_RATE_PER_PLAY_MINUTE;
    }

    @Override
    public void visitAudiobookChapter(AudiobookChapter chapter) {
        totalRoyalties += chapter.getPlayCount() * AUDIOBOOK_RATE_PER_PLAY;
    }

    public double getTotalRoyalties() {
        return totalRoyalties;
    }
}
