package com.example.android.asimov2023.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.asimov2023.databinding.FragmentDirectorProfileBinding
import com.example.android.asimov2023.retrofit.Model.DirectorItem
import com.example.android.asimov2023.retrofit.RetrofitClient
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DirectorProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class DirectorProfile : Fragment() {

    private lateinit var activity: Activity
    private lateinit var binding: FragmentDirectorProfileBinding
    private var editingProfile: Boolean = false

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        activity = requireActivity()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentDirectorProfileBinding.inflate(inflater, container, false)

        val getShared = activity.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val userId = getShared.getInt("id",0)
        val directorToken = getShared.getString("token", null).toString()
        Log.d("TOKENSAVED", directorToken)
        Log.d("IDUSERSAVED", userId.toString())
        loadUserData(binding,userId,directorToken)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addImageProfile.setOnClickListener {
            pickImageFromGallery()
        }
        val getShared = activity.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val userId = getShared.getInt("id",0)
        val directorToken = getShared.getString("token", null).toString()
        val buttonEditProfile = binding.buttonProfile
        binding.buttonProfile.setOnClickListener {
            editingProfile = true
            if(editingProfile && buttonEditProfile.text == "Save" ){
                Log.d("Pusheando nuevos datos", userId.toString() + "tok " + directorToken)
                loadNewUserData(userId,directorToken)
                binding.etFirstName.isEnabled = false
                binding.etLastName.isEnabled = false
                binding.etAge.isEnabled = false
                binding.etPassword.isEnabled = false
                binding.etPhone.isEnabled = false
                editingProfile = false
                buttonEditProfile.text = "Edit"
            }
            else if(editingProfile && buttonEditProfile.text != "Save" ){
                Log.d("Modo edicion", "EDITANDO")
                binding.etFirstName.isEnabled = true
                binding.etLastName.isEnabled = true
                binding.etAge.isEnabled = true
                binding.etPassword.isEnabled = true
                binding.etPhone.isEnabled = true
                buttonEditProfile.text = "Save"

            }

            else{
                binding.etFirstName.isEnabled = false
                binding.etLastName.isEnabled = false
                binding.etAge.isEnabled = false
                binding.etPassword.isEnabled = false
                binding.etPhone.isEnabled = false
            }
        }
    }
    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }
    private fun getUserData(directorId: Int, directorToken:String,callback: (DirectorItem)->Unit){
        val directorService = RetrofitClient.getDirectorsInterface().getDirectorById("Bearer $directorToken",directorId)



        directorService.enqueue(object: Callback<DirectorItem?> {
            override fun onResponse(call: Call<DirectorItem?>, response: Response<DirectorItem?>) {
                val director=response.body()
                Log.d("RESPONSEBODY",response.body().toString())
                if(director!=null){
                    callback(director)
                }

            }

            override fun onFailure(call: Call<DirectorItem?>, t: Throwable) {
                Log.d("Director", "failure" + t.message)

            }

        })

    }

    private fun loadUserData(binding: FragmentDirectorProfileBinding, id: Int, token:String){
        getUserData(id,token) { director ->
            //text views
            binding.TextNameUser.text = director.first_name + " " + director.last_name
            binding.TextMailUser.text = director.email


            //edit texts
            binding.etFirstName.text = Editable.Factory.getInstance().newEditable(director.first_name)
            binding.etLastName.text = Editable.Factory.getInstance().newEditable(director.last_name)
            binding.etAge.text = Editable.Factory.getInstance().newEditable(director.age.toString())
            binding.etPhone.text = Editable.Factory.getInstance().newEditable(director.phone)

        }
    }
    companion object {

        private const val REQUEST_IMAGE_PICK = 1
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DirectorProfile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DirectorProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            binding.imageView.setImageURI(selectedImageUri)
        }
    }

    private fun updateUserData(directorId: Int, directorToken: String, data: RequestBody) {
        val directorService = RetrofitClient.getDirectorsInterface().updateDirectorData("Bearer $directorToken",directorId,data)

        directorService.enqueue(object: Callback<DirectorItem?> {
            override fun onResponse(call: Call<DirectorItem?>, response: Response<DirectorItem?>) {
                val director=response.body()
                Log.d("RESPONSEBODYUPDATE",response.body().toString())
                if(director!=null){
                    Log.d("Successfully Updated data","Data Updated")
                }

            }

            override fun onFailure(call: Call<DirectorItem?>, t: Throwable) {
                Log.d("Director UPDATE", "failure" + t.message)

            }

        })
    }
    private fun loadNewUserData(directorId: Int, directorToken: String){
        val requestBody = RequestBody.create(MediaType.parse("application/json"), loadJsonData().toString())
        Log.d("RequestBody Sending...", requestBody.toString())
        updateUserData(directorId,directorToken,requestBody)
    }
    private fun loadJsonData(): JSONObject {
        val json = JSONObject()
        json.put("first_name", binding.etFirstName.text.toString())
        json.put("last_name", binding.etLastName.text.toString())
        json.put("password", binding.etPassword.text.toString())
        json.put("age", binding.etAge.text.toString().toInt())
        json.put("email",binding.TextMailUser.text.toString())
        json.put("phone", binding.etPhone.text.toString())


        return json
    }
}