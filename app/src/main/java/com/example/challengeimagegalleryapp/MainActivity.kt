package com.example.challengeimagegalleryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challengeimagegalleryapp.ui.theme.ChallengeImageGalleryAppTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeImageGalleryAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GalleryApp()
                }
            }
        }
    }
}

// the view
@Composable
fun GalleryApp(modifier: Modifier = Modifier
    .fillMaxSize()
) {
    // the app has:
    // nav buttons, next, previous, clock icon to start gallery animation
    // gallery picture
    // title of the picture

    // have mutable states
    var currentImage by remember { mutableIntStateOf(1) }

    // the mutable states of image resources
    var currentImageResource by remember { mutableIntStateOf(R.drawable.gallery_image_1) }
    var currentImageTitleResource by remember { mutableIntStateOf(R.string.gallery_title_1) }

    // add onvalue change
    val onCurrentImageResourceChange =
        { newResource: Int -> currentImageResource = newResource }
    val onCurrentImageTitleResourceChange =
        { newResource: Int -> currentImageTitleResource = newResource }
    val onCurrentImageChange =
        { newImageNumber: Int -> currentImage = newImageNumber }

    // have a parent modifier that all child inherit from
    val parentModifier: Modifier = Modifier
        .fillMaxWidth()
    Column (
        modifier = parentModifier
    ) {
        AppTitleSection(modifier = parentModifier)
        NavigationButtonsSection(
            imageNumber = currentImage,
            onImageChange = onCurrentImageChange,
            onImageResourceChange = onCurrentImageResourceChange,
            onImageTitleResourceChange = onCurrentImageTitleResourceChange,
            modifier = parentModifier
        )
        ImageSection(
            currentImageResource = currentImageResource,
            currentImageTitleResource= currentImageTitleResource,
            modifier = parentModifier
        )
        ImageTitleSection(
            currentImageTitleResource = currentImageTitleResource,
            modifier = parentModifier
        )
    }
}


@Composable
fun AppTitleSection(modifier: Modifier = Modifier) {
    Row (
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(text = stringResource(id = R.string.app_name),
            modifier = modifier.padding(12.dp),
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.Monospace,
            fontSize = 20.sp
        )
    }
}


@Composable
fun NavigationButtonsSection(
    imageNumber: Int,
    onImageResourceChange: (Int) -> Unit,
    onImageTitleResourceChange: (Int) -> Unit,
    onImageChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var timerIsOn by remember { mutableStateOf(false) }

//    if (timerIsOn) {
//        // execute the timer method
//        setInterval(500) {
//            onImageChange(goToNextImageNumber(imageNumber))
//            println(imageNumber)
//
//            // recalculate the image resources
//            onImageResourceChange(getImageResourceByNumber(imageNumber))
//            onImageTitleResourceChange(getImageTitleResourceByNumber(imageNumber))
//        }
//    }

    Row (
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(16.dp, 8.dp)
    ) {
        Button(onClick = {
            timerIsOn = false
            onImageChange(goToPrevImageNumber(imageNumber))

            // recalculate the image resources
            onImageResourceChange(getImageResourceByNumber(imageNumber))
            onImageTitleResourceChange(getImageTitleResourceByNumber(imageNumber))
        }) {
            Icon(painter = painterResource(id = R.drawable.arrow_prev), contentDescription = "Go toPrevious Image")
        }
        Button(onClick = {
            timerIsOn = !timerIsOn

        }) {
            Icon(painter = painterResource(id = R.drawable.timer_animation_icon), contentDescription = "Go toPrevious Image")
        }
        Button(onClick = {
            timerIsOn = false
            onImageChange(goToNextImageNumber(imageNumber))

            // recalculate the image resources
            onImageResourceChange(getImageResourceByNumber(imageNumber))
            onImageTitleResourceChange(getImageTitleResourceByNumber(imageNumber))
        }) {
            Icon(painter = painterResource(id = R.drawable.arrow_next), contentDescription = "Go toPrevious Image")
        }
    }
}


@Composable
fun ImageSection(
    currentImageResource: Int,
    currentImageTitleResource: Int,
    modifier: Modifier = Modifier
) {
    Row (
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(8.dp, 20.dp)
    ) {
        Image(
            painter = painterResource(id = currentImageResource),
            contentDescription = stringResource(id = currentImageTitleResource)
        )
    }
}


@Composable
fun ImageTitleSection(
    currentImageTitleResource: Int,
    modifier: Modifier = Modifier
) {
    Row (
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(8.dp, 12.dp)
    ) {
        Text(
            text = stringResource(id = currentImageTitleResource),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
    }
}

// the data
val imageResourcesArray = arrayListOf<Int>(
    R.drawable.gallery_image_1,
    R.drawable.gallery_image_2,
    R.drawable.gallery_image_3,
    R.drawable.gallery_image_4,
    R.drawable.gallery_image_5,
    R.drawable.gallery_image_6,
    R.drawable.gallery_image_7,
    R.drawable.gallery_image_8,
    R.drawable.gallery_image_9,
    R.drawable.gallery_image_10,
    R.drawable.gallery_image_11,
    R.drawable.gallery_image_12
)

val imageTitleResourceArray = arrayListOf<Int>(
    R.string.gallery_title_1,
    R.string.gallery_title_2,
    R.string.gallery_title_3,
    R.string.gallery_title_4,
    R.string.gallery_title_5,
    R.string.gallery_title_6,
    R.string.gallery_title_7,
    R.string.gallery_title_8,
    R.string.gallery_title_9,
    R.string.gallery_title_10,
    R.string.gallery_title_11,
    R.string.gallery_title_12
)




// the controller

// this will return the current image so we can
// change the state to the return value
// image number start from 1 to 12, which are the numeration
// I use to name the gallery titles and images in the resources
fun getImageResourceByNumber(imageNumber:Int) : Int {
    if (imageNumber>0 && imageNumber<= imageResourcesArray.size) {
        return imageResourcesArray[imageNumber - 1]
    } else {
        return imageResourcesArray[0]
    }
}


fun getImageTitleResourceByNumber(imageNumber:Int) : Int {
    if (imageNumber>0 && imageNumber<= imageTitleResourceArray.size) {
        return imageTitleResourceArray[imageNumber - 1]
    } else {
        return imageTitleResourceArray[0]
    }
}

fun goToNextImageNumber(imageNumber: Int): Int {
    if (imageNumber>0 && imageNumber < imageResourcesArray.size
        && imageNumber < imageTitleResourceArray.size) {
        return imageNumber + 1
    } else {
        return 1
    }
}

fun goToPrevImageNumber(imageNumber: Int): Int {
    if (imageNumber>1 && imageNumber <=imageResourcesArray.size
        && imageNumber <= imageTitleResourceArray.size) {
        return  imageNumber - 1
    } else {
        return imageTitleResourceArray.size
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChallengeImageGalleryAppTheme {
        GalleryApp()
    }
}