import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// R dosyanızın doğru yolunu buraya yazın
// Örneğin: import com.yourpackage.R
// Bu örnekte varsayılan bir placeholder kullanılmıştır.
// Kendi projenizin R dosyasını içe aktardığınızdan emin olun.
// Eğer pil ikonu yoksa, Android Studio'da res/drawable klasörüne sağ tıklayıp
// New -> Vector Asset seçeneğinden bir pil ikonu ekleyebilirsiniz.
// Adını ic_battery_full olarak ayarlayın.
// import com.example.yourappname.R // Bu satırı kendi projenizin R dosyasıyla değiştirin


/**
 * Akıllı saatin menü ekranını çizen ana composable.
 * Sadece saat ekranının görselini içerir.
 */
@Composable
fun WatchMenuDisplayOnly(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0E0E0)), // Main background color (gray in the image)
        contentAlignment = Alignment.Center // Center the watch on the screen
    ) {
        WatchFrameContent(navController)
    }
}

/**
 * Akıllı saatin kasasını ve içindeki ekran içeriğini çizen composable.
 */
@Composable
fun WatchFrameContent(navController: NavController) {
    Box(
        modifier = Modifier
            .width(250.dp) // Watch width
            .height(300.dp) // Watch height
            .background(Color.Black, RoundedCornerShape(24.dp)) // Brown watch casing
            .padding(vertical = 20.dp), // Inner padding for the screen
        contentAlignment = Alignment.Center
    ) {
        // İç ekran kısmı
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp)) // Screen corner roundness
                .background(Color.Black) // Black background for the screen
                .padding(18.dp)
                .verticalScroll(rememberScrollState()), // Ekran içeriğini kaydırılabilir yap
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // İçeriği yukarıdan başlat
        ) {
            // Saat üst çubuğu (menü başlığı, pil durumu ve saat)
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Menü",
                    color = Color.White,
                    fontSize = 20.sp, // Title font size increased slightly
                    fontWeight = FontWeight.Bold,
                )

            }

            Spacer(modifier = Modifier.height(24.dp)) // Top bar and first menu button spacing

            // Menü butonları
            WatchMenuItem(text = "BÜTÇE DURUMU", onclick = {
                navController.navigate("budget_summary")
            })
            Spacer(modifier = Modifier.height(8.dp)) // Spacing between buttons
            WatchMenuItem(text = "HARCAMA GEÇMİŞİ", onclick = {
                navController.navigate("budget_history")
            })
            Spacer(modifier = Modifier.height(8.dp))
            WatchMenuItem(text = "BİLDİRİMLER", onclick = {
                navController.navigate("notification")
            })
            Spacer(modifier = Modifier.height(24.dp)) // Last button and bottom of screen spacing
        }

        // Saatin yanındaki döner tuş (örnek olarak bir Box ekleyelim)
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd) // Align to the right center
                .offset(x = 10.dp) // Offset slightly outside to the right
                .size(30.dp)
                .background(Color.Gray, CircleShape)
                .border(1.dp, Color.DarkGray, CircleShape)
        )
    }
}

/**
 * Tek bir menü butonunu çizen composable.
 * @param text Butonda görüntülenecek metin.
 */
@Composable
fun WatchMenuItem(text: String, onclick: () -> Unit) {
    Button(
        onClick = { onclick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFC0C0C0) // Button background color (light gray)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp) // Button height increased slightly
            .border(2.dp, Color.Black, RoundedCornerShape(10.dp)), // Black border and slightly more rounded corners
        shape = RoundedCornerShape(10.dp), // Same roundness
        contentPadding = PaddingValues(0.dp) // Zero inner padding
    ) {
        Text(
            text = text,
            color = Color.Black, // Text color set to black
            fontSize = 14.sp, // Text font size increased
            fontWeight = FontWeight.Bold // Text made bolder
        )
    }
}
