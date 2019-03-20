package dev.android.museum.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import dev.android.museum.R

class ShowpieceDetailFragment : Fragment() {

    lateinit var btnRussian: Button
    lateinit var btnEnglish: Button
    lateinit var btnGerman: Button
    lateinit var description: TextView
    lateinit var title: TextView
    lateinit var authorName: TextView
    lateinit var yearCreate: TextView

    lateinit var grid: GridLayout


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.showpiece_detail_fragment, container, false)
        init(view)

        return view
    }


    private val clickListnerLanguage = View.OnClickListener { view ->
        when (view) {

            btnRussian -> {
                Toast.makeText(this.context, "РУССКИЙ", Toast.LENGTH_SHORT).show()
                description.text = "Русский"
            }

            btnEnglish -> {
                description.text = "English"
            }

            btnGerman -> {
                description.text = "German"
            }
        }
    }


    private fun init(view: View) {
        description = view.findViewById(R.id.showpiece_description)
        btnRussian = view.findViewById(R.id.btn_rus)
        btnEnglish = view.findViewById(R.id.btn_eng)
        btnGerman = view.findViewById(R.id.btn_ger)
        title = view.findViewById(R.id.showpiece_title)
        authorName = view.findViewById(R.id.showpiece_author)
        yearCreate = view.findViewById(R.id.showpiece_year)

        btnRussian.setOnClickListener(clickListnerLanguage)
        btnEnglish.setOnClickListener(clickListnerLanguage)
        btnGerman.setOnClickListener(clickListnerLanguage)
    }


    companion object {
        fun newInstance(): ShowpieceDetailFragment = ShowpieceDetailFragment()
    }
}