package io.delr3ves.whereismy.app;

import android.app.Application;
import dagger.ObjectGraph;
import io.delr3ves.whereismy.app.dao.DBHelper;
import io.delr3ves.whereismy.app.di.AppModule;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sergio Arroyo - @delr3ves
 */
public class WhereismyApplication extends Application {

    private ObjectGraph objectGraph;

    @Override public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(getModules().toArray());
        objectGraph.inject(this);
    }

    private List<Object> getModules() {
        return Arrays.<Object>asList(new AppModule(this));
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }


}
