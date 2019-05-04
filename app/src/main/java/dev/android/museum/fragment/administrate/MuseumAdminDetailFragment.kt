package dev.android.museum.fragment.administrate

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import dev.android.museum.R
import dev.android.museum.model.Museum
import dev.android.museum.presenters.administrate.MuseumAdminDetailPresenter

class MuseumAdminDetailFragment: Fragment() {

    companion object {
        val MUSEUM_ID = "museumId"

        fun newInstance(museumId: String): MuseumAdminDetailFragment {
            val fragment = MuseumAdminDetailFragment()
            val bundle = Bundle()
            bundle.putString(MUSEUM_ID, museumId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var listener: OnFragmentInteractionListener? = null
    private lateinit var museumId: String
    private lateinit var presenter: MuseumAdminDetailPresenter

    private lateinit var image: ImageView
    private lateinit var title: TextView
    private lateinit var address: TextView
    private lateinit var exhibitions: TextView
    private lateinit var edit: TextView
    private lateinit var delete: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null && arguments!!.get(MUSEUM_ID) != null) {
            museumId = arguments!!.get(MUSEUM_ID) as String
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_admin_museum_detail, container, false)
        init(view)
        presenter = MuseumAdminDetailPresenter(this)
        presenter.loadInfoMuseum(museumId)
        return view
    }


    private fun init(view: View){
        image = view.findViewById(R.id.admin_museum_image)
        title = view.findViewById(R.id.admin_museum_title)
        address = view.findViewById(R.id.admin_museum_address)
        exhibitions = view.findViewById(R.id.admin_museum_exhibition)
        edit = view.findViewById(R.id.admin_museum_edit)
        delete = view.findViewById(R.id.admin_museum_delete)

        exhibitions.setOnClickListener(exhibitionListener)
        edit.setOnClickListener(editListener)
        delete.setOnClickListener(deleteListener)
    }


    private val exhibitionListener = View.OnClickListener {
        listener?.openExhibitionAdminList(museumId)
    }


    private val editListener = View.OnClickListener {
        val builder = AlertDialog.Builder(activity)
        val museumView = activity!!.layoutInflater.inflate(R.layout.dialog_museum, null)
        val newTitle = museumView.findViewById<TextInputLayout>(R.id.new_museum_title)
        val oldTitle = museumView.findViewById<EditText>(R.id.name_mus_dialog)
        val newAddress = museumView.findViewById<TextInputLayout>(R.id.new_museum_address)
        val oldAddress = museumView.findViewById<EditText>(R.id.address_dialog)

        oldTitle.setText(title.text)
        oldAddress.setText(address.text)

        builder.setView(museumView)
        builder.setTitle("Изменение информации о музее")
                .setCancelable(false)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", null)
        val museumAlert = builder.create()
        museumAlert.show()
        museumAlert.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {
            if (presenter.updateMuseum(museumId, newTitle.editText?.text.toString(),
                            newAddress.editText?.text.toString())) {
                museumAlert.cancel()
            }
        }
    }


    private val deleteListener = View.OnClickListener {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Вы точно хотите удалить музей?")
                .setCancelable(false)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", null)
        val alert = builder.create()
        alert.show()
        alert.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {
            presenter.deleteMuseum(museumId)
            alert.cancel()
        }
    }

    fun openMuseumList(){
        listener?.openAdminMuseumList()
    }


    fun displayInfo(museumResponse: Museum){
        //        Picasso.get()
//                .load(museumResponse.srcPhoto)
//                .into(image)

        title.text = museumResponse.name
        address.text = museumResponse.address
    }


    private fun openAlertDialog(alertText: String) {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }



    interface OnFragmentInteractionListener {
        fun openAdminMuseumList()
        fun openExhibitionAdminList(museumId: String)
    }
}