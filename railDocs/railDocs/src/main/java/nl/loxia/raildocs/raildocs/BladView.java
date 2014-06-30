package nl.loxia.raildocs.raildocs;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import nl.loxia.raildocs.raildocs.domein.Blad;

/**
 * Created by Tiemen on 26-6-2014.
 */
@EViewGroup(R.layout.list_item_blad)
public class BladView extends RelativeLayout {
    @ViewById
    protected TextView idnummer;

    private Blad blad;

    public BladView(Context context) {
        super(context);
    }

    public void bind(Blad blad) {
        this.blad = blad;
        idnummer.setText(blad.idnummer);
    }
}
