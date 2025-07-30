package com.example.budgee.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon // Icon için import
import androidx.compose.ui.tooling.preview.Devices
import com.example.budgee.R

// import com.example.yourappname.R // Kendi R dosyanızı buraya import edin. Örn: import com.mybankapp.R

// Renkler
val Purple700 = Color(0xFF673AB7) // Mor (35%)
val Blue500 = Color(0xFF3F51B5)   // Mavi (50%)
val PinkA200 = Color(0xFFE91E63)  // Pembe (15%)
val DarkBackground = Color(0xFF1C1C1E) // Koyu arka plan

/**
 * Bütçe Durumu ekranını çizen composable.
 */
@Composable
fun BudgetStatusScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground) // Koyu arka plan
            .padding(24.dp)
    ) {
        // Başlık
        Text(
            text = "Bütçe Durumu",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 12.dp,bottom = 8.dp)
        )

        // Banka Bilgisi
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            // DenizBank Logosu (Placeholder - Kendi logonuzu R.drawable içine ekleyin)
            // Eğer logonuz yoksa, bu Icon'u kaldırabilir veya farklı bir placeholder kullanabilirsiniz.
            Image(
                // painter = painterResource(id = R.drawable.denizbank_logo), // Kendi logo kaynağınızı ekleyin
                painter = painterResource(R.drawable.denizbanklogo), // Geçici bir Android ikonu
                contentDescription = "DenizBank Logo",
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "DenizBank",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        // Grafik ve Legend Bölümü
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Kalan alanı kapla
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Donut Grafik (Basit Temsil)
            BudgetDonutChart(
                modifier = Modifier.size(100.dp), // Grafiğin boyutu
                slices = listOf(
                    BudgetSlice(50f, Blue500),    // Alışveriş
                    BudgetSlice(15f, PinkA200),   // Fatura
                    BudgetSlice(35f, Purple700)   // Diğer
                ),
                totalBalance = "30.000"
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Legend (Açıklama)
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f) // Kalan yatay alanı kapla
            ) {
                LegendItem(color = Blue500, text = "Alışveriş")
                Spacer(modifier = Modifier.height(8.dp))
                LegendItem(color = PinkA200, text = "Fatura")
                Spacer(modifier = Modifier.height(8.dp))
                LegendItem(color = Purple700, text = "Diğer")
            }
        }
    }
}

/**
 * Donut grafik dilimi veri sınıfı.
 */
data class BudgetSlice(val percentage: Float, val color: Color)

/**
 * Donut grafiği çizen composable.
 * Not: Bu basit bir görsel temsilidir, gerçek bir grafik kütüphanesi kadar fonksiyonel değildir.
 */
@Composable
fun BudgetDonutChart(
    modifier: Modifier = Modifier,
    slices: List<BudgetSlice>,
    totalBalance: String
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = size.minDimension * 0.2f // Halka kalınlığı
            var startAngle = -90f // Başlangıç açısı (yukarıdan)

            slices.forEach { slice ->
                val sweepAngle = 360f * (slice.percentage / 100f)
                drawArc(
                    color = slice.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt) // Halkanın ucu kesik
                )
                startAngle += sweepAngle
            }
        }

        // Ortadaki Beyaz Metin Kutusu (Toplama bakiyeyi içerir)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize(0.6f) // Ortadaki dairenin boyutu (genişliğin %60'ı)
                .background(Color.Black, CircleShape) // Siyah arka plan ve daire şekli
        ) {
            Text(
                text = "Toplam bakiye",
                color = Color.White,
                fontSize = 7.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = totalBalance,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Yüzdelik göstergeler
        // Bu kısımlar, Canvas'ın üzerine tam olarak konumlandırmak için biraz daha karmaşık olabilir.
        // Şimdilik sadece metin olarak gösterelim, resimdeki gibi tam konumlandırma için
        // daha fazla hesaplama (açısal konumlandırma) gereklidir.
        // Eğer yüzdeleri direk grafiğin üzerine yazmak isterseniz,
        // Modifier.offset ve Modifier.rotate gibi şeyler kullanılarak konumlandırılabilir.
        // Bu örnekte, basit bir konumlandırma yapıldı.
        Text(
            text = "35%", // Mor dilim için
            color = Color.White,
            fontSize = 9
                .sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 10.dp, y = 10.dp) // Kaba konumlandırma
        )
        Text(
            text = "50%", // Mavi dilim için
            color = Color.White,
            fontSize = 9.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (10).dp, y = (-50).dp) // Kaba konumlandırma
        )
        Text(
            text = "15%", // Pembe dilim için
            color = Color.White,
            fontSize = 9.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = 20.dp, y = (-5).dp) // Kaba konumlandırma
        )
    }
}

/**
 * Legend (açıklama) öğesini çizen composable.
 */
@Composable
fun LegendItem(color: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color, CircleShape) // Renkli küçük daire
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = Color.White,
            fontSize = 11.sp
        )
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showBackground = true)
@Composable
fun PreviewBudgetStatusScreen() {
    // Kendi uygulamanızın temasını kullanmak isterseniz, bu satırları uncomment edin
    // YourAppTheme {
    BudgetStatusScreen()
    // }
}