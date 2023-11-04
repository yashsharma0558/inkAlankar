package com.example.wedetect

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.wedetect.databinding.FragmentGameBinding
import java.io.OutputStream


class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private var bitmapList = mutableListOf<Bitmap>()
    private val valuesList = mutableListOf("ka", "kha", "ga", "gha")
    private var counter: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //reset canvas
        binding.button4.setOnClickListener {
            resetCanvas()
        }
        //
        binding.textView13.text = valuesList[counter]

        binding.button3.setOnClickListener {
            if (binding.paintView.path.isEmpty) {
                Toast.makeText(context, "Draw something dum dum", Toast.LENGTH_SHORT).show()
            } else {
                if (binding.button3.text.equals(R.string.submit.toString())) {
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
        binding.textView13.text = valuesList[counter]
        if (counter == COUNT) {
            binding.button3.text = R.string.submit.toString()

        }
    }

    private fun resetCanvas() {
        binding.paintView.eraseCanvas()
    }

    private fun saveDrawingAsBitmap(bitmap: Bitmap) {


        val imageOutStream: OutputStream?
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
        const val COUNT = 4
    }

}