package nl.loxia.raildocs.raildocs;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

import nl.loxia.raildocs.raildocs.nl.loxia.raildocs.BladView;
import nl.loxia.raildocs.raildocs.nl.loxia.raildocs.BladView_;
import nl.loxia.raildocs.raildocs.nl.loxia.raildocs.domein.Blad;
import nl.loxia.raildocs.raildocs.nl.loxia.raildocs.util.CredentialsStore;

/**
 * Created by Tiemen on 24-6-2014.
 */
@EBean
public class BladArrayAdapter extends BaseAdapter {
    private List<Blad> bladen = new ArrayList<Blad>();

    @RootContext
    protected Context context;

    @RestService
    protected IRailCloud railCloud;
    @Bean
    protected CredentialsStore credentialsStore;


    public void initAdapter(String post, String dossier, String document) {
        loadPosts(post, dossier, document);
    }

    @Background
    void loadPosts(String post, String dossier, String document) {
        try {
            credentialsStore.setCredentials(railCloud);
            setData(railCloud.getBladen(post, dossier, document));
        } catch (RestClientException exception) {
            exception.printStackTrace();
        }
    }

    @UiThread
    void setData(List<Blad> bladen) {
        this.bladen = bladen;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BladView bladView;
        if (convertView == null) {
            bladView = BladView_.build(context);
        } else {
            bladView = (BladView) convertView;
        }
        bladView.bind(getItem(position));
        return bladView;
    }

    @Override
    public int getCount() {
        return bladen.size();
    }

    @Override
    public Blad getItem(int position) {
        return bladen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
