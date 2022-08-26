package gov.dol.childlabor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
        return string.replace(" ", "_").replace("-", "_").replace("/", "_").replace("ô", "o").replace("ã", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("á", "a")
                .replace("(", "").replace(")", "").replace(",", "").replace("'", "").replace("`", "").replace(".", "").replace("ç", "c").toLowerCase();
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

    public static void openPDFIntent(Activity activity, String filename) {
        AppHelpers.saveAssetInExternalStorage(activity, filename);
        File pdfFile = new File(activity.getExternalFilesDir(null), filename);//File path
        if (pdfFile.exists()) //Checking for the file is exist or not
        {
            Uri path = Uri.fromFile(pdfFile);
            Intent objIntent = new Intent(Intent.ACTION_VIEW);
            objIntent.setDataAndType(path, "application/pdf");
            objIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            if (objIntent.resolveActivity(activity.getPackageManager()) == null) {
                Toast.makeText(activity, "No PDF Reader Application Installed! ", Toast.LENGTH_SHORT).show();
            }
            else {
                activity.startActivity(objIntent);//Staring the pdf viewer
            }
        } else {
            Toast.makeText(activity, "The file not exists! ", Toast.LENGTH_SHORT).show();
        }
    }

    private static void saveAssetInExternalStorage(Activity activity, String filename) {
        AssetManager assetManager = activity.getAssets();

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            File outFile = new File(activity.getExternalFilesDir(null), filename);
            out = new FileOutputStream(outFile);

            byte[] buffer = new byte[1024];
            int read;
            while((read = in.read(buffer)) != -1){
                out.write(buffer, 0, read);
            }
        } catch(IOException e) {
            Log.e("tag", "Failed to copy asset file: " + filename, e);
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // NOOP
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // NOOP
                }
            }
        }
    }
}
