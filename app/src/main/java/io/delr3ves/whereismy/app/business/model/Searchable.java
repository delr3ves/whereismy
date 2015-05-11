package io.delr3ves.whereismy.app.business.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * @author Sergio Arroyo - @delr3ves
 */
@Data
@DatabaseTable
@NoArgsConstructor
public class Searchable implements Serializable {

    public static final String ID = "id";
    @DatabaseField(generatedId = true, columnName = ID)
    private Long id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String description;
    @DatabaseField
    private String deviceId;
    @DatabaseField
    private String photo;
    @DatabaseField
    private Date lastUpdate;

    @ForeignCollectionField(orderAscending = false, orderColumnName = "date", eager = false)
    private Collection<TrackedLocation> locations = new ArrayList<>();

    public Searchable(String name, String description) {
        this.name = name;
        this.description = description;
        this.lastUpdate = new Date();
    }

    public void trackLocation(TrackedLocation location) {
        locations.add(location);
        lastUpdate = location.getDate();
    }
}
