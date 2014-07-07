package nl.loxia.raildocs.raildocs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import nl.loxia.raildocs.raildocs.domein.Blad;
import nl.loxia.raildocs.raildocs.util.CredentialsStore;
import nl.loxia.raildocs.raildocs.util.SwodWithLocation;

/**
 * Created by Tiemen on 26-6-2014.
 */
@EViewGroup(R.layout.list_item_swodwithlocation)
public class SwodWithLocationView extends RelativeLayout {
    private final Context context;
    @ViewById
    protected TextView document;

    private SwodWithLocation swodWithLocation;

    public SwodWithLocationView(Context context) {
        super(context);
        this.context = context;
    }

    public void bind(SwodWithLocation swodWithLocation) {
        this.swodWithLocation = swodWithLocation;
        document.setText(swodWithLocation.document);
    }
}
