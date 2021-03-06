package dev.android.museum.fragment.account

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import dev.android.museum.R
import dev.android.museum.presenters.account.SignUpPresenter


class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var presenter: SignUpPresenter

    private lateinit var username: EditText
    private lateinit var name: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var signUpBtn: Button
    private lateinit var cancelBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        init(view)
        presenter = SignUpPresenter(this)
        return view
    }


    private fun init(view: View) {
        username = view.findViewById(R.id.username_sign_up_txt)
        name = view.findViewById(R.id.name_sign_up_txt)
        password = view.findViewById(R.id.password_sign_up_txt)
        confirmPassword = view.findViewById(R.id.confirm_password_sign_up_txt)
        signUpBtn = view.findViewById(R.id.sign_up_btn)
        cancelBtn = view.findViewById(R.id.cancel_sign_up_btn)

        signUpBtn.setOnClickListener(clickListenerSignIn)
        cancelBtn.setOnClickListener(clickListenerCancel)
    }


    private val clickListenerSignIn = View.OnClickListener {
        val usernameText = username.text.toString()
        val nameText = name.text.toString()
        val passwordText = password.text.toString()
        val confirmPasswordText = confirmPassword.text.toString()

        presenter.signUp(nameText, usernameText, passwordText, confirmPasswordText)
    }


    fun signUp(){
        listener?.onButtonSignUp()
    }


    private val clickListenerCancel = View.OnClickListener {
        listener?.onButtonCancel()
    }


    fun alertSmallUsername(){
        username.error = "Слишком короткий username"
    }

    fun alertSmallName(){
        name.error = "Слишком короткое имя"
    }

    fun alertSmallPassword(){
        password.error = "Слишком короткий пароль"
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
        fun onButtonSignUp()
        fun onButtonCancel()
    }
}
