package dev.android.museum.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import dev.android.museum.R
import dev.android.museum.model.Author
import dev.android.museum.model.AuthorLocaleData
import dev.android.museum.presenters.AuthorDetailPresenter

class AuthorDetailFragment : Fragment() {


    companion object {

        val AUTHOR_ID = "authorId"

        fun newInstance(authorId: String): AuthorDetailFragment {
            val fragment = AuthorDetailFragment()
            val bundle = Bundle()
            bundle.putString(AUTHOR_ID, authorId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var btnRussian: Button
    private lateinit var btnEnglish: Button
    private lateinit var btnGerman: Button
    private lateinit var description: TextView
    private lateinit var titleDescription: TextView
    private lateinit var titleShowShowpieces: TextView
    private lateinit var name: TextView
    private lateinit var year: TextView
    private lateinit var showShowpiece: TableRow
    private lateinit var btnMore: TextView
    private lateinit var btnLess: TextView
    private lateinit var presenter: AuthorDetailPresenter
    private lateinit var authorId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            authorId = arguments!!.get(AUTHOR_ID) as String
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_author_detail, container, false)
        init(view)
        presenter = AuthorDetailPresenter(this)
        presenter.loadInfoAuthorDetail(authorId)
        presenter.loadAuthorConstantData(authorId)
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


    fun displayAuthorDetailInfo(authorLocaleDataResponse: AuthorLocaleData) {
        description.text = authorLocaleDataResponse.description
        name.text = authorLocaleDataResponse.name
        when (authorLocaleDataResponse.language) {
            "ru" -> {
                titleDescription.text = resources.getText(R.string.description_ru)
                titleShowShowpieces.text = resources.getText(R.string.show_showpieces_ru)
            }
            "en" -> {
                titleDescription.text = resources.getText(R.string.description_en)
                titleShowShowpieces.text = resources.getText(R.string.show_showpieces_en)
            }
            "ge" -> {
                titleDescription.text = resources.getText(R.string.description_ge)
                titleShowShowpieces.text = resources.getText(R.string.show_showpieces_ge)
            }
        }
    }


    fun displayDate(authorResponse: Author) {
        if (authorResponse.diedAt.toString() != "") {
            year.text = "${authorResponse.bornAt} - ${authorResponse.diedAt}"
        } else {
            year.text = authorResponse.bornAt.toString()
        }
    }


    private val clickListenerLanguage = View.OnClickListener { view ->
        when (view) {
            btnRussian -> presenter.loadInfoAuthorDetail(authorId, "ru")
            btnEnglish -> presenter.loadInfoAuthorDetail(authorId, "en")
            btnGerman -> presenter.loadInfoAuthorDetail(authorId, "ge")
        }
    }


    private val clickShowShowpiece = View.OnClickListener {
        listener!!.openListShowpieceByAuthor(authorId)
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
        fun openListShowpieceByAuthor(authorId: String)
    }
}
