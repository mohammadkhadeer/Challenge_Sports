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
import com.example.model.odds.OddsCompanyComp
import com.example.model.odds.OddsRoot
import java.sql.DriverManager
import java.text.SimpleDateFormat
import java.util.*


object GeneralTools {
    fun getFirstDigOnly(number: String): Int {
        val myDouble: Double = number.toDouble()
        val myInt: Int = myDouble.toInt()

        return myInt
    }
    fun hasFractionalPart25(number: String): Boolean {
        val myDouble: Double = number.toDouble()
        // Extract the fractional part of the number
        val fractionalPart = myDouble - myDouble.toInt()
        // Check if the fractional part is 0.25
        return fractionalPart == 0.25
    }

    fun hasFractionalPart75(number: String): Boolean {
        val myDouble: Double = number.toDouble()
        // Extract the fractional part of the number
        val fractionalPart = myDouble - myDouble.toInt()
        // Check if the fractional part is 0.25
        return fractionalPart == 0.75
    }

    fun convertTimeFormat(timeStamp: String): String{

        var intValue = timeStamp
        val number: Int? = intValue.toIntOrNull()
        val dateFormatter = SimpleDateFormat("h:mm a EEEE, MMMM dd, yyyy", Locale.getDefault())
        val date = Date(number?.times(1000L) ?: 0)  // Kotlin uses milliseconds for the Date constructor

        val dateString = dateFormatter.format(date)

        return dateString
    }

    fun filterOddRoot(company_id:Int,data: List<OddsRoot> ,odd_type:String): List<List<String>> {
        var odd_list_data: List<List<String>> = ArrayList()

        val list_size = data!!.size - 1
        for (i in 0..list_size) {
            if (data?.get(i)!!.companyId == company_id)
            {
                odd_list_data = getOddsList(data?.get(i)!!.oddlist,odd_type)
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

    fun fillCompanyList(context: Context): ArrayList<OddsCompanyComp> {

        var company_list: ArrayList<OddsCompanyComp> = ArrayList()

        company_list.add(OddsCompanyComp(context.getString(R.string.BET365), R.drawable.bet365,2))
        company_list.add(OddsCompanyComp(context.getString(R.string.Crown), R.drawable.crown,3))
        company_list.add(OddsCompanyComp(context.getString(R.string.Bet10), R.drawable.x1bet,4))
        company_list.add(OddsCompanyComp(context.getString(R.string.Ladbrokes), R.drawable.ladbrokes,5))
        company_list.add(OddsCompanyComp(context.getString(R.string.Mansion88), R.drawable.mansion88,6))
        company_list.add(OddsCompanyComp(context.getString(R.string.Macauslot), R.drawable.macauslot,7))
        company_list.add(OddsCompanyComp(context.getString(R.string.SNAI), R.drawable.sani,8))
        company_list.add(OddsCompanyComp(context.getString(R.string.William), R.drawable.william_hill,9))
        company_list.add(OddsCompanyComp(context.getString(R.string.Easybets), R.drawable.easybets,10))
        company_list.add(OddsCompanyComp(context.getString(R.string.Vcbet), R.drawable.vcbet,11))
        company_list.add(OddsCompanyComp(context.getString(R.string.EuroBet), R.drawable.euro_bet,12))
        company_list.add(OddsCompanyComp(context.getString(R.string.Interwetten), R.drawable.interwetten,13))
        company_list.add(OddsCompanyComp(context.getString(R.string.Bet12), R.drawable.bet12,14))
        company_list.add(OddsCompanyComp(context.getString(R.string.Sbobet), R.drawable.sbobet,15))
        company_list.add(OddsCompanyComp(context.getString(R.string.Wewbet), R.drawable.wewbet,16))
        company_list.add(OddsCompanyComp(context.getString(R.string.Bet18), R.drawable.bet18,17))
        company_list.add(OddsCompanyComp(context.getString(R.string.Fun88), R.drawable.fun88,18))
        company_list.add(OddsCompanyComp(context.getString(R.string.Bet188), R.drawable.bet188,21))
        company_list.add(OddsCompanyComp(context.getString(R.string.Pinnacle), R.drawable.pinnacle,22))

        return company_list
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