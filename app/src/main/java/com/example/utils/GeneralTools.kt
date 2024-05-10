package com.example.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Parcelable
import com.example.apisetup.BuildConfig
import com.example.apisetup.R
import com.example.model.MatchStatusJ
import java.text.SimpleDateFormat
import java.util.*


object GeneralTools {

    fun  fillMatchesStatus(context: Context):ArrayList<MatchStatusJ>{

        var match_status_list: ArrayList<MatchStatusJ> = ArrayList()


        match_status_list.add(MatchStatusJ(context.getString(R.string.matches), true))
        match_status_list.add(MatchStatusJ(context.getString(R.string.live), false))
        match_status_list.add(MatchStatusJ(context.getString(R.string.up_coming), false))
        match_status_list.add(MatchStatusJ(context.getString(R.string.finish), false))
//        match_status_list.add(context.getString(R.string.hot))
//        match_status_list.add(context.getString(R.string.live))
//        match_status_list.add(context.getString(R.string.up_coming))
//        match_status_list.add(context.getString(R.string.finish))

        return match_status_list
    }

    fun getLocale(context: Context): String {
        return SharedPreference.getInstance().getStringValueFromPreference(SharedPreference.LOCALE_KEY,SharedPreference.CHINESE,context)
    }
    fun setLocale(context: Context,locale:String) {
        SharedPreference.getInstance().saveStringToPreferences(SharedPreference.LOCALE_KEY,locale,context)
    }

    @SuppressLint("SimpleDateFormat")
    fun getCalculatedDate(dateFormat: String?, days: Int): String{
        val cal: Calendar = Calendar.getInstance()
        val s = SimpleDateFormat(dateFormat)
        cal.add(Calendar.DAY_OF_YEAR, days)
        return s.format(Date(cal.timeInMillis))
    }
    fun getPastWeekDates():List<String>{
        val datesList=ArrayList<String>()
        for (i in -1 downTo -8){
            datesList.add(getCalculatedDate("yyyy-MM-dd",i))
//            datesList.add(getCalculatedDate("yyyy-MM-dd",i))
        }
        return datesList
    }
    fun getFutureDates():List<String>{
        val datesList=ArrayList<String>()
        for (i in 1 .. 8){
            datesList.add(getCalculatedDate("yyyy-MM-dd",i))
//            datesList.add(getCalculatedDate("yyyy-MM-dd",i))
        }
        return datesList
    }

      fun return24HrsOnly(matchTime:String):String{
        val splitDateTime=matchTime.split(" ")[1].split(":")
        return splitDateTime[0]+":"+splitDateTime[1]
    }



    fun shareApp(activity: Activity){
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                activity.getResources().getString(R.string.app_name).toString() + " App"
            )
            var shareMessage = """
                 
                 Let me recommend you this application
                 
                 
                 """.trimIndent()
            shareMessage =
                """
                 ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                 
                 
                 """.trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            activity.startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: Exception) {
            e.toString()
        }
    }

    private fun createEmailOnlyChooserIntent(
        activity: Activity,
        source: Intent?,
        chooserTitle: CharSequence?
    ): Intent? {
        val intents = Stack<Intent>()
        val i = Intent(
            Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",
                "", null
            )
        )
        val activities: List<ResolveInfo> =activity.getPackageManager()
            .queryIntentActivities(i, 0)
        for (ri in activities) {
            val target = Intent(source)
            target.setPackage(ri.activityInfo.packageName)
            intents.add(target)
        }
        return if (!intents.isEmpty()) {
            val chooserIntent = Intent.createChooser(
                intents.removeAt(0),
                chooserTitle
            )
            chooserIntent.putExtra(
                Intent.EXTRA_INITIAL_INTENTS,
                intents.toTypedArray<Parcelable>()
            )
            chooserIntent
        } else {
            Intent.createChooser(source, chooserTitle)
        }
    }

    fun openAdsOnBrowser(context: Context,url:String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent)
    }

    fun rateUs(activity: Activity) {

        val appPackageName: String = activity.getPackageName()
        try {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri
                        .parse("market://details?id=$appPackageName")
                )
            )
        } catch (anfe: ActivityNotFoundException) {
           activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        "http://play.google.com/store/apps/details?id="
                                + appPackageName
                    )
                )
            )
        }
    }
}