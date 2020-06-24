package com.example.pro_restauranttool

data class ReservationData(val id:Int, val date:String, val from:Time, val till:Time, val duration:Int, val reservationId: String, val name: String = ""): Comparable<ReservationData>{
    override fun compareTo(other: ReservationData): Int {
        var da = date.split(".")
        var dao = other.date.split(".")
        if(da[2].toInt() < dao[2].toInt()) {
            //this is bevore other
            return 1
        }
        if(da[2].toInt() > dao[2].toInt()) {
            //this is after other
            return -1
        }
        //Jahr is gleich
        if(da[1].toInt() < dao[1].toInt()) {
            //this is bevore other
            return 1
        }
        if(da[1].toInt() > dao[1].toInt()) {
            //this is bevore other
            return -1
        }
        //Monat ist gleich
        if(da[0].toInt() < dao[0].toInt()) {
            //this is bevore other
            return 1
        }
        if(da[0].toInt() > dao[0].toInt()) {
            //this is bevore other
            return -1
        }
        //tag ist gleich
        if(this.from.compareTime(other.from)) {
            return 1
        }
        return -1
    }

}

data class TableData(var id: Int, var seatCount: Int): Comparable<TableData> {
    override fun compareTo(other: TableData): Int {
        if(this.seatCount > other.seatCount) {
            return 1
        }
        if(this.seatCount < other.seatCount) {
            return -1
        }
        return 0
    }

}