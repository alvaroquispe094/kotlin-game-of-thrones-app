package com.groupal.mygameofthronesapp

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import kotlin.random.Random

const val  URL_CHARACTERS = "https://60bd2dc7b8ab3700175a034b.mockapi.io/characters"
object CharactersRepository {
    var characters: MutableList<Character> =  mutableListOf()
//        get(){ //getter custom de la lista mutable
//            if(field.isEmpty())
//                field.addAll(dummyCharactersMap())
//            return  field
//        }

    private fun dummyCharacters(): MutableList<Character>{

        val dummies: MutableList<Character> =  mutableListOf()

        for(index in 1..10){
            val character:Character = Character(
                name = "Personaje ${index}",
                born = "Nació en ${index}",
                title = "Titulo ${index}",
                actor = "Actor ${index}",
                quote = "Frase ${index}",
                father = "Padre ${index}",
                mother = "Madre ${index}",
                spouse = "Espos@ ${index}",
                imagen = "imagen ${index}",
                house = House(
                        name = "Casa ${index}",
                        region = "Region ${index}",
                        words = "Words ${index}",
                        imagen = "imagen ${index}"
                )
            )
            dummies.add(character)
        }
        return dummies
    }

    fun requestCharacter(context: Context, success: ((MutableList<Character>) -> Unit), error:(() -> Unit)){
        if(characters.isEmpty()) {
            val request = JsonArrayRequest(Request.Method.GET, URL_CHARACTERS, null,
                { response ->
                    characters = parseCharacters(response)
                    success.invoke(characters)
            }, { VolleyError ->
                    error.invoke()
            })
            Volley.newRequestQueue(context).add(request)
        }else{
            success.invoke(characters)
        }
    }

    private fun parseCharacters(jsonArray: JSONArray): MutableList<Character>{
        val characters = mutableListOf<Character>()
        for(index in 0 until jsonArray.length() ){
            val character = parseCharacter(jsonArray.getJSONObject(index))
            characters.add(character)
        }
        return characters
    }

    private fun parseCharacter(jsonCharacter: JSONObject): Character{
        return Character(jsonCharacter.getString("id"),
                        jsonCharacter.getString("name"),
                        jsonCharacter.getString("born"),
                        jsonCharacter.getString("title"),
                        jsonCharacter.getString("actor"),
                        jsonCharacter.getString("quote"),
                        jsonCharacter.getString("father"),
                        jsonCharacter.getString("mother"),
                        jsonCharacter.getString("spouse"),
                        jsonCharacter.getString("img"),
                        parseHouse((jsonCharacter.getJSONObject("house"))
                        )
        )
    }
    private fun parseHouse(jsonHouse: JSONObject): House{
        return House(jsonHouse.getString("name"),
                jsonHouse.getString("region"),
                jsonHouse.getString("words"),
                jsonHouse.getString("img"))
    }

    private fun dummyCharactersMap(): MutableList<Character>{
        return  (1..10).map{ index -> inToCharacter(index) }.toMutableList()

    }

    fun findCharacterById(id: String): Character?{
        return characters.find {character ->
            character.id == id
        }
    }

    private fun inToCharacter(int: Int): Character{
        return Character(
                name = "Personaje ${int}",
                born = "Nació en ${int}",
                title = "Titulo ${int}",
                actor = "Actor ${int}",
                quote = "Frase ${int}",
                father = "Padre ${int}",
                mother = "Madre ${int}",
                spouse = "Espos@ ${int}",
                imagen = "Imagen ${int}",
                house = dummyHouse()
        )
    }

    private fun dummyHouse(): House{
        val ids = arrayOf("stark","lannister","tyrell","arryn","baratheon","tully")
        val randomIdPosition = Random.nextInt(ids.size)
        return House(name=ids[randomIdPosition],
                   region = "Region",
                    words = "Lema",
                    imagen = "imagen"
        )
    }

}