package nl.loxia.raildocs.raildocs;

/**
 * Created by Tiemen on 3-7-2014.
 */
public class NavDrawerItem {
    public static final int BROWSE = 0;
    public static final int NEARBY = 1;

    public final int iconResource;
    public final int textResource;
    public final int id;

    public NavDrawerItem(int textResource, int iconResource, int id) {
        this.textResource = textResource;
        this.iconResource = iconResource;
        this.id = id;
    }
}
