package Project.royhatdanotish

import Adapters.MyAdapterRecycle
import DB.MyDBHelper
import Models.Users
import Project.royhatdanotish.databinding.FragmentShowAllBinding
import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_s_ign_in.*
import kotlinx.android.synthetic.main.item_bottom_sheets_dialog.*
import kotlinx.android.synthetic.main.item_recycle_view.*

class ShowAll : Fragment() {
    lateinit var binding: FragmentShowAllBinding
    lateinit var myDBHelper: MyDBHelper
    lateinit var list: ArrayList<Users>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowAllBinding.inflate(layoutInflater)
        myDBHelper = MyDBHelper(binding.root.context)
        list = ArrayList()
        list.addAll(myDBHelper.getAllImage())

        binding.recycleView.adapter =
            MyAdapterRecycle(binding.root.context, list, object : MyAdapterRecycle.CLick {
                override fun itemClick(users: Users, position: Int) {
                    val bottomSheetDialog = BottomSheetDialog(binding.root.context,R.style.MyBottomSheet)
                    bottomSheetDialog.setContentView(
                        layoutInflater.inflate(
                            R.layout.item_bottom_sheets_dialog,
                            null,
                            false
                        )
                    )
                    if (users.imagePath != null) {
                        val bitmap = BitmapFactory.decodeByteArray(
                            users.imagePath,
                            0,
                            users.imagePath?.size!!)
                        bottomSheetDialog.dialog_image.setImageBitmap(bitmap)
                        bottomSheetDialog.dialog_users_name.text = users.name
                        bottomSheetDialog.users_location.text = "${users.country}, ${users.city}"
                    }

                    bottomSheetDialog.call_phone.setOnClickListener {
                        askPermission(Manifest.permission.CALL_PHONE) {
                            //all permissions already granted or just granted
                            val intent = Intent(Intent.ACTION_CALL)
                            intent.data = Uri.parse("tel:${users.number}")
                            startActivity(intent)
                        }.onDeclined { e ->
                            if (e.hasDenied()) {

                                AlertDialog.Builder(context)
                                    .setMessage("Please accept our permissions")
                                    .setPositiveButton("yes") { dialog, which ->
                                        e.askAgain();
                                    } //ask again
                                    .setNegativeButton("no") { dialog, which ->
                                        dialog.dismiss();
                                    }
                                    .show();
                            }

                            if (e.hasForeverDenied()) {
                                //the list of forever denied permissions, user has check 'never ask again'

                                // you need to open setting manually if you really need it
                                e.goToSettings();
                            }
                        }
                    }
                    bottomSheetDialog.send_sms.setOnClickListener {
                        val it = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${users.number}"))
                        it.putExtra("sms_body", "")
                        startActivity(it)
                    }
                    bottomSheetDialog.send_sms_.setOnClickListener {
                        val it = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${users.number}"))
                        it.putExtra("sms_body", "")
                        startActivity(it)
                    }
                    bottomSheetDialog.show()
                }

                override fun itemDelete(users: Users, position: Int) {
                    myDBHelper.deleteUser(users)

                }
            })
        return binding.root
    }


}