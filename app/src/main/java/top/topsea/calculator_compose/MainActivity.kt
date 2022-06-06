package top.topsea.calculator_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
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

                val formulaComponent = remember { mutableStateListOf("") }
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
                            formulaComponent = formulaComponent,
                            onError = onError
                        )

                        Divider(modifier = Modifier.fillMaxWidth())

                        NumPad(
                            formulaComponent = formulaComponent,
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
    formulaComponent: SnapshotStateList<String>,
    onError: MutableState<Boolean>,
) {
    val expend = remember { mutableStateOf(false) }

    val onClick: (value : Any) -> Unit = { value: Any ->
        when (value) {
            "=" -> {
                try {
                    val finalFormula = getFormula(formulaComponent).replace("√", "sqrt")
                        .replace("×", "*")

                    val e: Expression = ExpressionBuilder(finalFormula)
                        .function(ln)
                        .function(lg)
                        .operator(factorial)
                        .build()
                    val result = e.evaluate().toString()
                    if (result == "NaN") {
                        onError.value = true
                    }
                    formulaComponent.clear()
                    formulaComponent.add(result)
                } catch (e: Exception) {
                    onError.value = true
                    formulaComponent.clear()
                    formulaComponent.add("Error!")
                }
            }
            "C" -> {
                formulaComponent.clear()
            }
            else -> {
                if (onError.value) {
                    formulaComponent.clear()
                    formulaComponent.add("$value")
                    onError.value = false
                } else {
                    if (getFormula(formulaComponent).length < 36) {
                        formulaComponent.add("$value")
                    }
                }
            }
        }
    }
    val onClickAdvanced: (value : Any) -> Unit = { value: Any ->
        when (value) {
            R.drawable.ic_backspace -> {
                formulaComponent.removeLast()
            }
            R.drawable.ic_more -> {
                expend.value = !expend.value
            }
            else -> {
                if (getFormula(formulaComponent).length < 36) {
                    formulaComponent.add("$value")
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
