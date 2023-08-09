package com.moong.dailymission.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.moong.dailymission.R
import com.moong.dailymission.databinding.ActivityLoginBinding
import com.moong.dailymission.util.GlobalApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val binding by lazy{ ActivityLoginBinding.inflate(layoutInflater) }
    private val TAG = this.javaClass.simpleName

    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var firebaseAuth: FirebaseAuth

    private var email: String = ""
    private var tokenId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()

        checkLogin()

        binding.run {
            googleLoginBtn.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    googleLogin()
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build()
                    val googleSignInClient = GoogleSignIn.getClient(this@LoginActivity, gso)
                    val signInIntent: Intent = googleSignInClient.signInIntent
                    launcher.launch(signInIntent)
                }
            }

            anonymousLoginBtn.setOnClickListener {
                anonymousLogin()
            }
        }
    }

    // 자동로그인
    private fun checkLogin(){
        val currentUid = GlobalApplication.prefs.getString("uid")
        if(currentUid.isNotEmpty()){
            goMainActivity()
        }
    }

    private fun googleLogin(){
        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(), ActivityResultCallback { result ->
                if (result.resultCode == RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    try {
                        task.getResult(ApiException::class.java)?.let { account ->
                            tokenId = account.idToken
                            if (tokenId != null && tokenId != "") {
                                val credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
                                firebaseAuth.signInWithCredential(credential)
                                    .addOnCompleteListener {
                                        if (firebaseAuth.currentUser != null) {
                                            val user: FirebaseUser = firebaseAuth.currentUser!!
                                            email = user.email.toString()
                                            Log.e(TAG, "email : $email")
                                            val googleSignInToken = account.idToken ?: ""
                                            if (googleSignInToken != "") {
                                                Log.e(TAG, "googleSignInToken : $googleSignInToken")
                                                goMainActivity()
                                            } else {
                                                Log.e(TAG, "googleSignInToken이 null")
                                            }
                                        }
                                    }
                            }
                        } ?: throw Exception()
                    }   catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            })
    }

    private fun anonymousLogin(){
        val user = firebaseAuth.currentUser
        var userId = ""

        if(user != null){ // 이미 가입한 회원인 경우
            userId = user.uid // uid를 가져온다.
        }else{
            // 익명으로 가입한다.
            firebaseAuth.signInAnonymously().addOnCompleteListener(this){ task ->
                    if(task.isSuccessful){ // 가입 성공한 경우
                        userId = firebaseAuth.currentUser!!.uid
                    }else{
                        // 가입 실패한 경우
                    }
                }
        }
        if(userId.isNotEmpty()){
            goMainActivity()
        }
    }

    private fun goMainActivity(){
        firebaseAuth.currentUser?.uid?.let { GlobalApplication.prefs.setString("uid", it) }
        Intent(applicationContext, MainActivity::class.java).run { startActivity(this) }
    }
}