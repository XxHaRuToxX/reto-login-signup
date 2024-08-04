package com.xxharutoxx.loginapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.xxharutoxx.loginapp.model.request.RegisterRequest
import com.xxharutoxx.loginapp.navegation.ScreensManager
import com.xxharutoxx.loginapp.ui.design.BackgroundDesign
import com.xxharutoxx.loginapp.viewmodels.UserViewModel
import android.view.Gravity
import androidx.compose.material3.Button

//@Preview(showBackground = true)
//@Composable
//fun PreviewScreen(){
//    LazyColumn(modifier = Modifier.height(500.dp)) {
//        item{
//            BackgroundDesign()
//            RegisterUserScreen()
//        }
//    }
//}

//@Preview(showBackground = true)
@Composable
fun PreviewScreen(userViewModel: UserViewModel = viewModel(), navController: NavController){
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item{
            BackgroundDesign(height = 130.dp)
            RegisterUserScreen(userViewModel = userViewModel, navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterUserScreen(userViewModel: UserViewModel = viewModel(), navController: NavController){
    val registerResponse by userViewModel.registerResponse.collectAsState()
    val loading by userViewModel.isLoadingRegister.collectAsState()
    val errorResponse by userViewModel.errorResponse.collectAsState()

    val usernameError = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf("") }
    val confirmedPasswordError = remember { mutableStateOf("") }
    val firstnameError = remember { mutableStateOf("") }
    val lastnameError = remember { mutableStateOf("") }
    val cellphoneError = remember { mutableStateOf("") }
    val countryError = remember { mutableStateOf("") }

    val passwordVisible = remember { mutableStateOf(false) }
    val confirmedPasswordVisible = remember { mutableStateOf(false) }

    val context = LocalContext.current

    val username = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val confirmedPassword = remember {
        mutableStateOf("")
    }
    val firstname = remember {
        mutableStateOf("")
    }
    val lastname = remember {
        mutableStateOf("")
    }
    val cellphone = remember {
        mutableStateOf("")
    }
    val country = remember {
        mutableStateOf("")
    }
    TitleOfTheScreen(title = "Registro")
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
                    VisiblePassword(passwordVisible = passwordVisible)
                }
            )
            CustomValidTextField(passwordError)
            CustomTextField(
                text = confirmedPassword,
                labelTitle = "Confirmar contraseña",
                labelDesc = "Confirme la contraseña",
                icon = Icons.Default.Lock,
                setVisualTransformation = true,
                visualTransformation = if(confirmedPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    VisiblePassword(passwordVisible = confirmedPasswordVisible)
                }
            )
            CustomValidTextField(confirmedPasswordError)
            CustomTextField(text = firstname, labelTitle = "Nombres", labelDesc = "Ingrese nombres", icon = Icons.Default.Person, setVisualTransformation = false, visualTransformation = VisualTransformation.None)
            CustomValidTextField(firstnameError)
            CustomTextField(text = lastname, labelTitle = "Apellidos", labelDesc = "Ingrese apellidos", icon = Icons.Default.AccountCircle, setVisualTransformation = false, visualTransformation = VisualTransformation.None)
            CustomValidTextField(lastnameError)
            CustomTextField(text = cellphone, labelTitle = "Celular", labelDesc = "Ingrese celular", icon = Icons.Default.Call, setVisualTransformation = false, visualTransformation = VisualTransformation.None)
            CustomValidTextField(cellphoneError)
            CustomTextField(text = country, labelTitle = "País", labelDesc = "Ingrese país", icon = Icons.Default.Place, setVisualTransformation = false, visualTransformation = VisualTransformation.None)
            CustomValidTextField(countryError)
            CustomButton(
                text = "Crear cuenta",
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
                    if (confirmedPassword.value.isEmpty()) {
                        confirmedPasswordError.value = "El campo de contraseña no puede estar vacío"
                        valid = false
                    } else {
                        confirmedPasswordError.value = ""
                    }
                    if (password.value != confirmedPassword.value) {
                        confirmedPasswordError.value = "Las contraseñas no coinciden"
                        valid = false
                    } else {
                        confirmedPasswordError.value = ""
                    }
                    if (firstname.value.isEmpty()) {
                        firstnameError.value = "El campo de nombres no puede estar vacío"
                        valid = false
                    } else {
                        firstnameError.value = ""
                    }
                    if (lastname.value.isEmpty()) {
                        lastnameError.value = "El campo de apellidos no puede estar vacío"
                        valid = false
                    } else {
                        lastnameError.value = ""
                    }
                    if (cellphone.value.isEmpty()) {
                        cellphoneError.value = "El campo de celular no puede estar vacío"
                        valid = false
                    } else {
                        cellphoneError.value = ""
                    }
                    if (country.value.isEmpty()) {
                        countryError.value = "El campo de país no puede estar vacío"
                        valid = false
                    } else {
                        countryError.value = ""
                    }

                    if (valid) {
                        val request = RegisterRequest(
                            username = username.value,
                            password = password.value,
                            confirmedPassword = confirmedPassword.value,
                            firstname = firstname.value,
                            lastname = lastname.value,
                            cellphone = cellphone.value,
                            country = country.value
                        )
                        userViewModel.registerUser(request)
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
            if (errorResponse != null) {
                LaunchedEffect(key1 = true) {
                    val toast = Toast.makeText(context,  errorResponse?.message ?: "Ocurrio algun error!!", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP, 0, 0)
                    toast.show()
                }
            }
            if (!loading && registerResponse.token.isNotEmpty()) {
                navController.navigate(route = ScreensManager.HomeScreen.route)
            }
            Box(modifier = Modifier
                .align(Alignment.End)
                .padding(end = 50.dp)){
                Row(horizontalArrangement = Arrangement.End) {
                    Text(text = "Ya tienes una cuenta? ")
                    Text(text = "Ingresar", modifier = Modifier.clickable {
                        navController.popBackStack()
                    }, style = TextStyle(color = Color(0xFF6F7FA9)))
                }
            }
        }
    }

}

@Composable
fun TitleOfTheScreen(title: String) {
    Box(modifier = Modifier.padding(start = 55.dp)) {
        Column {
            Text(
                text = title,
                style = TextStyle(color = Color(0xFF6F7FA9)),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(
                modifier = Modifier
                    .width(5.dp)
                    .padding(1.dp)
            )
            Divider(
                color = Color(0xFF6F7FA9), modifier = Modifier
                    .height(2.dp)
                    .width(40.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    text:MutableState<String>,
    labelTitle: String,
    labelDesc: String,
    icon: ImageVector,
    setVisualTransformation: Boolean,
    visualTransformation: VisualTransformation,
    trailingIcon: @Composable () -> Unit = {}
){
    Box(modifier = Modifier.height(70.dp)){
        Column {
            Text(text = labelTitle, fontWeight = FontWeight.Medium)
            TextField(
                value = text.value,
                onValueChange = {text.value = it},
                label = { Text(text = labelDesc, fontSize = 12.sp)},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent),
                leadingIcon = {
                    IconButton(onClick = { /* Acción al hacer clic en el icono */ }) {
                        Row {
                            Icon(
                                imageVector = icon,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Divider(color = Color.Black, modifier = Modifier
                                .height(20.dp)
                                .width(1.dp))
                        }
                    }
                },
                visualTransformation = if (setVisualTransformation) visualTransformation else VisualTransformation.None,
                trailingIcon = { trailingIcon() }
            )
        }
    }
}