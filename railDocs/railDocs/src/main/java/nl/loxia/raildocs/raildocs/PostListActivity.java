package nl.loxia.raildocs.raildocs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.androidannotations.annotations.EActivity;

import nl.loxia.raildocs.raildocs.nl.loxia.raildocs.util.BundleKeys;

@EActivity(R.layout.activity_list)
public class PostListActivity extends Activity implements PostListFragment.OnFragmentInteractionListener {
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
        Intent intent = DossierListActivity_.intent(this).get();
        intent.putExtra(BundleKeys.POST, post);
        startActivity(intent);
    }

    @Override
    public void itemGeselecteerd(String selectie) {

    }
}
