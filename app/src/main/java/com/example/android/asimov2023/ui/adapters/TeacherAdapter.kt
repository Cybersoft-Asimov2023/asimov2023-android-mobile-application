import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.asimov2023.R
import com.example.android.asimov2023.retrofit.Model.TeacherItem

class TeacherAdapter(private val teachersList: List<TeacherItem>) : RecyclerView.Adapter<TeacherAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define las vistas del CardView
        val nameTextView: TextView = itemView.findViewById(R.id.cardTeacherTxtIdName)
        val emailTextView: TextView = itemView.findViewById(R.id.cardTeacherTxtIdEmail)
        val phoneTextView: TextView = itemView.findViewById(R.id.cardTeacherTxtIdPhone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Infla el archivo de diseño para crear una nueva instancia de MyViewHolder
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_teacher, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Actualiza las vistas en el MyViewHolder con los datos del elemento correspondiente de la lista
        val teacher = teachersList[position]
        holder.nameTextView.text = teacher.first_name +" "+ teacher.last_name
        holder.emailTextView.text = teacher.email
        holder.phoneTextView.text = teacher.phone

    }

    override fun getItemCount(): Int {
        return teachersList.size
    }
}
