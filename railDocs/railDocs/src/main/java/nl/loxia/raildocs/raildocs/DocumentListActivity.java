package nl.loxia.raildocs.raildocs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.androidannotations.annotations.EActivity;

import nl.loxia.raildocs.raildocs.nl.loxia.raildocs.util.BundleKeys;

@EActivity(R.layout.activity_list)
public class DocumentListActivity extends Activity implements DocumentListFragment.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            DocumentListFragment_ fragment = new DocumentListFragment_();
            fragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();

            getActionBar().setSubtitle(getIntent().getStringExtra(BundleKeys.POST) + ", " + getIntent().getStringExtra(BundleKeys.DOSSIER));
        }
    }

    @Override
    public void postGeselecteerd(String post) {

    }

    @Override
    public void dossierGeselecteerd(String dossier) {

    }

    @Override
    public void itemGeselecteerd(String selectie) {
        Intent intent = BladenListActivity_.intent(this).get();
        intent.putExtras(getIntent());
        intent.putExtra(BundleKeys.DOCUMENT, selectie);
        startActivity(intent);
    }
}
