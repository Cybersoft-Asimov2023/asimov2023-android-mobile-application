package com.example.android.asimov2023.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.asimov2023.R
import com.example.android.asimov2023.retrofit.Model.CourseItem
import com.example.android.asimov2023.retrofit.RetrofitClient
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class CourseItemsAdapter(private val courseItemsList:MutableList<CourseItem>,private val context:Context): RecyclerView.Adapter<CourseItemsAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textCourseItem:TextView=itemView.findViewById(R.id.lblItemName)
        val btnCourseItem:Button=itemView.findViewById(R.id.btnItemState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_course_items, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courseItemsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val courseItem=courseItemsList[position]
        holder.textCourseItem.text=courseItem.name
        if(!courseItem.state){
            holder.btnCourseItem.visibility=View.VISIBLE
        }
        else{
            holder.btnCourseItem.visibility=View.INVISIBLE
        }
        holder.btnCourseItem.setOnClickListener {
            courseItem.state=true
            val it_name=courseItem.name
            val it_description=courseItem.description
            val it_state=courseItem.state
            val jsonObject=JSONObject()
            jsonObject.put("name",it_name)
            jsonObject.put("description",it_description)
            jsonObject.put("state",it_state)
            val requestBody=RequestBody.create(
                "application/json".toMediaTypeOrNull(),
                jsonObject.toString()
            )

            val getShared=context.getSharedPreferences("userPrefs",Context.MODE_PRIVATE)
            val toke=getShared.getString("token",null)
            val courseInterface=RetrofitClient.getCoursesInterface()
            val retrofitData=courseInterface.finalizeItem(requestBody,"Bearer $toke",courseItem.id)
            retrofitData.enqueue(object:retrofit2.Callback<CourseItem?>{
                override fun onResponse(call: Call<CourseItem?>, response: Response<CourseItem?>) {
                    if(response.isSuccessful){
                        Log.d("Actualizar item","Item actualizado correctamente")
                        holder.btnCourseItem.visibility=View.INVISIBLE
                    }
                }

                override fun onFailure(call: Call<CourseItem?>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
        Log.d("Iteam","Id: "+courseItem.id+" Name"+courseItem.name)
    }


}