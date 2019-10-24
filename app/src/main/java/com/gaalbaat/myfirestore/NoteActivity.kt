package com.gaalbaat.myfirestore

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import dmax.dialog.SpotsDialog

class NoteActivity : AppCompatActivity() {

    lateinit var mNameText : EditText
    lateinit var mStatusText : EditText
    lateinit var mSaveBtn : Button
    lateinit var mFirestoreDB: FirebaseFirestore
    lateinit var mDialogSpotsDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        mDialogSpotsDialog = SpotsDialog.
            Builder().
            setContext(this).
            build()

        mFirestoreDB = FirebaseFirestore.getInstance()
        mNameText = findViewById(R.id.saveNameEditText)
        mStatusText = findViewById(R.id.saveStatusEditText)
        mSaveBtn = findViewById(R.id.saveDataBtn)

//        mSaveBtn.setOnClickListener {
//            saveDataStore()
//        }

        loadFireStoreData()
    }

    private fun loadFireStoreData() {
        mFirestoreDB.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
//                    Log.d(TAG, "${document.id} => ${document.data}"
                    Toast.makeText(applicationContext , "${document.id} => ${document.data} \n" ,
                        Toast.LENGTH_LONG).show()

                    val n = document.data.get("name")
                    val s = document.data.get("status")

                }

            }
            .addOnFailureListener { exception ->
                //                Log.w(TAG, "Error getting documents.", exception)
                Toast.makeText(applicationContext , "Error" , Toast.LENGTH_LONG).show()

            }

    }

    private fun saveDataStore() {
        val name = mNameText.text.toString().trim()
        val status = mStatusText.text.toString().trim()

        if(name.isEmpty()){
            mNameText.error = "Enter Name"
            return
        }
        if(status.isEmpty()){
            mStatusText.error = "Enter Status"
            return
        }
        mDialogSpotsDialog.show()

        val  user = HashMap<String, Any>()
        user.put("name" , name)
        user.put("status", status)

        mFirestoreDB.collection("users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(applicationContext , "Saved "  , Toast.LENGTH_LONG).show()
                mDialogSpotsDialog.dismiss()

            }
            .addOnFailureListener {
                Toast.makeText(applicationContext , "Failed "  , Toast.LENGTH_LONG).show()
                mDialogSpotsDialog.dismiss()

            }
    }
}
