package nl.loxia.raildocs.raildocs;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.springframework.web.client.RestClientException;

import java.util.List;

import nl.loxia.raildocs.raildocs.nl.loxia.raildocs.util.BundleKeys;

@EFragment(R.layout.fragment_post)
public class DossierListFragment extends BrowseListFragment implements AbsListView.OnItemClickListener {
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != listener) {
            listener.dossierGeselecteerd(listContent.get((int) id));
        }
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
}
