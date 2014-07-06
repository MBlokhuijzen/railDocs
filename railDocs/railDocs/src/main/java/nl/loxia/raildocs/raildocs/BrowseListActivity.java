package nl.loxia.raildocs.raildocs;

import android.content.Intent;
import android.os.Bundle;

import org.androidannotations.annotations.EActivity;

import nl.loxia.raildocs.raildocs.util.BundleKeys;

@EActivity(R.layout.activity_list)
public class BrowseListActivity extends NavDrawerActivity implements PostListFragment.OnFragmentInteractionListener {
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
        BladenListFragment_ fragment = new BladenListFragment_();
        getIntent().putExtra(BundleKeys.DOCUMENT, document);
        fragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("bladen").commit();
        getActionBar().setSubtitle(getIntent().getStringExtra(BundleKeys.DOCUMENT));
    }
}
