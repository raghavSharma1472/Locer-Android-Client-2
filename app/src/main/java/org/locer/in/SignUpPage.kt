package org.locer.`in`

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_firstscreen.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth

class SignUpPage : AppCompatActivity() {
//    private var mDatabaseReference: DatabaseReference? = null
//    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    override fun onCreate (savedInstancestate: Bundle?){
        super.onCreate(savedInstancestate)
        setContentView(R.layout.activity_signuppage)
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
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        // circle center
        System.out.println("Width : "+displayMetrics.widthPixels)
        val center_x = (displayMetrics.widthPixels/4.2).toFloat()
        val center_y = (displayMetrics.heightPixels/75).toFloat()
        val radius = 300F

        // draw circles
        canvas1.drawCircle(center_x, center_y, radius, paint1)
        canvas2.drawCircle(center_x/11,center_y*38,radius,paint1)
        // now bitmap holds the updated pixels

        // set bitmap as background to ImageView
        imageV.background = BitmapDrawable(getResources(), bitmap)

        mAuth = FirebaseAuth.getInstance()
        val emailTxt: EditText = findViewById(R.id.emailId)
        val email = emailTxt.toString()
        val passwordTxt: EditText = findViewById(R.id.password)
        val password = passwordTxt.toString()
        val phoneTxt: EditText = findViewById(R.id.phone_number)
        val button: Button = findViewById(R.id.signup_button)
        button.setOnClickListener {
            mAuth!!
                .createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) { task->
                    if (task.isSuccessful){
                    val userId = mAuth!!.currentUser?.uid
                    }
                }
        }

    }
}