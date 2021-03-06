package com.opendrive.android.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;

import com.opendrive.android.OpenDriveApplication;
import com.opendrive.android.R;
import com.opendrive.android.common.Constant;
import com.opendrive.android.common.Utils;
import com.opendrive.android.ui.fragment.PassCodeFragment;

public class PassCodeActivity extends FragmentActivity {

    private FragmentTransaction mFragmentTransaction;
    private PassCodeFragment mPassCodeFragment;
    private boolean isBackPressed = false;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_passcode);
        mPassCodeFragment = new PassCodeFragment();

        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.passcode_fragment, mPassCodeFragment);
        mFragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (Utils.getPassCodeTurned(this) && !OpenDriveApplication.isPasscodeEntered()) {
            Intent intent = new Intent(this, EnterPassCodeActivity.class);
            intent.putExtra(Constant.ENTER_PASS_CODE_MODE, Constant.MODE_ENTER_PASSCODE);
            startActivityForResult(intent, Constant.PASSCODE_CHECK_REQUEST_CODE);
        }
    }

    @Override
    protected void onResume() {
        OpenDriveApplication.applicationResumed();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        isBackPressed = true;
        OpenDriveApplication.setPasscodeEntered(true);
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isBackPressed) {
            OpenDriveApplication.setPasscodeEntered(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.PASSCODE_CHECK_REQUEST_CODE) {
            if (resultCode == RESULT_CANCELED) {
                finish();
            } else if (resultCode == RESULT_OK) {
                OpenDriveApplication.setPasscodeEntered(true);
            }
        }
    }

}
