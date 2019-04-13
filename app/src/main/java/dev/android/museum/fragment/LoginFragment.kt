package dev.android.museum.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import dev.android.museum.R
import dev.android.museum.presenters.LoginPresenter


class LoginFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var presenter: LoginPresenter

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var signInBtn: Button
    private lateinit var signUpBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        init(view)
        presenter = LoginPresenter(this)
        return view
    }


    private fun init(view: View) {
        username = view.findViewById(R.id.username_login_txt)
        password = view.findViewById(R.id.password_login_txt)
        signInBtn = view.findViewById(R.id.sign_in_login_btn)
        signUpBtn = view.findViewById(R.id.sign_up_login_btn)

        signInBtn.setOnClickListener(clickListenerSignIn)
        signUpBtn.setOnClickListener(clickListenerSignUp)
    }


    private val clickListenerSignIn = View.OnClickListener {
        val username = username.text.toString()
        val password = password.text.toString()
        presenter.authorize(username, password)
    }

    fun authorize(isExistsUser: Boolean){
        if (!isExistsUser) {
            openAlertDialog()
        }
    }


    private val clickListenerSignUp = View.OnClickListener {
        listener?.onButtonSignUpOpenFragment()
    }


    private fun openAlertDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Такого пользователя не существует")
                .setPositiveButton("OK") { dialog, _ -> dialog!!.cancel() }
        val alert = builder.create()
        alert.show()
    }


    fun openUserFragment() {
        listener?.openUserFragment()
    }


    fun openAdminFragment() {
        listener?.openAdminFragment()
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
        fun onButtonSignUpOpenFragment()
        fun openAdminFragment()
        fun openUserFragment()
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}
