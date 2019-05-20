package dev.android.museum.fragment.administrate

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import dev.android.museum.R
import dev.android.museum.adapter.ExhibitionAdminRVAdapter
import dev.android.museum.adapter.SampleRecycler
import dev.android.museum.fragment.abstractFragment.IExhibitionListFragment
import dev.android.museum.model.Exhibition
import dev.android.museum.presenters.common.ExhibitionListPresenter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ExhibitionListAdminFragment : Fragment(), IExhibitionListFragment {

    companion object {
        private val MUSEUM_ID = "museumId"

        fun newInstance(museumId: String): ExhibitionListAdminFragment {
            val fragment = ExhibitionListAdminFragment()
            val bundle = Bundle()
            bundle.putString(MUSEUM_ID, museumId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var museumId: String
    private lateinit var presenter: ExhibitionListPresenter

    private lateinit var rv: RecyclerView
    private lateinit var adapter: ExhibitionAdminRVAdapter
    override lateinit var progressBar: ProgressBar
    private lateinit var fab: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null && arguments!!.get(MUSEUM_ID) != null) {
            museumId = arguments!!.get(ExhibitionListAdminFragment.MUSEUM_ID) as String
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_admin_exhibition_list, container, false)
        progressBar = view.findViewById(R.id.progress_bar)
        setupView(view)
        presenter = ExhibitionListPresenter(this)
        presenter.loadListExhibition(museumId)
        return view
    }


    private fun setupView(view: View) {
        fab = view.findViewById(R.id.fab)
        fab.setOnClickListener(createNewExhibition)

        rv = view.findViewById(R.id.admin_exhibition_list_rv)
        rv.adapter = SampleRecycler()
        rv.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
    }


    override fun displayListExhbition(list: ArrayList<Exhibition>) {
        if (list.size != 0) {
            adapter = ExhibitionAdminRVAdapter(list, this.context!!, presenter)
            rv.adapter = adapter
        }
        adapter.notifyDataSetChanged()
    }


    private val createNewExhibition = View.OnClickListener {
        val builder = AlertDialog.Builder(activity)
        val exhView = activity!!.layoutInflater.inflate(R.layout.dialog_exhibition, null)
        val title = exhView.findViewById<TextInputLayout>(R.id.new_exhibition_title)
        val startDay = exhView.findViewById<TextInputLayout>(R.id.start_day_exhibition)
        val editStart = exhView.findViewById<EditText>(R.id.edit_start)
        val finishDay = exhView.findViewById<TextInputLayout>(R.id.finish_day_exhibition)
        val editFinish = exhView.findViewById<EditText>(R.id.edit_finish)
        val selectStart = exhView.findViewById<Button>(R.id.select_start_btn)
        val selectFinish = exhView.findViewById<Button>(R.id.select_finish_btn)

        selectStart.setOnClickListener {
            val calendar = Calendar.getInstance()
            val y = calendar.get(Calendar.YEAR)
            val m = calendar.get(Calendar.MONTH)
            val d = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this.context!!, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val day: String
                if (dayOfMonth < 10) {
                    day = "0$dayOfMonth"
                } else day = "$dayOfMonth"

                val filterMonth: String
                if (month < 10) {
                    filterMonth = "0" + (month + 1).toString()
                } else filterMonth = (month + 1).toString()

                editStart.setText("$day/$filterMonth/$year")
            }, y, m, d)

            datePickerDialog.show()
        }

        selectFinish.setOnClickListener {
            val calendar = Calendar.getInstance()
            val y = calendar.get(Calendar.YEAR)
            val m = calendar.get(Calendar.MONTH)
            val d = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this.context!!, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val day: String
                if (dayOfMonth < 10) {
                    day = "0$dayOfMonth"
                } else day = "$dayOfMonth"

                val filterMonth: String
                if (month < 10) {
                    filterMonth = "0" + (month + 1).toString()
                } else filterMonth = (month + 1).toString()

                editFinish.setText("$day/$filterMonth/$year")
            }, y, m, d)

            datePickerDialog.show()
        }

        builder.setView(exhView)
        builder.setTitle("Создание выставки")
                .setCancelable(false)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", null)
        val alert = builder.create()
        alert.show()
        alert.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {
            if (presenter.createExhibition(title.editText?.text.toString(), editStart?.text.toString(),
                            editFinish?.text.toString(), museumId)) {
                alert.cancel()
            }
        }
    }


    override fun editExhbition(oldExhibition: Exhibition) {
        val builder = AlertDialog.Builder(activity)
        val exhView = activity!!.layoutInflater.inflate(R.layout.dialog_exhibition, null)
        val title = exhView.findViewById<TextInputLayout>(R.id.new_exhibition_title)
        val name = exhView.findViewById<EditText>(R.id.name_exh_dialog)
        val startDay = exhView.findViewById<TextInputLayout>(R.id.start_day_exhibition)
        val editStart = exhView.findViewById<EditText>(R.id.edit_start)
        val finishDay = exhView.findViewById<TextInputLayout>(R.id.finish_day_exhibition)
        val editFinish = exhView.findViewById<EditText>(R.id.edit_finish)
        val selectStart = exhView.findViewById<Button>(R.id.select_start_btn)
        val selectFinish = exhView.findViewById<Button>(R.id.select_finish_btn)

        name.setText(oldExhibition.name)
        editStart.setText(SimpleDateFormat("dd/MM/yyyy").format(oldExhibition.startsAt))
        editFinish.setText(SimpleDateFormat("dd/MM/yyyy").format(oldExhibition.endsAt))

        selectStart.setOnClickListener {
            val calendar = Calendar.getInstance()
            val y = calendar.get(Calendar.YEAR)
            val m = calendar.get(Calendar.MONTH)
            val d = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this.context!!, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val day: String
                if (dayOfMonth < 10) {
                    day = "0$dayOfMonth"
                } else day = "$dayOfMonth"

                val filterMonth: String
                if (month < 10) {
                    filterMonth = "0" + (month + 1).toString()
                } else filterMonth = (month + 1).toString()

                editStart.setText("$day/$filterMonth/$year")
            }, y, m, d)

            datePickerDialog.show()
        }

        selectFinish.setOnClickListener {
            val calendar = Calendar.getInstance()
            val y = calendar.get(Calendar.YEAR)
            val m = calendar.get(Calendar.MONTH)
            val d = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this.context!!, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val day: String
                if (dayOfMonth < 10) {
                    day = "0$dayOfMonth"
                } else day = "$dayOfMonth"

                val filterMonth: String
                if (month < 10) {
                    filterMonth = "0" + (month + 1).toString()
                } else filterMonth = (month + 1).toString()


                editFinish.setText("$day/$filterMonth/$year")
            }, y, m, d)

            datePickerDialog.show()
        }

        builder.setView(exhView)
        builder.setTitle("Создание выставки")
                .setCancelable(false)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", null)
        val alert = builder.create()
        alert.show()
        alert.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {
            if (presenter.updateExhibition(oldExhibition.id, title.editText?.text.toString(), editStart?.text.toString(),
                            editFinish?.text.toString(), museumId)) {
                alert.cancel()
            }
        }

    }

}