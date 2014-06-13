package nl.loxia.raildocs.raildocs;

import android.widget.ArrayAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.springframework.web.client.RestClientException;

import java.util.List;

import nl.loxia.raildocs.raildocs.nl.loxia.raildocs.util.BundleKeys;

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
public class DossierListFragment extends BrowseListFragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DossierListFragment() {
    }

    @AfterViews
    public void init() {
        listView.setOnItemClickListener(this);
        loadData(getArguments().getString(BundleKeys.POST));
    }

    @Background
    protected void loadData(String post) {
        try {
            credentialsStore.setCredentials(railCloud);
            setData(railCloud.getDossiers(post));
        } catch (RestClientException e) {
            loadingError();
        }
    }

    @UiThread
    protected void setData(List<String> data) {
        this.listContent = data;
        listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, data);
        setListAdapter(listAdapter);
    }

}
