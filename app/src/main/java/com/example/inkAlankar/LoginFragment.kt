package com.example.inkAlankar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.inkAlankar.databinding.FragmentLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private lateinit var reference: DatabaseReference
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        reference = Firebase.database.reference.child("acc")

        binding.signup.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate((R.id.action_loginFragment_to_signupFragment))
        }
        binding.button.setOnClickListener {
            val checkEmptyFields: Boolean = checkTextFields()
            if (checkEmptyFields) {
                DataSource(reference).doesDataExist(
                    reference,
                    binding.email.text?.trim().toString().replace('.', ','),
                    binding.password.text?.trim().toString()
                ) { dataExists ->
                    if (dataExists) {
                        // Data exists at the specified path
                        val bundle = Bundle()
                        val path = binding.email.text?.trim().toString().replace('.', ',')
                        bundle.putString("path", path)

                        Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_profileFragment, bundle)

                    } else {
                        // Data does not exist at the specified path
                        Toast.makeText(context, "Check your credentials!", Toast.LENGTH_SHORT ).show()
                    }
                }


            }

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkTextFields(): Boolean {
        if (binding.email.text!!.isEmpty()) {
            binding.email.error = "Please enter your name"
            return false
        }
        if (binding.password.text!!.isEmpty()) {
            binding.password.error = "Please enter your phone number"
            return false
        }

        return true
    }

}