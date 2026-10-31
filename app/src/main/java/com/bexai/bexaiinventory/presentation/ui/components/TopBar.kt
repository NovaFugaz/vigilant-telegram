package com.bexai.bexaiinventory.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bexai.bexaiinventory.presentation.ui.theme.NaranjaIntenso
import com.bexai.bexaiinventory.presentation.ui.theme.fonditoSplash

@Composable
fun TopBar (
    subtitle: String,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier.fillMaxWidth()
            .background(fonditoSplash)
            .statusBarsPadding()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(NaranjaIntenso),
            contentAlignment = Alignment.Center)
        {
            Text(text = "BX", color = Color.White,
                fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
        }
        Spacer(Modifier.width(12.dp))

        Column {
            Text(text = "BexAI", fontSize = 16.sp, fontWeight = FontWeight.Bold,
                color = Color.White)

            Text(text = subtitle.uppercase(), color = NaranjaIntenso,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold)
        }
    }
}