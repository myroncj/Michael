package com.example.michael

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_product_log.*

class ProductLogActivity : AppCompatActivity() {

    private val TAG: String = "MICHAEL"

    private lateinit var db: FirebaseFirestore

    private lateinit var data: Map<String, Any?>
    private lateinit var bundle: Bundle
    private lateinit var codeValue: Any
    private lateinit var qty: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_log)

        bundle = intent.extras!!
        codeValue = bundle.get("codeValue")!!

        FirebaseApp.initializeApp(applicationContext)

        db = FirebaseFirestore.getInstance()

        codeValueText.setText(codeValue.toString())

        submitData.setOnClickListener(View.OnClickListener {

            try {
                prepareDataForSubmission()
            } catch (ex: Exception) {
                Log.e(TAG, ex.toString())
            }

            try {
                sendDataToFirestore()
            } catch (ex: Exception) {
                Log.e(TAG, ex.toString())
            }

        })

    }

    private fun sendDataToFirestore() {

        db.collection("store")
            .add(data)
            .addOnSuccessListener { documentReference ->
                intent = Intent(this, MainActivity::class.java)
                Toast.makeText(applicationContext, "Stored $codeValue", Toast.LENGTH_LONG)
                    .show()
                Log.e(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                startActivity(intent)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding document", e)
            }
    }

    private fun prepareDataForSubmission() {

        qty = quantityValue!!.text.toString()

        data = hashMapOf(
            "code" to codeValue,
            "quantity" to qty,
            "time" to System.currentTimeMillis()
        )
    }
}
