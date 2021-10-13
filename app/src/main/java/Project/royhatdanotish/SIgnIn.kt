package Project.royhatdanotish

import DB.MyDBHelper
import Models.Users
import Models.fragmentpositions
import Project.royhatdanotish.databinding.FragmentSIgnInBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlin.math.log

class SIgnIn : Fragment() {
    lateinit var binding: FragmentSIgnInBinding
    lateinit var myDBHelper: MyDBHelper
    lateinit var list: ArrayList<Users>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSIgnInBinding.inflate(layoutInflater)
        myDBHelper = MyDBHelper(binding.root.context)
        list = ArrayList()
        list.addAll(myDBHelper.getAllImage())

        val number = binding.number.text.trim()
        val password = binding.password.text.trim()
        var tru = 0
        for (i in list.indices) {
            if (number == list[i].number) {
                if (password == list[i].password) {
                    tru = 1
                }
            }

        }
        binding.btnClick.setOnClickListener {
            if (tru == 1) {
                findNavController().navigate(R.id.showAll)
            } else {
                Toast.makeText(binding.root.context, "Error", Toast.LENGTH_SHORT).show()
            }

        }

        binding.registrationBtn.setOnClickListener {
            findNavController().navigate(R.id.registration)
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        fragmentpositions.position = 1
    }

}