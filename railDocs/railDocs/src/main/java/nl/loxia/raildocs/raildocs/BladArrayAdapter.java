package nl.loxia.raildocs.raildocs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Tiemen on 24-6-2014.
 */
public class BladArrayAdapter extends ArrayAdapter<Blad> {
    private final List<Blad> bladen;
    private final Context context;

    public BladArrayAdapter(Context context, List<Blad> bladen) {
        super(context, android.R.layout.simple_list_item_1, bladen);
        this.bladen = bladen;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(bladen.get(position).idnummer);

        return view;
    }
}
