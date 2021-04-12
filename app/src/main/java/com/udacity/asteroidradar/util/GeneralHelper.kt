package com.udacity.asteroidradar.util

import com.udacity.asteroidradar.Constants
import java.text.SimpleDateFormat
import java.util.*

class GeneralHelper {

    fun getCurrentDateInString(): String {
        val sdf = SimpleDateFormat(Constants.CURRENT_DATE_FORMAT)
        return sdf.format(Date())

    }

    fun getEndDateInString(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 7)
        val currentTime = calendar.time
        return SimpleDateFormat(Constants.CURRENT_DATE_FORMAT, Locale.getDefault()).format(currentTime)

    }


}