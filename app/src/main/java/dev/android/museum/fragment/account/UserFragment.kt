package dev.android.museum.fragment.account

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.*
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
import dev.android.museum.R
import dev.android.museum.model.Favorite
import dev.android.museum.model.User
import dev.android.museum.presenters.account.UserPresenter

class UserFragment : Fragment() {

    companion object {
        val USER_ID = "userId"

        fun newInstance(userId: String): UserFragment {
            val fragment = UserFragment()
            val bundle = Bundle()
            bundle.putString(USER_ID, userId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var presenter: UserPresenter
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var image: CircleImageView
    private lateinit var name: TextView

    private lateinit var userId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null && arguments!!.get(USER_ID) != null) {
            userId = arguments!!.get(USER_ID) as String
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        init(view)
        presenter = UserPresenter(this)
        presenter.getUserInfo(userId)
        setHasOptionsMenu(true)
        return view
    }


    private fun init(view: View){
        image = view.findViewById(R.id.user_photo)
        name = view.findViewById(R.id.user_name)
    }


    fun displayUserInfo(userResponse: User){
        name.text = userResponse.name
        //добавить загрузку фото
    }


    fun displayFavorite(listFavorite: List<Favorite>){

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.favorite_container, FavoriteFragment.newInstance())
        transaction.commit()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    fun openAlertDialog(alertText: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(alertText)
                .setPositiveButton("OK") { dialog, _ -> dialog!!.cancel() }
        val alert = builder.create()
        alert.show()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.change_name_item -> {
                showResetUsernameAlert()
                return true
            }
            R.id.change_password_item -> {
                showResetPasswordAlert()
                return true
            }
            R.id.delete_account_item->{
                // Добавить диалог с подтверждением
                presenter.deleteAccount()
                return true
            }
            R.id.exit_item -> {
                presenter.exitFromAccount()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    fun exitFromAccount() {
        listener?.onButtonExit()
    }


    private fun showResetPasswordAlert() {
        val builder = AlertDialog.Builder(activity)
        val resetPasswordView = activity!!.layoutInflater.inflate(R.layout.dialog_change_password, null)
        val password = resetPasswordView.findViewById<TextInputLayout>(R.id.old_password)
        val newPassword = resetPasswordView.findViewById<TextInputLayout>(R.id.new_password)
        val conPassword = resetPasswordView.findViewById<TextInputLayout>(R.id.confirm_password)
        builder.setView(resetPasswordView)
        builder.setTitle("Изменение пароля")
                .setCancelable(false)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", null)
        val resetPasswordAlert = builder.create()
        resetPasswordAlert.show()
        resetPasswordAlert.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {
            if(presenter.resetPassword(password.editText?.text.toString(), newPassword.editText?.text.toString(), conPassword.editText?.text.toString())){
                resetPasswordAlert.cancel()
            }
        }
    }


    private fun showResetUsernameAlert() {
        val builder = AlertDialog.Builder(activity)
        val resetUsernameView = activity!!.layoutInflater.inflate(R.layout.dialog_change_name, null)
        val newUsername = resetUsernameView.findViewById(R.id.new_username) as TextInputLayout
        builder.setView(resetUsernameView)
        builder.setTitle("Изменение имени")
                .setCancelable(false)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", null)
        val resetUsernameAlert = builder.create()
        resetUsernameAlert.show()
        resetUsernameAlert.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {
            if(presenter.resetName(newUsername.editText?.text.toString())){
                resetUsernameAlert.cancel()
            }
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener") as Throwable
        }
    }


    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        fun onButtonExit()
    }
}
