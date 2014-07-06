package nl.loxia.raildocs.raildocs;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiemen on 24-6-2014.
 */
@EBean
public class NavDrawerItemAdapter extends BaseAdapter {
    private List<NavDrawerItem> items = new ArrayList<NavDrawerItem>();

    @RootContext
    protected Context context;

    public void initAdapter() {
        items.add(new NavDrawerItem(R.string.title_activity_browse, NavDrawerItem.BROWSE));
        items.add(new NavDrawerItem(R.string.title_activity_nearby, NavDrawerItem.NEARBY));

        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NavDrawerItemView itemView;
        if (convertView == null) {
            itemView = NavDrawerItemView_.build(context);
        } else {
            itemView = (NavDrawerItemView) convertView;
        }
        itemView.bind(getItem(position));
        return itemView;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public NavDrawerItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
