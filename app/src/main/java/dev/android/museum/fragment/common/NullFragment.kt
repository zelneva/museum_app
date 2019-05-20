package dev.android.museum.fragment.common

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dev.android.museum.R

class NullFragment(): Fragment() {

    private lateinit var text: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.null_fragment, container, false)
        val name: TextView = view.findViewById(R.id.textPrivet)
        name.text = text

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null){
            text = arguments!!.get("name").toString()
        }
    }


    companion object {
        fun newInstance(text: String): NullFragment {
            val fragment = NullFragment()
            val bundle = Bundle()
            bundle.putString("name", text)
            fragment.arguments = bundle
            return fragment
        }
    }
}