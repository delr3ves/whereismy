package io.delr3ves.whereismy.app.dao;

import android.content.Context;
import android.widget.Toast;
import com.j256.ormlite.dao.Dao;
import io.delr3ves.whereismy.app.business.model.Searchable;
import io.delr3ves.whereismy.app.business.model.TrackedLocation;

import javax.inject.Inject;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public class TrackedLocationDaoOrmLiteImpl implements TrackedLocationDao {

    private Dao<TrackedLocation, Long> trackedLocationDao;
    private Context mContext;

    @Inject
    public TrackedLocationDaoOrmLiteImpl(Dao<TrackedLocation, Long> searchableDao, Context mContext) {
        this.trackedLocationDao = searchableDao;
        this.mContext = mContext;
    }

    @Override
    public TrackedLocation create(TrackedLocation trackedLocation) {
        try {
            trackedLocationDao.create(trackedLocation);
            return trackedLocation;
        } catch (Throwable e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
            return new TrackedLocation();
            //throw new RuntimeException(e); //TODO serch change this shit!
        }
    }

}
