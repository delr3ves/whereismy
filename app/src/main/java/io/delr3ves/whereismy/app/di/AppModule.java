package io.delr3ves.whereismy.app.di;

import android.content.Context;
import com.j256.ormlite.dao.Dao;
import dagger.Module;
import dagger.Provides;
import io.delr3ves.whereismy.app.WhereismyApplication;
import io.delr3ves.whereismy.app.business.model.Searchable;
import io.delr3ves.whereismy.app.dao.DBHelper;
import io.delr3ves.whereismy.app.dao.SearchableDao;
import io.delr3ves.whereismy.app.dao.SearchableDaoOrmLiteImpl;
import io.delr3ves.whereismy.app.receiver.BluetoothDisconnectionReceiver;
import io.delr3ves.whereismy.app.ui.AddSearchableFragment;
import io.delr3ves.whereismy.app.ui.ListSearchablesFragment;
import io.delr3ves.whereismy.app.ui.SearchableDetailActivity;
import io.delr3ves.whereismy.app.ui.SearchableDetailFragment;

import javax.inject.Singleton;

/**
 * @author Sergio Arroyo - @delr3ves
 */
@Module(
        injects = {
                WhereismyApplication.class,

                ListSearchablesFragment.class,
                AddSearchableFragment.class,
                SearchableDetailFragment.class,

                BluetoothDisconnectionReceiver.class
        },
        library = true
)
public class AppModule {

    private WhereismyApplication app;

    public AppModule(WhereismyApplication app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return app;
    }

    @Provides
    @Singleton
    public DBHelper provideDBHelper(Context context) {
        return new DBHelper(context);
    }

    @Provides
    @Singleton
    public Dao<Searchable, Long> provideSearchableDao(DBHelper dbHelper) {
        return dbHelper.getSearchableDao();
    }

    @Provides
    @Singleton
    public SearchableDao provideSearchableDao(Dao<Searchable, Long> ormLiteDao) {
        return new SearchableDaoOrmLiteImpl(ormLiteDao);
    }

}