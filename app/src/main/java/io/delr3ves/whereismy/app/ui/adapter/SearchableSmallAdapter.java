package io.delr3ves.whereismy.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import io.delr3ves.whereismy.app.R;
import io.delr3ves.whereismy.app.business.model.Searchable;

import java.util.List;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public class SearchableSmallAdapter extends ArrayAdapter<Searchable> {

    private int resource;

    public SearchableSmallAdapter(Context context, int resource, List<Searchable> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(resource, null);
        }

        Searchable searchable = getItem(position);
        TextView name = (TextView) v.findViewById(R.id.searchable_name);
        name.setText(searchable.getName());
        TextView description = (TextView) v.findViewById(R.id.searchable_description);
        description.setText(searchable.getDescription());

        TextView lastLocated = (TextView) v.findViewById(R.id.searchable_last_located);
        lastLocated.setText(new Integer(searchable.getLocations().size()).toString());

        return v;
    }
}
