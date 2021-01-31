package com.kotlin.marvelgeek.Fragments

import android.R.attr
import com.facebook.appevents.AppEventsLogger
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.facebook.*
import com.facebook.FacebookSdk.getApplicationContext
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.ViewModel.MainViewModel
import com.kotlin.marvelgeek.ui.TestActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*


class LoginFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
    }

    override fun onStart() {
        (activity as AppCompatActivity).supportActionBar?.hide()
        super.onStart()

        val user = viewModel.auth.currentUser

        if (user != null) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        FacebookSdk.sdkInitialize(context)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        viewModel.initAuth()
        viewModel.initGSO()
        val mGoogleSignInClient = activity?.let { GoogleSignIn.getClient(it, viewModel.gso) }



        view.btnLoginGoogle.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient!!.signInIntent
            startActivityForResult(signInIntent, viewModel.RC_SIGN_IN)
        }

        // FACEBOOK SIGN-IN ------------------------------------------------------------------------
        view.btnLoginFacebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))
            LoginManager.getInstance().registerCallback(viewModel.callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult?) {
                        result?.let {
                            val token = result.accessToken
                            val credential = FacebookAuthProvider.getCredential(token.token)

                            FirebaseAuth.getInstance().signInWithCredential(credential)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        //Log.i("TAG", "Login com sucesso")
                                        //viewModel.showToast(getApplicationContext(),"Welcome: ${it.result!!.user!!.email}")
                                        viewModel.user = it.result!!.user!!.email
                                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment2)
                                    } else {
                                        //Log.i("TAG", "Login falho")
                                        viewModel.showToast(getApplicationContext(),"LogIn Failed")
                                    }
                                }
                        }
                    }
                    override fun onCancel() {viewModel.showToast(getApplicationContext(),"LogIn Cancel")}
                    override fun onError(error: FacebookException?) {viewModel.showToast(getApplicationContext(),error.toString())}
                })
        }

        // VISITANTE SIGN-IN -----------------------------------------------------------------------
        view.btnLoginVisitante.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment2)
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        viewModel.callbackManager.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode === viewModel.RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResultGoogle(task)
        }
    }

    private fun handleSignInResultGoogle(completedTask: Task<GoogleSignInAccount>) {
        try {
            val userGoogle = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            if (userGoogle != null) {
                //viewModel.showToast(getApplicationContext(),"Welcome: ${userGoogle.email}")
                viewModel.user = userGoogle.email
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment2)
            }
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            viewModel.showToast(getApplicationContext(),"LogIn failed.")
        }
    }
}