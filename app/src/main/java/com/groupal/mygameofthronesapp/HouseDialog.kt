package com.groupal.mygameofthronesapp

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.groupal.mygameofthronesapp.databinding.ActivityDetailBinding
import com.groupal.mygameofthronesapp.databinding.DialogHouseBinding
import com.groupal.mygameofthronesapp.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso
import java.io.Serializable

class HouseDialog: DialogFragment() {
    companion object{
        fun newInstance(house: House): HouseDialog{
            val arguments = Bundle()
            arguments.putSerializable("key_house", house as Serializable)

            val dialog = HouseDialog()
            dialog.arguments = arguments
            return dialog
        }
    }

    private var _bindingDialog: DialogHouseBinding? = null
    private val bindingDialog get() = _bindingDialog!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _bindingDialog = DialogHouseBinding.inflate(inflater, container, false)
        val view = bindingDialog.root
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val view = bindingDialog.root
//        val bindingDetail = DialogHouseBinding.inflate(layoutInflater)

        _bindingDialog = DialogHouseBinding.inflate(LayoutInflater.from(context))

        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_house, null, false)
        val house = arguments?.getSerializable("key_house") as House
        with(house){
            bindingDialog.labelName.text = name
            bindingDialog.labelRegion.text = region
            bindingDialog.labelWords.text = words
            bindingDialog.layoutDialog.background = context?.let { ContextCompat.getDrawable(it, House.getBaseColor(name)) }
        }

        Picasso.get()
                .load(house.imagen)
                .placeholder(R.drawable.test)
                .into(bindingDialog.imgHouse)

        return AlertDialog.Builder(requireActivity())
                .setView(bindingDialog.root)
                .setPositiveButton(R.string.label_accept, {_, _ -> dismiss()})
                .create()

//        return AlertDialog.Builder(context) //para que al darle al boton desaparezca y no haga nada es decir dismiss()
//                .setView(dialogView)
//                .setPositiveButton(R.string.label_accept, {_, _ -> dismiss()})
//                .create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingDialog= null
    }

}