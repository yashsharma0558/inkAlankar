package com.example.inkAlankar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.inkAlankar.databinding.FragmentHomeBinding
import com.example.inkAlankar.databinding.FragmentProfileBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
        DataSource(reference).getTop3ChildrenWithMaxNumber{
            initializeTop3Values(it)
        }
        DataSource(reference).getLoginDataFromDatabase(path){
            initializeProfileValues(it)
        }

        binding.button2.setOnClickListener{
//            createAlertDialog()
            val bundle = Bundle()
            bundle.putString("path", path)
            Navigation.findNavController(binding.root).navigate(R.id.action_profileFragment_to_gameFragment, bundle)

        }


    }

    private fun initializeTop3Values(map: Map<String, Any>) {

        binding.emailRow1.text = map.keys.elementAt(0).replace(',', '.')
        binding.emailRow2.text = map.keys.elementAt(1).replace(',', '.')
        binding.emailRow3.text = map.keys.elementAt(2).replace(',', '.')
        binding.contributionRow1.text = map.values.elementAt(0).toString()
        binding.contributionRow2.text = map.values.elementAt(1).toString()
        binding.contributionRow3.text = map.values.elementAt(2).toString()
    }

    private fun createAlertDialog() {
        val item = arrayOf("i agree")
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Alert!")
            .setMessage("chup kr saale").setItems(item){ _, _->


            }
            .setPositiveButton("yes"){ dialog, _ ->

            }.setNegativeButton("no"){  _, _ ->

            }
    }

    private fun initializeProfileValues(map: Map<String, Any>) {
        binding.textView8.text = map["name"].toString()
        binding.textView9.text = map["email"].toString().replace(',', '.')
        binding.textView10.text = getString(R.string.contributions, map["contributions"].toString())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}