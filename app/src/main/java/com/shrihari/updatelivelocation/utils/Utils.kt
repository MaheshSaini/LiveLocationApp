package com.shrihari.updatelivelocation.utils

import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {
        fun getDatetime(): String? {
            val c: Calendar = Calendar.getInstance()
            System.out.println("Current time => " + c.getTime())
            val df = SimpleDateFormat("yyyy-MM-dd HH:mms")
            return df.format(c.getTime())
        }

    }
}