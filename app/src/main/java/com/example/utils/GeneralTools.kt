package com.example.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Parcelable
import android.provider.Settings.Global.getString
import android.util.Log
import com.example.apisetup.BuildConfig
import com.example.apisetup.R
import com.example.model.hotMatches.MatchStatusJ
import com.example.model.odds.Oddlist
import com.example.model.odds.OddsRoot
import java.text.SimpleDateFormat
import java.util.*


object GeneralTools {

    fun filterOddRoot(company_id:Int,data: List<OddsRoot> ,odd_type:String): List<List<String>> {
        var odd_list_data: List<List<String>> = ArrayList()
        val list_size = data!!.size - 1
        for (i in 0..list_size) {
            if (data?.get(i)!!.companyId == company_id)
            {
                odd_list_data = getOddsList(data?.get(i)!!.oddlist,odd_type)
            }
            when(company_id){

                2->{
                    //BET365
                    Log.i("TAG" ,"TAG BET365 @@@ im here")
                    odd_list_data = getOddsList(data?.get(i)!!.oddlist,odd_type)
                }
                3->{
                    //Crown
                    odd_list_data = getOddsList(data?.get(i)!!.oddlist,odd_type)
                }
                4->{
                    //Bet10
                    odd_list_data = getOddsList(data?.get(i)!!.oddlist,odd_type)
                }
                5->{
                    //Ladbrokes
                    odd_list_data = getOddsList(data?.get(i)!!.oddlist,odd_type)
                }
                6->{
                    //Mansion88
                    odd_list_data = getOddsList(data?.get(i)!!.oddlist,odd_type)
                }
                7->{
                    //Macauslot
                    odd_list_data = getOddsList(data?.get(i)!!.oddlist,odd_type)
                }
                8->{
                    //SNAI
                    odd_list_data = getOddsList(data?.get(i)!!.oddlist,odd_type)
                }
                9->{
                    //William
                    odd_list_data = getOddsList(data?.get(i)!!.oddlist,odd_type)
                }
                10->{
                    //Easybets
                    odd_list_data = getOddsList(data?.get(i)!!.oddlist,odd_type)
                }
                11->{
                    //Vcbet
                    odd_list_data = getOddsList(data?.get(i)!!.oddlist,odd_type)
                }
                12->{
                    //EuroBet
                    odd_list_data = getOddsList(data?.get(i)!!.oddlist,odd_type)
                }
                13->{
                    //Interwetten
                    odd_list_data = getOddsList(data?.get(i)!!.oddlist,odd_type)
                }
                14->{
                    //Bet12
                    odd_list_data = getOddsList(data?.get(i)!!.oddlist,odd_type)
                }
                15->{
                    //Mansion88
                    odd_list_data = getOddsList(data?.get(i)!!.oddlist,odd_type)
                }
                16->{
                    //Wewbet
                    odd_list_data = getOddsList(data?.get(i)!!.oddlist,odd_type)
                }
                17->{
                    //Bet18
                    odd_list_data = getOddsList(data?.get(i)!!.oddlist,odd_type)
                }
                18->{
                    //Fun88
                    odd_list_data = getOddsList(data?.get(i)!!.oddlist,odd_type)
                }
                21->{
                    //Bet188
                    odd_list_data = getOddsList(data?.get(i)!!.oddlist,odd_type)
                }
                22->{
                    //Pinnacle
                    odd_list_data = getOddsList(data?.get(i)!!.oddlist,odd_type)
                }

            }
        }
        return odd_list_data
    }

    fun getOddsList(oddsList: List<Oddlist> , odd_type:String ): List<List<String>>{
        var odd_list_data: List<List<String>> = ArrayList()
        val odd_list_size = oddsList.size - 1
        for (j in 0..odd_list_size) {
            if (oddsList.get(j).type == odd_type)
            {
                odd_list_data = oddsList.get(j).odds
            }
        }
        return odd_list_data
    }

    fun companyName(company_id:Int): String {

        var company_name: String = ""

        when(company_id){

            2->{
                //BET365
                company_name = "BET365"
            }
            3->{
                //Crown
                company_name = "BET365"
            }
            4->{
                //Bet10
                company_name = "BET365"
            }
            5->{
                //Ladbrokes
                company_name = "BET365"
            }
            6->{
                //Mansion88
                company_name = "BET365"
            }
            7->{
                //Macauslot
                company_name = "BET365"
            }
            8->{
                //SNAI
                company_name = "BET365"
            }
            9->{
                //William
                company_name = "BET365"
            }
            10->{
                //Easybets
                company_name = "BET365"
            }
            11->{
                //Vcbet
                company_name = "BET365"
            }
            12->{
                //EuroBet
                company_name = "BET365"
            }
            13->{
                //Interwetten
                company_name = "BET365"
            }
            14->{
                //Bet12
                company_name = "BET365"
            }
            15->{
                //Mansion88
                company_name = "BET365"
            }
            16->{
                //Wewbet
                company_name = "BET365"
            }
            17->{
                //Bet18
                company_name = "BET365"
            }
            18->{
                //Fun88
                company_name = "BET365"
            }
            21->{
                //Bet188
                company_name = "BET365"
            }
            22->{
                //Pinnacle
                company_name = "BET365"
            }

        }


        return company_name
    }

    fun  fillMatchesStatus(context: Context,):ArrayList<MatchStatusJ>{

        var match_status_list: ArrayList<MatchStatusJ> = ArrayList()


        match_status_list.add(
            MatchStatusJ(
                context.getString(
                    R.string.hot
                ), true
            )
        )
        match_status_list.add(
            MatchStatusJ(
                context.getString(
                    R.string.live
                ), false
            )
        )
        match_status_list.add(
            MatchStatusJ(
                context.getString(
                    R.string.up_coming
                ), false
            )
        )
        match_status_list.add(
            MatchStatusJ(
                context.getString(
                    R.string.finish
                ), false
            )
        )
//        match_status_list.add(context.getString(R.string.hot))
//        match_status_list.add(context.getString(R.string.live))
//        match_status_list.add(context.getString(R.string.up_coming))
//        match_status_list.add(context.getString(R.string.finish))

        return match_status_list
    }

    fun getLocale(context: Context): String {
        return SharedPreference.getInstance().getStringValueFromPreference(SharedPreference.LOCALE_KEY,SharedPreference.CHINESE,context)
    }

    fun getWeatherStatus(context: Context,weather_status_number : Int): String {
        var weathr_status_string:String = ""

        when (weather_status_number) {
            0 -> {
                weathr_status_string = context.getString(R.string.weather_case_0)
            }
            1 -> {
                weathr_status_string = context.getString(R.string.weather_case_1)
            }
            2 -> {
                weathr_status_string = context.getString(R.string.weather_case_2)
            }
            3 -> {
                weathr_status_string = context.getString(R.string.weather_case_3)
            }
            4 -> {
                weathr_status_string = context.getString(R.string.weather_case_4)
            }
            5 -> {
                weathr_status_string = context.getString(R.string.weather_case_5)
            }
            6 -> {
                weathr_status_string = context.getString(R.string.weather_case_6)
            }
            7 -> {
                weathr_status_string = context.getString(R.string.weather_case_7)
            }
            8 -> {
                weathr_status_string = context.getString(R.string.weather_case_8)
            }
            9 -> {
                weathr_status_string = context.getString(R.string.weather_case_9)
            }
            10 -> {
                weathr_status_string = context.getString(R.string.weather_case_10)
            }
            11 -> {
                weathr_status_string = context.getString(R.string.weather_case_11)
            }
            12 -> {
                weathr_status_string = context.getString(R.string.weather_case_12)
            }
        }

        return weathr_status_string
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