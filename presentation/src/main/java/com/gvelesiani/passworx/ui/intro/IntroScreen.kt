package com.gvelesiani.passworx.ui.intro

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.gvelesiani.passworx.R
import com.gvelesiani.passworx.constants.IntroPage
import com.gvelesiani.passworx.navGraph.Screen
import com.gvelesiani.passworx.ui.intro.firstStep.FirstStepScreen
import com.gvelesiani.passworx.ui.intro.secondStep.SecondStepScreen
import com.gvelesiani.passworx.ui.intro.thirdStep.ThirdStepScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun IntroScreen(navController: NavController, viewModel: IntroVM = getViewModel()) {
    val context = LocalContext.current
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (pagerState.currentPage != IntroPage.FIRST)
                Column(modifier = Modifier
                    .align(Alignment.TopStart)
                    .width(40.dp)
                    .height(40.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }) {
                    Icon(
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = ""
                    )
                }

            if (pagerState.currentPage != IntroPage.THIRD)
                Text(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 16.dp, end = 16.dp)
                        .clickable {
                            viewModel.finishIntro()
                            navController.navigate(Screen.CreateMasterPassword.route) {
                                popUpTo(0)
                            }
                        },
                    text = context.getString(R.string.skip_intro_button_text),
                    fontFamily = FontFamily(Font(R.font.medium)),
                    fontSize = 16.sp
                )

            Column(modifier = Modifier.align(Alignment.Center)) {
                HorizontalPager(
                    state = pagerState,
                    count = 3
                ) { page ->
                    when (page) {
                        IntroPage.FIRST -> {
                            FirstStepScreen()
                        }
                        IntroPage.SECOND -> {
                            SecondStepScreen()
                        }
                        IntroPage.THIRD -> {
                            ThirdStepScreen()
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(56.dp))

                LinearProgressIndicator(
                    modifier = Modifier.width(150.dp),
                    progress = when (pagerState.currentPage) {
                        IntroPage.FIRST -> 0.3f
                        IntroPage.SECOND -> 0.7f
                        IntroPage.THIRD -> 1f
                        else -> {
                            0f
                        }
                    }
                )

                FloatingActionButton(
                    modifier = Modifier
                        .size(56.dp),
                    elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp),
                    onClick = {
                        if (pagerState.currentPage == IntroPage.THIRD) {
                            viewModel.finishIntro()
                            navController.navigate(Screen.CreateMasterPassword.route) {
                                popUpTo(0)
                            }
                        } else {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    }) {
                    Icon(
                        painterResource(
                            id = R.drawable.ic_overview_item_arrow
                        ),
                        contentDescription = ""
                    )
                }
            }
        }
    }
}