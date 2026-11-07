package streaming.catalog;

import java.util.ArrayList;
import java.util.List;

public class Catalog {
    private final List<CatalogItem> items = new ArrayList<>();

    public void add(CatalogItem item) {
        items.add(item);
    }

    public void accept(CatalogVisitor visitor) {
        for (CatalogItem item : items) {
            item.accept(visitor);
        }
    }
}