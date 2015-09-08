package tk.yurkiv.recipes;

import android.app.Application;

import com.github.mmin18.layoutcast.LayoutCast;

/**
 * Created by yurkiv on 07.09.2015.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG){
            LayoutCast.init(this);
        }

    }
}
