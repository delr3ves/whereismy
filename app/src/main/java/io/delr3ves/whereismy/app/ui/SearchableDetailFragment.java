package io.delr3ves.whereismy.app.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import io.delr3ves.whereismy.app.R;
import io.delr3ves.whereismy.app.WhereismyApplication;
import io.delr3ves.whereismy.app.business.model.Searchable;
import io.delr3ves.whereismy.app.dao.SearchableDao;

import javax.inject.Inject;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public class SearchableDetailFragment extends Fragment {

    @Inject
    SearchableDao searchableDao;

    private Searchable searchable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((WhereismyApplication) getActivity().getApplication()).inject(this);
        final View rootView = inflater.inflate(R.layout.fragment_searchable_detail, container, false);
        searchable = (Searchable) getActivity().getIntent().getSerializableExtra(
                SearchableDetailActivity.SEARCHABLE_EXTRA);

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete_searchable) {
            searchableDao.delete(searchable);
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
