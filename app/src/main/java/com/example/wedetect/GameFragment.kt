package com.example.wedetect

import android.graphics.Paint
import android.graphics.Path
import android.health.connect.datatypes.units.Length
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.wedetect.databinding.FragmentGameBinding
import com.example.wedetect.databinding.FragmentHomeBinding


class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button3.setOnClickListener(){
            Toast.makeText(context, "YAYYYY", Toast.LENGTH_SHORT).show()
        }
        binding

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object{

        var path = Path()
        var paintBrush= Paint()

    }
}