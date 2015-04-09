package io.delr3ves.whereismy.app.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import io.delr3ves.whereismy.app.R;
import io.delr3ves.whereismy.app.dao.DBHelper;
import io.delr3ves.whereismy.app.ui.ListSearchablesFragment;

import javax.inject.Inject;


public class ListSearchablesActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_searchables);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ListSearchablesFragment())
                    .commit();
        }
    }
}
