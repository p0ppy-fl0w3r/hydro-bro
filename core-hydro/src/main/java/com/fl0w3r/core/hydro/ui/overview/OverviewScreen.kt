package com.fl0w3r.core.hydro.ui.overview

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fl0w3r.core.hydro.R
import com.fl0w3r.core.ui.theme.HydroTheme

@Composable
fun OverviewScreen(modifier: Modifier = Modifier) {
    OverviewBody(modifier = modifier)
}

@Composable
fun OverviewBody(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(ARTICLE_LIST) {
            ArticleItem(article = it, modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(it.articleUrl)
                }
                context.startActivity(intent)
            })
        }
    }
}

@Composable
fun ArticleItem(article: Article, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.articleImage)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.placeholder),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = article.articleTitle,
                    style = MaterialTheme.typography.h2.copy(fontSize = 24.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Divider(modifier = Modifier.background(color = if (article.articleId % 2 == 0) MaterialTheme.colors.secondary else MaterialTheme.colors.primary))
                Text(
                    text = article.articleDescription,
                    style = MaterialTheme.typography.body2,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}