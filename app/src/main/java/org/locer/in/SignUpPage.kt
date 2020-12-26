package org.locer.`in`

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_firstscreen.*
import org.locer.`in`.databinding.FragmentSignUpBinding

private const val TAG = "SignUpPage"

class SignUpPage : Fragment() {
    private lateinit var signUpPageDataBindingLayout: FragmentSignUpBinding
    private val sharedPreferenceUtil: SharedPreferenceUtil by
    lazy { SharedPreferenceUtil(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstancestate: Bundle?
    ): View {
        signUpPageDataBindingLayout =
            FragmentSignUpBinding.inflate(layoutInflater, container, false)
        return signUpPageDataBindingLayout.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bitmap = Bitmap.createBitmap(500, 1000, Bitmap.Config.ARGB_4444)
        val canvas1 = Canvas(bitmap)
        val canvas2 = Canvas(bitmap)

        val paint1 = Paint()
        paint1.setColor(Color.parseColor("#FEE65E"))
        paint1.setStrokeWidth(30F)
        paint1.setStyle(Paint.Style.FILL)
        paint1.setAntiAlias(true)
        paint1.setDither(true)

        // get device dimensions
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)

        // circle center
        Log.d(TAG, "onViewCreated: checking screen width- Width: ${displayMetrics.widthPixels}")

        val center_x = (displayMetrics.widthPixels / 4.2).toFloat()
        val center_y = (displayMetrics.heightPixels / 75).toFloat()
        val radius = 300F

        // draw circles
        canvas1.drawCircle(center_x, center_y, radius, paint1)
        canvas2.drawCircle(center_x / 11, center_y * 38, radius, paint1)
        // now bitmap holds the updated pixels

        // set bitmap as background to ImageView
        imageV.background = BitmapDrawable(getResources(), bitmap)

        val emailParam = signUpPageDataBindingLayout.emailId
        val passwordParam = signUpPageDataBindingLayout.password
        signUpPageDataBindingLayout.signupButton.setOnClickListener {
            val email = emailParam.text.toString().trim()
            val password = passwordParam.text.toString().trim()
            signUp(email, password)
        }
    }

    private fun signUp(email: String, password: String) {
        val mAuth = FirebaseAuth.getInstance()
        Log.d(TAG, "onCreate: checking email: $email & password: $password")
        activity?.let {
            mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        val userId = mAuth.currentUser?.uid
                        Log.d(
                            TAG,
                            "onCreate: Retrieved user credentials: mail address: $email & Password: $password "
                        )
                        Log.d(TAG, "onCreate: USER-ID: $userId")
                        Toast.makeText(activity, "Login Successful!!", Toast.LENGTH_LONG).show()
                        sharedPreferenceUtil.setLoggedIn()
                    } else {
                        Toast.makeText(activity, "Error logging you in...", Toast.LENGTH_LONG)
                            .show()
                        Log.d(TAG, "signUp: Task Failed!!!, ${task.result}")
                    }
                }
        }
    }
}
