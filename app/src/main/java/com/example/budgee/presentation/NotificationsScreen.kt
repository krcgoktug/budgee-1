package com.example.budgee.presentation


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

// Renkler
val NotificationItemBackground = Color(0xFFD3D3D3) // Bildirim öğesi arka plan rengi (açık gri)
val GreenIndicator = Color(0xFF4CAF50) // Yeşil indikatör (MAAŞ, FAST)
val RedIndicator = Color(0xFFF44336)   // Kırmızı indikatör (YEMEK)

/**
 * Bildirimler ekranını çizen ana composable. Wear OS için optimize edilmiştir.
 */
@Composable
fun NotificationsScreenWear() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground) // Koyu arka plan
            .padding(16.dp), // Genel padding
        horizontalAlignment = Alignment.Start
    ) {
        // Başlık
        Text(
            text = "Bildirimler",
            color = Color.White,
            fontSize = 20.sp, // Başlık fontu
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp, top = 12.dp, start = 16.dp)
        )

        // Bildirim öğeleri için kaydırılabilir alan
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()), // İçeriği kaydırılabilir yap
            verticalArrangement = Arrangement.spacedBy(10.dp) // Öğeler arası boşluk
        ) {
            NotificationItemWear(
                indicatorColor = GreenIndicator,
                title = "MAAŞ",
                details = "Denizbank mobil +80.000TL"
            )
            NotificationItemWear(
                indicatorColor = RedIndicator,
                title = "YEMEK",
                details = "Burger King -380.00TL"
            )
            NotificationItemWear(
                indicatorColor = GreenIndicator,
                title = "FAST",
                details = "AY** BA* kişisinden fast"
            )
            // Daha fazla bildirim öğesi ekleyerek kaydırmayı test edebilirsiniz
            NotificationItemWear(
                indicatorColor = RedIndicator,
                title = "FATURA",
                details = "TurkNet -150.00TL"
            )
            NotificationItemWear(
                indicatorColor = GreenIndicator,
                title = "HAVALE",
                details = "Zeynep T. +500.00TL"
            )
        }
    }
}

/**
 * Tek bir bildirim öğesini çizen composable. Wear OS için optimize edilmiştir.
 * @param indicatorColor Bildirim durumunu gösteren renkli kare.
 * @param title Bildirimin ana başlığı (örn: "MAAŞ").
 * @param details Bildirimin detay metni (örn: "Denizbank mobil +80.000TL").
 */
@Composable
fun NotificationItemWear(indicatorColor: Color, title: String, details: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(NotificationItemBackground, RoundedCornerShape(10.dp)) // Yuvarlak köşeler
            .padding(8.dp) // Kutu iç padding
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp) // Başlık ile detay arası boşluk
        ) {
            // Renk İndikatörü
            Box(
                modifier = Modifier
                    .size(10.dp) // İndikatör boyutu
                    .background(indicatorColor, RoundedCornerShape(2.dp)) // Hafif yuvarlak köşeli kare
            )
            Spacer(modifier = Modifier.width(6.dp)) // İndikatör ile başlık arası boşluk
            Text(
                text = title,
                color = Color.Black,
                fontSize = 14.sp, // Başlık fontu
                fontWeight = FontWeight.Bold
            )
        }
        // Detay metni
        Text(
            text = details,
            color = Color.Black,
            fontSize = 12.sp, // Detay fontu
            fontWeight = FontWeight.Normal,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
fun PreviewNotificationsScreenWear() {
    // Kendi uygulamanızın temasını kullanmak isterseniz, bu satırları uncomment edin
    // BudgeeTheme {
    NotificationsScreenWear()
    // }
}