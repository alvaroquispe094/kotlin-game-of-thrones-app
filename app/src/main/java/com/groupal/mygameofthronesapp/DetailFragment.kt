package com.groupal.mygameofthronesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.groupal.mygameofthronesapp.databinding.ActivityCharactersBinding
import com.groupal.mygameofthronesapp.databinding.DataCharacterBinding
import com.groupal.mygameofthronesapp.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso


class DetailFragment: Fragment() {
    companion object{
        fun newInstance(id: String): DetailFragment{
            val instance = DetailFragment()
            val args = Bundle()
            args.putString("key_id", id)
            instance.arguments = args

            return instance
        }
    }

//    private var bindingDetail: FragmentDetailBinding
    private var _bindingDetail: FragmentDetailBinding? = null
    private val bindingDetail get() = _bindingDetail!!

    private var _bindingDataCharacter: DataCharacterBinding? = null
    private val bindingDataCharacter get() = _bindingDataCharacter!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _bindingDetail = FragmentDetailBinding.inflate(inflater, container, false)
        val view = bindingDetail.root
        return view

//        bindingDetail = FragmentDetailBinding.inflate(layoutInflater)
//        setContentView(R.layout.fragment_detail)
//        setContentView(bindingDetail.root)
//        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingDetail = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setContentView(R.layout.fragment_detail)
        val id = arguments?.getString("key_id")
        val character = id?.let { CharactersRepository.findCharacterById(it) }

        character?.let {
            with(character){
                bindingDetail.includeHeaderCharacter?.labelName?.text = name
                bindingDetail.includeDataCharacter?.labelActor?.text = actor
                bindingDetail.includeDataCharacter?.labelBorn?.text = born
                bindingDetail.includeHeaderCharacter?.labelTitle?.text = title
                bindingDetail.includeDataCharacter?.labelQuote?.text = quote
                bindingDetail.includeDataCharacter?.labelSpouse?.text = spouse
                bindingDetail.includeDataCharacter?.labelParents?.text = "${father} & ${mother}"
//                bindingDetail.btnSend.text = house.name
                val overlayColor = House.getOverlayColor(character.house.name)
                bindingDetail.includeHeaderCharacter?.imgOverlay?.background = context?.let { it1 -> ContextCompat.getDrawable(it1, overlayColor) }

                val baseColor = House.getBaseColor(character.house.name)
                bindingDetail.includeHeaderCharacter?.btnHouse?.backgroundTintList = context?.let { it1 -> ContextCompat.getColorStateList(it1, baseColor) }

                val idDrawable = House.getIcon(character.house.name)
                val drawable = context?.let { it1 -> ContextCompat.getDrawable(it1, idDrawable) }
                bindingDetail.includeHeaderCharacter?.btnHouse?.setImageDrawable(drawable)

                Picasso.get()
                    .load(character.imagen)
                    .placeholder(R.drawable.test)
                    .into( bindingDetail.includeHeaderCharacter.imgCharacter)
            }
        }
        bindingDetail.includeHeaderCharacter?.btnHouse?.setOnClickListener{
//            Toast.makeText(context, character?.house?.words, Toast.LENGTH_SHORT).show()
            if(character != null){
                showDialog(character.house)
            }
        }
    }

    private fun showDialog(house: House){
        val dialog = HouseDialog.newInstance(house)
        dialog.show(childFragmentManager, "house_dialog")
    }
}