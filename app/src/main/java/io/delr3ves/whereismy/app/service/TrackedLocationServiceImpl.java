package io.delr3ves.whereismy.app.service;

import io.delr3ves.whereismy.app.business.model.Searchable;
import io.delr3ves.whereismy.app.business.model.TrackedLocation;
import io.delr3ves.whereismy.app.dao.SearchableDao;
import io.delr3ves.whereismy.app.dao.TrackedLocationDao;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public class TrackedLocationServiceImpl implements TrackedLocationService {

    private SearchableDao searchableDao;
    private TrackedLocationDao locationDao;


    @Inject
    public TrackedLocationServiceImpl(SearchableDao searchableDao, TrackedLocationDao locationDao) {
        this.searchableDao = searchableDao;
        this.locationDao = locationDao;
    }

    @Override
    public void locate(List<Searchable> searchables) {
        for (Searchable searchable : searchables) {
            TrackedLocation location = new TrackedLocation();
            location.setDate(new Date());
            location.setStatus(TrackedLocation.Status.FOUND);
            location.setLatitude(35.3);
            location.setLongitude(-3.3);
            locationDao.create(location);
            searchable.trackLocation(location);
            searchableDao.update(searchable);
        }
    }
}
