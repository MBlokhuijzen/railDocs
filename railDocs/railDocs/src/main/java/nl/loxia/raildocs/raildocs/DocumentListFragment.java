package nl.loxia.raildocs.raildocs;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.springframework.web.client.RestClientException;

import java.util.List;

import nl.loxia.raildocs.raildocs.util.BundleKeys;

@EFragment(R.layout.fragment_post)
public class DocumentListFragment extends BrowseListFragment implements AbsListView.OnItemClickListener {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DocumentListFragment() {
    }

    @AfterViews
    public void init() {
        getActivity().setTitle(R.string.title_activity_browse);
        listView.setOnItemClickListener(this);
        loadData(getArguments().getString(BundleKeys.POST), getArguments().getString(BundleKeys.DOSSIER));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != listener) {
            listener.documentGeselecteerd(listContent.get((int) id));
        }
    }


    @Background
    protected void loadData(String post, String dossier) {
        try {
            credentialsStore.setCredentials(railCloud);
            List<String> documenten = railCloud.getDocumenten(post, dossier);
            setData(documenten);
            if (documenten.size() == 1) {
                listener.documentGeselecteerd(documenten.get(0));
            }
        } catch (RestClientException e) {
            loadingError();
        }
    }
}
