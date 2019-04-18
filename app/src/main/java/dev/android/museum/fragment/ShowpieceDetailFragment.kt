package dev.android.museum.fragment

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
import dev.android.museum.activity.MainActivity


class ShowpieceDetailFragment : Fragment() {

    lateinit var btnRussian: Button
    lateinit var btnEnglish: Button
    lateinit var btnGerman: Button
    lateinit var description: TextView
    lateinit var titleDescription: TextView
    lateinit var title: TextView
    lateinit var authorName: TextView
    lateinit var yearCreate: TextView
    lateinit var btnFavorite: ToggleButton
    lateinit var seeCommentText: TextView
    lateinit var seeComment: TableRow
    lateinit var seeAuthor: TableRow

    lateinit var seeMore: TextView
    lateinit var seeLess: TextView

    lateinit var authorId: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_showpiece_detail, container, false)
        init(view)

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
        seeCommentText =  view.findViewById(R.id.see_comment_text)
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

        seeComment.setOnClickListener(clickListnerComment)
        seeAuthor.setOnClickListener(clickListenerAuthor)
        btnFavorite.setOnCheckedChangeListener(changeListener)
        btnRussian.setOnClickListener(clickListenerLanguage)
        btnEnglish.setOnClickListener(clickListenerLanguage)
        btnGerman.setOnClickListener(clickListenerLanguage)
    }


    private val changeListener = CompoundButton.OnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
        compoundButton.startAnimation(animate())
        if (isChecked) {
            Toast.makeText(this.context, "Избранное", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this.context, "Не избранное", Toast.LENGTH_SHORT).show()
        }
    }


    private val clickListenerAuthor = View.OnClickListener {
//        val fragment = AuthorDetailFragment.newInstance()
//        val activity: MainActivity = context as MainActivity
//        val ft = activity.supportFragmentManager.beginTransaction()
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
//        ft.replace(R.id.main_container, fragment).addToBackStack(null).commit()
    }


    private val clickListenerLanguage = View.OnClickListener { view ->
        when (view) {

            btnRussian -> {
                Toast.makeText(this.context, "РУССКИЙ", Toast.LENGTH_SHORT).show()
                description.text = "Русский"
                titleDescription.text = resources.getText(R.string.description_ru)
                seeCommentText.text = resources.getText(R.string.see_comment_ru)
            }

            btnEnglish -> {
                description.text = "English"
                titleDescription.text = resources.getText(R.string.description_en)
                seeCommentText.text = resources.getText(R.string.see_comment_en)
            }

            btnGerman -> {
                description.text = "German"
                titleDescription.text = resources.getText(R.string.description_ge)
                seeCommentText.text = resources.getText(R.string.see_comment_ge)
            }
        }
    }

    private val clickListnerComment = View.OnClickListener {
        val fragment = CommentListFragment.newInstance()
        val activity: MainActivity = context as MainActivity
        val ft = activity.supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
        ft.replace(R.id.main_container, fragment).addToBackStack(null).commit()
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


    companion object {
        fun newInstance(): ShowpieceDetailFragment = ShowpieceDetailFragment()
    }
}