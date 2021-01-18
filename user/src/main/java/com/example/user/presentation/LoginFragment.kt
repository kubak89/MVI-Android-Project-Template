package com.example.user.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.base.presentation.BaseFragment
import com.example.base.presentation.MviObservableView
import com.example.user.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewState, LoginViewEvent, LoginPresenter>(),
        MviObservableView.Listener<LoginIntent> {

    override val presenter by viewModels<LoginPresenter>()

    private lateinit var mviView: LoginMviView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mviView = LoginMviView(inflater, container)
        return mviView.rootView
    }

    override fun onStart() {
        super.onStart()
        mviView.registerListener(this)
    }

    override fun onStop() {
        super.onStop()
        mviView.unregisterListener(this)
    }

    override fun render(viewState: LoginViewState) {
        mviView.render(viewState)
    }

    override fun handle(viewEvent: LoginViewEvent) {
        when (viewEvent) {
            is LoginViewEvent.LoginFailed -> Toast.makeText(requireContext(), R.string.login_failed, Toast.LENGTH_SHORT).show()
            is LoginViewEvent.Navigate -> navigation.navigate(viewEvent.destination)
        }
    }

    override fun onViewIntent(viewIntent: LoginIntent) {
        presenter.acceptIntent(viewIntent)
    }
}
