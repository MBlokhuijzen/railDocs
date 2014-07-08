package nl.loxia.raildocs.raildocs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListFragment;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.springframework.web.client.RestClientException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.loxia.raildocs.raildocs.util.SwodWithLocation;

@EFragment(R.layout.fragment_post)
public class NearbyListFragment extends ListFragment implements AbsListView.OnItemClickListener, GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
    private LocationClient locationClient;

    @ViewById(android.R.id.list)
    protected ListView listView;

    @Bean
    SwodWithLocationAdapter adapter;

    private OnFragmentInteractionListener listener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NearbyListFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().getActionBar().setTitle(R.string.title_activity_nearby);
        locationClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        locationClient.disconnect();
    }

    @AfterViews
    public void init() {
        listView.setOnItemClickListener(this);

        locationClient = new LocationClient(getActivity(), this, this);
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
    public void onConnected(Bundle bundle) {
        if (adapter.getCount() > 0) {
            return;
        }
        Location location = locationClient.getLastLocation();
        adapter.initAdapter(this, location);
    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SwodWithLocation swod = adapter.getItem(position);
        listener.nearbyItemGeselecteerd(swod.post, swod.dossier, swod.document);
    }
}
