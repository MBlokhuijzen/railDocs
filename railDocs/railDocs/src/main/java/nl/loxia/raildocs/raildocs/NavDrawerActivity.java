package nl.loxia.raildocs.raildocs;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Tiemen on 3-7-2014.
 */
@EActivity
public abstract class NavDrawerActivity extends Activity {
    private ActionBarDrawerToggle drawerToggle;
    @ViewById
    protected DrawerLayout drawerLayout;
    @ViewById
    protected ListView drawer;
    @Bean
    NavDrawerItemAdapter adapter;

    @ItemClick(R.id.drawer)
    public void itemClick(int position) {
        int selectedId = adapter.getItem(position).id;
        navDrawerItemSelected(selectedId);
        drawer.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawer);
    }

    protected abstract void navDrawerItemSelected(int item);

    @AfterViews
    protected void init() {
        drawer.setAdapter(adapter);
        adapter.initAdapter();
        drawer.setItemChecked(0, true);

        drawerToggle = new MyActionBarDrawerToggle();

        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyActionBarDrawerToggle extends ActionBarDrawerToggle {
        private CharSequence title = getTitle();
        private CharSequence subtitle = getActionBar().getSubtitle();

        public MyActionBarDrawerToggle() {
            super(NavDrawerActivity.this, NavDrawerActivity.this.drawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close);
        }

        public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);
            getActionBar().setTitle(title);
            getActionBar().setSubtitle(subtitle);
        }

        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            title = getTitle();
            subtitle = getActionBar().getSubtitle();
            getActionBar().setTitle(R.string.drawerOpenTitle);
            if (subtitle != null) {
                getActionBar().setSubtitle("");
            }
        }
    }
}
