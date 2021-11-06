package com.groupal.mygameofthronesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CharactersAdapter: RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder> {

    private val items = mutableListOf<Character>()
    private val itemClickListener: ((Character, Int) -> Unit)?

    constructor():super(){
        itemClickListener = null
    }

    constructor(itemClickListener: ((Character, Int) -> Unit)):super(){
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character,parent, false);
        return CharacterViewHolder(view);
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = items[position]
        holder.character = item
    }

    fun setCharacters(characters: MutableList<Character>){
        items.clear()
        items.addAll(characters)
        notifyDataSetChanged()
    }

    inner class CharacterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var character: Character? = null
            set(value){
                value?.let {
                    //averiguar como usar viewBinding en adapter que hereda de Recyclerview en vez de fragment o activity
                    itemView.findViewById<TextView>(R.id.labelName).text = value?.name;
                    itemView.findViewById<TextView>(R.id.labelTitle).text = value?.title;
                    val overlayColor = House.getOverlayColor(value?.house.name)
                    itemView.findViewById<View>(R.id.imgOverlay).background = ContextCompat.getDrawable(itemView.context, overlayColor)

                    Picasso.get()
                        .load(value.imagen)
                        .placeholder(R.drawable.test)
                        .into(itemView.findViewById<ImageView>(R.id.imgCharacter))
                }
                field = value
            }

        init {
            itemView.setOnClickListener {
                character?.let {
                    itemClickListener?.invoke(character as Character, bindingAdapterPosition)
                }
            }
        }
    }

}