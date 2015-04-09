package io.delr3ves.whereismy.app.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.AdapterView;
import android.widget.ListView;
import io.delr3ves.whereismy.app.R;
import io.delr3ves.whereismy.app.WhereismyApplication;
import io.delr3ves.whereismy.app.business.model.Searchable;
import io.delr3ves.whereismy.app.dao.SearchableDao;
import io.delr3ves.whereismy.app.ui.adapter.SearchableSmallAdapter;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static io.delr3ves.whereismy.app.ui.SearchableDetailActivity.SEARCHABLE_EXTRA;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public class ListSearchablesFragment extends Fragment {

    @Inject
    SearchableDao searchableDao;

    private List<Searchable> searchables;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((WhereismyApplication) getActivity().getApplication()).inject(this);
        this.searchables = searchableDao.findSearchables();
        View v;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            v = inflater.inflate(R.layout.fragment_list_searchables, container, false);
        } else {
            v = super.onCreateView(inflater, container, savedInstanceState);
        }

        View.OnClickListener addNewSearchableListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewSearchable();
            }
        };


        ListView searchablesView = (ListView) v.findViewById(R.id.searchables);
        View emptySearchablesView = v.findViewById(R.id.emptySearchables);
        emptySearchablesView.setOnClickListener(addNewSearchableListener);

        searchablesView.setEmptyView(emptySearchablesView);
        final SearchableSmallAdapter searchablesAdapter = new SearchableSmallAdapter(getActivity(), R.layout.searchable_small_item, searchables);
        searchablesView.setAdapter(
                searchablesAdapter);

        searchablesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Searchable selectedItem = searchables.get(position);
                Intent intent = new Intent(getActivity(), SearchableDetailActivity.class);
                intent.putExtra(SEARCHABLE_EXTRA, selectedItem);
                startActivity(intent);
            }
        });

        View addButton = v.findViewById(R.id.add_button);
        makeRoundButton(addButton);
        addButton.setOnClickListener(addNewSearchableListener);

        return v;
    }


    private void makeRoundButton(View addButton) {
        addButton.setOutlineProvider(new ViewOutlineProvider() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void getOutline(View view, Outline outline) {
                int diameter = getResources().getDimensionPixelSize(R.dimen.diameter);
                outline.setOval(0, 0, diameter, diameter);
            }
        });
        addButton.setClipToOutline(true);
    }

    private void addNewSearchable() {
        Intent intent = new Intent(getActivity(), AddNewSearchableActivity.class);
        startActivity(intent);
    }

}
