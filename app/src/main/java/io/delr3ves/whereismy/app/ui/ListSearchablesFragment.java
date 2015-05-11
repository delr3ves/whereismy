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
import android.widget.Toast;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import io.delr3ves.whereismy.app.R;
import io.delr3ves.whereismy.app.WhereismyApplication;
import io.delr3ves.whereismy.app.business.model.Searchable;
import io.delr3ves.whereismy.app.dao.SearchableDao;
import io.delr3ves.whereismy.app.ui.adapter.SearchableSmallAdapter;

import javax.inject.Inject;
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

        final SearchableSmallAdapter searchablesAdapter =
                new SearchableSmallAdapter(getActivity(), R.layout.searchable_small_item, searchables);

        configureSearchableList(v, addNewSearchableListener, searchablesAdapter);
        configureAddNewSearchableButton(v, addNewSearchableListener);
        cofigurePullToRefresh(v, searchablesAdapter);

        return v;
    }

    private void configureSearchableList(View v, View.OnClickListener addNewSearchableListener, SearchableSmallAdapter searchablesAdapter) {
        ListView searchablesView = (ListView) v.findViewById(R.id.searchables);
        searchablesView.setAdapter(searchablesAdapter);
        View emptySearchablesView = v.findViewById(R.id.emptySearchables);
        emptySearchablesView.setOnClickListener(addNewSearchableListener);

        searchablesView.setEmptyView(emptySearchablesView);

        searchablesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Searchable selectedItem = searchables.get(position);
                Intent intent = new Intent(getActivity(), SearchableDetailActivity.class);
                intent.putExtra(SEARCHABLE_EXTRA, selectedItem);
                startActivity(intent);
            }
        });
    }

    private void configureAddNewSearchableButton(View v, View.OnClickListener addNewSearchableListener) {
        View addButton = v.findViewById(R.id.add_button);
        makeRoundButton(addButton);
        addButton.setOnClickListener(addNewSearchableListener);
    }

    private void cofigurePullToRefresh(View v, final SearchableSmallAdapter searchablesAdapter) {
        PtrFrameLayout ptrFrameLayout = (PtrFrameLayout) v.findViewById(R.id.searchables_ptr_list);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view1) {
                return true;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                Toast.makeText(getActivity(), "refreshing", Toast.LENGTH_SHORT).show();
                refreshSearchables();
                searchablesAdapter.notifyDataSetChanged();
                ptrFrameLayout.refreshComplete();
            }
        });
    }

    private void refreshSearchables() {
        searchables.removeAll(searchables);
        searchables.addAll(searchableDao.findSearchables());
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
