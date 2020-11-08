package com.moussa.gys_task.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.moussa.gys_task.R
import dagger.android.support.DaggerAppCompatActivity
import java.util.*

open class ParentClass : DaggerAppCompatActivity() {


    companion object {
        var visitor: Dialog? = null

        var manager: FragmentManager? = null

        fun getLang(context: Context): String {
            var value = ""
            val prefs = context.getSharedPreferences(
                "language", 0
            )
            if (Locale.getDefault().language == "ar")
                value = prefs.getString("language", "ar").toString()

            if (Locale.getDefault().language == "en")
                value = prefs.getString("language", "en").toString()

            if (Locale.getDefault().language == "cmn")
                value = prefs.getString("language", "cmn").toString()

            if (Locale.getDefault().language == "du")
                value = prefs.getString("language", "du").toString()

            if (Locale.getDefault().language == "fr")
                value = prefs.getString("language", "fr").toString()

            if (Locale.getDefault().language == "in")
                value = prefs.getString("language", "in").toString()

            if (Locale.getDefault().language == "ja")
                value = prefs.getString("language", "ja").toString()

            if (Locale.getDefault().language == "pl")
                value = prefs.getString("language", "pl").toString()

            if (Locale.getDefault().language == "ru")
                value = prefs.getString("language", "ru").toString()

            if (Locale.getDefault().language == "tr")
                value = prefs.getString("language", "tr").toString()

            Log.e("valueLang", value)
            return value
        }


        fun delcareDialog(context: Context) {
            visitor = Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen)

            visitor!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            visitor!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            visitor!!.setContentView(R.layout.error_occure_dialog)

            // to set width and height
            val lp = WindowManager.LayoutParams()
            lp.copyFrom(visitor!!.window!!.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT

//        context?.let { ParentClass.dismiss_keyboard(it) }
        }

        fun showDialog() {
            visitor?.show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        manager = supportFragmentManager

        delcareDialog(this)
        initTransparentStatus()
    }


    fun isConnection(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun initTransparentStatus() {
        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setLocale()
    }

    fun setLocale() {
        val locale = Locale(getLang(this))
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
    }

    fun loadImage(imageView: ImageView, url: String) {
        val option = RequestOptions()
            .placeholder(R.color.light_gray)
            .error(R.drawable.default_image)

        Glide.with(imageView.context)
            .setDefaultRequestOptions(option)
            .load(url)
            .into(imageView)
    }


}