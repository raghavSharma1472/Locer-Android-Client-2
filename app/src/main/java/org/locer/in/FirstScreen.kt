package org.locer.`in`

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_firstscreen.*
import org.locer.`in`.databinding.ActivityFirstscreenBinding

private const val TAG = "FirstScreen"

class FirstScreen : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var firstDataBindingLayout: ActivityFirstscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firstDataBindingLayout = ActivityFirstscreenBinding.inflate(layoutInflater)
        setContentView(firstDataBindingLayout.root)

        val bitmap = Bitmap.createBitmap(500, 1000, Bitmap.Config.ARGB_4444)
        val canvas1 = Canvas(bitmap)
        val canvas2 = Canvas(bitmap)

        val paint1 = Paint()
        paint1.apply {
            color = Color.parseColor("#FF2800")
            strokeWidth = 30F
            style = Paint.Style.FILL
            isAntiAlias = true
            isDither = true
        }
        // get device dimensions
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        // circle center
        Log.d(TAG, "Getting screen width of device, ScreenWidth : ${displayMetrics.widthPixels}")
        val center_x = (displayMetrics.widthPixels / 22).toFloat()
        val center_y = (displayMetrics.heightPixels / 22).toFloat()
        val radius = 300F

        // draw circles
        canvas1.drawCircle(center_x, center_y, radius, paint1)
        canvas2.drawCircle(center_x * 11, center_y * 11, radius, paint1)
        // now bitmap holds the updated pixels

        // set bitmap as background to ImageView
        imageV.background = BitmapDrawable(getResources(), bitmap)

        val emailAddress = firstDataBindingLayout.emailId.text.toString()
        val password = firstDataBindingLayout.password.text.toString()
        mAuth = FirebaseAuth.getInstance()
        firstDataBindingLayout.loginButton.setOnClickListener {
            Toast.makeText(this, "Not implemented yet :(", Toast.LENGTH_LONG).show()
        }
    }
}
