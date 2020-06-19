package com.example.pro_restauranttool

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Time(private val hours:Int, private val minutes:Int) {
    companion object {
        fun getTime(sTime: String):Time {
            val time = sTime.split(":")
            val hours = time[0].toInt()
            val minutes = time[1].toInt()
            return Time(hours, minutes)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getTodaysDate(): String {
            var today_unformated = LocalDate.now()
            var today = today_unformated.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            return today;
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getTime(): Time {
            val now = LocalDateTime.now()
            val minutes = now.format(DateTimeFormatter.ofPattern("mm")).toInt()
            val hours = now.format(DateTimeFormatter.ofPattern("HH")).toInt()
            return Time(hours, minutes);
        }
    }

    fun compareTime(time: Time):Boolean {
        if(this.hours < time.hours) {
            return true;
        } else if(this.hours == time.hours) {
            if(this.minutes < time.minutes) {
                return true
            }
        }
        return false;
    }

    fun isBetween(time1: Time, time2: Time): Boolean {
        return !this.compareTime(time1) && this.compareTime(time2)
    }

    fun compareTime(_time: String): Boolean {
        val time = _time.split(":")
        val hours = time[0].toInt()
        val minutes = time[1].toInt()
        return compareTime(Time(hours, minutes))
    }

    fun addTime(duration: Int): Time {
        var aHours = duration/60
        var aMinutes = duration%60
        val hours_plus = (aMinutes + this.minutes)/60
        aMinutes = (aMinutes + this.minutes)%60
        aHours = (aHours + this.hours + hours_plus)
        if(aHours > 24) {
            aHours -= 24
        }
        return Time(aHours, aMinutes)
    }

    override fun toString(): String {
        var sHours: String = if(hours <= 9) {
            "0$hours"
        } else {
            hours.toString()
        }
        val sMinutes: String = if(minutes <= 9) {
            "0$minutes"
        } else {
            minutes.toString()
        }
        return "$sHours:$sMinutes"
    }
}