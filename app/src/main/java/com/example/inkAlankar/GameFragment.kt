package com.example.inkAlankar

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract.Data
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.inkAlankar.DataSource
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.inkAlankar.databinding.FragmentGameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Firebase
import com.google.firebase.database.database
import java.io.OutputStream


class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private var bitmapList = mutableListOf<Bitmap>()
    private val valuesList = mutableListOf(
        "क (ka)",
        "ख (kha)",
        "ग (ga)",
        "घ (gha)",
        "ङ (nga)",
        "च (cha)",
        "छ (chha)",
        "ज (ja)",
        "झ (jha)",
        "ञ (nya)",
        "ट (ṭa)",
        "ठ (ṭha)",
        "ड (ḍa)",
        "ढ (ḍha)",
        "ण (ṇa)",
        "त (ta)",
        "थ (tha)",
        "द (da)",
        "ध (dha)",
        "न (na)",
        "प (pa)",
        "फ (pha)",
        "ब (ba)",
        "भ (bha)",
        "म (ma)",
        "य (ya)",
        "र (ra)",
        "ल (la)",
        "व (va)",
        "श (sha)",
        "ष (ṣa)",
        "स (sa)",
        "ह (ha)",
        "ष (ṣa)",
        "त्र (tra)",
        "क्‍ (adha k)",
        "ख्‍ (adha kha)",
        "ग्‍ (adha ga)",
        "घ्‍ (adha gha)",
        "च्‍ (adha cha)",
        "छ्‍(adha jh)",
        "ज्‍ (adha nya)",
        "झ्‍ (adha jh)",
        "ञ्‍ (adha jh)",
        "त्‍ (adha ta)",
        "थ्‍ (adha tha)",
        "द् (adha da)",
        "ध्‍ (adha dha)",
        "न्‍ (adha na)",
        "प्‍ (adha pa)",
        "फ्‍ (adha fa)",
        "ब्‍  (adha ba)",
        "भ्‍  (adha bha)",
        "म्‍. (adha m)",
        "य्‍. (adha ya)",
        "ल्‍. (adha la)",
        "व्‍  (adha va)",
        "श्‍. (adha sh)",
        "ष्‍. (adha sha)",
        "स्‍. (adha sa)",
        "ह्‍ (adha ha)",
        "\\",
        "forward slash",
        ".",
        ":",
        "(ँ)",
        "ो (o)",
        "ौ (au)",
        "अ (a)",
        "इ (i)",
        "उ (u)",
        "ऊ (uu)",
        "ऐ (ae)",
        "ा (a)",
        "ू (bada u)",
        "ि (i)",
        "ी (ii)",
        "ु (chota u)",
        "े (e)",
        "ै (ai)"
    )
    private var counter: Int = 0
    private lateinit var path: String

    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.let {
            path = it.getString("path") as String
        }
        super.onCreate(savedInstanceState)
    }

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
        //change button text to valueList's first object
        binding.textView13.text = valuesList[counter]

        //save bitmap in list
        binding.button3.setOnClickListener {
            if (!binding.paintView.path.isEmpty) {
                Toast.makeText(context, "Draw something dum dum", Toast.LENGTH_SHORT).show()
            } else {
                if (binding.button3.text.equals("Submit")) {
                    saveBitmapInList()
//                    OperationAsyncTask().execute()
                    binding.progressBar.visibility = View.VISIBLE
                    val reference = Firebase.database.reference.child("acc")
                    Log.d("maplength", valuesList.size.toString())
                    Log.d("maplength", bitmapList.size.toString())
                    val initializedMap: MutableMap<String, Bitmap> = mutableMapOf()
                    var count = 0
                    for ((index, value) in bitmapList.withIndex()) {
                        initializedMap[valuesList[index]] = value
                        Log.d("maplength", valuesList[index])

                    }
                    Log.d("maplength", initializedMap.size.toString())
                    for ((index, bitmap) in initializedMap) {
                        // Upload each bitmap to Firebase Storage
                        DataSource(reference).uploadBitmapToFirebaseStorage(bitmap, index, path) {
                            if (it == 1) {
                                count++
                            }
                            Log.d("counter", count.toString())
                            binding.progressBar.setProgress(count, true)
                            if (count == 79) {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(context, "Successfully Submitted!", Toast.LENGTH_SHORT)
                                    .show()
                                findNavController().popBackStack()
                            }

                        }


//                        showFinalScoreDialog()
                    }


                } else {
                    saveBitmapInList()
                    updateWord()
                    resetCanvas()
                }
            }
        }


    }


    private fun updateWord() {
        counter++
        if (counter < valuesList.size) {
            binding.textView13.text = valuesList[counter]
        }
//        if (counter < COUNT) {
//            binding.textView13.text = valuesList[counter]
//        }
        if (counter == COUNT - 1) {
            binding.button3.text = "Submit"
//            Log.d("here", binding.button3.text.toString())

        }
    }

    private fun saveBitmapInList() {
        val bitmap: Bitmap = binding.paintView.getBitmap()
        bitmapList.add(bitmap)

    }

    private fun resetCanvas() {
        binding.paintView.eraseCanvas()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val COUNT = 80
    }


}