package io.delr3ves.whereismy.app.dao;

import com.j256.ormlite.dao.Dao;
import io.delr3ves.whereismy.app.business.model.Searchable;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public class SearchableDaoOrmLiteImpl implements SearchableDao {

    private Dao<Searchable, Long> searchableDao;

    @Inject
    public SearchableDaoOrmLiteImpl(Dao<Searchable, Long> searchableDao) {
        this.searchableDao = searchableDao;
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
    public Searchable save(Searchable searchable) {
        try {
            searchableDao.create(searchable);
            return searchable;
        } catch (Throwable e) {
            throw new RuntimeException(e); //TODO serch change this shit!
        }
    }

    @Override
    public void delete(Searchable searchable) {
        try {
            searchableDao.delete(searchable);
        } catch (SQLException e) {
            throw  new RuntimeException(e); //TODO serch change this shit!
        }
    }
}
