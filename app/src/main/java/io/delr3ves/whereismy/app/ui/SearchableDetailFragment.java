package io.delr3ves.whereismy.app.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
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

        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_searchable_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete_searchable) {
            conformableRemove();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void conformableRemove() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE: // Yes button clicked Toast.makeText(AlertDialogActivity.this, "Yes Clicked", Toast.LENGTH_LONG).show(); break; case DialogInterface.BUTTON_NEGATIVE: // No button clicked // do nothing Toast.makeText(AlertDialogActivity.this, "No Clicked", Toast.LENGTH_LONG).show(); break; } } }; AlertDialog.Builder builder = new AlertDialog.Builder(this); builder.setMessage("Are you sure?") .setPositiveButton("Yes", dialogClickListener) .setNegativeButton("No", dialogClickListener).show(); }
                        searchableDao.delete(searchable);
                        getActivity().finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE: // No button clicked // do nothing Toast.makeText(AlertDialogActivity.this, "No Clicked", Toast.LENGTH_LONG).show(); break; } }
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.confirm_delete_searchable))
                .setPositiveButton(getString(R.string.confirm_delete_searchable_positive), dialogClickListener)
                .setNegativeButton(getString(R.string.confirm_delete_searchable_negative), dialogClickListener).show();

    }
}
