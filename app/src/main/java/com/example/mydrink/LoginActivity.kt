package com.example.mydrink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_register.setOnClickListener{
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        }
        btn_login.setOnClickListener{
            when{
                TextUtils.isEmpty(my_register_email.text.toString().trim {it <=  ' ' }) -> {
                    Toast.makeText(this@LoginActivity,
                        "Please enter email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(my_register_password.text.toString().trim{ it <= ' ' }) ->{
                    Toast.makeText(this@LoginActivity,
                        "Please enter password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else ->{
                    val email: String = my_register_email.text.toString().trim{it <= ' '}
                    val password: String = my_register_email.text.toString().trim{it <= ' '}

                    //Login using FirebaseAuth.
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult>{ task ->
                                // If the registration is successful
                                if(task.isSuccessful){
                                    //Firebase registered user
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(this@LoginActivity,
                                        "Success",
                                        Toast.LENGTH_SHORT
                                    ).show()


                                    val intent = Intent(this@LoginActivity,MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id",FirebaseAuth.getInstance().currentUser!!.uid
                                    )
                                    intent.putExtra("email_id",email)
                                    startActivity(intent)
                                    finish()
                                }else{
                                    //If registration is unsuccessful
                                    Toast.makeText(this@LoginActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            })

                }
            }




        }


    }
}