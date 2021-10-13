package Project.royhatdanotish

import DB.MyDBHelper
import Models.Users
import Models.fragmentpositions
import Project.royhatdanotish.databinding.FragmentRegistrationBinding
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.github.florent37.runtimepermission.kotlin.coroutines.experimental.askPermission
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.ArrayList


class Registration : Fragment() {
    lateinit var myDBHelper: MyDBHelper
    lateinit var binding: FragmentRegistrationBinding
    var absolutePath: ByteArray? = null
    var list = arrayOf(
        "Albaniya",
        "Uzbekiston",
        "Qozog'iston",
        "Kambodja",
        "Qirg'iziston",
        "Russia",
        "Eron",
        "Tojikiston",
        "Pokiston",
        "Panama"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(layoutInflater)
        myDBHelper = MyDBHelper(binding.root.context)
        binding.editCountry.adapter =
            ArrayAdapter(binding.root.context, android.R.layout.select_dialog_item, list)

        binding.cameraClickImage.setOnClickListener {
            askPermission(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
            ) {
                val dialog = AlertDialog.Builder(binding.root.context)
                dialog.setMessage("Rasmni kameradan yoki gallereyadan yuklashingiz mumkin:")
                dialog.setPositiveButton("Camera") { _, _ ->
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, 0)
                }

                dialog.setNegativeButton("Gallery") { dialog, which ->
                    startActivityForResult(Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "image/*"
                    }, 1)
                }
                dialog.show()
            }.onDeclined {
                if (it.hasDenied()) {
                    android.app.AlertDialog.Builder(context)
                        .setMessage("Please accept our permissions")
                        .setPositiveButton("yes") { dialog, which ->
                            it.askAgain();
                        } //ask again
                        .setNegativeButton("no") { dialog, which ->
                            dialog.dismiss();
                        }
                        .show();
                }
                if (it.hasForeverDenied()) {
                    //the list of forever denied permissions, user has check 'never ask again'

                    // you need to open setting manually if you really need it
                    it.goToSettings();
                }
            }
        }


        binding.btnClick.setOnClickListener {
            val name = binding.editName.text.toString().trim()
            val number = binding.editNumber.text.toString().trim()
            val city = binding.editCity.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()
            val country = binding.editCountry.selectedItem.toString()

            if (name != "" && number != "" && city != "" && password != "" && country != "" && absolutePath != null) {
                myDBHelper.addUser(
                    Users(
                        name,
                        absolutePath,
                        number,
                        country,
                        city,
                        password
                    )
                )
                Toast.makeText(binding.root.context, "Save", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.showAll)
            } else {
                Toast.makeText(binding.root.context, "Data is insufficient", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK) {
            val uri = data?.data ?: return
            binding.cameraClickImage.setImageURI(uri)
            val format = SimpleDateFormat("yyyyMMdd_hhmmss").format(Date())
            val inputStream = activity?.contentResolver?.openInputStream(uri)
            val file = File(activity?.filesDir, "${format}_image.jpg")
            val fileOutputStream = FileOutputStream(file)
            inputStream?.copyTo(fileOutputStream)
            inputStream?.close()
            fileOutputStream.close()
            val fileInputStream = FileInputStream(file)
            val readBytes = fileInputStream.readBytes()
            absolutePath = readBytes
        } else if (requestCode == 0 && resultCode == AppCompatActivity.RESULT_OK) {
            val bitmap = data?.extras?.get("data") as Bitmap
            binding.cameraClickImage.setImageBitmap(bitmap)
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            absolutePath = byteArray
        }
    }
    override fun onStart() {
        super.onStart()
        fragmentpositions.position = 2
    }
}