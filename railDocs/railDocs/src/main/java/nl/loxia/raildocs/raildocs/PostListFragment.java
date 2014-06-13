package nl.loxia.raildocs.raildocs;

import android.app.Activity;
import android.app.ListFragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.RestClientException;

import java.util.List;

import nl.loxia.raildocs.raildocs.nl.loxia.raildocs.util.CredentialsStore;

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
public class PostListFragment extends ListFragment implements AbsListView.OnItemClickListener {
    private OnFragmentInteractionListener listener;

    @ViewById(android.R.id.list)
    protected AbsListView listView;

    @RestService
    protected IRailCloud railCloud;

    @Bean
    protected CredentialsStore credentialsStore;

    private ListAdapter listAdapter;
    private List<String> posten;

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

    @UiThread
    protected void loadingError() {
        Toast.makeText(getActivity(), "Fout tijdens laden", Toast.LENGTH_SHORT).show();
    }

    @UiThread
    protected void setData(List<String> posten) {
        this.posten = posten;
        listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, posten);
        setListAdapter(listAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != listener) {
            listener.postGeselecteerd(posten.get((int) id));
        }
    }

    public interface OnFragmentInteractionListener {
        public void postGeselecteerd(String post);
    }

}
