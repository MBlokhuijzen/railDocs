package nl.loxia.raildocs.raildocs;

import android.content.Context;
import android.location.Location;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.RestClientException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

import nl.loxia.raildocs.raildocs.domein.Blad;
import nl.loxia.raildocs.raildocs.util.CredentialsStore;
import nl.loxia.raildocs.raildocs.util.SwodWithLocation;

/**
 * Created by Tiemen on 24-6-2014.
 */
@EBean
public class SwodWithLocationAdapter extends BaseAdapter {
    private List<SwodWithLocation> swodWithLocations = new ArrayList<SwodWithLocation>();

    @RootContext
    protected Context context;
    private NearbyListFragment nearbyListFragment;

    public void initAdapter(NearbyListFragment nearbyListFragment, Location location) {
        this.nearbyListFragment = nearbyListFragment;
        loadData(location);
    }

    @Background
    protected void loadData(final Location location) {
        List<SwodWithLocation> swodWithLocationList = null;
        try {
            swodWithLocationList = readFile();
            Collections.sort(swodWithLocationList, new Comparator<SwodWithLocation>() {
                @Override
                public int compare(SwodWithLocation lhs, SwodWithLocation rhs) {
                    return (int) (location.distanceTo(lhs.location) - location.distanceTo(rhs.location));
                }
            });
            setData(new LinkedHashSet<SwodWithLocation>(swodWithLocationList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<SwodWithLocation> readFile() throws IOException {
        List<SwodWithLocation> locations = new ArrayList<SwodWithLocation>();

        InputStream inputStream = NearbyListFragment.class.getResourceAsStream("posities.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            String[] split = line.split(",");
            Location location = new Location("location");
            location.setLatitude(Double.parseDouble(split[5]));
            location.setLongitude(Double.parseDouble(split[4]));
            locations.add(new SwodWithLocation(split[6], split[7], split[8], location));
        }

        return locations;
    }

    @UiThread
    void setData(LinkedHashSet<SwodWithLocation> swods) {
        this.swodWithLocations = new ArrayList<SwodWithLocation>(swods);
        nearbyListFragment.setListAdapter(this);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SwodWithLocationView view;
        if (convertView == null) {
            view = SwodWithLocationView_.build(context);
        } else {
            view = (SwodWithLocationView) convertView;
        }
        view.bind(getItem(position));
        return view;
    }

    @Override
    public int getCount() {
        return swodWithLocations.size();
    }

    @Override
    public SwodWithLocation getItem(int position) {
        return swodWithLocations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
