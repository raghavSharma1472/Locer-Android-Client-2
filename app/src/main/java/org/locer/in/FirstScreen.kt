package org.locer.`in`

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_firstscreen.*
import org.locer.`in`.databinding.ActivityFirstscreenBinding

private const val TAG = "FirstScreen"

class FirstScreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firstBoundLayout: ActivityFirstscreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firstscreen)

        firstBoundLayout = ActivityFirstscreenBinding.inflate(layoutInflater)
        val bitmap = Bitmap.createBitmap(500, 1000, Bitmap.Config.ARGB_4444)
        val canvas1 = Canvas(bitmap)
        val canvas2 = Canvas(bitmap)

        val paint1 = Paint()
        paint1.setColor(Color.parseColor("#FF2800"))
        paint1.setStrokeWidth(30F)
        paint1.setStyle(Paint.Style.FILL)
        paint1.setAntiAlias(true)
        paint1.setDither(true)

        // get device dimensions
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        // circle center
        System.out.println("Width : " + displayMetrics.widthPixels)
        val center_x = (displayMetrics.widthPixels / 22).toFloat()
        val center_y = (displayMetrics.heightPixels / 22).toFloat()
        val radius = 300F

        // draw circles
        canvas1.drawCircle(center_x, center_y, radius, paint1)
        canvas2.drawCircle(center_x * 11, center_y * 11, radius, paint1)
        // now bitmap holds the updated pixels

        // set bitmap as background to ImageView
        imageV.background = BitmapDrawable(getResources(), bitmap)

        val emailTxt = findViewById<View>(R.id.emailId)
        var email = emailTxt.toString()
        val passwordTxt = findViewById<View>(R.id.password)
        var password = passwordTxt.toString()
        auth = Firebase.auth
        val button = findViewById<View>(R.id.login_button)

    }

    private fun createAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Log.d(TAG, "onCreate: Signed in successfully")
                val user = auth.currentUser
//                updateUI()
            } else {
                Log.e(TAG, "onCreate: Sigining in failed!!")
                Toast.makeText(this, "Logging in failed", Toast.LENGTH_LONG).show()
            }

        }
    }


}
