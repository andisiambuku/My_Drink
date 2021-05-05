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
import kotlinx.android.synthetic.main.activity_register.view.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        md_login.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            onBackPressed()
        }

        btn_register.setOnClickListener{
            when{
                TextUtils.isEmpty(my_register_email.text.toString().trim {it <=  ' ' }) -> {
                    Toast.makeText(this@RegisterActivity,
                                    "Please enter email",
                                    Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(my_register_password.text.toString().trim{ it <= ' ' }) ->{
                    Toast.makeText(this@RegisterActivity,
                                   "Please enter password",
                                    Toast.LENGTH_SHORT
                    ).show()
                }
                else ->{
                    val email: String = my_register_email.text.toString().trim{it <= ' '}
                    val password: String = my_register_email.text.toString().trim{it <= ' '}

                    //Create an instance and create a user with email and password.
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult>{ task ->
                            // If the registration is successful
                            if(task.isSuccessful){
                                //Firebase registered user
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(this@RegisterActivity,
                                               "Success",
                                               Toast.LENGTH_SHORT
                                ).show()


                                val intent = Intent(this@RegisterActivity,MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id",firebaseUser.uid)
                                intent.putExtra("email_id",email)
                                startActivity(intent)
                                finish()
                            }else{
                            //If registration is unsuccessful
                                Toast.makeText(this@RegisterActivity,
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