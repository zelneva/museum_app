package dev.android.museum.fragment.administrate

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
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
import android.widget.*
import dev.android.museum.R
import dev.android.museum.adapter.SampleRecycler
import dev.android.museum.adapter.ShowpieceAdminRVAdapter
import dev.android.museum.model.AuthorLocaleData
import dev.android.museum.model.ShowpieceLocaleData
import dev.android.museum.presenters.administrate.ShowpieceAdminListPresenter
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class ShowpieceListAdminFragment : Fragment() {

    private val RESULT_LOAD_IMAGE = 1

    companion object {
        val EXHIBITION_ID = "exhibitionId"
        val AUTHOR_ID = "authorId"

        fun newInstance(exhibitionId: String): ShowpieceListAdminFragment {
            val fragment = ShowpieceListAdminFragment()
            val bundle = Bundle()
            bundle.putString(EXHIBITION_ID, exhibitionId)
            fragment.arguments = bundle
            return fragment
        }


        fun newInstanceForAuthor(authorId: String): ShowpieceListAdminFragment {
            val fragment = ShowpieceListAdminFragment()
            val bundle = Bundle()
            bundle.putString(AUTHOR_ID, authorId)
            fragment.arguments = bundle
            return fragment
        }


        fun newInstance() = ShowpieceListAdminFragment()
    }


    private lateinit var presenter: ShowpieceAdminListPresenter
    private lateinit var rv: RecyclerView
    private lateinit var adapter: ShowpieceAdminRVAdapter
    lateinit var progressBar: ProgressBar
    lateinit var fab: FloatingActionButton
    private var exhibitionId: String? = null
    private var authorId: String? = null
    private var listener: OnFragmentInteractionListener? = null

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
        if (arguments != null) {
            if (arguments!!.get(EXHIBITION_ID) != null) {
                exhibitionId = arguments!!.get(EXHIBITION_ID) as String
            } else if (arguments!!.get(AUTHOR_ID) != null) {
                authorId = arguments!!.get(AUTHOR_ID) as String
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_admin_showpiece_list, container, false)
        setupView(view)
        presenter = ShowpieceAdminListPresenter(this)

        if (exhibitionId != null) {
            presenter.loadListShowpieceByExhibition(exhibitionId!!)
        } else if (authorId != null) {
            presenter.loadListShowpieceByAuthor(authorId!!)
        } else {
            presenter.loadListShowpiece()
        }
        presenter.loadListAuthor()

        return view
    }


    private fun setupView(view: View) {
        progressBar = view.findViewById(R.id.progress_bar)
        fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener(doSomethingShowpiece)
        rv = view.findViewById(R.id.showpiece_admin_list)
        rv.adapter = SampleRecycler()
        rv.layoutManager = GridLayoutManager(this.context, 2)
    }


    fun displayListShowpiece(showpieceResponse: ArrayList<ShowpieceLocaleData>?) {
        if (showpieceResponse != null) {
            adapter = ShowpieceAdminRVAdapter(showpieceResponse, this.context!!)
            rv.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }


    fun loadAuthor(author: ArrayList<AuthorLocaleData>) {
        auhtorList = author.toList()
                .filter { it.language == "ru" }
                .map { it.name }
    }


    private val doSomethingShowpiece = View.OnClickListener {
        if (exhibitionId != null) {
            addShowpieceToExhibition()
        } else if (authorId != null) {
// !!!!!
        } else {
            createShowpiece()
        }
    }


    private fun createShowpiece() {

        val builder = AlertDialog.Builder(activity)
        val showpieceView = activity!!.layoutInflater.inflate(R.layout.dialog_showpiece, null)
        val button = showpieceView.findViewById<Button>(R.id.load_image_showpiece)
        val yearInput = showpieceView.findViewById<TextInputLayout>(R.id.year_showpiece)
        val yearText = showpieceView.findViewById<EditText>(R.id.year_dialog)
        val authorSpinner = showpieceView.findViewById<Spinner>(R.id.spinner_showpiece)
        val btnRus = showpieceView.findViewById<MaterialButton>(R.id.btn_rus)
        val btnEng = showpieceView.findViewById<MaterialButton>(R.id.btn_eng)
        val btnGer = showpieceView.findViewById<MaterialButton>(R.id.btn_ger)

        button.setOnClickListener {
            val i = Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

            startActivityForResult(i, RESULT_LOAD_IMAGE)
        }

        val adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, auhtorList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        authorSpinner.adapter = adapter
        authorSpinner.prompt = "Выберите автора"
        var authorName = ""
        authorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                authorName = auhtorList[position]
            }
        }

        btnRus.setOnClickListener { createShowpieceLocale("ru") }
        btnEng.setOnClickListener { createShowpieceLocale("en") }
        btnGer.setOnClickListener { createShowpieceLocale("ge") }


        builder.setView(showpieceView)
        builder.setTitle("Создание экспоната")
                .setCancelable(false)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", null)
        val showpieceAlert = builder.create()
        showpieceAlert.show()
        showpieceAlert.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {
            if (presenter.createShowpiece(body!!,
                            yearInput.editText!!.text.toString(),
                            authorName,
                            titleRus, descriptionRus,
                            titleEng, descriptionEng,
                            titleGer, descriptionGer)) {
                showpieceAlert.cancel()
            }
        }
    }


    private fun createShowpieceLocale(language: String) {
        val builder = AlertDialog.Builder(activity)
        val showpieceView = activity!!.layoutInflater.inflate(R.layout.dialog_showpiece_locale, null)
        val titleInput = showpieceView.findViewById<TextInputLayout>(R.id.title_showpiece)
        val titleText = showpieceView.findViewById<EditText>(R.id.title_text)
        val descriptionInput = showpieceView.findViewById<TextInputLayout>(R.id.description_showpiece)
        val descriptionText = showpieceView.findViewById<EditText>(R.id.description_text)

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

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
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


    private fun addShowpieceToExhibition() {
        listener?.openShowpieceSelector(exhibitionId!!)
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
        fun openShowpieceSelector(exhibitionId: String)
    }

    override fun onResume() {
        super.onResume()
        if (exhibitionId != null) {
            presenter.loadListShowpieceByExhibition(exhibitionId!!)
        } else if (authorId != null) {
            presenter.loadListShowpieceByAuthor(authorId!!)
        }
    }
}