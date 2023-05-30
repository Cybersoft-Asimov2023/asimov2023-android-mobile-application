import TeacherAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.asimov2023.R
import com.example.android.asimov2023.retrofit.Model.TeacherItem
import com.example.android.asimov2023.retrofit.RetrofitClient
import com.example.android.asimov2023.ui.auth.SignUpTeacherFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeacherListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TeacherAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_teacher_list, container, false)
        setupViews(view)
        return view
    }

    private fun setupViews(view: View) {

        val btAddTeacher = view.findViewById<Button>(R.id.btnAddTeacher)
        btAddTeacher.setOnClickListener() {
            Log.d("BOTON","DONE")
            val fragment = SignUpTeacherFragment() // Replace with the fragment you want to switch to
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        recyclerView = view.findViewById(R.id.recycler_view_teachers)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadTeachers { teachersList ->
            adapter = TeacherAdapter(teachersList)
            recyclerView.adapter = adapter
        }
    }

    private fun loadTeachers(callback: (List<TeacherItem>) -> Unit) {
        val getShared = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val id = getShared.getInt("id", 0)
        val directorToken = getShared.getString("token", null)
        val teachersInterface = RetrofitClient.getTeachersInterface()
        val retrofitData = teachersInterface.getTeachers("Bearer $directorToken", id)
        Log.d("ID", id.toString())
        retrofitData.enqueue(object : Callback<List<TeacherItem>?> {
            override fun onResponse(
                call: Call<List<TeacherItem>?>,
                response: Response<List<TeacherItem>?>
            ) {
                val teachersList = response.body()
                if (teachersList != null) {
                    Log.d("TeachersList", "SUCCESS")
                    callback(teachersList)
                } else {
                    callback(emptyList())
                }
            }

            override fun onFailure(call: Call<List<TeacherItem>?>, t: Throwable) {
                Log.d("TeachersList", "failure" + t.message)
                callback(emptyList())
            }
        })
    }
}
