package streaming.catalog;

public interface CatalogItem {
    void accept(CatalogVisitor visitor);
}
