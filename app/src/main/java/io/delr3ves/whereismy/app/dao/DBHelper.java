package io.delr3ves.whereismy.app.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import io.delr3ves.whereismy.app.business.model.TrackedLocation;
import io.delr3ves.whereismy.app.business.model.Searchable;

import javax.inject.Inject;
import java.sql.SQLException;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "whereismy.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Searchable, Long> searchableDao;
    private Dao<TrackedLocation, Long> trackedLocationDao;

    @Inject
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            initializeTable(connectionSource, Searchable.class);
            initializeTable(connectionSource, TrackedLocation.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeTable(ConnectionSource connectionSource, Class clazz) throws SQLException {
        TableUtils.dropTable(connectionSource, clazz, true);
        TableUtils.createTable(connectionSource, clazz);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        onCreate(database, connectionSource);
    }

    @Override
    public void close() {
        super.close();
        searchableDao = null;
    }

    public Dao<Searchable, Long> getSearchableDao() {
        try {
            if (searchableDao == null) {
                searchableDao = getDao(Searchable.class);
            }
            return searchableDao;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<TrackedLocation, Long> getTrackedLocationDaoDao() {
        try {
            if (trackedLocationDao == null) {
                trackedLocationDao = getDao(TrackedLocation.class);
            }
            return trackedLocationDao;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
