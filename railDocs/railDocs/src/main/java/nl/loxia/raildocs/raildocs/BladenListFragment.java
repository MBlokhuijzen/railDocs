package nl.loxia.raildocs.raildocs;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
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

import nl.loxia.raildocs.raildocs.nl.loxia.raildocs.domein.Blad;
import nl.loxia.raildocs.raildocs.nl.loxia.raildocs.util.BundleKeys;
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
public class BladenListFragment extends ListFragment implements AbsListView.OnItemClickListener {
    @ViewById(android.R.id.list)
    protected AbsListView listView;
    @RestService
    protected IRailCloud railCloud;
    @Bean
    protected CredentialsStore credentialsStore;
    protected ListAdapter listAdapter;
    protected List<Blad> listContent;
    protected OnFragmentInteractionListener listener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BladenListFragment() {
    }

    @AfterViews
    public void init() {
        listView.setOnItemClickListener(this);
        String post = getArguments().getString(BundleKeys.POST);
        String dossier = getArguments().getString(BundleKeys.DOSSIER);
        String document = getArguments().getString(BundleKeys.DOCUMENT);
        loadData(post, dossier, document);
    }

    @Background
    protected void loadData(String post, String dossier, String document) {
        try {
            credentialsStore.setCredentials(railCloud);
            setData(railCloud.getBladen(post, dossier, document));
        } catch (RestClientException e) {
            loadingError();
        }
    }


    @UiThread
    protected void loadingError() {
        Toast.makeText(getActivity(), "Fout tijdens laden", Toast.LENGTH_SHORT).show();
    }

    @UiThread
    protected void setData(List<Blad> data) {
        this.listContent = data;

        listAdapter = new BladArrayAdapter(getActivity(), data);
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
        // todo download and open pdf
    }

    public interface OnFragmentInteractionListener {
        public void itemGeselecteerd(String selectie);
    }

}
