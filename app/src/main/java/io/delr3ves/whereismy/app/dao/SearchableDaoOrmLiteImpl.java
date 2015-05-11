package io.delr3ves.whereismy.app.dao;

import android.content.Context;
import android.widget.Toast;
import com.j256.ormlite.dao.Dao;
import io.delr3ves.whereismy.app.business.model.Searchable;
import io.delr3ves.whereismy.app.business.model.TrackedLocation;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public class SearchableDaoOrmLiteImpl implements SearchableDao {

    private Dao<Searchable, Long> searchableDao;
    private Context mContext;

    @Inject
    public SearchableDaoOrmLiteImpl(Dao<Searchable, Long> searchableDao, Context mContext) {
        this.searchableDao = searchableDao;
        this.mContext = mContext;
    }

    @Override
    public List<Searchable> findSearchables() {
        try {
            return searchableDao.queryForAll();
        } catch (Throwable e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Searchable> findByDeviceId(String deviceId) {
        try {
            return searchableDao.queryForEq("deviceId", deviceId);
        } catch (Throwable e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Searchable create(Searchable searchable) {
        try {
            searchableDao.create(searchable);
            return searchable;
        } catch (Throwable e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
            return new Searchable();
            //throw new RuntimeException(e); //TODO serch change this shit!
        }
    }

    @Override
    public Searchable update(Searchable searchable) {
        try {
            searchableDao.update(searchable);
            return searchable;
        } catch (Throwable e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
            return new Searchable();
            //throw new RuntimeException(e); //TODO serch change this shit!
        }
    }

    @Override
    public void delete(Searchable searchable) {
        try {
            searchableDao.delete(searchable);
        } catch (SQLException e) {
            throw new RuntimeException(e); //TODO serch change this shit!
        }
    }
}
