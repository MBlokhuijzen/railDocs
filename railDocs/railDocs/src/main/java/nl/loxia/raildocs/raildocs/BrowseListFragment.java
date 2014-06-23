package nl.loxia.raildocs.raildocs;

import android.app.Activity;
import android.app.ListFragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.util.List;

import nl.loxia.raildocs.raildocs.nl.loxia.raildocs.util.CredentialsStore;

/**
 * Created by Tiemen on 13-6-2014.
 */
@EFragment
public class BrowseListFragment extends ListFragment implements AbsListView.OnItemClickListener {
    @ViewById(android.R.id.list)
    protected AbsListView listView;
    @RestService
    protected IRailCloud railCloud;
    @Bean
    protected CredentialsStore credentialsStore;
    protected ListAdapter listAdapter;
    protected List<String> listContent;
    protected OnFragmentInteractionListener listener;

    @UiThread
    protected void loadingError() {
        Toast.makeText(getActivity(), "Fout tijdens laden", Toast.LENGTH_SHORT).show();
    }

    @UiThread
    protected void setData(List<String> data) {
        this.listContent = data;
        listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, data);
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
            listener.itemGeselecteerd(listContent.get((int) id));
        }
    }

    public interface OnFragmentInteractionListener {
        public void itemGeselecteerd(String selectie);
    }
}
