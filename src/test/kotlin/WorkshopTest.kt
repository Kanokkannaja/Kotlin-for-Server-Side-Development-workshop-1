import kotlin.test.Test
import kotlin.test.assertEquals

class WorkshopTest {
    fun celsiusToFahrenheit(celsius: Double): Double {
        return celsius * 9.0 / 5.0 + 32
    }
    fun kilometersToMiles(kilometers: Double): Double {
        return kilometers * 0.621371
    }

    data class Product(val name: String, val price: Double, val category: String)
    //Returns sum of all Electronics items priced > 500

    fun calculateTotalElectronicsPriceOver500(products: List<Product>): Double =
        products
            .filter { it.category == "Electronics" && it.price > 500.0 }
            .sumOf { it.price }


    //Returns count of all Electronics items priced > 500

    fun countElectronicsOver500(products: List<Product>): Int =
        products.count { it.category == "Electronics" && it.price > 500.0 }


    // --- Tests for Workshop #1: Unit Converter ---

    // celsius input: 20.0
    // expected output: 68.0
    @Test
    fun `test celsiusToFahrenheit with positive value`() {
        // Arrange: ตั้งค่า input และผลลัพธ์ที่คาดหวัง
        val celsiusInput = 20.0
        val expectedFahrenheit = 68.0

        // Act: เรียกใช้ฟังก์ชันที่ต้องการทดสอบ
        val actualFahrenheit = celsiusToFahrenheit(celsiusInput)
        // Assert: ตรวจสอบว่าผลลัพธ์ที่ได้ตรงกับที่คาดหวัง
        assertEquals(expectedFahrenheit, actualFahrenheit, 0.001, "20°C should be 68°F")
    }

    // celsius input: 0.0
    // expected output: 32.0
    @Test
    fun `test celsiusToFahrenheit with zero`() {
        // Act
        val actualFahrenheit = celsiusToFahrenheit(0.0)
        // Assert
        assertEquals(32.0, actualFahrenheit, 0.001, "0°C should be 32°F")
    }

    // celsius input: -10.0
    // expected output: 14.0
    @Test
    fun `test celsiusToFahrenheit with negative value`() {
        // Act
        val actualFahrenheit = celsiusToFahrenheit(-10.0)
        // Assert
        assertEquals(14.0, actualFahrenheit, 0.001, "-10°C should be 14°F")

    }

    // test for kilometersToMiles function
    // kilometers input: 1.0
    // expected output: 0.621371
    @Test
    fun `test kilometersToMiles with one kilometer`() {
        // Act
        val actualMiles = kilometersToMiles(1.0)
        // Assert
        assertEquals(0.621371, actualMiles, 0.000001, "1 km should be ~0.621371 miles")
    }

    // --- Tests for Workshop #2: Data Analysis Pipeline ---

    // จงเขียน test cases สำหรับฟังก์ชันนี้ โดยตรวจสอบผลรวมราคาสินค้า Electronics ที่ราคา > 500 บาท
    @Test
    fun `test calculateTotalElectronicsPriceOver500`() {
        val products = listOf(
            Product("Laptop", 600.0, "Electronics"),
            Product("Mouse", 50.0, "Electronics"),
            Product("TV", 1000.0, "Electronics"),
            Product("Book", 700.0, "Books")
        )
        val total = calculateTotalElectronicsPriceOver500(products)
        assertEquals(1600.0, total, 0.001, "Sum of Electronics >500 should be 1600.0")
    }

    @Test
    fun `test countElectronicsOver500`() {
        val products = listOf(
            Product("Laptop", 600.0, "Electronics"),
            Product("Mouse", 50.0, "Electronics"),
            Product("TV", 1000.0, "Electronics"),
            Product("Headphones", 550.0, "Electronics"),
            Product("Book", 700.0, "Books")
        )
        val count = countElectronicsOver500(products)
        assertEquals(3, count, "There should be 3 Electronics items over 500")
    }



    // --- Tests for Workshop #1: Unit Converter End ---

    // --- Tests for Workshop #2: Data Analysis Pipeline ---
    // ทำการแก้ไขไฟล์ Workshop2.kt ให้มีฟังก์ชันที่ต้องการทดสอบ
    // เช่น ฟังก์ชันที่คำนวณผลรวมราคาสินค้า Electronics ที่ราคา > 500 บาท
    // ในที่นี้จะสมมุติว่ามีฟังก์ชันชื่อ calculateTotalElectronicsPriceOver500 ที่รับ List<Product> และคืนค่า Double
    // จงเขียน test cases สำหรับฟังก์ชันนี้ โดยตรวจสอบผลรวมราคาสินค้า Electronics ที่ราคา > 500 บาท
    // 🚨

    // จงเขียน test cases เช็คจำนวนสินค้าที่อยู่ในหมวด 'Electronics' และมีราคามากกว่า 500 บาท
    // 🚨


    // --- Tests for Workshop #2: Data Analysis Pipeline End ---
}
