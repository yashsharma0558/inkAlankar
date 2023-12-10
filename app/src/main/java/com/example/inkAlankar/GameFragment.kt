package com.example.inkAlankar

import android.R
import android.graphics.Bitmap
import android.graphics.Color
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
import android.view.Window
import android.view.WindowManager
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
    private val imageList = mutableListOf(
        com.example.inkAlankar.R.drawable.draw_1,
        com.example.inkAlankar.R.drawable.draw_2,
        com.example.inkAlankar.R.drawable.draw_3,
        com.example.inkAlankar.R.drawable.draw_4,
        com.example.inkAlankar.R.drawable.draw_5,
        com.example.inkAlankar.R.drawable.draw_6,
        com.example.inkAlankar.R.drawable.draw_7,
        com.example.inkAlankar.R.drawable.draw_8,
        com.example.inkAlankar.R.drawable.draw_9,
        com.example.inkAlankar.R.drawable.draw_10,
        com.example.inkAlankar.R.drawable.draw_11,
        com.example.inkAlankar.R.drawable.draw_12,
        com.example.inkAlankar.R.drawable.draw_13,
        com.example.inkAlankar.R.drawable.draw_14,
        com.example.inkAlankar.R.drawable.draw_15,
        com.example.inkAlankar.R.drawable.draw_16,
        com.example.inkAlankar.R.drawable.draw_17,
        com.example.inkAlankar.R.drawable.draw_18,
        com.example.inkAlankar.R.drawable.draw_19,
        com.example.inkAlankar.R.drawable.draw_20,
        com.example.inkAlankar.R.drawable.draw_21,
        com.example.inkAlankar.R.drawable.draw_22,
        com.example.inkAlankar.R.drawable.draw_23,
        com.example.inkAlankar.R.drawable.draw_24,
        com.example.inkAlankar.R.drawable.draw_25,
        com.example.inkAlankar.R.drawable.draw_26,
        com.example.inkAlankar.R.drawable.draw_27,
        com.example.inkAlankar.R.drawable.draw_28,
        com.example.inkAlankar.R.drawable.draw_29,
        com.example.inkAlankar.R.drawable.draw_30,
        com.example.inkAlankar.R.drawable.draw_31,
        com.example.inkAlankar.R.drawable.draw_32,
        com.example.inkAlankar.R.drawable.draw_33,
        com.example.inkAlankar.R.drawable.draw_34,
        com.example.inkAlankar.R.drawable.draw_35,
        com.example.inkAlankar.R.drawable.draw_36,
        com.example.inkAlankar.R.drawable.draw_37,
        com.example.inkAlankar.R.drawable.draw_38,
        com.example.inkAlankar.R.drawable.draw_39,
        com.example.inkAlankar.R.drawable.draw_40,
        com.example.inkAlankar.R.drawable.draw_41,
        com.example.inkAlankar.R.drawable.draw_42,
        com.example.inkAlankar.R.drawable.draw_43,
        com.example.inkAlankar.R.drawable.draw_44,
        com.example.inkAlankar.R.drawable.draw_45,
        com.example.inkAlankar.R.drawable.draw_46,
        com.example.inkAlankar.R.drawable.draw_47,
        com.example.inkAlankar.R.drawable.draw_48,
        com.example.inkAlankar.R.drawable.draw_49,
        com.example.inkAlankar.R.drawable.draw_50,
        com.example.inkAlankar.R.drawable.draw_51,
        com.example.inkAlankar.R.drawable.draw_52,
        com.example.inkAlankar.R.drawable.draw_53,
        com.example.inkAlankar.R.drawable.draw_54,
        com.example.inkAlankar.R.drawable.draw_55,
        com.example.inkAlankar.R.drawable.draw_56,
        com.example.inkAlankar.R.drawable.draw_57,
        com.example.inkAlankar.R.drawable.draw_58,
        com.example.inkAlankar.R.drawable.draw_59,
        com.example.inkAlankar.R.drawable.draw_60,
        com.example.inkAlankar.R.drawable.draw_61,
        com.example.inkAlankar.R.drawable.draw_62,
        com.example.inkAlankar.R.drawable.draw_63,
        com.example.inkAlankar.R.drawable.draw_64,
        com.example.inkAlankar.R.drawable.draw_65,
        com.example.inkAlankar.R.drawable.draw_66,
        com.example.inkAlankar.R.drawable.draw_67,
        com.example.inkAlankar.R.drawable.draw_68,
        com.example.inkAlankar.R.drawable.draw_69,
        com.example.inkAlankar.R.drawable.draw_70,
        com.example.inkAlankar.R.drawable.draw_71,
        com.example.inkAlankar.R.drawable.draw_72,
        com.example.inkAlankar.R.drawable.draw_73,
        com.example.inkAlankar.R.drawable.draw_74,
        com.example.inkAlankar.R.drawable.draw_75,
        com.example.inkAlankar.R.drawable.draw_76,
        com.example.inkAlankar.R.drawable.draw_77,
        com.example.inkAlankar.R.drawable.draw_78,
        com.example.inkAlankar.R.drawable.draw_79,
        com.example.inkAlankar.R.drawable.background
    )
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
        binding.imageView.setImageResource(imageList[counter])

        //save bitmap in list
        binding.button3.setOnClickListener {
            if (binding.paintView.path.isEmpty) {
                Toast.makeText(context, "Draw something dum dum", Toast.LENGTH_SHORT).show()
            } else {
                if (binding.button3.text.equals("Submit")) {
                    activity?.window?.setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    )
                    saveBitmapInList()
                    showOverlay()
                    val reference = Firebase.database.reference.child("acc")
                    val initializedMap: MutableMap<String, Bitmap> = mutableMapOf()
                    var count = 0
                    for ((index, value) in bitmapList.withIndex()) {
                        initializedMap[valuesList[index]] = value

                    }
                    for ((index, bitmap) in initializedMap) {
                        // Upload each bitmap to Firebase Storage
                        DataSource(reference).uploadBitmapToFirebaseStorage(bitmap, index, path) {
                            if (it == 1) {
                                count++
                            }
                            binding.progressBar.setProgress(count, true)
                            binding.progressText.text = "${count}/${binding.progressBar.max}"
                            if (count == 79) {
                                hideOverlay()
                            }

                        }

                    }


                } else {
                    saveBitmapInList()
                    updateWord()
                    resetCanvas()
                }
            }
        }


    }

    private fun showOverlay() {
        binding.progressLinearLayout.visibility = View.VISIBLE
        binding.overlayBackground.visibility = View.VISIBLE
        // Disable user interactions if needed

        binding.textView12.setTextColor(Color.GRAY)
        binding.button4.setTextColor(Color.GRAY)
        binding.button3.setTextColor(Color.GRAY)
    }

    private fun hideOverlay() {
        binding.progressLinearLayout.visibility = View.GONE
        binding.overlayBackground.visibility = View.GONE
        binding.textView12.setTextColor(Color.WHITE)
        binding.button4.setTextColor(Color.WHITE)
        binding.button3.setTextColor(Color.WHITE)
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        Toast.makeText(context, "Successfully Submitted!", Toast.LENGTH_SHORT)
            .show()
        findNavController().popBackStack()
    }

    private fun updateWord() {

        counter++
        if (counter < valuesList.size) {
            binding.textView13.text = valuesList[counter]
            binding.imageView.setImageResource(imageList[counter])
        }
        if (counter == COUNT - 1) {
            binding.button3.text = getString(com.example.inkAlankar.R.string.submit)

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