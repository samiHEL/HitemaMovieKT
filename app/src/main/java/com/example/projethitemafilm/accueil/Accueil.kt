package com.example.projethitemafilm.accueil

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.projethitemafilm.db.Film
import com.example.projethitemafilm.db.FilmResponse
import com.example.projethitemafilm.db.TMDB
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.withContext

@ExperimentalFoundationApi
@Composable
fun Accueil(navController: NavController) {
    val apiService = TMDB.create()

    val popularMovies by fetchMovies(apiService::getPopularMovies)
    val topRatedMovies by fetchMovies(apiService::getTopRatedMovies)
    val upcomingMovies by fetchMovies(apiService::getUpcomingMovies)
    val nowPlayingMovies by fetchMovies(apiService::getNowPlayingMovies)

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            CategorieFilm("Les plus populaires", popularMovies, navController)
        }
        item {
            CategorieFilm("Les mieux notés", topRatedMovies, navController)
        }
        item {
            CategorieFilm("À venir", upcomingMovies, navController)
        }
        item {
            CategorieFilm("En ce moment", nowPlayingMovies, navController)
        }

        item {
            Button(
                modifier = Modifier.padding(16.dp),
                onClick = { navController.navigate("favoris") }
            ) {
                Text(text = "Favoris")
            }
        }
    }
}

@Composable
fun CategorieFilm(categorieTitre: String, films: List<Film>, navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = categorieTitre, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(contentPadding = PaddingValues(start = 8.dp, end = 16.dp)) {
            items(films) { film ->
                FilmItem(film, navController)
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun fetchMovies(apiCall: suspend () -> FilmResponse): State<List<Film>> {
    val movies = remember { mutableStateOf<List<Film>>(emptyList()) }

    LaunchedEffect(Unit) {
        val response = withContext(Dispatchers.IO) {
            apiCall.invoke()
        }
        movies.value = response.results
    }

    return movies
}

@Composable
fun FilmItem(film: Film, navController: NavController) {

    val imageSize = "w300"
    val fullImageUrl = "https://image.tmdb.org/t/p/$imageSize/${film.poster_path}"
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(end = 12.dp)
            .clickable { navController.navigate("details/${film.id}") }
    ) {
        Image(
            painter = rememberImagePainter(fullImageUrl),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = film.title,
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}
