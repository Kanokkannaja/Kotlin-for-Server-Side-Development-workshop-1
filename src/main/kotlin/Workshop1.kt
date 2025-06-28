package org.example

// Workshop #1: Simple Console Application - Unit Converter

fun main() {
    // 2. ใช้ while (true) เพื่อให้โปรแกรมทำงานวนซ้ำ
    while (true) {
        // 1. แสดงเมนูให้ผู้ใช้เลือก
        println("===== Unit Converter =====")
        println("โปรดเลือกหน่วยที่ต้องการแปลง:")
        println("1. Celsius to Fahrenheit")
        println("2. Kilometers to Miles")
        println("พิมพ์ 'exit' เพื่อออกจากโปรแกรม")
        print("เลือกเมนู (1, 2, or exit): ")

        // 2. รับข้อมูลตัวเลือกจากผู้ใช้
        val choice = readln()

        // 3. ควบคุมการทำงานด้วย when expression
        // เลือก 1 เพื่อแปลง Celsius เป็น Fahrenheit: convertCelsiusToFahrenheit()
        // เลือก 2 เพื่อแปลง Kilometers เป็น Miles: convertKilometersToMiles()
        // เลือก 'exit' เพื่อออกจากโปรแกรม
        // 🚨
        when (choice.lowercase()) {
            "1"    -> convertCelsiusToFahrenheit()
            "2"    -> convertKilometersToMiles()
            "exit" -> {
                println("ขอบคุณที่ใช้โปรแกรมค่ะ 👋")
                break
            }
            else   -> println("⚠️  เมนูไม่ถูกต้อง โปรดลองใหม่")
        }

        println() // พิมพ์บรรทัดว่างเพื่อความสวยงาม
    }
}

// 4. สร้างฟังก์ชันแยกสำหรับการแปลงหน่วย Celsius to Fahrenheit: celsiusToFahrenheit
// สูตร celsius * 9.0 / 5.0 + 32
// 🚨
fun celsiusToFahrenheit(celsius: Double): Double {
    return celsius * 9.0 / 5.0 + 32
}

// 4. สร้างฟังก์ชันแยกสำหรับการแปลงหน่วย Kilometers to Miles: kilometersToMiles
// สูตร kilometers * 0.621371
// 🚨
// ฟังก์ชันแปลงหน่วย

fun kilometersToMiles(km: Double): Double {
    return km * 0.621371
}


// ฟังก์ชันสำหรับจัดการกระบวนการแปลง Celsius to Fahrenheit ทั้งหมด
// --- แปลง Celsius ➔ Fahrenheit ---
fun convertCelsiusToFahrenheit() {
    print("ป้อนค่า °C: ")
    val celsius = readln().toDoubleOrNull() ?: run {
        println("❌ กรุณาป้อนตัวเลขให้ถูกต้อง แล้วลองใหม่อีกครั้ง")
        return
    }
    val result = celsiusToFahrenheit(celsius)
    println("ผลลัพธ์: $celsius °C = ${"%.2f".format(result)} °F")
}
// 5. จัดการ Null Safety ด้วย toDoubleOrNull() และ Elvis operator (?:)
// ออกจากฟังก์ชัน convertCelsiusToFahrenheit() หากข้อมูลผิดพลาด: return
// celsius
// 🚨

//🚨    val fahrenheitResult = celsiusToFahrenheit(celsius)

// 6. แสดงผลลัพธ์
// ใช้ String format เพื่อแสดงทศนิยม 2 ตำแหน่ง
//🚨    println("ผลลัพธ์: $celsius °C เท่ากับ ${"%.2f".format(fahrenheitResult)} °F")


// ฟังก์ชันสำหรับจัดการกระบวนการแปลง Kilometers to Miles ทั้งหมด

// --- แปลง Kilometers ➔ Miles ---

// 5. จัดการ Null Safety ด้วย toDoubleOrNull() และ Elvis operator (?:)
// ออกจากฟังก์ชัน convertKilometersToMiles() หากข้อมูลผิดพลาด: return
// kilometers
// 🚨


//🚨    val milesResult = kilometersToMiles(kilometers)

// 6. แสดงผลลัพธ์
//🚨    println("ผลลัพธ์: $kilometers km เท่ากับ ${"%.2f".format(milesResult)} miles")

fun convertKilometersToMiles() {
    print("ป้อนค่ากิโลเมตร (Kilometers):  ")
    val km = readln().toDoubleOrNull() ?: run {
        println("❌ กรุณาป้อนตัวเลขให้ถูกต้อง แล้วลองใหม่อีกครั้ง")
        return
    }

    val result = kilometersToMiles(km) //
    println("ผลลัพธ์: $km km = ${"%.2f".format(result)} miles") //
}