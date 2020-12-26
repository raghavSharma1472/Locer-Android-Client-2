package org.locer.`in`

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.constraintlayout.solver.widgets.ConstraintWidget.GONE
import androidx.constraintlayout.widget.ConstraintSet.GONE
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.transition.Visibility
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.locer.`in`.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {


    private val sharedPreferenceUtil by lazy { SharedPreferenceUtil(context = this) }
    private lateinit var boundLayout: ActivityMainBinding
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var navController: NavController
    private var actionMode: ActionMode? = null
    private val finishActionModeOnDestinationChanged =
        NavController.OnDestinationChangedListener { controller, destination, bundle -> actionMode?.finish() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: log check!")
//        val intent = Intent(this, SignUpPage::class.java)
//        startActivity(intent)
//        finish()

        boundLayout = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (!sharedPreferenceUtil.showIntro()) {
        bottomNavView = boundLayout.mainBottomNavView
        navController = Navigation.findNavController(this, R.id.main_nav_host_fragment)
        navController.addOnDestinationChangedListener(finishActionModeOnDestinationChanged)
        appBarConfig = AppBarConfiguration(navController.graph)
        boundLayout.mainBottomNavView.setupWithNavController(navController)
        } else {
            handleIntro()
        }
        if (!sharedPreferenceUtil.isLoggedIn) {
            navigateToSignUp()
        }
    }

    private fun handleIntro() {
        val homeIntent = Intent(this, IntroActivity::class.java)
        startActivity(homeIntent)
        sharedPreferenceUtil.setIntroShown()
        finish()
    }

    private fun setUpNavMenus(navController: NavController) {
        boundLayout.mainBottomNavView.setupWithNavController(navController = navController)
    }

    private fun navigateToSignUp() {
        boundLayout.mainBottomNavView.visibility = View.GONE
        findNavController(R.id.main_nav_host_fragment).navigate(R.id.action_navigation_home_to_signUpPage)
    }

    // don't remove the following code, it may be helpful in future that's why it is kept here
    val intentForNextScreen: Intent
        get() = if (sharedPreferenceUtil.showIntro())
            Intent(this, IntroActivity::class.java)
        else
            Intent(this, MainActivity::class.java)

}
