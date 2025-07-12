package com.example

import com.example.IngredientsRepository.Ingredients
import com.example.RecipeIngredientRepository.recipeIngredients
import io.ktor.http.HttpStatusCode
import io.ktor.http.invoke
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable


@Serializable
data class Recipes (val id: Int,val name: String,val instructions: String)

@Serializable
data class RecipesRequest(val name: String,val instructions: String)

@Serializable
data class Ingredients (val id: Int,val name: String,val quantity: Int,val unit: String)

@Serializable
data class IngredientsRequest (val name: String,val quantity: Int,val unit: String)

@Serializable
data class RecipeIngredient(val recipeId: Int, val ingredientId: Int, val quantity: Int, val unit: String)

//Recipes
object RecipesRepository {
    private val Recipes = mutableListOf<Recipes>(
        Recipes(id = 1, name = "แกงเขียวหวานไก่", instructions = "1. ใส่กะทิลงในหม้อและตั้งไฟ\n2. ใส่เครื่องแกงเขียวหวาน\n3. ใส่ไก่และต้มจนสุก\n4. ปรุงรสและเสิร์ฟ"),
        Recipes(id = 2, name = "ข้าวผัด", instructions = "1. ตีไข่และผัดในกระทะ\n2. ใส่ข้าวและผัดให้เข้ากัน\n3. ปรุงรสด้วยซีอิ๊วและผัก\n4. เสิร์ฟพร้อมแตงกวาและมะนาว"),
        Recipes(id = 3, name = "ยำวุ้นเส้น", instructions = "1. ลวกวุ้นเส้นในน้ำเดือด\n2. ผสมวุ้นเส้นกับเครื่องยำ\n3. ใส่กุ้งและปรุงรส\n4. เสิร์ฟในจาน")
    )

    private var nextId = 4

    fun getAll(): List<Recipes> = Recipes

    fun getById(id: Int): Recipes? = Recipes.find { it.id == id }

    fun add(taskRequest: RecipesRequest): Recipes {
        val task = Recipes(id = nextId++, name = taskRequest.name, instructions = taskRequest.instructions)
        Recipes.add(task)
        return task
    }

    fun update(id: Int, updatedTask: Recipes): Boolean {
        val index = Recipes.indexOfFirst { it.id == id }
        return if (index != -1) {
            Recipes[index] = Recipes(id, updatedTask.name, updatedTask.instructions)
            true
        } else {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return Recipes.removeIf { it.id == id }
    }

    fun search(ingredientName: String): List<Pair<Recipes, List<RecipeIngredient>>> {
        val matchedIngredients = IngredientsRepository.getAll().filter {
            it.name.contains(ingredientName, ignoreCase = true)
        }

        if (matchedIngredients.isEmpty()) return emptyList()

        val matchedIngredientIds = matchedIngredients.map { it.id }

        val matchingRecipeIds = RecipeIngredientRepository.getAll()
            .filter { it.ingredientId in matchedIngredientIds }
            .map { it.recipeId }
            .distinct()

        val matchingRecipes = RecipesRepository.getAll().filter { it.id in matchingRecipeIds }

        return matchingRecipes.map { recipe ->
            val ingredientsInRecipe = RecipeIngredientRepository.getAll().filter { it.recipeId == recipe.id }
            recipe to ingredientsInRecipe
        }
    }
}

//Ingredients
object IngredientsRepository {
    Ingredients(id = 1, name = "กะทิ", quantity = 400, unit = "ml"),
    Ingredients(id = 2, name = "เครื่องแกงเขียวหวาน", quantity = 2, unit = "ช้อนโต๊ะ"),
    Ingredients(id = 3, name = "ไก่", quantity = 300, unit = "กรัม"),
    Ingredients(id = 4, name = "น้ำตาลปี๊บ", quantity = 1, unit = "ช้อนโต๊ะ"),
    Ingredients(id = 5, name = "น้ำปลา", quantity = 2, unit = "ช้อนโต๊ะ"),
    Ingredients(id = 6, name = "พริกขี้หนู", quantity = 5, unit = "เม็ด"),
    Ingredients(id = 7, name = "ใบมะกรูด", quantity = 3, unit = "ใบ"),
    Ingredients(id = 8, name = "ข้าว", quantity = 200, unit = "กรัม"),
    Ingredients(id = 9, name = "ไข่ไก่", quantity = 2, unit = "ฟอง"),
    Ingredients(id = 10, name = "น้ำมันพืช", quantity = 2, unit = "ช้อนโต๊ะ"),
    Ingredients(id = 11, name = "ผักรวม", quantity = 150, unit = "กรัม"),
    Ingredients(id = 12, name = "ซอสปรุงรส", quantity = 1, unit = "ช้อนโต๊ะ")
    )
    private var nextId = 11

    fun getAll(): List<Ingredients> = Ingredients

    fun getById(id: Int): Ingredients? = Ingredients.find { it.id == id }

    fun add(taskRequest: IngredientsRequest): Ingredients {
        val task = Ingredients(id = nextId++, name = taskRequest.name, quantity = taskRequest.quantity , unit = taskRequest.unit)
        Ingredients.add(task)
        return task
    }

    fun update(id: Int, updatedTask: Ingredients): Boolean {
        val index = Ingredients.indexOfFirst { it.id == id }
        return if (index != -1) {
            Ingredients[index] = Ingredients(id, updatedTask.name, updatedTask.quantity, updatedTask.unit)
            true
        } else {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return Ingredients.removeIf { it.id == id }
    }
}

//สำหรับจับคู่
object RecipeIngredientRepository {
    private val recipeIngredients = mutableListOf<RecipeIngredient>(
        // แกงเขียวหวานไก่ (recipeId = 1)
        RecipeIngredient(recipeId = 1, ingredientId = 1, quantity = 400, unit = "ml"), // กะทิ
        RecipeIngredient(recipeId = 1, ingredientId = 2, quantity = 2, unit = "ช้อนโต๊ะ"), // เครื่องแกงเขียวหวาน
        RecipeIngredient(recipeId = 1, ingredientId = 3, quantity = 300, unit = "กรัม"), // ไก่
        RecipeIngredient(recipeId = 1, ingredientId = 4, quantity = 1, unit = "ช้อนโต๊ะ"), // น้ำตาลปี๊บ
        RecipeIngredient(recipeId = 1, ingredientId = 5, quantity = 2, unit = "ช้อนโต๊ะ"), // น้ำปลา
        RecipeIngredient(recipeId = 1, ingredientId = 6, quantity = 3, unit = "ใบ"), // ใบมะกรูด
        RecipeIngredient(recipeId = 1, ingredientId = 7, quantity = 5, unit = "เม็ด"), // พริกขี้หนู

        // ข้าวผัด (recipeId = 2)
        RecipeIngredient(recipeId = 2, ingredientId = 8, quantity = 2, unit = "ฟอง"), // ไข่ไก่
        RecipeIngredient(recipeId = 2, ingredientId = 9, quantity = 200, unit = "กรัม"), // ข้าว
        RecipeIngredient(recipeId = 2, ingredientId = 10, quantity = 1, unit = "ช้อนโต๊ะ"), // ซอสปรุงรส
        RecipeIngredient(recipeId = 2, ingredientId = 8, quantity = 2, unit = "ช้อนโต๊ะ"), // น้ำมันพืช

        // ยำวุ้นเส้น (recipeId = 3)
        RecipeIngredient(recipeId = 3, ingredientId = 11, quantity = 150, unit = "กรัม"), // วุ้นเส้น
        RecipeIngredient(recipeId = 3, ingredientId = 7, quantity = 200, unit = "กรัม"), // กุ้ง
        RecipeIngredient(recipeId = 3, ingredientId = 12, quantity = 2, unit = "ช้อนโต๊ะ"), // น้ำปลา
        RecipeIngredient(recipeId = 3, ingredientId = 5, quantity = 2, unit = "ใบ") // ใบมะกรูด
    )
    fun getAll(): List<RecipeIngredient> = recipeIngredients

    fun getByRecipeId(recipeId: Int): List<RecipeIngredient> {
        return recipeIngredients.filter { it.recipeId == recipeId }
    }

    fun getByRecipeAndIngredient(recipeId: Int, ingredientId: Int): RecipeIngredient? {
        return recipeIngredients.find { it.recipeId == recipeId && it.ingredientId == ingredientId }
    }

    fun add(newItem: RecipeIngredient): Boolean {
        // ห้ามซ้ำ recipeId + ingredientId
        if (getByRecipeAndIngredient(newItem.recipeId, newItem.ingredientId) != null) {
            return false
        }
        recipeIngredients.add(newItem)
        return true
    }

    fun update(recipeId: Int, ingredientId: Int, updated: RecipeIngredient): Boolean {
        val index = recipeIngredients.indexOfFirst {
            it.recipeId == recipeId && it.ingredientId == ingredientId
        }
        return if (index != -1) {
            recipeIngredients[index] = updated
            true
        } else {
            false
        }
    }
    fun clear() {
        recipeIngredients.clear()
    }

    fun delete(recipeId: Int, ingredientId: Int): Boolean {
        return recipeIngredients.removeIf {
            it.recipeId == recipeId && it.ingredientId == ingredientId
        }
    }
}

fun Application.configureRouting() {
    routing {

        //Recipes
        get("/recipes") {
            val allRecipes = RecipesRepository.getAll()
            if (allRecipes.isEmpty()) {
                call.respond(HttpStatusCode.NotFound, "Not found")
            } else {
                call.respond(HttpStatusCode.OK, allRecipes)
            }
        }

        get("/recipes/{id}") {
            val allRecipes = RecipesRepository.getAll()
            if (allRecipes.isEmpty()) {
                call.respond(HttpStatusCode.NotFound, "Not found")
            } else {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                    return@get
                }

                val recipe  = RecipesRepository.getById(id)
                if (recipe  != null) {
                    call.respond(recipe)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Recipes not found")
                }
            }
        }
        post("/recipes") {
            val allRecipes = RecipesRepository.getAll()
            if (allRecipes.isEmpty()) {
                call.respond(HttpStatusCode.NotFound, "Not found")
            }else{
                val recipeRequest = call.receive<RecipesRequest>()
                val createdRecipes = RecipesRepository.add(recipeRequest)
                call.respond(HttpStatusCode.Created, createdRecipes)
            }
        }

        put("/recipes/{id}") {
            val recipeId = call.parameters["id"]?.toIntOrNull()
            val updatedRecipe = call.receive<Recipes>()
            if (recipeId == null) {
                call.respond(HttpStatusCode.NotFound, "Not found")
            }else{
                val task = RecipesRepository.getById(recipeId)
                if (task != null) {
                    call.respond(HttpStatusCode.OK, updatedRecipe)
                }
            }
        }
        delete("/recipes/{id}") {
            val recipeId = call.parameters["id"]?.toIntOrNull()
            if (recipeId == null) {
                call.respond(HttpStatusCode.NotFound, "Not found")
                return@delete
            }else{
                val success = RecipesRepository.delete(recipeId)
                if (success) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Recipes not found")
                }
            }
        }
        get("/recipes/search") {
            val ingredientName = call.request.queryParameters["ingredient"]

            if (ingredientName.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Missing or empty ingredient parameter")
                return@get
            }

            val searchResults = RecipesRepository.search(ingredientName)

            if (searchResults.isEmpty()) {
                call.respond(HttpStatusCode.NotFound, "No recipes found using ingredient '$ingredientName'")
            } else {
                val result = searchResults.map { (recipe, ingredients) ->
                    mapOf(
                        "recipe" to recipe,
                        "ingredients" to ingredients
                    )
                }
                call.respond(HttpStatusCode.OK, result)
            }
        }

        //Ingredients
        get("/ingredients") {
            val allIngredients = IngredientsRepository.getAll()
            if (allIngredients.isEmpty()) {
                call.respond(HttpStatusCode.NotFound, "Not found")
            } else {
                call.respond(HttpStatusCode.OK, allIngredients)
            }
        }

        get("/ingredients/{id}") {
            val allIngredients = IngredientsRepository.getAll()
            if (allIngredients.isEmpty()) {
                call.respond(HttpStatusCode.NotFound, "Not found")
            } else {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                    return@get
                }

                val ingredient = IngredientsRepository.getById(id)
                if (ingredient != null) {
                    call.respond(ingredient)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Ingredients not found")
                }
            }
        }
        post("/ingredients") {
            val allIngredients = IngredientsRepository.getAll()
            if (allIngredients.isEmpty()) {
                call.respond(HttpStatusCode.NotFound, "Not found")
            }else{
                val ingredientRequest = call.receive<IngredientsRequest>()
                val createdIngredient = IngredientsRepository.add(ingredientRequest)
                call.respond(HttpStatusCode.Created, createdIngredient)
            }
        }

        put("/ingredients/{id}") {
            val ingredientId = call.parameters["id"]?.toIntOrNull()
            if (ingredientId == null) {
                call.respond(HttpStatusCode.NotFound, "Not found")
            }else{
                val task = IngredientsRepository.getById(ingredientId)
                if (task != null) {
                    call.respond(HttpStatusCode.OK, Ingredients)
                }
            }
        }
        delete("/ingredients/{id}") {
            val ingredientId = call.parameters["id"]?.toIntOrNull()
            if (ingredientId == null) {
                call.respond(HttpStatusCode.NotFound, "Not found")
                return@delete
            }else{
                val success = IngredientsRepository.delete(ingredientId)
                if (success) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound, "Ingredients not found")
                }
            }
        }

        // RecipeIngredients
        get("/recipeingredients") {
            val allRecipeIngredients = RecipeIngredientRepository.getAll()
            if (allRecipeIngredients.isEmpty()) {
                call.respond(HttpStatusCode.NotFound, "Not found")
            } else {
                call.respond(HttpStatusCode.OK, allRecipeIngredients)
            }
        }

        get("/recipeingredients/{recipeId}/{ingredientId}") {
            val recipeId = call.parameters["recipeId"]?.toIntOrNull()
            val ingredientId = call.parameters["ingredientId"]?.toIntOrNull()

            if (recipeId == null || ingredientId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid recipeId or ingredientId")
                return@get
            }

            val item = RecipeIngredientRepository.getByRecipeAndIngredient(recipeId, ingredientId)
            if (item != null) {
                call.respond(HttpStatusCode.OK, item)
            } else {
                call.respond(HttpStatusCode.NotFound, "RecipeIngredient not found")
            }
        }

        post("/recipeingredients") {
            val newItem = call.receive<RecipeIngredient>()
            val added = RecipeIngredientRepository.add(newItem)

            if (!added) {
                call.respond(HttpStatusCode.Conflict, "This recipe-ingredient pair already exists")
            } else {
                call.respond(HttpStatusCode.Created, newItem)
            }
        }

        put("/recipeingredients/{recipeId}/{ingredientId}") {
            val recipeId = call.parameters["recipeId"]?.toIntOrNull()
            val ingredientId = call.parameters["ingredientId"]?.toIntOrNull()

            if (recipeId == null || ingredientId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid recipeId or ingredientId")
                return@put
            }

            val updatedItem = call.receive<RecipeIngredient>()
            val success = RecipeIngredientRepository.update(recipeId, ingredientId, updatedItem)

            if (success) {
                call.respond(HttpStatusCode.OK, updatedItem)
            } else {
                call.respond(HttpStatusCode.NotFound, "RecipeIngredient not found")
            }
        }

        delete("/recipeingredients/{recipeId}/{ingredientId}") {
            val recipeId = call.parameters["recipeId"]?.toIntOrNull()
            val ingredientId = call.parameters["ingredientId"]?.toIntOrNull()

            if (recipeId == null || ingredientId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid recipeId or ingredientId")
                return@delete
            }

            val success = RecipeIngredientRepository.delete(recipeId, ingredientId)
            if (success) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound, "RecipeIngredient not found")
            }
        }
    }
}