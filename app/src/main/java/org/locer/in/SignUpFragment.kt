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
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import org.locer.`in`.SignUpFragmentDirections.Companion.actionSignUpPageToHomeFragment
import org.locer.`in`.SignUpFragmentDirections.Companion.actionSignupFragmentToLoginFragment
import org.locer.`in`.databinding.FragmentSignUpBinding

private const val TAG = "SignUpFragment"

class SignUpFragment : Fragment() {
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

        val paint = Paint()
        paint.apply {
            color = Color.parseColor("#FEE65E")
            strokeWidth = 30F
            style = Paint.Style.FILL
            isAntiAlias = true
            isDither = true
        }

        // get device dimensions
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)

        // circle center
        Log.d(TAG, "onViewCreated: checking screen width- Width: ${displayMetrics.widthPixels}")

        val centerX = (displayMetrics.widthPixels / 4.2).toFloat()
        val centerY = (displayMetrics.heightPixels / 75).toFloat()
        val radius = 300F

        // draw circles
        canvas1.drawCircle(centerX, centerY, radius, paint)
        canvas2.drawCircle(centerX / 11, centerY * 38, radius, paint)
        // now bitmap holds the updated pixels

        // set bitmap as background to ImageView
        signUpPageDataBindingLayout.imageV.background = BitmapDrawable(resources, bitmap)

        val emailParam = signUpPageDataBindingLayout.emailId
        val passwordParam = signUpPageDataBindingLayout.password
        signUpPageDataBindingLayout.signupButton.setOnClickListener {
            val email = emailParam.text.toString().trim()
            val password = passwordParam.text.toString().trim()
            signUp(email, password)
        }

        signUpPageDataBindingLayout.loginButton.setOnClickListener { navigateToLogin() }

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
                        Toast.makeText(requireContext(), "Login Successful!!", Toast.LENGTH_LONG)
                            .show()
                        sharedPreferenceUtil.setLoggedIn()
                        navigateToHome()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error logging you in...",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        Log.d(TAG, "signUp: Task Failed!!!, ${task.result}")
                    }
                }
        }
    }

    private fun navigateToHome() = findNavController().navigate(actionSignUpPageToHomeFragment())
    private fun navigateToLogin() =
        findNavController().navigate(actionSignupFragmentToLoginFragment())
}
