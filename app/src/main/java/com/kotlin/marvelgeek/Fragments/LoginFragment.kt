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
import androidx.navigation.fragment.findNavController
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.marvelgeek.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*


class LoginFragment : Fragment() {
    private val RC_SIGN_IN = 0
    private lateinit var callbackManager: CallbackManager
    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        (activity as AppCompatActivity).supportActionBar?.hide()
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()

        // GOOGLE SIGN-IN --------------------------------------------------------------------------
        // Configure sign-in to request the user's ID, email address, and basic profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = activity?.let { GoogleSignIn.getClient(it, gso) };

        view.btnLoginGoogle.setOnClickListener{
            val signInIntent: Intent = mGoogleSignInClient!!.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        // FACEBOOK SIGN-IN ------------------------------------------------------------------------
        FacebookSdk.sdkInitialize(view.context);

        callbackManager = CallbackManager.Factory.create()

        btnLoginFacebook.setReadPermissions("email", "public_profile")
        btnLoginFacebook.fragment = this
        btnLoginFacebook.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    handleSignInResultFacebook(loginResult.accessToken)
                }

                override fun onCancel() {}
                override fun onError(error: FacebookException) {}
            })

        // VISITANTE SIGN-IN -----------------------------------------------------------------------
        view.btnLoginVisitante.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment2)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode === RC_SIGN_IN) {
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
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment2)
            }
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(
                activity, "Authentication failed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun handleSignInResultFacebook(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        Log.i("Teste", "handleSignInResultFacebook")
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val userFacebook = auth.currentUser

                    if (userFacebook != null) {
                        callMain(userFacebook.displayName.toString(), userFacebook.email.toString())
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}