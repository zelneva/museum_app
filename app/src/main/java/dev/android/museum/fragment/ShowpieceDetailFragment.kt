package dev.android.museum.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import android.widget.*
import dev.android.museum.R
import dev.android.museum.model.AuthorLocaleData
import dev.android.museum.model.ShowpieceLocaleData
import dev.android.museum.presenters.ShowpieceDetailPresenter


class ShowpieceDetailFragment : Fragment() {

    companion object {
        val SHOWPIECE_ID = "showpieceId"

        fun newInstance(showpieceId: String): ShowpieceDetailFragment {
            val fragment = ShowpieceDetailFragment()
            val bundle = Bundle()
            bundle.putString(SHOWPIECE_ID, showpieceId)
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
    private lateinit var title: TextView
    private lateinit var authorName: TextView
    private lateinit var yearCreate: TextView
    private lateinit var btnFavorite: ToggleButton
    private lateinit var seeCommentText: TextView
    private lateinit var seeComment: TableRow
    private lateinit var seeAuthor: TableRow
    private lateinit var seeMore: TextView
    private lateinit var seeLess: TextView

    lateinit var presenter: ShowpieceDetailPresenter

    private lateinit var authorId: String
    private lateinit var showpieceId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null && arguments!!.get(SHOWPIECE_ID) != null) {
            showpieceId = arguments!!.get(SHOWPIECE_ID) as String
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_showpiece_detail, container, false)
        init(view)
        presenter = ShowpieceDetailPresenter(this)
        presenter.loadInfoShowpieceDetail(showpieceId)
        return view
    }


    private fun init(view: View) {
        description = view.findViewById(R.id.showpiece_description)
        btnRussian = view.findViewById(R.id.btn_rus)
        btnEnglish = view.findViewById(R.id.btn_eng)
        btnGerman = view.findViewById(R.id.btn_ger)
        title = view.findViewById(R.id.showpiece_title)
        titleDescription = view.findViewById(R.id.description_title)
        authorName = view.findViewById(R.id.showpiece_author)
        yearCreate = view.findViewById(R.id.showpiece_year)
        btnFavorite = view.findViewById(R.id.button_favorite)
        seeCommentText = view.findViewById(R.id.see_comment_text)
        seeComment = view.findViewById(R.id.see_comment)
        seeAuthor = view.findViewById(R.id.see_author)

        seeMore = view.findViewById(R.id.btn_more)
        seeLess = view.findViewById(R.id.btn_less)

        seeMore.setOnClickListener {
            seeMore.visibility = View.GONE
            seeLess.visibility = View.VISIBLE
            description.maxLines = Integer.MAX_VALUE
        }

        seeLess.setOnClickListener {
            seeMore.visibility = View.VISIBLE
            seeLess.visibility = View.GONE
            description.maxLines = 5
        }

        seeComment.setOnClickListener(clickListenerComment)
        seeAuthor.setOnClickListener(clickListenerAuthor)
        btnFavorite.setOnCheckedChangeListener(changeListener)
        btnRussian.setOnClickListener(clickListenerLanguage)
        btnEnglish.setOnClickListener(clickListenerLanguage)
        btnGerman.setOnClickListener(clickListenerLanguage)
    }

    private val changeListener = CompoundButton.OnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
        if (isChecked) {
            if (presenter.addFavorite(showpieceId)) {
                compoundButton.startAnimation(animate())
            } else {
                compoundButton.startAnimation(animate())
                compoundButton.isChecked = false
            }
        } else {
            presenter.deleteFavorite(showpieceId)
        }
    }


    private val clickListenerAuthor = View.OnClickListener {
        listener?.openAuthorDetailFragment(authorId)
    }


    private val clickListenerLanguage = View.OnClickListener { view ->
        when (view) {
            btnRussian -> {
                presenter.loadInfoShowpieceDetail(showpieceId, "ru")
                presenter.loadAuthorName(authorId, "ru")
            }
            btnEnglish -> {
                presenter.loadInfoShowpieceDetail(showpieceId, "en")
                presenter.loadAuthorName(authorId, "en")
            }
            btnGerman -> {
                presenter.loadInfoShowpieceDetail(showpieceId, "ge")
                presenter.loadAuthorName(authorId, "ge")
            }
        }
    }

    private val clickListenerComment = View.OnClickListener {
        listener?.openCommentFragment(showpieceId)
    }


    fun displayShowpieceDetailInfo(showpiece: ShowpieceLocaleData) {
        title.text = showpiece.name
        description.text = showpiece.description
        yearCreate.text = showpiece.showpiece.date.toString()
        authorId = showpiece.showpiece.author.id.toString()

        when (showpiece.language) {
            "ru" -> {
                titleDescription.text = resources.getText(R.string.description_ru)
                seeCommentText.text = resources.getText(R.string.see_comment_ru)
            }
            "en" -> {
                titleDescription.text = resources.getText(R.string.description_en)
                seeCommentText.text = resources.getText(R.string.see_comment_en)
            }
            "ge" -> {
                titleDescription.text = resources.getText(R.string.description_ge)
                seeCommentText.text = resources.getText(R.string.see_comment_ge)
            }
        }
    }

    fun displayAuthorName(author: AuthorLocaleData) {
        authorName.text = author.name
    }

    fun alertNullUser() {
        Toast.makeText(context, "Войдите, чтобы добавить в 'Избранное'", Toast.LENGTH_SHORT).show()
    }


    // animation favorite button
    private fun animate(): ScaleAnimation {
        val scaleAnimation = ScaleAnimation(0.8f, 1.0f, 0.8f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f)
        scaleAnimation.duration = 500
        val bounceInterpolator = BounceInterpolator()
        scaleAnimation.interpolator = bounceInterpolator
        return scaleAnimation
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
        fun openCommentFragment(showpieceId: String)
        fun openAuthorDetailFragment(authorId: String)
    }
}
