
package sci.dashboard.dashboardhelp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;

public class AlertMessage {

    public static boolean checkPermission(Context con){
        int result = ContextCompat.checkSelfPermission(con, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }

    public static boolean isNetConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showMessage(final Context c, final String title, final String s) {
        final AlertDialog.Builder aBuilder = new AlertDialog.Builder(c);
        aBuilder.setTitle(title);
        aBuilder.setIcon(R.drawable.info);
        aBuilder.setMessage(s);

        aBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                dialog.dismiss();
            }

        });

        aBuilder.show();
    }

    public static void showProgress(final Context c, ProgressDialog progressDialog) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(c);
        }
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog = ProgressDialog.show(c, "Please wait...", "Buffering...", true, true);
        }

    }

    public static void cancelProgress(final Context c, final ProgressDialog progressDialog) {

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();

        }
    }
}
