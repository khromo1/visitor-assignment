package streaming.catalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class CatalogExporter implements CatalogVisitor {
    private static final String SEPARATOR = ";";

    private final List<String> lines = new ArrayList<>();

    @Override
    public void visitSong(Song song) {
        lines.add(String.join(SEPARATOR,
                "SONG", song.getTitle(), song.getArtist(), String.valueOf(song.getDurationSeconds())));
    }

    @Override
    public void visitPodcastEpisode(PodcastEpisode episode) {
        lines.add(String.join(SEPARATOR,
                "PODCAST", episode.getTitle(), episode.getShowName(), String.valueOf(episode.getDurationSeconds())));
    }

    @Override
    public void visitAudiobookChapter(AudiobookChapter chapter) {
        lines.add(String.join(SEPARATOR,
                "AUDIOBOOK", chapter.getTitle(), chapter.getBookTitle(), String.valueOf(chapter.getDurationSeconds())));
    }

    public List<String> getLines() {
        return Collections.unmodifiableList(lines);
    }
}