package ice.lib.crash;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/*
*
* CrashProvider
*
* 用于在应用启动时对主线程进行CrashHandler的设置
*
* */
public class CrashProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        if (Config.setHandlerOnMainThreadWhenAppStart) {
            new CrashHandler(getContext());
        }
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
