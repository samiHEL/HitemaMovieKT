package com.example.projethitemafilm.favoris


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.projethitemafilm.db.Film



@ExperimentalFoundationApi
@Composable
fun Favoris(navController: NavController, favoris: MutableList<Film>) {

    Column(modifier = Modifier.padding(16.dp)) {
        Button(
            modifier = Modifier.padding(bottom = 16.dp),
            onClick = { navController.popBackStack() }
        ) {
            Text(text = "Retour")
        }
        favoris.forEach { film ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    val imageSize = "w500"
                    val fullImageUrl = "https://image.tmdb.org/t/p/$imageSize/${film.poster_path}"
                    Image(
                        painter = rememberImagePainter(fullImageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = film.title)
                }
            }

    }
}