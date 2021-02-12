package com.kotlin.marvelgeek.Fragments

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
import androidx.navigation.fragment.findNavController
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.ViewModel.MainViewModel
import com.kotlin.marvelgeek.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {
    private val RC_SIGN_IN = 0
    private lateinit var auth: FirebaseAuth
    private val callbackManager = CallbackManager.Factory.create()
    private val viewModel: MainViewModel by activityViewModels()

    override fun onStart() {
        (activity as AppCompatActivity).supportActionBar?.hide()
        super.onStart()
        viewModel.user = null
        val user = auth.currentUser

        if (user != null) {
            Log.i("Email", user.email.toString())
            viewModel.user = user.email
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

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()

        // GOOGLE SIGN-IN --------------------------------------------------------------------------
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestProfile()
            .requestEmail()
            .build()

        val mGoogleSignInClient = activity?.let { GoogleSignIn.getClient(it, gso) }

        view.btnLoginGoogle.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient!!.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        // FACEBOOK SIGN-IN ------------------------------------------------------------------------
        view.btnLoginFacebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(
                this, listOf(
                    "email",
                    "public_profile"
                )
            )
            LoginManager.getInstance().registerCallback(
                callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult?) {
                        Log.d("Debugando", "facebook:onSuccess");
                        handleSignInResultFacebook(result!!.accessToken)
                    }

                    override fun onCancel() {
                        Log.d("Debugando", "facebook:onCancel");
                    }

                    override fun onError(error: FacebookException?) {
                        Log.d("Debugando", "facebook:onError", error);
                    }
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

        // FACEBOOK
        callbackManager.onActivityResult(requestCode, resultCode, data)

        // GOOGLE
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            Log.i("Task",data.toString())
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("Debugando", "firebaseAuthWithGoogle:" + account.id)
                handleSignInResultGoogle(task)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("Debugando", "Google sign in failed", e)
                // ...
            }
        }
    }

    private fun handleSignInResultGoogle(completedTask: Task<GoogleSignInAccount>) {
        try {
            val userGoogle = completedTask.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(userGoogle?.idToken, null)

            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //Log.d("Debugando", "signInWithCredential:success")
                        viewModel.user = auth.currentUser!!.email
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment2)
                    }
                }.addOnFailureListener {
                    //Log.w("Debugando", "signInWithCredential:failure", completedTask.exception)
                    Toast.makeText(
                        activity, "Authentication failed. + ${completedTask.exception?.cause}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } catch (e: ApiException) {
            Toast.makeText(
                activity, "Authentication failed. + ${e.statusCode}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun handleSignInResultFacebook(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)

        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    //Log.i("TAG", "Login com sucesso")
                    viewModel.user = auth.currentUser!!.email
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment2)
                } else {
                    //Log.i("TAG", "Login falho")
                }
            }.addOnFailureListener {
                //Log.w("Debugando", "signInWithCredential:failure", it.cause)
                Toast.makeText(
                    activity, "Authentication failed. + ${it.cause}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}