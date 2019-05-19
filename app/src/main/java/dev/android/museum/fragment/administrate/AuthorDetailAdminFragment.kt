package dev.android.museum.fragment.administrate

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.button.MaterialButton
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import dev.android.museum.R
import dev.android.museum.fragment.AuthorDetailFragment
import dev.android.museum.model.AuthorLocaleData
import dev.android.museum.presenters.administrate.AuthorDetailAdminPresenter
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AuthorDetailAdminFragment : Fragment() {

    companion object {
        val AUTHOR_ID = "authorId"

        fun newInstance(authorId: String): AuthorDetailAdminFragment {
            val fragment = AuthorDetailAdminFragment()
            val bundle = Bundle()
            bundle.putString(AUTHOR_ID, authorId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val RESULT_LOAD_IMAGE = 1
    private lateinit var btnRussian: MaterialButton
    private lateinit var btnEnglish: MaterialButton
    private lateinit var btnGerman: MaterialButton
    private lateinit var description: TextView
    private lateinit var titleDescription: TextView
    private lateinit var name: TextView
    private lateinit var year: TextView
    private lateinit var btnMore: TextView
    private lateinit var btnLess: TextView
    private lateinit var photo: ImageView
    private lateinit var edit: TextView
    private lateinit var delete: TextView

    private var titleRus = ""
    private var titleEng = ""
    private var titleGer = ""
    private var descriptionRus = ""
    private var descriptionEng = ""
    private var descriptionGer = ""

    private lateinit var authorId: String
    private lateinit var presenter: AuthorDetailAdminPresenter
    private lateinit var authorLD: AuthorLocaleData

    private var file: File? = null
    private var requestFile: RequestBody? = null
    private var body: MultipartBody.Part? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            authorId = arguments!!.get(AuthorDetailFragment.AUTHOR_ID) as String
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_admin_author_detail, container, false)
        init(view)
        presenter = AuthorDetailAdminPresenter(this)
        presenter.loadAuthor(authorId)
        return view
    }


    private fun init(view: View) {
        photo = view.findViewById(R.id.author_image)
        btnRussian = view.findViewById(R.id.btn_rus)
        btnEnglish = view.findViewById(R.id.btn_eng)
        btnGerman = view.findViewById(R.id.btn_ger)
        name = view.findViewById(R.id.author_name)
        description = view.findViewById(R.id.author_description)
        titleDescription = view.findViewById(R.id.description_title)
        year = view.findViewById(R.id.author_year)
        edit = view.findViewById(R.id.edit_author)
        delete = view.findViewById(R.id.delete_author)
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

        edit.setOnClickListener(editAuthor)
        delete.setOnClickListener(deleteShowpiece)
    }


    private val clickListenerLanguage = View.OnClickListener { view ->
        when (view) {
            btnRussian -> presenter.loadAuthor(authorId, "ru")
            btnEnglish -> presenter.loadAuthor(authorId, "en")
            btnGerman -> presenter.loadAuthor(authorId, "ge")
        }
    }


    fun displayAuthorDetailInfo(authorLocaleDataResponse: AuthorLocaleData) {
        authorLD = authorLocaleDataResponse
        description.text = authorLocaleDataResponse.description
        name.text = authorLocaleDataResponse.name
        if (authorLocaleDataResponse.author.diedAt.toString() != "") {
            year.text = "${authorLocaleDataResponse.author.bornAt} - ${authorLocaleDataResponse.author.diedAt}"
        } else {
            year.text = authorLocaleDataResponse.author.bornAt.toString()
        }
        when (authorLocaleDataResponse.language) {
            "ru" -> {
                titleDescription.text = resources.getText(R.string.description_ru)
                titleRus = authorLocaleDataResponse.name
                descriptionRus = authorLocaleDataResponse.description
            }
            "en" -> {
                titleDescription.text = resources.getText(R.string.description_en)
                titleEng = authorLocaleDataResponse.name
                descriptionEng = authorLocaleDataResponse.description
            }
            "ge" -> {
                titleDescription.text = resources.getText(R.string.description_ge)
                titleGer = authorLocaleDataResponse.name
                descriptionGer = authorLocaleDataResponse.description
            }
        }
    }


    private val editAuthor = View.OnClickListener {
        val builder = AlertDialog.Builder(activity)
        val authorView = activity!!.layoutInflater.inflate(R.layout.dialog_author, null)
        val buttonImage = authorView.findViewById<MaterialButton>(R.id.new_photo)
        val bornInput = authorView.findViewById<TextInputLayout>(R.id.new_author_born)
        val bornText = authorView.findViewById<EditText>(R.id.author_born_text)
        val deadInput = authorView.findViewById<TextInputLayout>(R.id.new_author_dead)
        val deadText = authorView.findViewById<EditText>(R.id.author_dead_text)
        val btnRus = authorView.findViewById<MaterialButton>(R.id.btn_rus)
        val btnEng = authorView.findViewById<MaterialButton>(R.id.btn_eng)
        val btnGer = authorView.findViewById<MaterialButton>(R.id.btn_ger)

        buttonImage.setOnClickListener {
            val i = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

            startActivityForResult(i, RESULT_LOAD_IMAGE)
        }

        btnRus.setOnClickListener { updateLocaleAuthor("ru") }
        btnEng.setOnClickListener { updateLocaleAuthor("en") }
        btnGer.setOnClickListener { updateLocaleAuthor("ge") }

        bornText.setText(authorLD.author.bornAt)
        deadText.setText(authorLD.author.diedAt)

        builder.setView(authorView)
        builder.setTitle("Изменение автора")
                .setCancelable(false)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", null)
        val showpieceAlert = builder.create()
        showpieceAlert.show()
        showpieceAlert.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {
            if (presenter.updateInfoAuthor(authorId, body!!,
                            bornInput.editText!!.text.toString(),
                            deadInput.editText!!.text.toString(),
                            titleRus, descriptionRus,
                            titleEng, descriptionEng,
                            titleGer, descriptionGer)) {
                showpieceAlert.cancel()
            }
        }
    }


    private fun updateLocaleAuthor(language: String) {
        val builder = AlertDialog.Builder(activity)
        val authorView = activity!!.layoutInflater.inflate(R.layout.dialog_author_locale, null)
        val titleInput = authorView.findViewById<TextInputLayout>(R.id.title_author)
        val titleText = authorView.findViewById<EditText>(R.id.title_text)
        val descriptionInput = authorView.findViewById<TextInputLayout>(R.id.description_author)
        val descriptionText = authorView.findViewById<EditText>(R.id.description_text)

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

        builder.setView(authorView)
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


    private val deleteShowpiece = View.OnClickListener {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Вы уверены, что хотите удалить автора?")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog!!.cancel()
                    presenter.deleteAuthor(authorId)
                }
        val alert = builder.create()
        alert.show()
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
}
