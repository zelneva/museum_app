package dev.android.museum.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import dev.android.museum.R
import dev.android.museum.activity.MainActivity

class AuthorDetailFragment: Fragment(){

    lateinit var btnRussian: Button
    lateinit var btnEnglish: Button
    lateinit var btnGerman: Button
    lateinit var description: TextView
    lateinit var titleDescription: TextView
    lateinit var titleShowShowpieces: TextView
    lateinit var name: TextView
    lateinit var year: TextView
    lateinit var showShowpiece: TableRow
    lateinit var btnMore: TextView
    lateinit var btnLess: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_author_detail, container, false)
        init(view)

        return view
    }


    private fun init(view: View) {
        description = view.findViewById(R.id.author_description)
        titleDescription = view.findViewById(R.id.description_title)
        titleShowShowpieces = view.findViewById(R.id.show_showpieces_title)
        btnRussian = view.findViewById(R.id.btn_rus)
        btnEnglish = view.findViewById(R.id.btn_eng)
        btnGerman = view.findViewById(R.id.btn_ger)
        name = view.findViewById(R.id.author_name)
        year = view.findViewById(R.id.author_year)
        showShowpiece = view.findViewById(R.id.see_showpiece)
        btnMore = view.findViewById(R.id.btn_more)
        btnLess = view.findViewById(R.id.btn_less)

        btnMore.setOnClickListener {
            btnMore.visibility = View.GONE
            btnLess.visibility = View.VISIBLE
            description.maxLines = Integer.MAX_VALUE
        }

        btnLess.setOnClickListener {
            btnMore.visibility = View.VISIBLE
            btnLess.visibility = View.GONE
            description.maxLines = 5
        }

        btnRussian.setOnClickListener(clickListenerLanguage)
        btnEnglish.setOnClickListener(clickListenerLanguage)
        btnGerman.setOnClickListener(clickListenerLanguage)
        showShowpiece.setOnClickListener(clickShowShowpiece)
    }


    private val clickListenerLanguage = View.OnClickListener { view ->
        when (view) {

            btnRussian -> {
                description.text = "Русский"
                titleDescription.text = resources.getText(R.string.description_ru)
                titleShowShowpieces.text = resources.getText(R.string.show_showpieces_ru)
            }

            btnEnglish -> {
                description.text = "English"
                titleDescription.text = resources.getText(R.string.description_en)
                titleShowShowpieces.text = resources.getText(R.string.show_showpieces_en)
            }

            btnGerman -> {
                description.text = "German"
                titleDescription.text = resources.getText(R.string.description_ge)
                titleShowShowpieces.text = resources.getText(R.string.show_showpieces_ge)
            }
        }
    }


    private val clickShowShowpiece = View.OnClickListener {
//        val showpieceFragment = ShowpieceImageListFragment.newInstance()
//        val activity: MainActivity = context as MainActivity
//
//        val ft = activity.supportFragmentManager.beginTransaction()
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
//        ft.replace(R.id.main_container, showpieceFragment).addToBackStack(null).commit()

    }


    companion object {
        fun newInstance(): AuthorDetailFragment = AuthorDetailFragment()
    }
}
