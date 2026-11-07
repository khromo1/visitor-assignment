# Catalog Visitor

Software Design Patterns — Assignment 6 (Visitor pattern), Astana IT University.

## What this is

Same music streaming theme as the earlier assignments. A streaming catalog holds a small, stable set of item types: songs, podcast episodes and audiobook chapters. That set rarely changes, but the *operations* people want to run over it keep growing: work out royalty payouts, export the catalog, total up listening time, and so on. Each operation treats every item type differently (a song is paid per play, a podcast per play-minute, an audiobook chapter per play).

Putting each operation inside the item classes would mean editing `Song`, `PodcastEpisode` and `AudiobookChapter` every time a new report is needed, and mixing unrelated concerns (billing, exporting, statistics) into classes that should only describe an item. Checking types with `instanceof` in one big method would be even worse. Visitor keeps the item classes untouched and puts each operation in its own class.

## Structure

```
src/streaming/catalog/
  CatalogItem.java            - Element interface: accept(visitor)
  Song.java                   - Concrete Element
  PodcastEpisode.java         - Concrete Element
  AudiobookChapter.java       - Concrete Element
  CatalogVisitor.java         - Visitor interface: one visitX() per element type
  RoyaltyCalculator.java      - Concrete Visitor: royalty payouts
  CatalogExporter.java        - Concrete Visitor: export lines
  TotalDurationCalculator.java- Concrete Visitor: total listening time
  Catalog.java                - Object Structure: holds items, calls accept() on each
  Client.java                 - builds the catalog and runs the visitors
```

## How double dispatch works

Two decisions have to be made for every call: which *element* is it, and which *operation* is being run. Java only picks the method based on one object at a time, so it takes two calls:

```java
// Catalog (object structure)
item.accept(visitor);              // 1st dispatch: runs the accept() of the real element type

// inside Song
visitor.visitSong(this);           // 2nd dispatch: runs the visitSong() of the real visitor type
```

The first call picks the element's `accept`, which already knows exactly what it is, so it can call the matching `visitX` and pass itself. The second call then picks the visitor's own implementation. No `instanceof`, no type checks anywhere.

## Running it

```
javac $(find src -name '*.java') -d out
java -cp out streaming.catalog.Client
```

Expected output:
```
Royalties
  Total payout: $76.40
Export
  SONG;Blinding Lights;The Weeknd;200
  SONG;Take Five;Dave Brubeck;324
  PODCAST;Design Patterns 101;Tech Talks;1800
  AUDIOBOOK;Chapter 1;Clean Code;2400
Duration
  Total listening time: 78 min 44 s
```

## Clean code choices

**1. Meaningful names that separate the two roles.**
Elements are nouns from the domain (`Song`, `PodcastEpisode`), visitors are named after the operation they perform (`RoyaltyCalculator`, `CatalogExporter`). Reading a name tells you whether a class describes data or does work on it.

**2. No `instanceof` or type-checking chains.**
Double dispatch does the work that a chain like `if (item instanceof Song) ... else if (item instanceof PodcastEpisode) ...` would do. A search for `instanceof` in the source finds nothing.

**3. Single responsibility: one visitor, one operation.**
`RoyaltyCalculator` only calculates royalties for every element type, `CatalogExporter` only exports, `TotalDurationCalculator` only sums time. Each visitor can be read, changed and tested on its own.

**4. Small, justified encapsulation trade-off.**
Visitors live outside the elements, so they need to read element data. I handled that with read-only getters and made the elements immutable (all fields `private final`, no setters). A visitor can see what it needs but cannot change an item. The cost is that the element's data is visible through its getters, which is the usual price of Visitor.

**5. Extensibility without touching existing code.**
`TotalDurationCalculator` was added as a third visitor after the other two worked. Nothing in `Song`, `PodcastEpisode`, `AudiobookChapter`, `Catalog` or the other visitors had to change.

**Magic numbers.** The payout rates and `SECONDS_PER_MINUTE` are named constants, so the royalty rules are readable at a glance.

## Trade-off to be aware of

Visitor makes it easy to add a new *operation* but harder to add a new *element type*: a fourth item type (say, a live radio show) would need a new `visitX` method in the interface and in every existing visitor. It fits here because the item types are stable and the reports keep growing.

## Notes

Strategy (previous assignment) swaps one algorithm inside a context. Visitor does something different: it runs a whole operation across a structure of different element types, with the behavior depending on both the element and the operation.
