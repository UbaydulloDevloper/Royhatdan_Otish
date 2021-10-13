package Adapters

import Models.Users
import Project.royhatdanotish.databinding.ItemRecycleViewBinding
import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MyAdapterRecycle(var context: Context, var list: ArrayList<Users>, var click: CLick) :
    RecyclerView.Adapter<MyAdapterRecycle.Vh>() {
    inner class Vh(var itemRv: ItemRecycleViewBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(users: Users, position: Int) {
            itemRv.itemUsersName.text = users.name
            itemRv.itemUsersNumber.text = users.number
            if (users.imagePath != null) {
                val bitmap =
                    BitmapFactory.decodeByteArray(users.imagePath, 0, users.imagePath?.size!!)
                itemRv.itemImage.setImageBitmap(bitmap)
            }

            itemRv.cardView.setOnClickListener {
                click.itemClick(users, position)
            }
            itemRv.cardView.setOnLongClickListener {
                //  click.itemDelete(users, position)
                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show()
                notifyItemChanged(position, list.size)
                return@setOnLongClickListener true
            }


        }

    }

    interface CLick {
        fun itemClick(users: Users, position: Int)
        fun itemDelete(users: Users, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemRecycleViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}

