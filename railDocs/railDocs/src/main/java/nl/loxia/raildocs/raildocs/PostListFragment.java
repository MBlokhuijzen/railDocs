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

@EFragment(R.layout.fragment_post)
public class PostListFragment extends BrowseListFragment implements AbsListView.OnItemClickListener {
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PostListFragment() {
    }

    @AfterViews
    public void init() {
        getActivity().setTitle(R.string.title_activity_browse);
        listView.setOnItemClickListener(this);
        loadData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != listener) {
            listener.postGeselecteerd(listContent.get((int) id));
        }
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
