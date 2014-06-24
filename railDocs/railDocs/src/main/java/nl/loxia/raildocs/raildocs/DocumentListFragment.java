package nl.loxia.raildocs.raildocs;

import android.os.Bundle;
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
public class DocumentListFragment extends BrowseListFragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DocumentListFragment() {
    }

    @AfterViews
    public void init() {
        listView.setOnItemClickListener(this);
        loadData(getArguments().getString(BundleKeys.POST), getArguments().getString(BundleKeys.DOSSIER));
    }

    @Background
    protected void loadData(String post, String dossier) {
        try {
            credentialsStore.setCredentials(railCloud);
            List<String> documenten = railCloud.getDocumenten(post, dossier);
            setData(documenten);
            if (documenten.size() == 1) {
                listener.itemGeselecteerd(documenten.get(0));
            }
        } catch (RestClientException e) {
            loadingError();
        }
    }
}
