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
import org.locer.`in`.databinding.FragmentLoginBinding

private const val TAG = "LoginFragment"

class LoginFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var loginFragmentBinding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        loginFragmentBinding = FragmentLoginBinding.inflate(layoutInflater)
        return loginFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bitmap = Bitmap.createBitmap(500, 1000, Bitmap.Config.ARGB_4444)
        val canvas1 = Canvas(bitmap)
        val canvas2 = Canvas(bitmap)

        val paint = Paint()
        paint.apply {
            color = Color.parseColor("#FF2800")
            strokeWidth = 30F
            style = Paint.Style.FILL
            isAntiAlias = true
            isDither = true
        }
        // get device dimensions
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        // circle center
        Log.d(TAG, "Getting screen width of device, ScreenWidth : ${displayMetrics.widthPixels}")
        val center_x = (displayMetrics.widthPixels / 22).toFloat()
        val center_y = (displayMetrics.heightPixels / 22).toFloat()
        val radius = 300F

        // draw circles
        canvas1.drawCircle(center_x, center_y, radius, paint)
        canvas2.drawCircle(center_x * 11, center_y * 11, radius, paint)
        // now bitmap holds the updated pixels

        // set bitmap as background to ImageView
        loginFragmentBinding.imageV.background = BitmapDrawable(getResources(), bitmap)

        val emailAddress = loginFragmentBinding.emailId.text.toString()
        val password = loginFragmentBinding.password.text.toString()
        mAuth = FirebaseAuth.getInstance()

        // TODO: 27/12/20  setup login method

        loginFragmentBinding.loginButton.setOnClickListener {
            Toast.makeText(requireContext(), "Not implemented yet :(", Toast.LENGTH_LONG).show()
        }
    }
}
