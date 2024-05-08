import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.turtlearmorstudios.PasswordInfo
import com.example.turtlearmorstudios.databinding.ItemPasswordBinding

class PasswordsAdapter(
    private val passwords: MutableList<PasswordInfo>,
    private val context: Context,
    private val onEdit: (Int) -> Unit,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<PasswordsAdapter.PasswordViewHolder>() {

    private var filteredPasswords: List<PasswordInfo> = passwords
    private var currentFilter: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        val binding = ItemPasswordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PasswordViewHolder(binding, onEdit, onDelete)
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        val passwordInfo = filteredPasswords[position]
        holder.bind(passwordInfo)
    }

    override fun getItemCount() = filteredPasswords.size

    fun addPassword(passwordInfo: PasswordInfo) {
        passwords.add(passwordInfo)
        filter(currentFilter)
    }

    fun removePassword(position: Int) {
        if (position < filteredPasswords.size) {
            val originalIndex = passwords.indexOf(filteredPasswords[position])
            if (originalIndex != -1) {
                passwords.removeAt(originalIndex)
            }
            filter(currentFilter)
        }
    }

    fun updatePassword(position: Int, newPasswordInfo: PasswordInfo) {
        if (position >= 0 && position < filteredPasswords.size) {
            val originalIndex = passwords.indexOf(filteredPasswords[position])
            if (originalIndex != -1) {
                passwords[originalIndex] = newPasswordInfo
            }
            filter(currentFilter)
        }
    }

    fun filter(query: String) {
        currentFilter = query
        filteredPasswords = if (query.isEmpty()) {
            passwords.toList()
        } else {
            passwords.filter { it.serviceName.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }

    class PasswordViewHolder(
        private val binding: ItemPasswordBinding,
        private val onEdit: (Int) -> Unit,
        private val onDelete: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(passwordInfo: PasswordInfo) {
            binding.textViewServiceName.text = passwordInfo.serviceName
            binding.textViewUsername.text = passwordInfo.username
            binding.textViewPassword.text = passwordInfo.password

            binding.buttonEdit.setOnClickListener { onEdit(adapterPosition) }
            binding.buttonDelete.setOnClickListener { onDelete(adapterPosition) }
            binding.buttonCopy.setOnClickListener { copyToClipboard(passwordInfo.password) }
        }

        private fun copyToClipboard(password: String) {
            val clipboard = itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Password", password)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(itemView.context, "Password copied to clipboard", Toast.LENGTH_SHORT).show()
        }
    }
}
