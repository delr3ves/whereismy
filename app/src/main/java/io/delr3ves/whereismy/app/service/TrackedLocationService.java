package io.delr3ves.whereismy.app.service;

import io.delr3ves.whereismy.app.business.model.Searchable;

import java.util.List;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public interface TrackedLocationService {

    /**
     * In charge to locate the searchable and store its location
     * @param searchables
     */
    void locate(List<Searchable> searchables);
}
