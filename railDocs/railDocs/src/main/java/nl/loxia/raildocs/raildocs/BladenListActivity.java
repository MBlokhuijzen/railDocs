package nl.loxia.raildocs.raildocs;

import android.os.Bundle;

import org.androidannotations.annotations.EActivity;

import nl.loxia.raildocs.raildocs.util.BundleKeys;

@EActivity(R.layout.activity_list)
public class BladenListActivity extends NavDrawerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            BladenListFragment_ fragment = new BladenListFragment_();
            fragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();

            getActionBar().setSubtitle(getIntent().getStringExtra(BundleKeys.DOCUMENT));
        }
    }
}
