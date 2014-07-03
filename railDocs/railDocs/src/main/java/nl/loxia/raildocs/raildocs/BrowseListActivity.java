package nl.loxia.raildocs.raildocs;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import nl.loxia.raildocs.raildocs.util.BundleKeys;

@EActivity(R.layout.activity_list)
public class BrowseListActivity extends NavBarActivity implements PostListFragment.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            PostListFragment_ fragment = new PostListFragment_();
            fragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
        }
    }

    @Override
    public void postGeselecteerd(String post) {
        DossierListFragment_ fragment = new DossierListFragment_();
        getIntent().putExtra(BundleKeys.POST, post);
        fragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("dossier").commit();
        getActionBar().setSubtitle(getIntent().getStringExtra(BundleKeys.POST));
    }

    @Override
    public void dossierGeselecteerd(String dossier) {
        DocumentListFragment_ fragment = new DocumentListFragment_();
        getIntent().putExtra(BundleKeys.DOSSIER, dossier);
        fragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("document").commit();
        getActionBar().setSubtitle(getIntent().getStringExtra(BundleKeys.POST) + ", " + getIntent().getStringExtra(BundleKeys.DOSSIER));
    }

    @Override
    public void documentGeselecteerd(String document) {
        Intent intent = BladenListActivity_.intent(this).get();
        intent.putExtras(getIntent());
        intent.putExtra(BundleKeys.DOCUMENT, document);
        startActivity(intent);
    }
}
