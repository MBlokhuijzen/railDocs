package nl.loxia.raildocs.raildocs;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
@EFragment
public class PostFragment extends Fragment implements AbsListView.OnItemClickListener {
    private OnFragmentInteractionListener listener;

    @ViewById(android.R.id.list)
    protected AbsListView listView;

    private ListAdapter listAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PostFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Arrays.asList("a", "b"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(this);

        return view;
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
            listener.postGeselecteerd("AAP");
        }
    }

    public void setEmptyText(CharSequence emptyText) {
        View emptyView = listView.getEmptyView();

        if (emptyText instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    public interface OnFragmentInteractionListener {
        public void postGeselecteerd(String post);
    }

}
