package nl.loxia.raildocs.raildocs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;

import org.androidannotations.annotations.EActivity;

import nl.loxia.raildocs.raildocs.nl.loxia.raildocs.util.BundleKeys;

@EActivity(R.layout.activity_list)
public class DossierListActivity extends Activity implements DossierListFragment.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            DossierListFragment_ fragment = new DossierListFragment_();
            fragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();

            getActionBar().setSubtitle(getIntent().getStringExtra(BundleKeys.POST));
        }
    }

    @Override
    public void postGeselecteerd(String post) {

    }

    @Override
    public void itemGeselecteerd(String selectie) {
        Intent intent = DocumentListActivity_.intent(this).get();
        intent.putExtras(getIntent());
        intent.putExtra(BundleKeys.DOSSIER, selectie);
        startActivity(intent);
    }
}
