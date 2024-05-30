package com.example.harvest_savior_mobile_dev.util

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.harvest_savior_mobile_dev.R

class AnimationUtil {

    companion object {
        fun startActivityWithSlideAnimation(activity: AppCompatActivity, intent: Intent) {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        fun finishActivityWithSlideAnimation(activity: AppCompatActivity) {
            activity.finish()
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }
}