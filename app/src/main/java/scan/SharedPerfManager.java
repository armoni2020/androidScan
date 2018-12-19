package myPackage.scan;
import android.content.Context;
import android.content.SharedPreferences;

// all methods are static , so we can call from any where in the code
//all member variables are private, so that we can save load with our own fun only
public class SharedPrefManager {
    //this is your shared preference file name, in which we will save data
    public static final String MY_EMP_PREFS = "MySharedPref";

    //saving the context, so that we can call all
    //shared pref methods from non activity classes.
    //because getSharedPreferences required the context.
    //but in activity class we can call without this context
    private static Context     mContext;

    // will get user input in below variables, then will store in to shared pref
    private static String mode = "";

    public static void Init(Context context)
    {
        mContext         = context;
    }
    public static void LoadFromPref()
    {
        SharedPreferences settings     = mContext.getSharedPreferences(MY_EMP_PREFS, 1);
        // Note here the 2nd parameter 0 is the default parameter for private access,
        //Operating mode. Use 0 or MODE_PRIVATE for the default operation,
        mode             = settings.getString("mode", "FUNC");
    }
    public static void StoreToPref()
    {
        // get the existing preference file
        SharedPreferences settings = mContext.getSharedPreferences(MY_EMP_PREFS, 1);
        //need an editor to edit and save values
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("mode", mode);// EmpID is the key and mEid is holding the value

        //final step to commit (save)the changes in to the shared pref
        editor.commit();
    }

    public static void DeleteSingleEntryFromPref(String keyName)
    {
        SharedPreferences settings = mContext.getSharedPreferences(MY_EMP_PREFS, 1);
        //need an editor to edit and save values
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(keyName);
        editor.commit();
    }

    public static void DeleteAllEntriesFromPref()
    {
        SharedPreferences settings = mContext.getSharedPreferences(MY_EMP_PREFS, 1);
        //need an editor to edit and save values
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    public static String getMode() {
        return mode;
    }

    public static void setMode(String modE) {
        mode = modE;
    }

}
