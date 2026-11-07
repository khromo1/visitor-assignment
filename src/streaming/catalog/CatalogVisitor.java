package streaming.catalog;

public interface CatalogVisitor {
    void visitSong(Song song);
    void visitPodcastEpisode(PodcastEpisode episode);
    void visitAudiobookChapter(AudiobookChapter chapter);
}
