package com.example.covidstatus.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.covidstatus.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SignInActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private companion object
    {
        private const val RC_SIGN_IN=100
        private const val TAG="GOOGLE_SIGN_IN_TAG"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        firebaseAuth= FirebaseAuth.getInstance()
        signinButton.setOnClickListener {
            progressbar.visibility= View.VISIBLE
            val gso = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.webclient_id))
                .requestEmail()
                .build()
            val signInClient=GoogleSignIn.getClient(this,gso)
            signInClient.signInIntent.also {
                startActivityForResult(it, RC_SIGN_IN)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN)
        {
            val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
            account?.let {
                googleAuthForFirebase(it)
            }
        }
    }

    private fun googleAuthForFirebase(account: GoogleSignInAccount) {
           val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                firebaseAuth.signInWithCredential(credential).await()
                withContext(Dispatchers.Main){
                    Toast.makeText(this@SignInActivity,"Successfully Logged in",Toast.LENGTH_SHORT).show()
                    progressbar.visibility= View.GONE
                    startActivity(Intent(this@SignInActivity,MainActivity::class.java))
                }
            }
            catch (e:Exception)
            {
               withContext(Dispatchers.Main){
                   Toast.makeText(this@SignInActivity,e.message,Toast.LENGTH_LONG).show()
                   progressbar.visibility= View.GONE
               }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentuser=firebaseAuth.currentUser
        if(currentuser!=null)
        {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}