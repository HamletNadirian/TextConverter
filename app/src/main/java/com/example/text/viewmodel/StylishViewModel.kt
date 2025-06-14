package com.example.text.viewmodel


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update



data class StylishUiState(
    val inputText: String = "",
    val resultText: String = "",
    val selectedStyle: Int = 0
)

class StylishViewModel : ViewModel() {
    private val normal = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    private val fancyStyles = listOf(
        // Стиль 1: Cool Font
        listOf(
            "𝓐", "𝓑", "𝓒", "𝓓", "𝓔", "𝓕", "𝓖", "𝓗", "𝓘", "𝓙", "𝓚", "𝓛", "𝓜", "𝓝", "𝓞", "𝓟", "𝓠", "𝓡", "𝓢", "𝓣", "𝓤", "𝓥", "𝓦", "𝓧", "𝓨", "𝓩",
            "𝓪", "𝓫", "𝓬", "𝓭", "𝓮", "𝓯", "𝓰", "𝓱", "𝓲", "𝓳", "𝓴", "𝓵", "𝓶", "𝓷", "𝓸", "𝓹", "𝓺", "𝓻", "𝓼", "𝓽", "𝓾", "𝓿", "𝔀", "𝔁", "𝔂", "𝔃"
        ),
        // Стиль 2: Gothic
        listOf(
            "𝔸", "𝔹", "ℂ", "𝔻", "𝔼", "𝔽", "𝔾", "ℍ", "𝕀", "𝕁", "𝕂", "𝕃", "𝕄", "𝕅", "𝕆", "𝕇", "𝕈", "𝕉", "𝕊", "𝕋", "𝕌", "𝕍", "𝕎", "𝕏", "𝕐", "𝕑",
            "𝕒", "𝕓", "𝕔", "𝕕", "𝕖", "𝕗", "𝕘", "𝕙", "𝕚", "𝕛", "𝕜", "𝕝", "𝕞", "𝕟", "𝕠", "𝕡", "𝕢", "𝕣", "𝕤", "𝕥", "𝕦", "𝕧", "𝕨", "𝕩", "𝕪", "𝕫"
        ),
        // Стиль 3: Bold
        listOf(
            "𝐀", "𝐁", "𝐂", "𝐃", "𝐄", "𝐅", "𝐆", "𝐇", "𝐈", "𝐉", "𝐊", "𝐋", "𝐌", "𝐍", "𝐎", "𝐏", "𝐐", "𝐑", "𝐒", "𝐓", "𝐔", "𝐕", "𝐖", "𝐗", "𝐘", "𝐙",
            "𝐚", "𝐛", "𝐜", "𝐝", "𝐞", "𝐟", "𝐠", "𝐡", "𝐢", "𝐣", "𝐤", "𝐥", "𝐦", "𝐧", "𝐨", "𝐩", "𝐪", "𝐫", "𝐬", "𝐭", "𝐮", "𝐯", "𝐰", "𝐱", "𝐲", "𝐳"
        ),
        listOf(
            "𝔄","𝔅","ℭ","𝔇","𝔈","𝔉","𝔊","ℌ","ℑ","𝔍","𝔎","𝔏","𝔐","𝔑","𝔒","𝔓","𝔔","ℜ","𝔖","𝔗","𝔘","𝔙","𝔚","𝔛","𝔜","ℨ","𝔞","𝔟","𝔠","𝔡","𝔢","𝔣",
            "𝔤","𝔥","𝔦","𝔧","𝔨","𝔩","𝔪","𝔫","𝔬","𝔭","𝔮","𝔯","𝔰","𝔱","𝔲","𝔳","𝔴","𝔵","𝔶","𝔷"
        )
    )

    private val _uiState = MutableStateFlow(StylishUiState())
    val uiState: StateFlow<StylishUiState> = _uiState

    fun changeFont(inputText: String) {
        val fancyText = toFancyUnicode(inputText, _uiState.value.selectedStyle)
        _uiState.update { it.copy(inputText = inputText, resultText = fancyText) }
    }

    fun changeStyle(styleIndex: Int) {
        val validIndex = styleIndex.coerceIn(0, fancyStyles.size - 1)
        _uiState.update {
            it.copy(
                selectedStyle = validIndex,
                resultText = toFancyUnicode(it.inputText, validIndex)
            )
        }
    }

    fun toFancyUnicode(input: String, style: Int): String {
        val selectedStyle = fancyStyles.getOrElse(style) { fancyStyles[0] }
        val fancyCodePoints = selectedStyle.map { it.codePointAt(0) }.toIntArray()
        val map = normal.mapIndexed { index, char -> char to fancyCodePoints.getOrNull(index) }.filterNotNull().toMap()
        return buildString {
            input.forEach { char ->
                val codePoint = map[char]
                if (codePoint != null) {
                    appendCodePoint(codePoint)
                } else {
                    append(char)
                }
            }
        }
    }
}