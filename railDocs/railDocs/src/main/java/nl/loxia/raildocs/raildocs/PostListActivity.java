package nl.loxia.raildocs.raildocs;

import android.app.Activity;
import android.os.Bundle;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_post_list)
public class PostListActivity extends Activity implements PostListFragment.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new PostListFragment_()).commit();
        }
    }

    @Override
    public void postGeselecteerd(String post) {

    }
}
