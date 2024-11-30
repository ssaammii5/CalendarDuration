package com.shefasoft.calendarduration.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shefasoft.calendarduration.R

@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Light grey background
    ) {
        // Center Content
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ick), // Replace with your image resource
                contentDescription = "Calendar Image",
                modifier = Modifier
                    .size(80.dp)
                    .graphicsLayer(
                        shadowElevation = 70f, // Apply the elevation to create a shadow
                        shape = RectangleShape, // Optional: Use a shape (optional for the shadow)
                        clip = false // Don't clip, keeping original shape (important for non-rectangular images)
                    )
            )



            Spacer(modifier = Modifier.height(64.dp))
            Button(
                onClick = { /* Handle Google Sign-in */ },
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier
                    .height(48.dp)
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(20)
                    )
                    .clip(RoundedCornerShape(20)),
                shape = RoundedCornerShape(20)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_google), // Replace with Google logo
                    contentDescription = "Google Logo",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sign in with Google",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
        }

        // Bottom Content
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp), // Add some padding to avoid it touching the screen edge
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "By signing in, you acknowledge and agree to our",
                color = Color.Black,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Legal Terms and Privacy Policy",
                color = Color.Gray,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable { /* Open legal terms */ }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}