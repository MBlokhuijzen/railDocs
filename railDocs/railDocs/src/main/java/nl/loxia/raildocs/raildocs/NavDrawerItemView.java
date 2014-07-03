package nl.loxia.raildocs.raildocs;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Tiemen on 3-7-2014.
 */
@EViewGroup(R.layout.navdrawer_item)
public class NavDrawerItemView extends LinearLayout {
    @ViewById
    protected TextView text;
    private NavDrawerItem item;

    public NavDrawerItemView(Context context) {
        super(context);
    }

    public void bind(NavDrawerItem item) {
        this.item = item;
        text.setText(item.textResource);
    }
}
