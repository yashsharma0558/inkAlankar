package com.example.inkAlankar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.inkAlankar.databinding.FragmentHomeBinding
import com.example.inkAlankar.databinding.FragmentSignupBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.example.inkAlankar.DataSource


class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private lateinit var reference: DatabaseReference
    private lateinit var map: Map<String, Any>
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        reference = Firebase.database.reference.child("acc")
        binding.button.setOnClickListener {
            val checkEmptyFields: Boolean = checkTextFields()
            if (checkEmptyFields) {
                map = mapOf(
                    "name" to binding.textView.text.toString(),
                    "phoneNo" to binding.textView2.text.toString(),
                    "rollNo" to binding.textView3.text.toString(),
                    "branch" to binding.textView4.text.toString(),
                    "year" to binding.textView5.text.toString(),
                    "password" to binding.textView6.text.toString(),
                    "email" to binding.textView7.text.toString().replace('.', ','),
                    "contributions" to 0
                )
                val isInserted = DataSource(reference).insertDataIntoDatabase(map)
                if(isInserted){
                    Toast.makeText(context, "SignedUp Successfully!!", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_signupFragment_to_loginFragment)
                }
                else{
                    Toast.makeText(context, "Account already Exists!!", Toast.LENGTH_SHORT).show()
                }



            }


        }

    }

    private fun checkTextFields(): Boolean {
        if (binding.textView.text!!.isEmpty()) {
            binding.textView.error = "Please enter your name"
            return false
        }
        if (binding.textView2.text!!.isEmpty()) {
            binding.textView2.error = "Please enter your phone number"
            return false
        }
        if (binding.textView3.text!!.isEmpty()) {
            binding.textView3.error = "Please enter your roll number"
            return false
        }
        if (binding.textView4.text!!.isEmpty()) {
            binding.textView4.error = "Please enter your branch"
            return false
        }
        if (binding.textView5.text!!.isEmpty()) {
            binding.textView5.error = "Please enter your year"
            return false
        }
        if (binding.textView6.text!!.isEmpty()) {
            binding.textView6.error = "Please enter your password"
            return false
        }
        if (binding.textView7.text!!.isEmpty()) {
            binding.textView7.error = "Please enter your email"
            return false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}