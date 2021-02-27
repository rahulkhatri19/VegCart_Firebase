package `in`.rahulkhatri.vegcartpro.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings

/**
 * Created by Rahul Khatri on 31, January, 2021.
 * github.com/rahulkhatri19
 **/
object Utility {
    /**
     *used to get deviceID
     * @param context - pass context
     *
     */
    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    /**
     * Gets the version name from version code. Note! Needs to be updated
     * when new versions arrive, or will return a single letter. Like Android 8.0 - Oreo
     * yields "O" as a version name.
     * @return version name of device's OS
     */
    fun getOsVersionName(): String {
        val fields = Build.VERSION_CODES::class.java.fields
        var name = fields[Build.VERSION.SDK_INT].name

        if (name == "O") name = "Oreo"
        if (name == "N") name = "Nougat"
        if (name == "M") name = "Marshmallow"
        if (name == "P") name = "PIE"
        if (name.startsWith("O_")) name = "Oreo++"
        if (name.startsWith("N_")) name = "Nougat++"
        if (name == "Q") name == "Android 10"
        if (name == "R") name == "Android 11"
        return name
    }

    //  device product/hardware model
    fun getDeviceModel():String{
        return Build.MODEL
    }

    //  device product/hardware Manufacturer
    fun getDeviceManufacturer():String{
        return Build.MANUFACTURER
    }
}