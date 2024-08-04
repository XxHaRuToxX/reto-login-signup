package com.xxharutoxx.loginapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.xxharutoxx.loginapp.model.request.LoginRequest
import com.xxharutoxx.loginapp.model.request.RegisterRequest
import com.xxharutoxx.loginapp.navegation.ScreensManager
import com.xxharutoxx.loginapp.ui.design.BackgroundDesign
import com.xxharutoxx.loginapp.viewmodels.UserViewModel

//@Preview(showBackground = true)
@Composable
fun LoginScreen(userViewModel: UserViewModel = viewModel(), navController: NavController){
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item{
            BackgroundDesign(height = 200.dp)
            CustomLogin(userViewModel = userViewModel, navController = navController)
        }
    }
}

@Composable
fun CustomLogin(userViewModel: UserViewModel = viewModel(), navController: NavController) {
    val loginResponse by userViewModel.loginResponse.collectAsState()
    val loading by userViewModel.isLoadingLogin.collectAsState()
    val errorResponse by userViewModel.errorResponse.collectAsState()
    val rememberMe = remember { mutableStateOf(false) }
    val passwordVisible = remember { mutableStateOf(false) }
    val usernameError = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf("") }

    val context = LocalContext.current
    val username = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }

    Column {
        TitleOfTheScreen(title = "Ingresar")
        Box(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomTextField(text = username, labelTitle = "Usuario", labelDesc = "Ingrese usuario", icon = Icons.Default.AccountBox, setVisualTransformation = false, visualTransformation = VisualTransformation.None)
                CustomValidTextField(usernameError)
                CustomTextField(
                    text = password,
                    labelTitle = "Contraseña",
                    labelDesc = "Ingrese contraseña",
                    icon = Icons.Default.Lock,
                    setVisualTransformation = true,
                    visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        VisiblePassword(passwordVisible)
                    }
                )
                CustomValidTextField(passwordError)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = rememberMe.value,
                        onCheckedChange = {
                            rememberMe.value = it
                            val message = if (it) "Recordar contraseña activado" else "Recordar contraseña desactivado"
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    )
                    Text(text = "Recordarme")
                    Spacer(modifier = Modifier.width(60.dp))
                    Text(text = "Contraseña Olvidada?", style = TextStyle(color = Color(0xFF6F7FA9)))
                }
                CustomButton(
                    text = "Ingresar",
                    onCLick = {
                        var valid = true
                        if (username.value.isEmpty()) {
                            usernameError.value = "El campo de usuario no puede estar vacío"
                            valid = false
                        } else {
                            usernameError.value = ""
                        }
                        if (password.value.isEmpty()) {
                            passwordError.value = "El campo de contraseña no puede estar vacío"
                            valid = false
                        } else {
                            passwordError.value = ""
                        }

                        if (valid) {
                            val request = LoginRequest(
                                username = username.value,
                                password = password.value
                            )
                            userViewModel.loginUser(request)
                        }
                    },
                    enabled = !loading,
                    modifier = Modifier
                        .width(250.dp)
                        .height(40.dp)
                )
                if (loading) {
                    CircularProgressIndicator() // Mostrar indicador de carga
                }
//                if (!loading && errorResponse != null) {
                if (errorResponse != null) {
                    LaunchedEffect(key1 = true) {
                        Toast.makeText(context,  errorResponse?.message ?: "Ocurrio algun error!!", Toast.LENGTH_SHORT).show()
                    }
                }
                if (!loading && loginResponse != null && loginResponse?.token?.isNotEmpty() == true) {
                    navController.navigate(route = ScreensManager.HomeScreen.route)
                }
            }
        }
        Box(modifier = Modifier
            .align(Alignment.End)
            .padding(end = 50.dp)){
            Row(horizontalArrangement = Arrangement.End) {
                Text(text = "No tienes una cuenta? ")
                Text(text = "Registrarse", modifier = Modifier.clickable {
                    navController.navigate(route = ScreensManager.RegisterScreen.route)
                },  style = TextStyle(color = Color(0xFF6F7FA9)))
            }
        }
    }
}

@Composable
fun VisiblePassword(passwordVisible: MutableState<Boolean>) {
    IconButton(onClick = {
        passwordVisible.value = !passwordVisible.value
    }) {
        Icon(
            imageVector = if (passwordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
            contentDescription = if (passwordVisible.value) "Hide password" else "Show password"
        )
    }
}

@Composable
fun CustomValidTextField(value: MutableState<String>) {
    if (value.value.isNotEmpty()) {
        Text(
            text = value.value,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun CustomButton(
    text:String,
    onCLick:()->Unit,
    modifier: Modifier,
    enabled: Boolean,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
    containerColor: Color = Color(0xFF6F7FA9),
){
    Button(
        onClick = { onCLick() },
        enabled = enabled,
        modifier = modifier,
        shape = shape,
        colors = ButtonDefaults.buttonColors(containerColor = containerColor)
    ) {
        Text(text = text, fontSize = 20.sp, style = TextStyle(color = Color.White))
    }
}