package top.topsea.calculator_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import top.topsea.calculator_compose.ui.theme.Calculator_ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Calculator_ComposeTheme {
                // set status bar background color and text color
                val systemUiController = rememberSystemUiController()
                val darkIcons = MaterialTheme.colors.isLight
                SideEffect {
                    systemUiController.setSystemBarsColor(Color.White, darkIcons = darkIcons)
                }

                val formula = remember { mutableStateOf("") }
                val onError = remember { mutableStateOf(false) }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = stringResource(id = R.string.app_name)) },
                            backgroundColor = Color.White
                        )
                    }
                ) {

                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.fillMaxSize()
                    ) {

                        SelectableText(
                            formula = formula,
                            onError = onError
                        )

                        Divider(modifier = Modifier.fillMaxWidth())

                        NumPad(
                            formula = formula,
                            onError = onError
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NumPad(
    modifier: Modifier = Modifier,
    formula: MutableState<String>,
    onError: MutableState<Boolean>,
) {
    val expend = remember { mutableStateOf(false) }

    val onClick: (value : Any) -> Unit = { value: Any ->
        when (value) {
            "=" -> {
                try {
                    val finalFormula = formula.value.replace("√", "sqrt")

                    val e: Expression = ExpressionBuilder(finalFormula)
                        .function(ln)
                        .function(lg)
                        .operator(factorial)
                        .build()
                    formula.value = e.evaluate().toString()
                } catch (e: Exception) {
                    onError.value = true
                    formula.value = "Error!"
                }
            }
            "C" -> {
                formula.value = ""
            }
            else -> {
                if (onError.value) {
                    formula.value = "$value"
                    onError.value = false
                } else {
                    if (formula.value.length < 36) {
                        formula.value += value
                    }
                }
            }
        }
    }
    val onClickAdvanced: (value : Any) -> Unit = { value: Any ->
        when (value) {
            R.drawable.ic_backspace -> {
                val last = formula.value.last().toString()
                formula.value = formula.value.removeSuffix(last)
            }
            R.drawable.ic_more -> {
                expend.value = !expend.value
            }
            else -> {
                if (formula.value.length < 36) {
                    formula.value += value
                }
            }
        }
    }

    Row(modifier = modifier.fillMaxWidth()) {
        if (expend.value) {
            NumPadExpend(onClick, onClickAdvanced)
        } else {
            NumPad(onClick, onClickAdvanced)
        }
    }
}
