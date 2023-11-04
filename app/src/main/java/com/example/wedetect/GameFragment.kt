package com.example.wedetect

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Path
import android.hardware.camera2.params.BlackLevelPattern.COUNT
import android.health.connect.datatypes.units.Length
import android.net.Uri
import android.os.Bundle
import com.example.wedetect.PaintView
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.wedetect.GameFragment.Companion.COUNT
import com.example.wedetect.databinding.FragmentGameBinding
import com.example.wedetect.databinding.FragmentHomeBinding
import java.io.OutputStream


class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private var bitmapList = mutableListOf<Bitmap>()
    private var counter: Int = 0
    private lateinit var paintView: PaintView
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
        //reset canvas
        binding.button4.setOnClickListener {
            resetCanvas()
        }
        //


        binding.button3.setOnClickListener() {
            if (binding.paintView.path.isEmpty) {
                Toast.makeText(context, "Draw something dum dum", Toast.LENGTH_SHORT).show()
            } else {
                if (binding.button3.text.equals("Submit")) {
                    bitmapList.forEach {
                        saveDrawingAsBitmap(it)
                    }
                } else {
                    saveBitmapInList()
                }
            }
        }


    }

    private fun saveBitmapInList() {
        val bitmap: Bitmap = binding.paintView.getBitmap()
        bitmapList.add(bitmap)
        counter++
        if (counter == COUNT) {
            binding.button3.setText("Submit")
        }
    }

    private fun resetCanvas() {
        binding.paintView.eraseCanvas()
    }

    private fun saveDrawingAsBitmap(bitmap: Bitmap) {


        var imageOutStream: OutputStream? = null
        val cv = ContentValues()
        // Name of the file
        cv.put(MediaStore.Images.Media.DISPLAY_NAME, "drawing.png")
        // Type of the file
        cv.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        // Location of the file to be saved
        cv.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        // Get the Uri of the file which is to be created in the storage
        val uri: Uri? =
            context?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv)
        try {
            // Open the output stream with the above uri
            imageOutStream = context?.contentResolver?.openOutputStream(uri!!)
            // This method writes the files in storage
            if (imageOutStream != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, imageOutStream)
            }
            // Close the output stream after use
            imageOutStream?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val COUNT = 10
    }

}