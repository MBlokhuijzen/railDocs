package nl.loxia.raildocs.raildocs;

import android.os.Bundle;

import org.androidannotations.annotations.EActivity;

import nl.loxia.raildocs.raildocs.util.BundleKeys;

@EActivity(R.layout.activity_list)
public class BrowseListActivity extends NavDrawerActivity implements PostListFragment.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            startBrowse();
        }
    }

    private void startBrowse() {
        PostListFragment_ fragment = new PostListFragment_();
        fragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    private void startNearby() {
        NearbyListFragment fragment = new NearbyListFragment_();
        fragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    public void postGeselecteerd(String post) {
        DossierListFragment_ fragment = new DossierListFragment_();
        getIntent().putExtra(BundleKeys.POST, post);
        fragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("dossier").commit();
    }

    @Override
    public void dossierGeselecteerd(String dossier) {
        DocumentListFragment_ fragment = new DocumentListFragment_();
        getIntent().putExtra(BundleKeys.DOSSIER, dossier);
        fragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("document").commit();
    }

    @Override
    public void documentGeselecteerd(String document) {
        BladenListFragment_ fragment = new BladenListFragment_();
        getIntent().putExtra(BundleKeys.DOCUMENT, document);
        fragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack("bladen").commit();
    }

    @Override
    protected void navDrawerItemSelected(int item) {
        switch (item) {
            case NavDrawerItem.BROWSE:
                startBrowse();
                break;
            case NavDrawerItem.NEARBY:
                startNearby();
                break;
        }
    }
}
