package com.groupal.mygameofthronesapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.groupal.mygameofthronesapp.databinding.ActivityCharactersBinding


class CharactersActivity : AppCompatActivity(), CharactersFragment.OnItemClickListener{

    private lateinit var bindingCharacters: ActivityCharactersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        bindingCharacters = ActivityCharactersBinding.inflate(layoutInflater) //asi se hace cuando se usa viewBinding
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_characters)
        setContentView(bindingCharacters.root)

//        val fragment = CharactersFragment()

        if(savedInstanceState == null){ //para que al rotar no oscurezca la fuente de letras
            val fragment = CharactersFragment()

            this.supportFragmentManager
                    .beginTransaction()
                    .add(R.id.listContainer, fragment)
                    .commit()
        }
    }

    override fun onItemClicked(character: Character) {

        if(isDetailViewAvailable())
            showFragmentDetail(character.id)
        else
            launchDetailActivity(character.id)
    }

    fun isDetailViewAvailable():Boolean{
      return bindingCharacters.detailContainer != null
    }

    private fun showFragmentDetail(characterId: String) {
        val detailFragment = DetailFragment.newInstance(characterId)

//        val args = Bundle()
//        args.putString("key_id", characterId)
//        detailFragment.arguments = args

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.detailContainer, detailFragment)
                .commit()
    }

    private fun launchDetailActivity(characterId: String) {
        val intent: Intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("key_id", characterId)
        startActivity(intent)
    }
}