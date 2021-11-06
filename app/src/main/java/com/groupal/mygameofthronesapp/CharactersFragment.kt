package com.groupal.mygameofthronesapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.groupal.mygameofthronesapp.databinding.FragmentCharactersBinding
import com.groupal.mygameofthronesapp.databinding.FragmentDetailBinding
import java.lang.IllegalArgumentException

class CharactersFragment: Fragment() {

    private var _bindingFragmentCharacter: FragmentCharactersBinding? = null
    private val bindingFragmentCharacter get() = _bindingFragmentCharacter!!

    val list: RecyclerView by lazy {
        val list: RecyclerView = view!!.findViewById(R.id.list)
        list.layoutManager = LinearLayoutManager(context)
        list //no es necesario poner return
    }

    val adapter: CharactersAdapter by lazy {
        val adapter = CharactersAdapter { item, position ->
//            showDetails(item.id)
            clickListener.onItemClicked(item)
        }
        adapter//no es necesario poner return
    }

    lateinit var clickListener: OnItemClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnItemClickListener)
            clickListener = context
        else
            throw IllegalArgumentException("Attached activity doesn't implemented CharacterFragment OnItemClickListener")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _bindingFragmentCharacter = FragmentCharactersBinding.inflate(inflater, container, false)
        val view = bindingFragmentCharacter.root
        return view
//        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val characters: MutableList<Character> = CharactersRepository.characters
//        adapter.setCharacters(characters)

        list.adapter = adapter
        bindingFragmentCharacter.btnRetry.setOnClickListener{
            retry()
        }

    }

    private fun retry(){
        bindingFragmentCharacter.layoutError.visibility = View.INVISIBLE
        bindingFragmentCharacter.progressBar.visibility = View.VISIBLE
        requestCharacters()
    }

    private fun requestCharacters(){

        context?.let {
            CharactersRepository.requestCharacter(
                it,
                { characters ->
                    view?.let {//por si hay coneccion lenta y no se llegue a cargar rapido
                        bindingFragmentCharacter.progressBar.visibility = View.INVISIBLE
                        bindingFragmentCharacter.list.visibility = View.VISIBLE
                        adapter.setCharacters(characters)
                    }

                },
                {
                    view?.let {
                        bindingFragmentCharacter.progressBar.visibility = View.INVISIBLE
                        bindingFragmentCharacter.layoutError.visibility = View.VISIBLE
                    }
                })
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingFragmentCharacter = null
    }

    override fun onResume() {
        super.onResume()
        requestCharacters()
    }

    interface OnItemClickListener{
        fun onItemClicked(character: Character)
    }
}