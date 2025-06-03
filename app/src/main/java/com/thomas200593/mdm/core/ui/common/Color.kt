package com.thomas200593.mdm.core.ui.common

import android.graphics.Color.argb
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object Color {
    object BootstrapV5Base {
        /**
         * Blue
         */
        val Blue = Color(0xFF0D6EFD)
        val Blue100 = Color(0xFFCCE5FF)
        val Blue200 = Color(0xFF99CCFF)
        val Blue300 = Color(0xFF66B2FF)
        val Blue400 = Color(0xFF3399FF)
        val Blue500 = Color(0xFF007BFF)
        val Blue600 = Color(0xFF0069D9)
        val Blue700 = Color(0xFF0056B3)
        val Blue800 = Color(0xFF004085)
        val Blue900 = Color(0xFF002752)
        /**
         * Indigo
         */
        val Indigo = Color(0xFF6610F2)
        val Indigo100 = Color(0xFFE0CFFC)
        val Indigo200 = Color(0xFFC29FFA)
        val Indigo300 = Color(0xFFA370F7)
        val Indigo400 = Color(0xFF8540F5)
        val Indigo500 = Color(0xFF6610F2)
        val Indigo600 = Color(0xFF520DC2)
        val Indigo700 = Color(0xFF3D0A91)
        val Indigo800 = Color(0xFF290661)
        val Indigo900 = Color(0xFF140330)
        /**
         * Purple
         */
        val Purple = Color(0xFF6F42C1)
        val Purple100 = Color(0xFFE2D9F3)
        val Purple200 = Color(0xFFC5B3E6)
        val Purple300 = Color(0xFFA98EDA)
        val Purple400 = Color(0xFF8C68CD)
        val Purple500 = Color(0xFF6F42C1)
        val Purple600 = Color(0xFF59359A)
        val Purple700 = Color(0xFF432874)
        val Purple800 = Color(0xFF2C1A4D)
        val Purple900 = Color(0xFF160D27)
        /**
         * Pink
         */
        val Pink = Color(0xFFD63384)
        val Pink100 = Color(0xFFF7D6E6)
        val Pink200 = Color(0xFFEFADCD)
        val Pink300 = Color(0xFFE685B5)
        val Pink400 = Color(0xFFDE5C9C)
        val Pink500 = Color(0xFFD63384)
        val Pink600 = Color(0xFFAB296A)
        val Pink700 = Color(0xFF801F4F)
        val Pink800 = Color(0xFF561435)
        val Pink900 = Color(0xFF2B0A1A)
        /**
         * Red
         */
        val Red = Color(0xFFDC3545)
        val Red100 = Color(0xFFF8D7DA)
        val Red200 = Color(0xFFF1AEB5)
        val Red300 = Color(0xFFEA868F)
        val Red400 = Color(0xFFE35D6A)
        val Red500 = Color(0xFFDC3545)
        val Red600 = Color(0xFFB02A37)
        val Red700 = Color(0xFF842029)
        val Red800 = Color(0xFF58151C)
        val Red900 = Color(0xFF2C0B0E)
        /**
         * Orange
         */
        val Orange = Color(0xFFFD7E14)
        val Orange100 = Color(0xFFFFE5D0)
        val Orange200 = Color(0xFFFFCBA1)
        val Orange300 = Color(0xFFFFB072)
        val Orange400 = Color(0xFFFF9643)
        val Orange500 = Color(0xFFFD7E14)
        val Orange600 = Color(0xFFCA6510)
        val Orange700 = Color(0xFF984C0C)
        val Orange800 = Color(0xFF653208)
        val Orange900 = Color(0xFF331904)
        /**
         * Yellow
         */
        val Yellow = Color(0xFFFFC107)
        val Yellow100 = Color(0xFFFFF3CD)
        val Yellow200 = Color(0xFFFFE69C)
        val Yellow300 = Color(0xFFFFDA6B)
        val Yellow400 = Color(0xFFFFCD3A)
        val Yellow500 = Color(0xFFFFC107)
        val Yellow600 = Color(0xFFCC9A06)
        val Yellow700 = Color(0xFF997404)
        val Yellow800 = Color(0xFF664D03)
        val Yellow900 = Color(0xFF332701)
        /**
         * Green
         */
        val Green = Color(0xFF198754)
        val Green100 = Color(0xFFD1E7DD)
        val Green200 = Color(0xFFA3CFBB)
        val Green300 = Color(0xFF75B798)
        val Green400 = Color(0xFF479F76)
        val Green500 = Color(0xFF198754)
        val Green600 = Color(0xFF146C43)
        val Green700 = Color(0xFF0F5132)
        val Green800 = Color(0xFF0A3622)
        val Green900 = Color(0xFF051B11)
        /**
         * Teal
         */
        val Teal = Color(0xFF20C997)
        val Teal100 = Color(0xFFD2F4EA)
        val Teal200 = Color(0xFFA6E9D5)
        val Teal300 = Color(0xFF79DFC1)
        val Teal400 = Color(0xFF4DD4AC)
        val Teal500 = Color(0xFF20C997)
        val Teal600 = Color(0xFF1AA179)
        val Teal700 = Color(0xFF13795B)
        val Teal800 = Color(0xFF0D503C)
        val Teal900 = Color(0xFF06281E)
        /**
         * Cyan
         */
        val Cyan = Color(0xFF0DCAF0)
        val Cyan100 = Color(0xFFCFF4FC)
        val Cyan200 = Color(0xFF9EEAF9)
        val Cyan300 = Color(0xFF6EDFF6)
        val Cyan400 = Color(0xFF3DD5F3)
        val Cyan500 = Color(0xFF0DCAF0)
        val Cyan600 = Color(0xFF0AA2C0)
        val Cyan700 = Color(0xFF087990)
        val Cyan800 = Color(0xFF055160)
        val Cyan900 = Color(0xFF032830)
        /**
         * Gray (Neutral grayscale used for text, background, borders)
         */
        val Gray100 = Color(0xFFF8F9FA)
        val Gray200 = Color(0xFFE9ECEF)
        val Gray300 = Color(0xFFDEE2E6)
        val Gray400 = Color(0xFFCED4DA)
        val Gray500 = Color(0xFFADB5BD)
        val Gray600 = Color(0xFF6C757D)
        val Gray700 = Color(0xFF495057)
        val Gray800 = Color(0xFF343A40)
        val Gray900 = Color(0xFF212529)
        /**
         * Black & White
         */
        val Black = Color(0xFF000000)
        val White = Color(0xFFFFFFFF)
    }
    object Light {
        val scrimARGB = argb(0xe6, 0xFF, 0xFF, 0xFF)
        object ContrastDefault {
            private val primaryLight = Color(0xFF0D6680)
            private val onPrimaryLight = Color(0xFFFFFFFF)
            private val primaryContainerLight = Color(0xFFBAEAFF)
            private val onPrimaryContainerLight = Color(0xFF004D62)
            private val secondaryLight = Color(0xFF4C616B)
            private val onSecondaryLight = Color(0xFFFFFFFF)
            private val secondaryContainerLight = Color(0xFFCFE6F1)
            private val onSecondaryContainerLight = Color(0xFF354A53)
            private val tertiaryLight = Color(0xFF5C5B7E)
            private val onTertiaryLight = Color(0xFFFFFFFF)
            private val tertiaryContainerLight = Color(0xFFE2DFFF)
            private val onTertiaryContainerLight = Color(0xFF444465)
            private val errorLight = Color(0xFFBA1A1A)
            private val onErrorLight = Color(0xFFFFFFFF)
            private val errorContainerLight = Color(0xFFFFDAD6)
            private val onErrorContainerLight = Color(0xFF93000A)
            private val backgroundLight = Color(0xFFF5FAFD)
            private val onBackgroundLight = Color(0xFF171C1F)
            private val surfaceLight = Color(0xFFF5FAFD)
            private val onSurfaceLight = Color(0xFF171C1F)
            private val surfaceVariantLight = Color(0xFFDCE4E8)
            private val onSurfaceVariantLight = Color(0xFF40484C)
            private val outlineLight = Color(0xFF70787D)
            private val outlineVariantLight = Color(0xFFC0C8CC)
            private val scrimLight = Color(0xFF000000)
            private val inverseSurfaceLight = Color(0xFF2C3134)
            private val inverseOnSurfaceLight = Color(0xFFEDF1F5)
            private val inversePrimaryLight = Color(0xFF89D0EE)
            private val surfaceDimLight = Color(0xFFD6DBDE)
            private val surfaceBrightLight = Color(0xFFF5FAFD)
            private val surfaceContainerLowestLight = Color(0xFFFFFFFF)
            private val surfaceContainerLowLight = Color(0xFFF0F4F7)
            private val surfaceContainerLight = Color(0xFFEAEEF2)
            private val surfaceContainerHighLight = Color(0xFFE4E9EC)
            private val surfaceContainerHighestLight = Color(0xFFDEE3E6)
            val colorScheme = lightColorScheme(
                primary = primaryLight,
                onPrimary = onPrimaryLight,
                primaryContainer = primaryContainerLight,
                onPrimaryContainer = onPrimaryContainerLight,
                inversePrimary = inversePrimaryLight,
                secondary = secondaryLight,
                onSecondary = onSecondaryLight,
                secondaryContainer = secondaryContainerLight,
                onSecondaryContainer = onSecondaryContainerLight,
                tertiary = tertiaryLight,
                onTertiary = onTertiaryLight,
                tertiaryContainer = tertiaryContainerLight,
                onTertiaryContainer = onTertiaryContainerLight,
                background = backgroundLight,
                onBackground = onBackgroundLight,
                surface = surfaceLight,
                onSurface = onSurfaceLight,
                surfaceVariant = surfaceVariantLight,
                onSurfaceVariant = onSurfaceVariantLight,
                surfaceTint = Color(scrimARGB),
                inverseSurface = inverseSurfaceLight,
                inverseOnSurface = inverseOnSurfaceLight,
                error = errorLight,
                onError = onErrorLight,
                errorContainer = errorContainerLight,
                onErrorContainer = onErrorContainerLight,
                outline = outlineLight,
                outlineVariant = outlineVariantLight,
                scrim = scrimLight,
                surfaceBright = surfaceBrightLight,
                surfaceContainer = surfaceContainerLight,
                surfaceContainerHigh = surfaceContainerHighLight,
                surfaceContainerHighest = surfaceContainerHighestLight,
                surfaceContainerLow = surfaceContainerLowLight,
                surfaceContainerLowest = surfaceContainerLowestLight,
                surfaceDim = surfaceDimLight
            )
        }
        object ContrastMedium {
            private val primaryLightMediumContrast = Color(0xFF003B4C)
            private val onPrimaryLightMediumContrast = Color(0xFFFFFFFF)
            private val primaryContainerLightMediumContrast = Color(0xFF277590)
            private val onPrimaryContainerLightMediumContrast = Color(0xFFFFFFFF)
            private val secondaryLightMediumContrast = Color(0xFF243942)
            private val onSecondaryLightMediumContrast = Color(0xFFFFFFFF)
            private val secondaryContainerLightMediumContrast = Color(0xFF5B707A)
            private val onSecondaryContainerLightMediumContrast = Color(0xFFFFFFFF)
            private val tertiaryLightMediumContrast = Color(0xFF333353)
            private val onTertiaryLightMediumContrast = Color(0xFFFFFFFF)
            private val tertiaryContainerLightMediumContrast = Color(0xFF6A6A8D)
            private val onTertiaryContainerLightMediumContrast = Color(0xFFFFFFFF)
            private val errorLightMediumContrast = Color(0xFF740006)
            private val onErrorLightMediumContrast = Color(0xFFFFFFFF)
            private val errorContainerLightMediumContrast = Color(0xFFCF2C27)
            private val onErrorContainerLightMediumContrast = Color(0xFFFFFFFF)
            private val backgroundLightMediumContrast = Color(0xFFF5FAFD)
            private val onBackgroundLightMediumContrast = Color(0xFF171C1F)
            private val surfaceLightMediumContrast = Color(0xFFF5FAFD)
            private val onSurfaceLightMediumContrast = Color(0xFF0D1214)
            private val surfaceVariantLightMediumContrast = Color(0xFFDCE4E8)
            private val onSurfaceVariantLightMediumContrast = Color(0xFF2F373B)
            private val outlineLightMediumContrast = Color(0xFF4C5458)
            private val outlineVariantLightMediumContrast = Color(0xFF666E73)
            private val scrimLightMediumContrast = Color(0xFF000000)
            private val inverseSurfaceLightMediumContrast = Color(0xFF2C3134)
            private val inverseOnSurfaceLightMediumContrast = Color(0xFFEDF1F5)
            private val inversePrimaryLightMediumContrast = Color(0xFF89D0EE)
            private val surfaceDimLightMediumContrast = Color(0xFFC2C7CA)
            private val surfaceBrightLightMediumContrast = Color(0xFFF5FAFD)
            private val surfaceContainerLowestLightMediumContrast = Color(0xFFFFFFFF)
            private val surfaceContainerLowLightMediumContrast = Color(0xFFF0F4F7)
            private val surfaceContainerLightMediumContrast = Color(0xFFE4E9EC)
            private val surfaceContainerHighLightMediumContrast = Color(0xFFD9DDE1)
            private val surfaceContainerHighestLightMediumContrast = Color(0xFFCDD2D5)
            val colorScheme = lightColorScheme(
                primary = primaryLightMediumContrast,
                onPrimary = onPrimaryLightMediumContrast,
                primaryContainer = primaryContainerLightMediumContrast,
                onPrimaryContainer = onPrimaryContainerLightMediumContrast,
                inversePrimary = inversePrimaryLightMediumContrast,
                secondary = secondaryLightMediumContrast,
                onSecondary = onSecondaryLightMediumContrast,
                secondaryContainer = secondaryContainerLightMediumContrast,
                onSecondaryContainer = onSecondaryContainerLightMediumContrast,
                tertiary = tertiaryLightMediumContrast,
                onTertiary = onTertiaryLightMediumContrast,
                tertiaryContainer = tertiaryContainerLightMediumContrast,
                onTertiaryContainer = onTertiaryContainerLightMediumContrast,
                background = backgroundLightMediumContrast,
                onBackground = onBackgroundLightMediumContrast,
                surface = surfaceLightMediumContrast,
                onSurface = onSurfaceLightMediumContrast,
                surfaceVariant = surfaceVariantLightMediumContrast,
                onSurfaceVariant = onSurfaceVariantLightMediumContrast,
                surfaceTint = Color(scrimARGB),
                inverseSurface = inverseSurfaceLightMediumContrast,
                inverseOnSurface = inverseOnSurfaceLightMediumContrast,
                error = errorLightMediumContrast,
                onError = onErrorLightMediumContrast,
                errorContainer = errorContainerLightMediumContrast,
                onErrorContainer = onErrorContainerLightMediumContrast,
                outline = outlineLightMediumContrast,
                outlineVariant = outlineVariantLightMediumContrast,
                scrim = scrimLightMediumContrast,
                surfaceBright = surfaceBrightLightMediumContrast,
                surfaceContainer = surfaceContainerLightMediumContrast,
                surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
                surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
                surfaceContainerLow = surfaceContainerLowLightMediumContrast,
                surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
                surfaceDim = surfaceDimLightMediumContrast
            )
        }
        object ContrastHigh {
            private val primaryLightHighContrast = Color(0xFF00313F)
            private val onPrimaryLightHighContrast = Color(0xFFFFFFFF)
            private val primaryContainerLightHighContrast = Color(0xFF005066)
            private val onPrimaryContainerLightHighContrast = Color(0xFFFFFFFF)
            private val secondaryLightHighContrast = Color(0xFF1A2F38)
            private val onSecondaryLightHighContrast = Color(0xFFFFFFFF)
            private val secondaryContainerLightHighContrast = Color(0xFF374C55)
            private val onSecondaryContainerLightHighContrast = Color(0xFFFFFFFF)
            private val tertiaryLightHighContrast = Color(0xFF292948)
            private val onTertiaryLightHighContrast = Color(0xFFFFFFFF)
            private val tertiaryContainerLightHighContrast = Color(0xFF464667)
            private val onTertiaryContainerLightHighContrast = Color(0xFFFFFFFF)
            private val errorLightHighContrast = Color(0xFF600004)
            private val onErrorLightHighContrast = Color(0xFFFFFFFF)
            private val errorContainerLightHighContrast = Color(0xFF98000A)
            private val onErrorContainerLightHighContrast = Color(0xFFFFFFFF)
            private val backgroundLightHighContrast = Color(0xFFF5FAFD)
            private val onBackgroundLightHighContrast = Color(0xFF171C1F)
            private val surfaceLightHighContrast = Color(0xFFF5FAFD)
            private val onSurfaceLightHighContrast = Color(0xFF000000)
            private val surfaceVariantLightHighContrast = Color(0xFFDCE4E8)
            private val onSurfaceVariantLightHighContrast = Color(0xFF000000)
            private val outlineLightHighContrast = Color(0xFF252D31)
            private val outlineVariantLightHighContrast = Color(0xFF424A4E)
            private val scrimLightHighContrast = Color(0xFF000000)
            private val inverseSurfaceLightHighContrast = Color(0xFF2C3134)
            private val inverseOnSurfaceLightHighContrast = Color(0xFFFFFFFF)
            private val inversePrimaryLightHighContrast = Color(0xFF89D0EE)
            private val surfaceDimLightHighContrast = Color(0xFFB4B9BD)
            private val surfaceBrightLightHighContrast = Color(0xFFF5FAFD)
            private val surfaceContainerLowestLightHighContrast = Color(0xFFFFFFFF)
            private val surfaceContainerLowLightHighContrast = Color(0xFFEDF1F5)
            private val surfaceContainerLightHighContrast = Color(0xFFDEE3E6)
            private val surfaceContainerHighLightHighContrast = Color(0xFFD0D5D8)
            private val surfaceContainerHighestLightHighContrast = Color(0xFFC2C7CA)
            val colorScheme = lightColorScheme(
                primary = primaryLightHighContrast,
                onPrimary = onPrimaryLightHighContrast,
                primaryContainer = primaryContainerLightHighContrast,
                onPrimaryContainer = onPrimaryContainerLightHighContrast,
                inversePrimary = inversePrimaryLightHighContrast,
                secondary = secondaryLightHighContrast,
                onSecondary = onSecondaryLightHighContrast,
                secondaryContainer = secondaryContainerLightHighContrast,
                onSecondaryContainer = onSecondaryContainerLightHighContrast,
                tertiary = tertiaryLightHighContrast,
                onTertiary = onTertiaryLightHighContrast,
                tertiaryContainer = tertiaryContainerLightHighContrast,
                onTertiaryContainer = onTertiaryContainerLightHighContrast,
                background = backgroundLightHighContrast,
                onBackground = onBackgroundLightHighContrast,
                surface = surfaceLightHighContrast,
                onSurface = onSurfaceLightHighContrast,
                surfaceVariant = surfaceVariantLightHighContrast,
                onSurfaceVariant = onSurfaceVariantLightHighContrast,
                surfaceTint = Color(scrimARGB),
                inverseSurface = inverseSurfaceLightHighContrast,
                inverseOnSurface = inverseOnSurfaceLightHighContrast,
                error = errorLightHighContrast,
                onError = onErrorLightHighContrast,
                errorContainer = errorContainerLightHighContrast,
                onErrorContainer = onErrorContainerLightHighContrast,
                outline = outlineLightHighContrast,
                outlineVariant = outlineVariantLightHighContrast,
                scrim = scrimLightHighContrast,
                surfaceBright = surfaceBrightLightHighContrast,
                surfaceContainer = surfaceContainerLightHighContrast,
                surfaceContainerHigh = surfaceContainerHighLightHighContrast,
                surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
                surfaceContainerLow = surfaceContainerLowLightHighContrast,
                surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
                surfaceDim = surfaceDimLightHighContrast
            )
        }
    }
    object Dark {
        val scrimARGB = argb(0x80, 0x1b, 0x1b, 0x1b)
        object ContrastDefault {
            private val primaryDark = Color(0xFF89D0EE)
            private val onPrimaryDark = Color(0xFF003545)
            private val primaryContainerDark = Color(0xFF004D62)
            private val onPrimaryContainerDark = Color(0xFFBAEAFF)
            private val secondaryDark = Color(0xFFB4CAD5)
            private val onSecondaryDark = Color(0xFF1E333C)
            private val secondaryContainerDark = Color(0xFF354A53)
            private val onSecondaryContainerDark = Color(0xFFCFE6F1)
            private val tertiaryDark = Color(0xFFC5C3EA)
            private val onTertiaryDark = Color(0xFF2D2D4D)
            private val tertiaryContainerDark = Color(0xFF444465)
            private val onTertiaryContainerDark = Color(0xFFE2DFFF)
            private val errorDark = Color(0xFFFFB4AB)
            private val onErrorDark = Color(0xFF690005)
            private val errorContainerDark = Color(0xFF93000A)
            private val onErrorContainerDark = Color(0xFFFFDAD6)
            private val backgroundDark = Color(0xFF0F1417)
            private val onBackgroundDark = Color(0xFFDEE3E6)
            private val surfaceDark = Color(0xFF0F1417)
            private val onSurfaceDark = Color(0xFFDEE3E6)
            private val surfaceVariantDark = Color(0xFF40484C)
            private val onSurfaceVariantDark = Color(0xFFC0C8CC)
            private val outlineDark = Color(0xFF8A9296)
            private val outlineVariantDark = Color(0xFF40484C)
            private val scrimDark = Color(0xFF000000)
            private val inverseSurfaceDark = Color(0xFFDEE3E6)
            private val inverseOnSurfaceDark = Color(0xFF2C3134)
            private val inversePrimaryDark = Color(0xFF0D6680)
            private val surfaceDimDark = Color(0xFF0F1417)
            private val surfaceBrightDark = Color(0xFF353A3D)
            private val surfaceContainerLowestDark = Color(0xFF0A0F11)
            private val surfaceContainerLowDark = Color(0xFF171C1F)
            private val surfaceContainerDark = Color(0xFF1B2023)
            private val surfaceContainerHighDark = Color(0xFF252B2D)
            private val surfaceContainerHighestDark = Color(0xFF303638)
            val colorScheme = darkColorScheme(
                primary = primaryDark,
                onPrimary = onPrimaryDark,
                primaryContainer = primaryContainerDark,
                onPrimaryContainer = onPrimaryContainerDark,
                inversePrimary = inversePrimaryDark,
                secondary = secondaryDark,
                onSecondary = onSecondaryDark,
                secondaryContainer = secondaryContainerDark,
                onSecondaryContainer = onSecondaryContainerDark,
                tertiary = tertiaryDark,
                onTertiary = onTertiaryDark,
                tertiaryContainer = tertiaryContainerDark,
                onTertiaryContainer = onTertiaryContainerDark,
                background = backgroundDark,
                onBackground = onBackgroundDark,
                surface = surfaceDark,
                onSurface = onSurfaceDark,
                surfaceVariant = surfaceVariantDark,
                onSurfaceVariant = onSurfaceVariantDark,
                surfaceTint = Color(scrimARGB),
                inverseSurface = inverseSurfaceDark,
                inverseOnSurface = inverseOnSurfaceDark,
                error = errorDark,
                onError = onErrorDark,
                errorContainer = errorContainerDark,
                onErrorContainer = onErrorContainerDark,
                outline = outlineDark,
                outlineVariant = outlineVariantDark,
                scrim = scrimDark,
                surfaceBright = surfaceBrightDark,
                surfaceContainer = surfaceContainerDark,
                surfaceContainerHigh = surfaceContainerHighDark,
                surfaceContainerHighest = surfaceContainerHighestDark,
                surfaceContainerLow = surfaceContainerLowDark,
                surfaceContainerLowest = surfaceContainerLowestDark,
                surfaceDim = surfaceDimDark
            )
        }
        object ContrastMedium {
            private val primaryDarkMediumContrast = Color(0xFFA9E5FF)
            private val onPrimaryDarkMediumContrast = Color(0xFF002A36)
            private val primaryContainerDarkMediumContrast = Color(0xFF529AB5)
            private val onPrimaryContainerDarkMediumContrast = Color(0xFF000000)
            private val secondaryDarkMediumContrast = Color(0xFFC9E0EB)
            private val onSecondaryDarkMediumContrast = Color(0xFF132831)
            private val secondaryContainerDarkMediumContrast = Color(0xFF7E949E)
            private val onSecondaryContainerDarkMediumContrast = Color(0xFF000000)
            private val tertiaryDarkMediumContrast = Color(0xFFDBD9FF)
            private val onTertiaryDarkMediumContrast = Color(0xFF222241)
            private val tertiaryContainerDarkMediumContrast = Color(0xFF8E8DB2)
            private val onTertiaryContainerDarkMediumContrast = Color(0xFF000000)
            private val errorDarkMediumContrast = Color(0xFFFFD2CC)
            private val onErrorDarkMediumContrast = Color(0xFF540003)
            private val errorContainerDarkMediumContrast = Color(0xFFFF5449)
            private val onErrorContainerDarkMediumContrast = Color(0xFF000000)
            private val backgroundDarkMediumContrast = Color(0xFF0F1417)
            private val onBackgroundDarkMediumContrast = Color(0xFFDEE3E6)
            private val surfaceDarkMediumContrast = Color(0xFF0F1417)
            private val onSurfaceDarkMediumContrast = Color(0xFFFFFFFF)
            private val surfaceVariantDarkMediumContrast = Color(0xFF40484C)
            private val onSurfaceVariantDarkMediumContrast = Color(0xFFD5DDE2)
            private val outlineDarkMediumContrast = Color(0xFFABB3B8)
            private val outlineVariantDarkMediumContrast = Color(0xFF899196)
            private val scrimDarkMediumContrast = Color(0xFF000000)
            private val inverseSurfaceDarkMediumContrast = Color(0xFFDEE3E6)
            private val inverseOnSurfaceDarkMediumContrast = Color(0xFF262B2D)
            private val inversePrimaryDarkMediumContrast = Color(0xFF004F64)
            private val surfaceDimDarkMediumContrast = Color(0xFF0F1417)
            private val surfaceBrightDarkMediumContrast = Color(0xFF404548)
            private val surfaceContainerLowestDarkMediumContrast = Color(0xFF04080A)
            private val surfaceContainerLowDarkMediumContrast = Color(0xFF191E21)
            private val surfaceContainerDarkMediumContrast = Color(0xFF23292B)
            private val surfaceContainerHighDarkMediumContrast = Color(0xFF2E3336)
            private val surfaceContainerHighestDarkMediumContrast = Color(0xFF393E41)
            val colorScheme = darkColorScheme(
                primary = primaryDarkMediumContrast,
                onPrimary = onPrimaryDarkMediumContrast,
                primaryContainer = primaryContainerDarkMediumContrast,
                onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
                inversePrimary = inversePrimaryDarkMediumContrast,
                secondary = secondaryDarkMediumContrast,
                onSecondary = onSecondaryDarkMediumContrast,
                secondaryContainer = secondaryContainerDarkMediumContrast,
                onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
                tertiary = tertiaryDarkMediumContrast,
                onTertiary = onTertiaryDarkMediumContrast,
                tertiaryContainer = tertiaryContainerDarkMediumContrast,
                onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
                background = backgroundDarkMediumContrast,
                onBackground = onBackgroundDarkMediumContrast,
                surface = surfaceDarkMediumContrast,
                onSurface = onSurfaceDarkMediumContrast,
                surfaceVariant = surfaceVariantDarkMediumContrast,
                onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
                surfaceTint = Color(scrimARGB),
                inverseSurface = inverseSurfaceDarkMediumContrast,
                inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
                error = errorDarkMediumContrast,
                onError = onErrorDarkMediumContrast,
                errorContainer = errorContainerDarkMediumContrast,
                onErrorContainer = onErrorContainerDarkMediumContrast,
                outline = outlineDarkMediumContrast,
                outlineVariant = outlineVariantDarkMediumContrast,
                scrim = scrimDarkMediumContrast,
                surfaceBright = surfaceBrightDarkMediumContrast,
                surfaceContainer = surfaceContainerDarkMediumContrast,
                surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
                surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
                surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
                surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
                surfaceDim = surfaceDimDarkMediumContrast
            )
        }
        object ContrastHigh {
            private val primaryDarkHighContrast = Color(0xFFDDF4FF)
            private val onPrimaryDarkHighContrast = Color(0xFF000000)
            private val primaryContainerDarkHighContrast = Color(0xFF85CCEA)
            private val onPrimaryContainerDarkHighContrast = Color(0xFF000D13)
            private val secondaryDarkHighContrast = Color(0xFFDDF4FF)
            private val onSecondaryDarkHighContrast = Color(0xFF000000)
            private val secondaryContainerDarkHighContrast = Color(0xFFB0C6D1)
            private val onSecondaryContainerDarkHighContrast = Color(0xFF000D13)
            private val tertiaryDarkHighContrast = Color(0xFFF1EEFF)
            private val onTertiaryDarkHighContrast = Color(0xFF000000)
            private val tertiaryContainerDarkHighContrast = Color(0xFFC1BFE6)
            private val onTertiaryContainerDarkHighContrast = Color(0xFF080726)
            private val errorDarkHighContrast = Color(0xFFFFECE9)
            private val onErrorDarkHighContrast = Color(0xFF000000)
            private val errorContainerDarkHighContrast = Color(0xFFFFAEA4)
            private val onErrorContainerDarkHighContrast = Color(0xFF220001)
            private val backgroundDarkHighContrast = Color(0xFF0F1417)
            private val onBackgroundDarkHighContrast = Color(0xFFDEE3E6)
            private val surfaceDarkHighContrast = Color(0xFF0F1417)
            private val onSurfaceDarkHighContrast = Color(0xFFFFFFFF)
            private val surfaceVariantDarkHighContrast = Color(0xFF40484C)
            private val onSurfaceVariantDarkHighContrast = Color(0xFFFFFFFF)
            private val outlineDarkHighContrast = Color(0xFFE9F1F6)
            private val outlineVariantDarkHighContrast = Color(0xFFBCC4C8)
            private val scrimDarkHighContrast = Color(0xFF000000)
            private val inverseSurfaceDarkHighContrast = Color(0xFFDEE3E6)
            private val inverseOnSurfaceDarkHighContrast = Color(0xFF000000)
            private val inversePrimaryDarkHighContrast = Color(0xFF004F64)
            private val surfaceDimDarkHighContrast = Color(0xFF0F1417)
            private val surfaceBrightDarkHighContrast = Color(0xFF4C5154)
            private val surfaceContainerLowestDarkHighContrast = Color(0xFF000000)
            private val surfaceContainerLowDarkHighContrast = Color(0xFF1B2023)
            private val surfaceContainerDarkHighContrast = Color(0xFF2C3134)
            private val surfaceContainerHighDarkHighContrast = Color(0xFF373C3F)
            private val surfaceContainerHighestDarkHighContrast = Color(0xFF42484A)
            val colorScheme = darkColorScheme(
                primary = primaryDarkHighContrast,
                onPrimary = onPrimaryDarkHighContrast,
                primaryContainer = primaryContainerDarkHighContrast,
                onPrimaryContainer = onPrimaryContainerDarkHighContrast,
                inversePrimary = inversePrimaryDarkHighContrast,
                secondary = secondaryDarkHighContrast,
                onSecondary = onSecondaryDarkHighContrast,
                secondaryContainer = secondaryContainerDarkHighContrast,
                onSecondaryContainer = onSecondaryContainerDarkHighContrast,
                tertiary = tertiaryDarkHighContrast,
                onTertiary = onTertiaryDarkHighContrast,
                tertiaryContainer = tertiaryContainerDarkHighContrast,
                onTertiaryContainer = onTertiaryContainerDarkHighContrast,
                background = backgroundDarkHighContrast,
                onBackground = onBackgroundDarkHighContrast,
                surface = surfaceDarkHighContrast,
                onSurface = onSurfaceDarkHighContrast,
                surfaceVariant = surfaceVariantDarkHighContrast,
                onSurfaceVariant = onSurfaceVariantDarkHighContrast,
                surfaceTint = Color(scrimARGB),
                inverseSurface = inverseSurfaceDarkHighContrast,
                inverseOnSurface = inverseOnSurfaceDarkHighContrast,
                error = errorDarkHighContrast,
                onError = onErrorDarkHighContrast,
                errorContainer = errorContainerDarkHighContrast,
                onErrorContainer = onErrorContainerDarkHighContrast,
                outline = outlineDarkHighContrast,
                outlineVariant = outlineVariantDarkHighContrast,
                scrim = scrimDarkHighContrast,
                surfaceBright = surfaceBrightDarkHighContrast,
                surfaceContainer = surfaceContainerDarkHighContrast,
                surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
                surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
                surfaceContainerLow = surfaceContainerLowDarkHighContrast,
                surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
                surfaceDim = surfaceDimDarkHighContrast
            )
        }
    }
}