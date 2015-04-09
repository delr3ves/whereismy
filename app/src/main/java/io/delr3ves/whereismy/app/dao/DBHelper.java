package io.delr3ves.whereismy.app.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
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

    @Inject
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Searchable.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
}
