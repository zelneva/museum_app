package dev.android.museum.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

import dev.android.museum.R

class AdminActionFragment : Fragment() {

    companion object {
        fun newInstance() = AdminActionFragment()
    }

    private lateinit var listViewAction: ListView


    private var listener: OnFragmentInteractionListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_admin_action, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        listViewAction = view.findViewById(R.id.list_view_admin)
        val actionTitle = resources.getStringArray(R.array.admin_action_title)
        val adapter = ArrayAdapter<String>(context, R.layout.list_item_admin_action, actionTitle)
        listViewAction.adapter = adapter
        listViewAction.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(context,actionTitle[position], Toast.LENGTH_SHORT).show()
        }
    }


//    fun onButtonPressed(uri: Uri) {
//        listener?.onFragmentInteraction(uri)
//    }

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
    }

}
