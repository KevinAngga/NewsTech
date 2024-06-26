package com.fjr619.newsloc.presentation.home.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fjr619.newsloc.R
import com.fjr619.newsloc.domain.model.Article
import com.fjr619.newsloc.presentation.Dimens
import com.fjr619.newsloc.presentation.Dimens.ArticleCardSize
import com.fjr619.newsloc.presentation.Dimens.ExtraSmallPadding
import com.fjr619.newsloc.presentation.Dimens.ExtraSmallPadding2
import com.fjr619.newsloc.presentation.Dimens.SmallIconSize
import com.fjr619.newsloc.ui.theme.customColorsPalette

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ArticleCard(
  modifier: Modifier = Modifier,
  article: Article,
  onClick: (() -> Unit)? = null
) {
  val context = LocalContext.current
  Row(
    modifier = modifier
        .clickable { onClick?.invoke() }
        .padding(horizontal = Dimens.MediumPadding1, vertical = ExtraSmallPadding2),

    ) {
    AsyncImage(
      modifier = Modifier
          .size(ArticleCardSize)
          .clip(MaterialTheme.shapes.medium),
      model = ImageRequest.Builder(context)
        .data(article.urlToImage)
        .error(R.drawable.ic_splash)
        .build(),
      contentDescription = null,
      contentScale = ContentScale.Crop
    )

    Spacer(modifier = Modifier.width(ExtraSmallPadding2))

    Column(
      verticalArrangement = Arrangement.SpaceAround,
      modifier = Modifier
          .padding(horizontal = ExtraSmallPadding)
          .height(ArticleCardSize)
    ) {
      Text(
        text = article.title,
        style = MaterialTheme.typography.bodyMedium.copy(),
        color = MaterialTheme.customColorsPalette.textTitle,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = article.source.name,
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
          color = MaterialTheme.customColorsPalette.body
        )
        Spacer(modifier = Modifier.width(ExtraSmallPadding2))
        Icon(
          painter = painterResource(id = R.drawable.ic_time),
          contentDescription = null,
          modifier = Modifier.size(SmallIconSize),
          tint = MaterialTheme.customColorsPalette.body
        )
        Spacer(modifier = Modifier.width(ExtraSmallPadding))
        Text(
          text = article.publishedAt,
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.customColorsPalette.body
        )
      }
    }
  }
}

//@OptIn(ExperimentalSharedTransitionApi::class)
//@Preview(showBackground = true)
//@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
//@Composable
//fun SharedTransitionScope.ArticleCardPreview() {
//    NewsLOCTheme() {
//        ArticleCard(
//            article = Article(
//                author = "",
//                content = "",
//                description = "",
//                publishedAt = "2 hours",
//                source = Source(id = "", name = "BBC"),
//                title = "Her train broke down. Her phone died. And then she met her Saver in a",
//                url = "",
//                urlToImage = "https://ichef.bbci.co.uk/live-experience/cps/624/cpsprodpb/11787/production/_124395517_bbcbreakingnewsgraphic.jpg"
//            )
//        )
//    }
//}
