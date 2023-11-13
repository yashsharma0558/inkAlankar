package com.example.wedetect

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.wedetect.databinding.FragmentHomeBinding
import com.example.wedetect.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var path: String
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.let {
            path = it.getString("path") as String
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        reference = Firebase.database.reference.child("acc")
        DataSource(reference).getLoginDataFromDatabase(path){
            initializeValues(it)
        }


        binding.button2.setOnClickListener(){
            Navigation.findNavController(binding.root).navigate(R.id.action_profileFragment_to_gameFragment)

        }

    }

    private fun initializeValues(map: Map<String, Any>) {
        println(map["name"].toString())
        println(map["email"].toString())
        println(map["phoneNo"].toString())
        binding.textView8.text = map["name"].toString()
        binding.textView9.text = map["email"].toString()
        binding.textView10.text = map["phoneNo"].toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}