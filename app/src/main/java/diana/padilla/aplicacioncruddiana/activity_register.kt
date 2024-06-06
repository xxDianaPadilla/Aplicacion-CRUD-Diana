package diana.padilla.aplicacioncruddiana

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import modelo.ClaseConexion

class activity_register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtName = findViewById<EditText>(R.id.txtName)
        val txtEmail = findViewById<EditText>(R.id.txtEmailR)
        val txtPassword = findViewById<EditText>(R.id.txtPasswordR)
        val txtConfirmPassword = findViewById<EditText>(R.id.txtConfirmPassword)
        val btnCreateAccount = findViewById<Button>(R.id.btnCreateAccount)


        btnCreateAccount.setOnClickListener {
            val name = txtName.text.toString()
            val email = txtEmail.text.toString()
            val password = txtPassword.text.toString()
            val confirmPassword = txtConfirmPassword.text.toString()

            if(txtName.text.isEmpty() || txtEmail.text.isEmpty() || txtPassword.text.isEmpty()){
                runOnUiThread {
                    Toast.makeText(this, "Please fill out all the data 😮", Toast.LENGTH_LONG).show()
                }
            } else if(password != confirmPassword){
                //Mostrar mensaje de error, para hacer mensajes desde un corrutina debe ejecutarse en el principal, por eso estoy utilizando "runOnUiThread :)"
                runOnUiThread {
                    Toast.makeText(this, "The passwords don't match 😅", Toast.LENGTH_LONG).show()
                }
            }else{
                CoroutineScope(Dispatchers.IO).launch {
                    val objConexion = ClaseConexion().cadenaConexion()

                    val addUser = objConexion?.prepareStatement("insert into Users (name, email, password) values(?, ?, ?)")!!

                    addUser.setString(1, name)
                    addUser.setString(2, email)
                    addUser.setString(3, password)
                    addUser.executeQuery()

                    runOnUiThread {
                        txtName.text.clear()
                        txtEmail.text.clear()
                        txtPassword.text.clear()
                        txtConfirmPassword.text.clear()
                        Toast.makeText(this@activity_register, "Registered user successfully 🎉", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}