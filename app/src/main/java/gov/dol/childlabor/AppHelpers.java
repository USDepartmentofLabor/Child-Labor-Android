package gov.dol.childlabor;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by tru on 10/28/2015.
 */
public class AppHelpers {

    public static Drawable getGoodDrawable(Context context, String name) {
        name = "icons_" + AppHelpers.getFormattedString(name);
        return AppHelpers.getDrawable(context, name);
    }

    public static Drawable getFlagDrawable(Context context, String name) {
        name = AppHelpers.getFormattedString(name);
        return AppHelpers.getDrawable(context, name);
    }

    public static Drawable getMapDrawable(Context context, String name) {
        name = AppHelpers.getFormattedString(name) + "_map";
        return AppHelpers.getDrawable(context, name);
    }

    public static Country.SectionHeader createSectionHeader(String header) {
        return new Country("Country").new SectionHeader(header.subSequence(0, 1).charAt(0), header);
    }

    private static Drawable getDrawable(Context context, String name) {
        Resources resources = context.getResources();

        Drawable drawable;
        try {
            int resourceId =  resources.getIdentifier(name, "drawable", context.getPackageName());
            drawable = resources.getDrawable(resourceId);
        }
        catch(Resources.NotFoundException e) {
            drawable = null;
        }

        return drawable;
    }

    private static int getReportResourceId(Context context, String name) {
        Resources resources = context.getResources();

        return resources.getIdentifier(AppHelpers.getFormattedString(name), "drawable", context.getPackageName());
    }

    public static String getFormattedString(String string) {
        return string.replace(" ", "_").replace("-", "_").replace("/", "_").replace("ô", "o").replace("ã", "a").replace("é", "e").replace("í", "i")
                .replace("(", "").replace(")", "").replace(",", "").replace("'", "").replace("`", "").replace(".", "").toLowerCase();
    }

    public static void trackScreenView(AnalyticsApplication application, String screenName) {
        Tracker mTracker = application.getDefaultTracker();
        mTracker.setScreenName(screenName);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public static void trackCountryEvent(AnalyticsApplication application, String action, String label) {
        AppHelpers.trackEvent(application, "Country", action, label);
    }

    public static void trackGoodEvent(AnalyticsApplication application, String action, String label) {
        AppHelpers.trackEvent(application, "Good", action, label);
    }

    public static void trackEvent(AnalyticsApplication application, String category, String action, String label) {
        Tracker mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label)
                .setValue(1)
                .build());
    }
}
