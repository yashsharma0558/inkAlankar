package com.example.inkAlankar

class HelperClass {



//    private fun showFinalScoreDialog() {
//        val dialog = MaterialAlertDialogBuilder(requireContext())
//            .setTitle("congrats")
//            .setMessage("Material Dialog Message")
//            .setPositiveButton("restart") { dialog, _ ->
//                // Handle positive button click if needed
//                reinitalize()
//                dialog.dismiss()
//            }
//            .setNegativeButton("Cancel") { dialog, _ ->
//                // Handle negative button click if needed
//                dialog.dismiss()
//
//            }
//        dialog.create().show()
//    }


//    private fun reinitalize() {
//        counter = 0
//        bitmapList.clear()
//        Navigation.findNavController(binding.root).navigate(R.id.action_gameFragment_self)
//    }






    //    private fun saveDrawingAsBitmap(bitmap: Bitmap) {
//
//        val imageOutStream: OutputStream?
//        val cv = ContentValues()
//        // Name of the file
//        cv.put(MediaStore.Images.Media.DISPLAY_NAME, "drawing.png")
//        // Type of the file
//        cv.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
//        // Location of the file to be saved
//        cv.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
//        // Get the Uri of the file which is to be created in the storage
//        val uri: Uri? =
//            context?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv)
//        try {
//            // Open the output stream with the above uri
//            imageOutStream = context?.contentResolver?.openOutputStream(uri!!)
//            // This method writes the files in storage
//            if (imageOutStream != null) {
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, imageOutStream)
//            }
//            // Close the output stream after use
//            imageOutStream?.close()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//    }
}