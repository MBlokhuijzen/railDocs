package nl.loxia.raildocs.raildocs.util;

import android.location.Location;

/**
 * Created by Tiemen on 7-7-2014.
 */
public class SwodWithLocation {
    public String post;
    public String dossier;
    public String document;
    public Location location;

    public SwodWithLocation(String post, String dossier, String document, Location location) {
        this.post = post;
        this.dossier = dossier;
        this.document = document;
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SwodWithLocation that = (SwodWithLocation) o;

        if (!document.equals(that.document)) return false;
        if (!dossier.equals(that.dossier)) return false;
        if (!post.equals(that.post)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = post.hashCode();
        result = 31 * result + dossier.hashCode();
        result = 31 * result + document.hashCode();
        return result;
    }
}
