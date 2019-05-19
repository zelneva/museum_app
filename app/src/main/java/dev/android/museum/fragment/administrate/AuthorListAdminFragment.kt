package dev.android.museum.fragment.administrate

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.button.MaterialButton
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import dev.android.museum.R
import dev.android.museum.adapter.AuthorRVAdapter
import dev.android.museum.adapter.AuthorRecyclerViewAdapter
import dev.android.museum.adapter.SampleRecycler
import dev.android.museum.model.AuthorLocaleData
import dev.android.museum.presenters.administrate.AuthorListAdminPresenter
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AuthorListAdminFragment : Fragment() {


    companion object {
        fun newInstance() = AuthorListAdminFragment()
    }

    private val RESULT_LOAD_IMAGE = 1
    private lateinit var presenter: AuthorListAdminPresenter
    private lateinit var rv: RecyclerView
    private lateinit var adapter: AuthorRVAdapter
    lateinit var progressBar: ProgressBar
    private lateinit var fab: FloatingActionButton

    private var titleRus = ""
    private var titleEng = ""
    private var titleGer = ""
    private var descriptionRus = ""
    private var descriptionEng = ""
    private var descriptionGer = ""

    private var file: File? = null
    private var requestFile: RequestBody? = null
    private var body: MultipartBody.Part? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_admin_author_list, container, false)
        progressBar = view.findViewById(R.id.progress_bar)
        setupView(view)
        presenter = AuthorListAdminPresenter(this)
        presenter.loadAuthorList()
        return view
    }


    private fun setupView(view: View) {
        fab = view.findViewById(R.id.fab)
        fab.setOnClickListener(createNewAuthor)

        rv = view.findViewById(R.id.showpiece_admin_list)
        rv.adapter = SampleRecycler()
        rv.layoutManager = GridLayoutManager(this.context, 2)
    }


    private val createNewAuthor = View.OnClickListener {
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

        btnRus.setOnClickListener { createAuthorLocale("ru") }
        btnEng.setOnClickListener { createAuthorLocale("en") }
        btnGer.setOnClickListener { createAuthorLocale("ge") }

        builder.setView(authorView)
        builder.setTitle("Создание автора")
                .setCancelable(false)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", null)
        val showpieceAlert = builder.create()
        showpieceAlert.show()
        showpieceAlert.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {
                        if (presenter.createAuthor(body!!,
                                        bornInput.editText!!.text.toString(),
                                        deadInput.editText!!.text.toString(),
                                        titleRus, descriptionRus,
                                        titleEng, descriptionEng,
                                        titleGer, descriptionGer)) {
            showpieceAlert.cancel()
            }
        }
    }


    private fun createAuthorLocale(language: String) {
        val builder = AlertDialog.Builder(activity)
        val authorView = activity!!.layoutInflater.inflate(R.layout.dialog_author_locale, null)
        val titleInput = authorView.findViewById<TextInputLayout>(R.id.title_author)
        val titleText = authorView.findViewById<EditText>(R.id.title_text)
        val descriptionInput = authorView.findViewById<TextInputLayout>(R.id.description_author)
        val descriptionText = authorView.findViewById<EditText>(R.id.description_text)

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


    fun displayListAuthor(authorResponce: ArrayList<AuthorLocaleData>) {
        adapter = AuthorRVAdapter(authorResponce, context!!)
        rv.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}