package Project.royhatdanotish

import Models.fragmentpositions
import Project.royhatdanotish.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        findNavController(R.id.my_host).navigate(R.id.SIgnIn)

    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.my_host).navigateUp()
    }

    override fun onBackPressed() {
        if (fragmentpositions.position == 2) {
            findNavController(R.id.my_host).navigate(R.id.SIgnIn)
        }else{
            finish()
        }
    }
}