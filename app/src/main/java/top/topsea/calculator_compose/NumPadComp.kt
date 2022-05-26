package top.topsea.calculator_compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalDensity

@Composable
fun SelectableText(
    modifier: Modifier = Modifier,
    formula: MutableState<String>,
    onError: MutableState<Boolean>,
) {
    SelectionContainer {
        TextField(
            value = formula.value,
            onValueChange = { },
            maxLines = 3,
            textStyle = TextStyle(
                color = if (onError.value) { Color.Red } else { Color.Black },
                fontSize = 35.sp,
                textAlign = TextAlign.End,
            ),
            readOnly = true,
            isError = onError.value,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = false,
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.None
            ),
            modifier = modifier
                .padding(4.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun NumPad(
    onClick: (value: Any) -> Unit = {},
    onClickAdvanced: (value: Any) -> Unit = {},
) {
    val context = LocalContext.current
    val width = context.resources.configuration.screenWidthDp
    val requireSize = width / 4

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            SingleBoard(requireSize = requireSize, char = "C", onClick = onClick)
            SingleBoard(
                requireSize = requireSize,
                i = R.drawable.ic_backspace,
                onClick = onClickAdvanced
            )
            SingleBoard(requireSize = requireSize, char = "%", onClick = onClick)
            SingleBoard(requireSize = requireSize, char = "/", onClick = onClick)
        }
        Row(Modifier.fillMaxWidth()) {
            Column(modifier = Modifier) {
                for (i in 2 downTo 0) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                    ) {
                        for (j in 1..3) {
                            SingleBoard(requireSize = requireSize, i = i * 3 + j, onClick = onClick)
                        }
                    }
                }
            }
            Column(modifier = Modifier) {
                SingleBoard(requireSize = requireSize, char = "×", onClick = onClick)
                SingleBoard(requireSize = requireSize, char = "-", onClick = onClick)
                SingleBoard(requireSize = requireSize, char = "+", onClick = onClick)
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            SingleBoard(
                requireSize = requireSize,
                i = R.drawable.ic_more,
                onClick = onClickAdvanced
            )
            SingleBoard(requireSize = requireSize, i = 0, onClick = onClick)
            SingleBoard(requireSize = requireSize, char = ".", onClick = onClick)
            SingleBoard(requireSize = requireSize, char = "=", onClick = onClick)
        }
    }
}

@Composable
fun NumPadExpend(
    onClick: (value: Any) -> Unit = {},
    onClickAdvanced: (value: Any) -> Unit = {},
) {
    val context = LocalContext.current
    val width = context.resources.configuration.screenWidthDp
    val requireSize = width / 5

    val arc = remember { mutableStateOf(false) }
    val arcClick: (value: Any) -> Unit = {
        arc.value = !arc.value
    }

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            SingleBoard(requireSize = requireSize, char = "Xⁿ", onClick = { onClick("^") })
            SingleBoard(requireSize = requireSize, char = "arc", onClick = arcClick)
            SingleBoard(
                requireSize = requireSize,
                char = if (arc.value) { "asin" } else { "sin" },
                onClick = { onClick(if (arc.value) { "asin(" } else { "sin(" }) }
            )
            SingleBoard(
                requireSize = requireSize,
                char = if (arc.value) { "acos" } else { "cos" },
                onClick = { onClick(if (arc.value) { "acos(" } else { "cos(" }) }
            )
            SingleBoard(
                requireSize = requireSize,
                char = if (arc.value) { "atan" } else { "tan" },
                onClick = { onClick(if (arc.value) { "atan(" } else { "tan(" }) }
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            SingleBoard(requireSize = requireSize, char = "log", onClick = { onClick("log(") })
            SingleBoard(requireSize = requireSize, char = "lg", onClick = { onClick("lg(") })
            SingleBoard(requireSize = requireSize, char = "ln", onClick = { onClick("ln(") })
            SingleBoard(requireSize = requireSize, char = "(", onClick = onClick)
            SingleBoard(requireSize = requireSize, char = ")", onClick = onClick)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            SingleBoard(requireSize = requireSize, char = "√", onClick = { onClick("√(") })
            SingleBoard(requireSize = requireSize, char = "AC", onClick = { onClick("C") })
            SingleBoard(
                requireSize = requireSize,
                i = R.drawable.ic_backspace,
                onClick = onClickAdvanced
            )
            SingleBoard(requireSize = requireSize, char = "%", onClick = onClick)
            SingleBoard(requireSize = requireSize, char = "/", onClick = onClick)
        }
        Row(Modifier.fillMaxWidth()) {
            Column(modifier = Modifier) {
                SingleBoard(requireSize = requireSize, char = "X!", onClick = { onClick("!") })
                SingleBoard(requireSize = requireSize, char = "⅟x", onClick = { onClick("1/(") })
                SingleBoard(requireSize = requireSize, char = "π", onClick = onClick)
            }
            Column(modifier = Modifier) {
                for (i in 2 downTo 0) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                    ) {
                        for (j in 1..3) {
                            SingleBoard(requireSize = requireSize, i = i * 3 + j, onClick = onClick)
                        }
                    }
                }
            }
            Column(modifier = Modifier) {
                SingleBoard(requireSize = requireSize, char = "×", onClick = onClick)
                SingleBoard(requireSize = requireSize, char = "-", onClick = onClick)
                SingleBoard(requireSize = requireSize, char = "+", onClick = onClick)
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            SingleBoard(
                requireSize = requireSize,
                i = R.drawable.ic_more,
                onClick = onClickAdvanced
            )
            SingleBoard(requireSize = requireSize, char = "e", onClick = onClick)
            SingleBoard(requireSize = requireSize, i = 0, onClick = onClick)
            SingleBoard(requireSize = requireSize, char = ".", onClick = onClick)
            SingleBoard(requireSize = requireSize, char = "=", onClick = onClick)
        }
    }
}

@Composable
fun SingleBoard(
    i: Int,
    requireSize: Int,
    onClick: (value: Any) -> Unit = {}
) {
    val textSize = with(LocalDensity.current) { requireSize.toSp() }
    val padding = with(LocalDensity.current) { requireSize.toDp() }

    val color = if (i in 0 until 10) {
        Color.Black
    } else {
        colorResource(id = R.color.numpad_orange)
    }
    Card(
        modifier = Modifier
            .size(requireSize.dp)
            .padding(padding / 12),
        elevation = 3.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClick(i)
                }
        ) {
            if (i in 0 until 10) {
                Text(
                    text = i.toString(),
                    color = color,
                    fontSize = textSize,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Icon(
                    painter = painterResource(i),
                    contentDescription = "",
                    tint = color
                )
            }
        }
    }
}

@Composable
fun SingleBoard(
    char: String,
    requireSize: Int,
    onClick: (value: Any) -> Unit = {}
) {
    val textSize = with(LocalDensity.current) { requireSize.toSp() }
    val padding = with(LocalDensity.current) { requireSize.toDp() }

    val backColor = if (char == "=") {
        colorResource(id = R.color.numpad_orange)
    } else {
        Color.White
    }

    val color = when (char) {
        "=" -> {
            Color.White
        }
        "+", "-", "/", "×", "C", "%", "AC", "." -> {
            colorResource(id = R.color.numpad_orange)
        }
        "e", "π" -> {
            Color.Black
        }
        else -> {
            Color.Gray
        }
    }

    Card(
        modifier = Modifier
            .size(requireSize.dp)
            .padding(padding / 12),
        elevation = 3.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClick(char)
                }
                .background(
                    color = backColor,
                )
        ) {
            Text(
                text = char,
                color = color,
                fontSize = textSize / 1.2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
