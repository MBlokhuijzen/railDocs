package nl.loxia.raildocs.raildocs;

import android.widget.ArrayAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.springframework.web.client.RestClientException;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
@EFragment(R.layout.fragment_post)
public class PostListFragment extends BrowseListFragment {
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PostListFragment() {
    }

    @AfterViews
    public void init() {
        listView.setOnItemClickListener(this);
        loadData();
    }

    @Background
    protected void loadData() {
        try {
            credentialsStore.setCredentials(railCloud);
            setData(railCloud.getPosten());
        } catch (RestClientException e) {
            loadingError();
        }
    }
}
