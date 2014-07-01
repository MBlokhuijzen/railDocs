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

/**
 * Created by Tiemen on 26-6-2014.
 */
@EViewGroup(R.layout.list_item_blad)
public class BladView extends RelativeLayout {
    private final Context context;
    @ViewById
    protected TextView idnummer;
    @ViewById
    protected TextView domein;
    @ViewById
    protected TextView bladnummer;
    @ViewById
    protected TextView ids;
    @ViewById
    protected TextView versie;

    @Bean
    protected CredentialsStore credentialsStore;

    private Blad blad;

    public BladView(Context context) {
        super(context);
        this.context = context;
    }

    public void bind(Blad blad) {
        this.blad = blad;
        idnummer.setText(blad.idnummer);
        domein.setText(blad.domeintype);
        ids.setText(blad.indienststellingsdatum);
        versie.setText(blad.versie);
        bladnummer.setText(blad.bladnummer);
    }

    @Click
    protected void downloadButton() {
        String uri = "https://" + silentEncode(credentialsStore.username) + ":" + silentEncode(credentialsStore.password) + "@" + blad.metaInfo.relaties.get("pdfBlad").get(0).url.replaceFirst("https://", "");
        Intent openIntent = new Intent(Intent.ACTION_VIEW);
        openIntent.setData(Uri.parse(uri));
        context.startActivity(openIntent);
    }

    private String silentEncode(String in) {
        try {
            return URLEncoder.encode(in, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
