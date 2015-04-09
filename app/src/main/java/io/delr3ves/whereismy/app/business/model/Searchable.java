package io.delr3ves.whereismy.app.business.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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

    public Searchable(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
