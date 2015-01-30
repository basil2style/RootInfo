package makeinfo.com.rootinfo;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;
import com.stericson.RootTools.RootTools;

import me.drakeet.materialdialog.MaterialDialog;

import static android.os.Build.*;


public class MainActivity extends ActionBarActivity {

    TextView rootStatus,device_name,android_ver,rootaccess,busybox,busybox_ver,boot_ver,radio,ker_name,ker_ver,debug,
            romName,fingerprint,runtime,xposed;
    View view;
    //private StartAppAd startAppAd = new StartAppAd(this);   //loading ads
    ImageView img;
    @Override
    protected void onResume() {
        super.onResume();
        //startAppAd.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //startAppAd.onPause();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // StartAppSDK.init(this, "112014756", "212762646", true);         //initiating startapp
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.makeinfoid);
        view = (View)findViewById(R.id.statusBarBackground);
        setStatusBarColor(findViewById(R.id.statusBarBackground),getResources().getColor(R.color.status_bar_color));
        final boolean isART;
        device_name = (TextView)findViewById(R.id.device_name);
        android_ver = (TextView) findViewById(R.id.android_ver);
        rootaccess = (TextView)findViewById(R.id.root_access);
        rootStatus = (TextView) findViewById(R.id.root_status);
        busybox = (TextView)findViewById(R.id.busy_box);
        busybox_ver = (TextView)findViewById(R.id.busy_ver);
        boot_ver = (TextView)findViewById(R.id.boot_ver);
        radio = (TextView)findViewById(R.id.radio_firm);
        ker_name = (TextView)findViewById(R.id.ker_name);
        ker_ver = (TextView)findViewById(R.id.ker_ver);
        debug = (TextView) findViewById(R.id.usb_debug);
        runtime = (TextView) findViewById(R.id.runtime);
        fingerprint= (TextView) findViewById(R.id.finger_print);

        xposed = (TextView) findViewById(R.id.xposed);

        final Button checksu = (Button) findViewById(R.id.check);

        final int currentVersion = VERSION.SDK_INT;
        isART = getIsArtInUse();                    //Checking Dalvik...


        final boolean xposed_installed = appInstalled("de.robv.android.xposed.installer");       //xposed installed?

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=MakeInfo"));
                startActivity(browserIntent);
            }
        });
        checksu.setOnClickListener(new View.OnClickListener() {             //Button Started...
            @Override
            public void onClick(View v) {

                checksu.setEnabled(false);
                device_name.setText(Build.MANUFACTURER+" "+Build.MODEL);        //samsung +s3

                fingerprint.setText(Build.FINGERPRINT);                         //Fingerprint
                android_ver.setText(String.format("%s", "Android " + VERSION.RELEASE));     //Android version
                boot_ver.setText(Build.BOOTLOADER);                 //Bootloader

                ker_name.setText(System.getProperty("os.name"));        //kernel name
                ker_ver.setText(System.getProperty("os.version"));      //kernel version

                if (xposed_installed){
                    xposed.setText("Installed");
                }
                else{
                    xposed.setText("Not Installed");
                }
                if(isART){
                    runtime.setText("ART");             //For ART
                }
                else{
                    runtime.setText("Dalvik");          //For Dalvik
                }
                if(currentVersion>= VERSION_CODES.ICE_CREAM_SANDWICH)
                radio.setText(Build.getRadioVersion());             //Radio version after ics
                else{
                    radio.setText(Build.RADIO);                     //radio version before ics
                }
                if(Settings.Secure.getInt(getApplicationContext().getContentResolver(), Settings.Secure.ADB_ENABLED, 0) == 1){
                    debug.setTextColor(Color.parseColor("#11ad12"));
                    debug.setText("ON");
                }else{
                    debug.setTextColor(Color.parseColor("#FF332C"));
                    debug.setText("OFF");
                }
                if(RootTools.isRootAvailable()){
                    rootStatus.setTextColor(Color.parseColor("#11ad12"));       //green color
                    rootaccess.setTextColor(Color.parseColor("#11ad12"));
                    rootStatus.setText("This Device is Rooted");
                    if(RootTools.isAccessGiven()){
                        rootaccess.setText("Granted");
                    }
                    if(RootTools.isBusyboxAvailable()){
                        busybox.setTextColor(Color.parseColor("#11ad12"));
                        busybox.setText("Installed");
                        busybox_ver.setText(RootTools.getBusyBoxVersion());
                    }
                    else{
                        busybox.setTextColor(Color.parseColor("#FF332C"));          //Red color
                        busybox_ver.setTextColor(Color.parseColor("#FF332C"));
                        busybox_ver.setText("Nil");
                        busybox.setText("Not Installed");
                            }



                }
                else{
                    rootStatus.setTextColor(Color.parseColor("#FF332C"));
                    rootaccess.setTextColor(Color.parseColor("#FF332C"));
                    rootStatus.setText("This Device is Rooted");
                    rootaccess.setText("Not Rooted");
                    busybox_ver.setTextColor(Color.parseColor("#FF332C"));
                    busybox.setTextColor(Color.parseColor("#FF332C"));
                    rootStatus.setText("Device is Not Rooted");
                    busybox_ver.setText("Not Installed");
                    busybox.setText("Not Installed");

                }
            }
        });

    }

    private boolean appInstalled(String s) {

        PackageManager pm =getPackageManager();
        boolean app_installed = false;

        try{
            pm.getPackageInfo(s,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
           app_installed = false;

        }
        return app_installed;
    }
    public void onBackPressed() {

        // alert();
      /*  startAppAd.onBackPressed();
        startAppAd.showAd(); // show the ad
        startAppAd.loadAd(); // load the next ad*/
        super.onBackPressed();
    }
    private boolean getIsArtInUse() {
        final String vmVersion = System.getProperty("java.vm.version");
        return vmVersion != null && vmVersion.startsWith("2");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void setStatusBarColor(View statusBar,int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //status bar height
            int actionBarHeight = getActionBarHeight();
            int statusBarHeight = getStatusBarHeight();
            //action bar height
            statusBar.getLayoutParams().height = actionBarHeight + statusBarHeight;
            statusBar.setBackgroundColor(color);
        }
        else{
            view.setVisibility(View.GONE);
        }
    }
    public int getActionBarHeight() {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id ==R.id.rate){
            Uri uri = Uri.parse("market://details?id=makeinfo.com.rootinfo" );
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=makeinfo.com.rootinfo")));
            }
            return true;
        }
        if (id ==R.id.about){
/*
            Context context = this;
            final AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("About");
            alert.setMessage("Check if your device get Root access or not\nE-mail : makeinfo14@gmail.com" +
                    "\nThank You!");
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.cancel();
                }
            });
            AlertDialog aialog = alert.create();
            aialog.show();*/
            MaterialDialog mDialog = new MaterialDialog(this)
                           .setTitle("ABOUT").setMessage("RootInfo 1.0.0" +
                            "\nSpecial Credits : drakeet(MaterialDialog),Stericson(RootTools)\nPlease report any Issue,if any" +
                            "\nThank for Using!!" );
                    mDialog.show();


            return true;
        }
        if(id == R.id.Share){
            Intent sendIntent =new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=makeinfo.com.rootinfo");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return true;
        }
        if(id == R.id.report){
            String[] TO = {"makeinfo14@gmail.com"};
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");

            emailIntent.putExtra(Intent.EXTRA_EMAIL,TO);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT,"RootInfo Issues");
            try{
                startActivity(Intent.createChooser(emailIntent,"Sending Issues"));
                finish();
            }catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(MainActivity.this,
                        "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }

            return true;
        }
        if (id==R.id.Exit){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
