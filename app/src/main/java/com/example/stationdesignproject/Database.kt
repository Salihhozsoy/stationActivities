package com.example.stationdesignproject

object Database {

    val cars = mutableListOf(

        Car(1, "Audi", "38ab38",FuelType.gasoline),
        Car(2, "Mercedes", "34ab34",FuelType.diesel),
        Car(3, "Bmw", "06ab06",FuelType.gas),
        Car(4, "Ford", "01ab01",FuelType.electric),
    )
    val pumps = mutableListOf(
        Pump(1, "Pump1"),
        Pump(2, "Pump2"),
        Pump(3, "Pump3"),
        Pump(4, "Pump4"),
    )
    val fuelList = mutableListOf(
        Fuel(36.0,FuelType.gasoline),
        Fuel(34.0,FuelType.diesel),
        Fuel(20.0,FuelType.gas),
        Fuel(10.0,FuelType.electric)
    )
}

data class Car(val id: Int, val name: String, val plaka: String,val fuelType: FuelType) {
    fun getListName() = "MARKA: $name PLAKA: $plaka"
}

data class Pump(val id: Int, val name: String)

class Fuel(val price: Double, val fuelType: FuelType) {}

class Invoice(val id: Int, val car: Car, val fuel: Fuel) {}

enum class FuelType {
    gasoline,
    diesel,
    gas,
    electric
}