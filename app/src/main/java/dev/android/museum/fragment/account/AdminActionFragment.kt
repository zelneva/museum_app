package dev.android.museum.fragment.account

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.design.button.MaterialButton
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import dev.android.museum.R
import dev.android.museum.activity.NFCActivity

class AdminActionFragment : Fragment() {

    companion object {
        fun newInstance() = AdminActionFragment()
    }

    private lateinit var adminMuseum: MaterialButton
    private lateinit var adminShowpiece: MaterialButton
    private lateinit var adminAuthor: MaterialButton
    private lateinit var adminNFC: MaterialButton

    private var listener: OnFragmentInteractionListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_admin_action, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        adminMuseum = view.findViewById(R.id.admin_museum)
        adminShowpiece = view.findViewById(R.id.admin_showpiece)
        adminAuthor = view.findViewById(R.id.admin_author)
        adminNFC = view.findViewById(R.id.admin_nfc)

        adminMuseum.setOnClickListener { listener!!.openMuseumAdminListFragment() }
        adminShowpiece.setOnClickListener { listener!!.openShowpiecesListAdminFragment() }
        adminAuthor.setOnClickListener { listener!!.openAuthorListAdminFragment() }
        adminNFC.setOnClickListener { listener!!.openNFCActivity() }
    }

    private val writeNfc = View.OnClickListener {
        Toast.makeText(this.context, "!!!!!", Toast.LENGTH_SHORT).show()
//        val builder = AlertDialog.Builder(activity)
//        val nfcView = activity!!.layoutInflater.inflate(R.layout.dialog_nfc, null)
//        val input = nfcView!!.findViewById<TextInputLayout>(R.id.showpiece_id_input)
//        builder.setView(nfcView)
//        builder.setTitle("Запись на метку")
//                .setCancelable(false)
//                .setNegativeButton("Cancel", null)
//                .setPositiveButton("Ok", null)
//        val showpieceAlert = builder.create()
//        showpieceAlert.show()
//        showpieceAlert.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {
//            if(input!!.editText!!.text.toString().isEmpty()) {
//                NFCActivity.str = (input.editText!!.text.toString())
//                showpieceAlert.cancel()
//            }
//        }

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        fun openMuseumAdminListFragment()
        fun openShowpiecesListAdminFragment()
        fun openAuthorListAdminFragment()
        fun openNFCActivity()
    }

}
