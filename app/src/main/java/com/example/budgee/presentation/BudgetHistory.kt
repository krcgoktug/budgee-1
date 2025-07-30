package com.example.budgee.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgee.R

// import com.example.yourappname.R // Kendi R dosyanızı buraya import edin. Örn: import com.mywalletapp.R

// Renkler

val LightGrayBackground = Color(0xFFD3D3D3) // Açık gri arka plan

/**
 * Harcama Geçmişi (Günlük Harcama) ekranını çizen composable. Wear OS için optimize edildi.
 */
@Composable
fun DailySpendingScreenWear() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground) // Koyu arka plan
            .padding(16.dp), // Genel padding'i azalttım
        horizontalAlignment = Alignment.Start // Başlığı sola hizala
    ) {
        // Başlık
        Text(
            text = "Harcama",
            color = Color.White,
            fontSize = 18.sp, // Başlık fontunu küçülttüm
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                start = 24.dp,
                bottom = 2.dp
            ) // Başlık alt boşluğunu azalttım
        )
        Text(
            text = "Günlük",
            color = Color.White,
            fontSize = 14.sp, // Alt başlık fontunu küçülttüm
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(
                start = 24.dp,
                bottom = 12.dp
            ) // Alt başlık alt boşluğunu azalttım
        )

        // Harcama Detayları Kutusu
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    LightGrayBackground,
                    RoundedCornerShape(8.dp)
                ) // Köşe yuvarlaklığını azalttım
                .padding(8.dp) // Kutu içindeki padding'i azalttım
        ) {
            SpendingItemWear(
                iconResId = R.drawable.food, // Placeholder icon: Yemek
                category = "Yemek",
                amount = "-2.500TL"
            )
            Spacer(modifier = Modifier.height(8.dp)) // Öğeler arası boşluğu azalttım
            SpendingItemWear(
                iconResId = R.drawable.house_rent, // Placeholder icon: Kira
                category = "Kira",
                amount = "-25.000TL"
            )
            Spacer(modifier = Modifier.height(8.dp))
            SpendingItemWear(
                iconResId = R.drawable.shopping, // Placeholder icon: Alışveriş
                category = "Alışveriş",
                amount = "-10.000TL"
            )
            Spacer(modifier = Modifier.height(8.dp))
            SpendingItemWear(
                iconResId = R.drawable.bill, // Placeholder icon: Faturalar
                category = "Faturalar",
                amount = "-4.000TL"
            )
        }
    }
}

/**
 * Tek bir harcama kategorisi öğesini (ikon, kategori adı, miktar) çizen composable. Wear OS için optimize edildi.
 * @param iconResId Kategori ikonunun kaynak ID'si.
 * @param category Harcama kategorisinin adı (örn: "Yemek").
 * @param amount Harcama miktarı (örn: "-2.500TL").
 */
@Composable
fun SpendingItemWear(iconResId: Int, category: String, amount: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Kategori İkonu
        Image(
            painter = painterResource(id = iconResId), // Kendi ikon kaynağınızı ekleyin
            contentDescription = category,
            modifier = Modifier.size(24.dp) // İkon boyutunu küçülttüm
        )
        Spacer(modifier = Modifier.width(8.dp)) // İkon ile metin arası boşluğu azalttım

        // Kategori Adı ve Miktar
        Text(
            text = "$category: $amount",
            color = Color.Black,
            fontSize = 16.sp, // Metin boyutunu küçülttüm
            fontWeight = FontWeight.Bold // Metin kalınlığı
        )
    }
}

@Preview(showBackground = true, device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
fun PreviewDailySpendingScreenWear() {
    // Kendi uygulamanızın temasını kullanmak isterseniz, bu satırları uncomment edin
    // BudgeeTheme {
    DailySpendingScreenWear()
    // }
}