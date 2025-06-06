package com.thomas200593.mdm.core.ui.component.text_field

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.thomas200593.mdm.R
import com.thomas200593.mdm.core.design_system.util.Constants
import com.thomas200593.mdm.core.ui.component.text.UiText

@Composable fun SearchToolBar(
    query : String, onQueryChanged : (String) -> Unit, onSearchTriggered : (String) -> Unit,
    modifier : Modifier, placeholder : @Composable () -> Unit = { UiText(stringResource(R.string.str_search)) }
) = Row (
    modifier = modifier,
    horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
    content = { SearchTextField(
        query = query, placeholder = placeholder,
        onQueryChanged = onQueryChanged, onSearchTriggered = onSearchTriggered,
    ) }
)
@OptIn(ExperimentalMaterial3Api::class) @Composable private fun SearchTextField(
    query : String, placeholder: @Composable () -> Unit,
    onQueryChanged: (String) -> Unit, onSearchTriggered:(String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val onSearchExplicitlyTriggered = { keyboardController?.hide() ; onSearchTriggered(query) }
    TextField(
        value = query,
        onValueChange = { if(Constants.STR_NEW_LINE !in it) onQueryChanged(it) },
        colors = TextFieldDefaults.colors().copy(focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent, disabledIndicatorColor = Color.Transparent),
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
        trailingIcon = { if(query.isNotEmpty()) IconButton (
            onClick = { onQueryChanged(Constants.STR_EMPTY) }, content = { Icon(
                imageVector = Icons.Default.Close, contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            ) }
        ) },
        modifier = Modifier.fillMaxWidth().padding(Constants.Dimens.dp8).focusRequester(focusRequester = focusRequester)
            .onKeyEvent {
                if (it.key == Key.Enter) { onSearchExplicitlyTriggered(); true }
                else { false }
            },
        shape = MaterialTheme.shapes.medium,
        keyboardOptions = KeyboardOptions (imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions (onSearch = { onSearchExplicitlyTriggered() } ),
        minLines = 1, maxLines = 1, singleLine = true, placeholder = placeholder
    )
    LaunchedEffect (Unit) { focusRequester.requestFocus() }
}