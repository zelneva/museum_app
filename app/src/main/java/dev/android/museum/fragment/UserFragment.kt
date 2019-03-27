package dev.android.museum.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Toast
import dev.android.museum.R

class UserFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.user_fragment, container, false)
        setHasOptionsMenu(true);
        return view
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


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.change_name_item->{
                showResetUsernameAlert()
                Toast.makeText(this.context, item.title, Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.change_password_item ->{
                Toast.makeText(this.context, item.title, Toast.LENGTH_SHORT).show()
                showResetPasswordAlert()
                return true
            }
            R.id.exit_item ->{
                Toast.makeText(this.context, item.title, Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showResetPasswordAlert() {
        val builder = AlertDialog.Builder(activity)
        val resetPasswordView = activity!!.layoutInflater.inflate(R.layout.change_password, null)
        val password = resetPasswordView.findViewById(R.id.old_password) as TextInputLayout
        val newPassword = resetPasswordView.findViewById(R.id.new_password) as TextInputLayout
        val conPassword = resetPasswordView.findViewById(R.id.confirm_password) as TextInputLayout
        builder.setView(resetPasswordView)
        builder.setTitle("Изменение пароля")
                .setCancelable(false)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", null)
        val resetPasswordAlert = builder.create()
        resetPasswordAlert.show()
        resetPasswordAlert.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {
            //settingsPresenter.resetPassword(password.editText?.text.toString(), newPassword.editText?.text.toString(), conPassword.editText?.text.toString())
            resetPassword(password.editText?.text.toString(), newPassword.editText?.text.toString(), conPassword.editText?.text.toString())
        }
    }


    private fun showResetUsernameAlert() {
        val builder = AlertDialog.Builder(activity)
        val resetUsernameView = activity!!.layoutInflater.inflate(R.layout.change_name, null)
        val password = resetUsernameView.findViewById(R.id.new_username) as TextInputLayout
        builder.setView(resetUsernameView)
        builder.setTitle("Изменение имени")
                .setCancelable(false)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", null)
        val resetUsernameAlert = builder.create()
        resetUsernameAlert.show()
        resetUsernameAlert.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener{
            //settingsPresenter.resetPassword(password.editText?.text.toString(), newPassword.editText?.text.toString(), conPassword.editText?.text.toString())
//            resetPassword(password.editText?.text.toString(), newPassword.editText?.text.toString(), conPassword.editText?.text.toString())
        }
    }

    fun resetPassword(password: String, newPassword: String, conPassword: String) {

    }


    companion object {
        fun newInstance(): UserFragment = UserFragment()
    }
}