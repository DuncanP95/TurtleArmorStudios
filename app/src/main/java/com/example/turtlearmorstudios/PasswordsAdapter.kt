package com.example.turtlearmorstudios

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.turtlearmorstudios.databinding.ItemPasswordBinding

class PasswordsAdapter(
    private var passwords: MutableList<PasswordInfo>,
    private val onEdit: (PasswordInfo, Int) -> Unit,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<PasswordsAdapter.PasswordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        val binding = ItemPasswordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PasswordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        val passwordInfo = passwords[position]
        holder.bind(passwordInfo, onEdit, onDelete)
    }

    override fun getItemCount() = passwords.size

    fun addPassword(passwordInfo: PasswordInfo) {
        passwords.add(passwordInfo)
        notifyItemInserted(passwords.size - 1)
    }

    fun removePassword(position: Int) {
        if (position < passwords.size) {
            passwords.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun updatePassword(position: Int, newPasswordInfo: PasswordInfo) {
        if (position >= 0 && position < passwords.size) {
            passwords[position] = newPasswordInfo
            notifyItemChanged(position)
        }
    }

    class PasswordViewHolder(private val binding: ItemPasswordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(passwordInfo: PasswordInfo, onEdit: (PasswordInfo, Int) -> Unit, onDelete: (Int) -> Unit) {
            binding.textViewServiceName.text = passwordInfo.serviceName
            binding.textViewUsername.text = passwordInfo.username
            binding.textViewPassword.text = passwordInfo.password

            binding.buttonEdit.setOnClickListener { onEdit(passwordInfo, adapterPosition) }
            binding.buttonDelete.setOnClickListener { onDelete(adapterPosition) }
        }
    }
}