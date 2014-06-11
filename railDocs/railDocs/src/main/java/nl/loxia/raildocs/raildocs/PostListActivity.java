package nl.loxia.raildocs.raildocs;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_post_list)
public class PostListActivity extends Activity implements PostFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new PostFragment()).commit();
        }
    }

    @Override
    public void postGeselecteerd(String post) {

    }
}
