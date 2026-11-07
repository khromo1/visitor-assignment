package streaming.catalog;

import java.util.Locale;

public class Client {
    private static final int SECONDS_PER_MINUTE = 60;

    public static void main(String[] args) {
        Catalog catalog = new Catalog();
        catalog.add(new Song("Blinding Lights", "The Weeknd", 200, 9500));
        catalog.add(new Song("Take Five", "Dave Brubeck", 324, 3100));
        catalog.add(new PodcastEpisode("Design Patterns 101", "Tech Talks", 1800, 1200));
        catalog.add(new AudiobookChapter("Chapter 1", "Clean Code", 2400, 800));

        RoyaltyCalculator royalties = new RoyaltyCalculator();
        catalog.accept(royalties);
        System.out.println("Royalties");
        System.out.println(String.format(Locale.US, "  Total payout: $%.2f", royalties.getTotalRoyalties()));

        CatalogExporter exporter = new CatalogExporter();
        catalog.accept(exporter);
        System.out.println("Export");
        exporter.getLines().forEach(line -> System.out.println("  " + line));

        TotalDurationCalculator duration = new TotalDurationCalculator();
        catalog.accept(duration);
        int total = duration.getTotalSeconds();
        System.out.println("Duration");
        System.out.println("  Total listening time: " + total / SECONDS_PER_MINUTE + " min " + total % SECONDS_PER_MINUTE + " s");
    }
}