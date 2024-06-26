//package com.fjr619.newsloc.presentation.news_navigator.components
//
//import android.annotation.SuppressLint
//import androidx.compose.animation.core.Spring
//import androidx.compose.animation.core.SpringSpec
//import androidx.compose.animation.core.animateDpAsState
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.RowScope
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.material3.Badge
//import androidx.compose.material3.BadgedBox
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.LocalContentColor
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.unit.dp
//
///**
// * https://blog.devgenius.io/animated-bottom-navigation-in-jetpack-compose-af8f590fbeca
// */
//@OptIn(ExperimentalMaterial3Api::class)
//@SuppressLint("CoroutineCreationDuringComposition")
//@Composable
//fun RowScope.AnimatedBottomNavigationItem(
//    selected: Boolean,
//    onClick: () -> Unit,
//    icon: ImageVector,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    label: String,
//    hasBadge: Boolean,
//    count: Int,
//    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
//    selectedContentColor: Color = LocalContentColor.current,
//) {
//    val top by animateDpAsState(
//        targetValue = if (selected) 0.dp else 80.dp,
//        animationSpec = SpringSpec(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessVeryLow), label = ""
//    )
//
//    Box(
//        modifier = modifier
//            .padding(start = 10.dp, end = 10.dp)
//            .weight(1f)
//            .clickable(
//                interactionSource = interactionSource,
//                indication = null
//            ) {
//                onClick.invoke()
//            },
//        contentAlignment = Alignment.Center,
//    ) {
//        BadgedBox(
//            modifier = Modifier
//                .width(26.dp)
//                .offset(y = top),
//            badge = {
//                if (hasBadge && count != 0)
//            Badge {
//                Text(text = "$count")
//            }
//        }) {
//            Icon(
//                imageVector = icon,
//                tint = selectedContentColor,
//                contentDescription = null,
//
//            )
//        }
//
//        BadgedBox(
//            modifier = Modifier
//                .offset(y = top - 80.dp),
//            badge = {
//                if (hasBadge && count != 0)
//            Badge {
//                Text(text = "$count")
//            }
//        }) {
//            Box(
//                contentAlignment = Alignment.Center,
//
//            ) {
//                Text(
//                    text = label,
//                )
//            }
//        }
//    }
//}