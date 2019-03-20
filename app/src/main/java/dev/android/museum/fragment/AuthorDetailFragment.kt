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
    lateinit var name: TextView
    lateinit var year: TextView
    lateinit var showShowpiece: TableRow


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.author_detail_fragment, container, false)
        init(view)

        return view
    }


    private fun init(view: View) {
        description = view.findViewById(R.id.author_description)
        btnRussian = view.findViewById(R.id.btn_rus)
        btnEnglish = view.findViewById(R.id.btn_eng)
        btnGerman = view.findViewById(R.id.btn_ger)
        name = view.findViewById(R.id.author_name)
        year = view.findViewById(R.id.author_year)
        showShowpiece = view.findViewById(R.id.see_more)

        btnRussian.setOnClickListener(clickListnerLanguage)
        btnEnglish.setOnClickListener(clickListnerLanguage)
        btnGerman.setOnClickListener(clickListnerLanguage)
        showShowpiece.setOnClickListener(clickShowShowpiece)
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


    private val clickShowShowpiece = View.OnClickListener {
        val showpieceFragment = ShowpieceImageListFragment.newInstance()
        val activity: MainActivity = context as MainActivity

        val ft = activity.supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
        ft.replace(R.id.main_container, showpieceFragment).addToBackStack(null).commit()

    }




    companion object {
        fun newInstance(): AuthorDetailFragment = AuthorDetailFragment()
    }
}