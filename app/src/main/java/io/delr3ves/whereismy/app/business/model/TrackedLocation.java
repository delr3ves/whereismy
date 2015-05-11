package io.delr3ves.whereismy.app.business.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Sergio Arroyo - @delr3ves
 */
@Data
@DatabaseTable
@NoArgsConstructor
public class TrackedLocation {

    public enum Status {FOUND, NOT_FOUND}

    public static final String ID = "id";
    @DatabaseField(generatedId = true, columnName = ID)
    private Long id;
    @DatabaseField
    private Date date;
    @DatabaseField
    private Status status;
    @DatabaseField
    private Double latitude;
    @DatabaseField
    private Double longitude;
    @DatabaseField(foreign=true, columnName="searchable_id" )
    private Searchable searchable;
}
