package com.example.stationdesignproject

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

object Database {
    val cars = mutableListOf(
        Car(1, "Audi", "38ab38",FuelType.Gasoline,20.0,100.0),
        Car(2, "Mercedes", "34ab34",FuelType.Diesel,15.0,8.0),
        Car(3, "Bmw", "06ab06",FuelType.Gas,30.0,90.0),
        Car(4, "Ford", "01ab01",FuelType.Electric,10.0,50.0),
    )

    val employee1= Employee(1,"salih")
    val employee2= Employee(1,"salih")
    val employee3= Employee(1,"salih")
    val employee4= Employee(1,"salih")

    val pumps = mutableListOf(
        Pump(1, 1, employee1),
        Pump(2, 2, employee2),
        Pump(3, 3, employee2),
        Pump(4, 4, employee3),
        Pump(5, 5, employee4),
        Pump(6, 6, employee4),
    )

}
@Parcelize
data class Car(val id: Int, val name: String, val plateNo: String,val fuelType: FuelType , var currentFuelAmount:Double,val capacity:Double) :
    Parcelable {
    fun getListName() = "MARKA: $name PLAKA: $plateNo"
    fun getEmptyFuel():Double = capacity-currentFuelAmount
}
@Parcelize
data class Employee(val id:Int, val name: String) : Parcelable

@Parcelize
data class Pump(val id: Int, val no:Int,val employee: Employee) :
    Parcelable
@Parcelize
data class Invoice(val id: Int,val price:Double,val liter:Double, val car: Car, val employee: Employee) :
    Parcelable {}

enum class FuelType(val price:Double) {
    Gasoline(36.0),
    Diesel(34.0),
    Gas(20.0),
    Electric(10.0)
}