package nl.loxia.raildocs.raildocs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_list)
public class DossierListActivity extends Activity implements DossierListFragment.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            DossierListFragment_ fragment = new DossierListFragment_();
            fragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
        }
    }

    @Override
    public void itemGeselecteerd(String selectie) {
    }
}
