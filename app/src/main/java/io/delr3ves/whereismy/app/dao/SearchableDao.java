package io.delr3ves.whereismy.app.dao;

import io.delr3ves.whereismy.app.business.model.Searchable;

import java.util.List;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public interface SearchableDao {

    List<Searchable> findSearchables();

    List<Searchable> findByDeviceId(String deviceId);

    Searchable save(Searchable searchable);

    void delete(Searchable searchable);

}
