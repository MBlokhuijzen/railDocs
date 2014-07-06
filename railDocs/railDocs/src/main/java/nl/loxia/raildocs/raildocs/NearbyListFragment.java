package nl.loxia.raildocs.raildocs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.springframework.web.client.RestClientException;

import java.util.List;

@EFragment(R.layout.fragment_post)
public class NearbyListFragment extends BrowseListFragment implements AbsListView.OnItemClickListener, GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationClient locationClient;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NearbyListFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        locationClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        locationClient.disconnect();
    }

    @AfterViews
    public void init() {
        getActivity().setTitle(R.string.title_activity_nearby);
        listView.setOnItemClickListener(this);

        locationClient = new LocationClient(getActivity(), this, this);

//        loadData(getArguments().getString(BundleKeys.POST), getArguments().getString(BundleKeys.DOSSIER));
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

    @Override
    public void onConnected(Bundle bundle) {
        Location location = locationClient.getLastLocation();
        Toast.makeText(getActivity(), "Location: " + location.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
