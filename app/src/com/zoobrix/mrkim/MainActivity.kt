package com.zoobrix.mrkim

import android.os.Build
import android.os.Bundle
import android.view.WindowManager

class MainActivity: RxCocos2dxActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.setEnableVirtualButton(false)
        super.onCreate(savedInstanceState)

        // Workaround in https://stackoverflow.com/questions/16283079/re-launch-of-activity-on-home-button-but-only-the-first-time/16447508
        if (!isTaskRoot) {
            // Android launched another instance of the root activity into an existing task
            //  so just quietly finish and go away, dropping the user back into the activity
            //  at the top of the stack (ie: the last state of this task)
            // Don't need to finish it again since it's finished in super.onCreate .
            return
        }

        // Make sure we're running on Pie or higher to change cutout mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // Enable rendering into the cutout area
            window.attributes = window.attributes.apply {
                layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
        }

        // DO OTHER INITIALIZATION BELOW
        activity = this
    }

    override fun onDestroy() {
        activity = null

        super.onDestroy()
    }

    companion object {
        private var activity: MainActivity? = null
    }
}