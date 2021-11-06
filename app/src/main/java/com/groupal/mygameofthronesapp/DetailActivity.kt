package com.groupal.mygameofthronesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.groupal.mygameofthronesapp.databinding.ActivityCharactersBinding.inflate
import com.groupal.mygameofthronesapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val bindingDetail = ActivityDetailBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(bindingDetail.root)

        val id = intent.getStringExtra("key_id").toString()

        if(savedInstanceState == null){
            val fragment = DetailFragment.newInstance(id)
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.detailContainer, fragment)
                    .commit()
        }

    }

    fun showMessage(){
        Toast.makeText(this@DetailActivity,"Winter is comming!!!", Toast.LENGTH_SHORT).show()
    }
}