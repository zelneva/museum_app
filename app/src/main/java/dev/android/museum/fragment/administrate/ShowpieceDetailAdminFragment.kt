package dev.android.museum.fragment.administrate

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.button.MaterialButton
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import dev.android.museum.R
import dev.android.museum.fragment.abstractFragment.IShowpieceDetailFragment
import dev.android.museum.model.AuthorLocaleData
import dev.android.museum.model.ShowpieceLocaleData
import dev.android.museum.presenters.common.ShowpieceDetailPresenter
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class ShowpieceDetailAdminFragment : Fragment(), IShowpieceDetailFragment {

    private val RESULT_LOAD_IMAGE = 1

    companion object {
        val SHOWPIECE_ID = "showpieceId"

        fun newInstance(showpieceId: String): ShowpieceDetailAdminFragment {
            val fragment = ShowpieceDetailAdminFragment()
            val bundle = Bundle()
            bundle.putString(SHOWPIECE_ID, showpieceId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var btnRussian: MaterialButton
    private lateinit var btnEnglish: MaterialButton
    private lateinit var btnGerman: MaterialButton
    private lateinit var description: TextView
    private lateinit var titleDescription: TextView
    private lateinit var title: TextView
    private lateinit var authorName: TextView
    private lateinit var yearCreate: TextView
    private lateinit var seeCommentText: MaterialButton
    private lateinit var seeComment: TableRow
    private lateinit var seeAuthor: TableRow
    private lateinit var seeMore: TextView
    private lateinit var seeLess: TextView
    private lateinit var edit: MaterialButton
    private lateinit var delete: MaterialButton

    lateinit var presenter: ShowpieceDetailPresenter

    private lateinit var authorId: String
    private lateinit var showpieceId: String

    private lateinit var showpieceLD: ShowpieceLocaleData

    private var titleRus = ""
    private var titleEng = ""
    private var titleGer = ""
    private var descriptionRus = ""
    private var descriptionEng = ""
    private var descriptionGer = ""

    private var auhtorList = listOf<String>()

    private var file: File? = null
    private var requestFile: RequestBody? = null
    private var body: MultipartBody.Part? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null && arguments!!.get(SHOWPIECE_ID) != null) {
            showpieceId = arguments!!.get(SHOWPIECE_ID) as String
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_admin_showpiece_detail, container, false)
        init(view)
        presenter = ShowpieceDetailPresenter(this)
        presenter.loadInfoShowpieceDetail(showpieceId)
        presenter.loadAuthor()
        return view
    }


    private fun init(view: View) {
        description = view.findViewById(R.id.showpiece_admin_description)
        btnRussian = view.findViewById(R.id.btn_rus)
        btnEnglish = view.findViewById(R.id.btn_eng)
        btnGerman = view.findViewById(R.id.btn_ger)
        title = view.findViewById(R.id.showpiece_admin_title)
        titleDescription = view.findViewById(R.id.description_title)
        authorName = view.findViewById(R.id.showpiece_admin_author)
        yearCreate = view.findViewById(R.id.showpiece_admin_year)
        seeCommentText = view.findViewById(R.id.see_comment_text)
        seeComment = view.findViewById(R.id.see_admin_comment)
        seeAuthor = view.findViewById(R.id.see_admin_author)
        edit = view.findViewById(R.id.edit_admin_showpiece)
        delete = view.findViewById(R.id.delete_admin_showpiece_from_exhibition)

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
        btnRussian.setOnClickListener(clickListenerLanguage)
        btnEnglish.setOnClickListener(clickListenerLanguage)
        btnGerman.setOnClickListener(clickListenerLanguage)
        edit.setOnClickListener(editShowpieceInfo)
        delete.setOnClickListener(deleteShowpiece)
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
        //        listener?.openCommentAdminFragment(showpieceId)
    }


    override fun displayDetailInfo(showpiece: ShowpieceLocaleData) {
        showpieceLD = showpiece
        title.text = showpiece.name
        description.text = showpiece.description
        yearCreate.text = showpiece.showpiece.date.toString()
        authorId = showpiece.showpiece.author.id.toString()

        when (showpiece.language) {
            "ru" -> {
                titleDescription.text = resources.getText(R.string.description_ru)
                seeCommentText.text = resources.getText(R.string.see_comment_ru)
                titleRus = showpiece.name
                descriptionRus = showpiece.description
            }
            "en" -> {
                titleDescription.text = resources.getText(R.string.description_en)
                seeCommentText.text = resources.getText(R.string.see_comment_en)
                titleEng = showpiece.name
                descriptionEng = showpiece.description
            }
            "ge" -> {
                titleDescription.text = resources.getText(R.string.description_ge)
                seeCommentText.text = resources.getText(R.string.see_comment_ge)
                titleGer = showpiece.name
                descriptionGer = showpiece.description
            }
        }
    }


    private val editShowpieceInfo = View.OnClickListener {
        updateShowpiece()
    }


    private val deleteShowpiece = View.OnClickListener {
        val builder = AlertDialog.Builder(context)
        if(showpieceLD.showpiece.exhibition != null){
            builder.setTitle("Вы уверены, что хотите удалить экспонат из выставки?")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog!!.cancel()
                        presenter.deleteShowpieceFromExhibition(showpieceLD)
                    }
            val alert = builder.create()
            alert.show()
        }
//        else{
//            presenter
//        }

    }


    override fun openShowpieceList(exhibitionId: String) {
        listener?.openShowpieceList(exhibitionId)
    }


    override fun displayAuthorName(author: AuthorLocaleData) {
        authorName.text = author.name
    }


    private fun updateShowpiece() {

        val builder = AlertDialog.Builder(activity)
        val showpieceView = activity!!.layoutInflater.inflate(R.layout.dialog_showpiece, null)
        val button = showpieceView.findViewById<Button>(R.id.load_image_showpiece)
        val yearInput = showpieceView.findViewById<TextInputLayout>(R.id.year_showpiece)
        val yearText = showpieceView.findViewById<EditText>(R.id.year_dialog)
        yearText.setText(yearCreate.text)
        val authorSpinner = showpieceView.findViewById<Spinner>(R.id.spinner_showpiece)
        val btnRus = showpieceView.findViewById<MaterialButton>(R.id.btn_rus)
        val btnEng = showpieceView.findViewById<MaterialButton>(R.id.btn_eng)
        val btnGer = showpieceView.findViewById<MaterialButton>(R.id.btn_ger)

        button.setOnClickListener {
            val i = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

            startActivityForResult(i, RESULT_LOAD_IMAGE)
        }

        val adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, auhtorList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        authorSpinner.adapter = adapter
        authorSpinner.prompt = authorName.text
        var authorName = ""
        authorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                authorName = auhtorList[position]
            }
        }

        btnRus.setOnClickListener { updateShowpieceLocale("ru") }
        btnEng.setOnClickListener { updateShowpieceLocale("en") }
        btnGer.setOnClickListener { updateShowpieceLocale("ge") }


        builder.setView(showpieceView)
        builder.setTitle("Создание экспоната")
                .setCancelable(false)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", null)
        val showpieceAlert = builder.create()
        showpieceAlert.show()
        showpieceAlert.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {
            if (presenter.updateShowpiece(showpieceId, body!!,
                            yearInput.editText!!.text.toString(),
                            authorName,
                            titleRus, descriptionRus,
                            titleEng, descriptionEng,
                            titleGer, descriptionGer)) {
                showpieceAlert.cancel()
            }
        }
    }


    private fun updateShowpieceLocale(language: String) {
        val builder = AlertDialog.Builder(activity)
        val showpieceView = activity!!.layoutInflater.inflate(R.layout.dialog_showpiece_locale, null)
        val titleInput = showpieceView.findViewById<TextInputLayout>(R.id.title_showpiece)
        val titleText = showpieceView.findViewById<EditText>(R.id.title_text)
        val descriptionInput = showpieceView.findViewById<TextInputLayout>(R.id.description_showpiece)
        val descriptionText = showpieceView.findViewById<EditText>(R.id.description_text)

        when (language) {
            "ru" -> {
                titleText.setText(titleRus)
                descriptionText.setText(descriptionRus)
            }
            "en" -> {
                titleText.setText(titleEng)
                descriptionText.setText(descriptionEng)
            }
            "ge" -> {
                titleText.setText(titleGer)
                descriptionText.setText(descriptionGer)
            }

        }

        builder.setView(showpieceView)
        builder.setTitle("Локализация экспоната")
                .setCancelable(false)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", null)

        val showpieceAlert = builder.create()
        showpieceAlert.show()
        showpieceAlert.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {
            when (language) {
                "ru" -> {
                    titleRus = titleInput.editText!!.text.toString()
                    descriptionRus = descriptionInput.editText!!.text.toString()
                }
                "en" -> {
                    titleEng = titleInput.editText!!.text.toString()
                    descriptionEng = descriptionInput.editText!!.text.toString()
                }
                "ge" -> {
                    titleGer = titleInput.editText!!.text.toString()
                    descriptionGer = descriptionInput.editText!!.text.toString()
                }
            }
            showpieceAlert.cancel()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            val selectedImage = data.getData()
            val filePathColumn: Array<String> = arrayOf(MediaStore.Images.Media.DATA)

            val cursor = activity!!.contentResolver.query(selectedImage,
                    filePathColumn, null, null, null)
            cursor.moveToFirst()

            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val picturePath = cursor.getString(columnIndex)
            file = File(picturePath)
            requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            body = MultipartBody.Part.createFormData("image", file?.name, requestFile);
            Toast.makeText(context, picturePath, Toast.LENGTH_SHORT).show()
            cursor.close()

        }
    }


    override fun loadAuthor(author: ArrayList<AuthorLocaleData>) {
        auhtorList = author.toList()
                .filter { it.language == "ru" }
                .map { it.name }
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
        //        fun openCommentAdminFragment(showpieceId: String)
        fun openShowpieceList(exhibitionId: String)
    }

    override fun alertNullUser() {
    }

    override fun isCheckFavorite(id: String) {

    }
}
