package com.example.logonrmlocal.githubaac.ui.userprofile


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import android.support.annotation.Nullable
import android.arch.lifecycle.ViewModelProviders
import com.example.logonrmlocal.githubaac.R
import com.example.logonrmlocal.githubaac.data.local.entity.User
import dagger.android.support.AndroidSupportInjection
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user_profile.*


class UserProfileFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: UserProfileViewModel
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.configureDagger()
        this.configureViewModel()
        this.setUpView()
    }

    private fun setUpView() {
        btPesquisar.setOnClickListener {
            viewModel.getUser(etUsuario.text.toString())
            viewModel.user.observe(this, Observer {
                 updateUI(it)
            })
        }
    }

    private fun updateUI(user: User?) {
        if (user != null) {
            Picasso.get().load(user.avatarURL).into(ivUsuario)
            tvUsuario.text = user.login
        }
    }

    private fun configureDagger() {
        AndroidSupportInjection.inject(this)
    }

    private fun configureViewModel() {
        viewModel = ViewModelProviders.of(this,
                viewModelFactory).get(UserProfileViewModel::class.java)
    }
}