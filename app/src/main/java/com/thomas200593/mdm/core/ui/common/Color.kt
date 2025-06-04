package com.thomas200593.mdm.core.ui.common

import android.graphics.Color.argb
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object Color {
    /**
     * For Light Theme:
     *   - Background (BG): The higher the contrast, the darker the color should be.
     *   - Foreground (FG): The higher the contrast, the lighter the color should be.
     * For Dark Theme:
     *   - Background (BG): The higher the contrast, the lighter the color should be.
     *   - Foreground (FG): The higher the contrast, the darker the color should be.
     */
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
    //V2
    object Light {
        val scrimARGB = argb(0xe6, 0xFF, 0xFF, 0xFF)
        object ContrastDefault {
            private val primaryLight = BootstrapV5Base.Blue600
            private val onPrimaryLight = BootstrapV5Base.White
            private val primaryContainerLight = BootstrapV5Base.Blue500
            private val onPrimaryContainerLight = BootstrapV5Base.White
            private val secondaryLight = BootstrapV5Base.Gray600
            private val onSecondaryLight = BootstrapV5Base.White
            private val secondaryContainerLight = BootstrapV5Base.Gray500
            private val onSecondaryContainerLight = BootstrapV5Base.Gray800
            private val tertiaryLight = BootstrapV5Base.Purple600
            private val onTertiaryLight = BootstrapV5Base.White
            private val tertiaryContainerLight = BootstrapV5Base.Purple500
            private val onTertiaryContainerLight = BootstrapV5Base.White
            private val errorLight = BootstrapV5Base.Red600
            private val onErrorLight = BootstrapV5Base.White
            private val errorContainerLight = BootstrapV5Base.Red200
            private val onErrorContainerLight = BootstrapV5Base.Red800
            private val backgroundLight = BootstrapV5Base.Gray100
            private val onBackgroundLight = BootstrapV5Base.Gray900
            private val surfaceLight = BootstrapV5Base.Gray100
            private val onSurfaceLight = BootstrapV5Base.Gray900
            private val surfaceVariantLight = BootstrapV5Base.Gray200
            private val onSurfaceVariantLight = BootstrapV5Base.Gray800
            private val outlineLight = BootstrapV5Base.Gray600
            private val outlineVariantLight = BootstrapV5Base.Gray500
            private val scrimLight = BootstrapV5Base.Black
            private val inverseSurfaceLight = BootstrapV5Base.Gray900
            private val inverseOnSurfaceLight = BootstrapV5Base.Gray100
            private val inversePrimaryLight = BootstrapV5Base.Blue100
            private val surfaceDimLight = BootstrapV5Base.Gray100
            private val surfaceBrightLight = BootstrapV5Base.Gray100
            private val surfaceContainerLowestLight = BootstrapV5Base.Gray300
            private val surfaceContainerLowLight = BootstrapV5Base.Gray300
            private val surfaceContainerLight = BootstrapV5Base.Gray300
            private val surfaceContainerHighLight = BootstrapV5Base.Gray300
            private val surfaceContainerHighestLight = BootstrapV5Base.Gray300
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
            private val primaryLightMediumContrast = BootstrapV5Base.Blue700
            private val onPrimaryLightMediumContrast = BootstrapV5Base.White
            private val primaryContainerLightMediumContrast = BootstrapV5Base.Blue500
            private val onPrimaryContainerLightMediumContrast = BootstrapV5Base.White
            private val secondaryLightMediumContrast = BootstrapV5Base.Gray700
            private val onSecondaryLightMediumContrast = BootstrapV5Base.White
            private val secondaryContainerLightMediumContrast = BootstrapV5Base.Gray500
            private val onSecondaryContainerLightMediumContrast = BootstrapV5Base.Gray800
            private val tertiaryLightMediumContrast = BootstrapV5Base.Purple600
            private val onTertiaryLightMediumContrast = BootstrapV5Base.White
            private val tertiaryContainerLightMediumContrast = BootstrapV5Base.Purple500
            private val onTertiaryContainerLightMediumContrast = BootstrapV5Base.White
            private val errorLightMediumContrast = BootstrapV5Base.Red700
            private val onErrorLightMediumContrast = BootstrapV5Base.White
            private val errorContainerLightMediumContrast = BootstrapV5Base.Red200
            private val onErrorContainerLightMediumContrast = BootstrapV5Base.Red800
            private val backgroundLightMediumContrast = BootstrapV5Base.Gray100
            private val onBackgroundLightMediumContrast = BootstrapV5Base.Gray900
            private val surfaceLightMediumContrast = BootstrapV5Base.Gray100
            private val onSurfaceLightMediumContrast = BootstrapV5Base.Gray900
            private val surfaceVariantLightMediumContrast = BootstrapV5Base.Gray200
            private val onSurfaceVariantLightMediumContrast = BootstrapV5Base.Gray800
            private val outlineLightMediumContrast = BootstrapV5Base.Gray600
            private val outlineVariantLightMediumContrast = BootstrapV5Base.Gray500
            private val scrimLightMediumContrast = BootstrapV5Base.Black
            private val inverseSurfaceLightMediumContrast = BootstrapV5Base.Gray900
            private val inverseOnSurfaceLightMediumContrast = BootstrapV5Base.Gray100
            private val inversePrimaryLightMediumContrast = BootstrapV5Base.Blue100
            private val surfaceDimLightMediumContrast = BootstrapV5Base.Gray100
            private val surfaceBrightLightMediumContrast = BootstrapV5Base.Gray100
            private val surfaceContainerLowestLightMediumContrast = BootstrapV5Base.Gray300
            private val surfaceContainerLowLightMediumContrast = BootstrapV5Base.Gray300
            private val surfaceContainerLightMediumContrast = BootstrapV5Base.Gray300
            private val surfaceContainerHighLightMediumContrast = BootstrapV5Base.Gray300
            private val surfaceContainerHighestLightMediumContrast = BootstrapV5Base.Gray300
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
            private val primaryLightHighContrast = BootstrapV5Base.Blue800
            private val onPrimaryLightHighContrast = BootstrapV5Base.White
            private val primaryContainerLightHighContrast = BootstrapV5Base.Blue500
            private val onPrimaryContainerLightHighContrast = BootstrapV5Base.White
            private val secondaryLightHighContrast = BootstrapV5Base.Gray800
            private val onSecondaryLightHighContrast = BootstrapV5Base.White
            private val secondaryContainerLightHighContrast = BootstrapV5Base.Gray500
            private val onSecondaryContainerLightHighContrast = BootstrapV5Base.Gray800
            private val tertiaryLightHighContrast = BootstrapV5Base.Purple800
            private val onTertiaryLightHighContrast = BootstrapV5Base.White
            private val tertiaryContainerLightHighContrast = BootstrapV5Base.Purple500
            private val onTertiaryContainerLightHighContrast = BootstrapV5Base.White
            private val errorLightHighContrast = BootstrapV5Base.Red800
            private val onErrorLightHighContrast = BootstrapV5Base.White
            private val errorContainerLightHighContrast = BootstrapV5Base.Red200
            private val onErrorContainerLightHighContrast = BootstrapV5Base.Red800
            private val backgroundLightHighContrast = BootstrapV5Base.Gray100
            private val onBackgroundLightHighContrast = BootstrapV5Base.Gray900
            private val surfaceLightHighContrast = BootstrapV5Base.Gray100
            private val onSurfaceLightHighContrast = BootstrapV5Base.Gray900
            private val surfaceVariantLightHighContrast = BootstrapV5Base.Gray200
            private val onSurfaceVariantLightHighContrast = BootstrapV5Base.Gray800
            private val outlineLightHighContrast = BootstrapV5Base.Gray600
            private val outlineVariantLightHighContrast = BootstrapV5Base.Gray500
            private val scrimLightHighContrast = BootstrapV5Base.Black
            private val inverseSurfaceLightHighContrast = BootstrapV5Base.Gray900
            private val inverseOnSurfaceLightHighContrast = BootstrapV5Base.Gray100
            private val inversePrimaryLightHighContrast = BootstrapV5Base.Blue100
            private val surfaceDimLightHighContrast = BootstrapV5Base.Gray100
            private val surfaceBrightLightHighContrast = BootstrapV5Base.Gray100
            private val surfaceContainerLowestLightHighContrast = BootstrapV5Base.Gray300
            private val surfaceContainerLowLightHighContrast = BootstrapV5Base.Gray300
            private val surfaceContainerLightHighContrast = BootstrapV5Base.Gray300
            private val surfaceContainerHighLightHighContrast = BootstrapV5Base.Gray300
            private val surfaceContainerHighestLightHighContrast = BootstrapV5Base.Gray300
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
            private val primaryDark = BootstrapV5Base.Blue400
            private val onPrimaryDark = BootstrapV5Base.White
            private val primaryContainerDark = BootstrapV5Base.Blue500
            private val onPrimaryContainerDark = BootstrapV5Base.White
            private val secondaryDark = BootstrapV5Base.Gray400
            private val onSecondaryDark = BootstrapV5Base.White
            private val secondaryContainerDark = BootstrapV5Base.Gray500
            private val onSecondaryContainerDark = BootstrapV5Base.Gray800
            private val tertiaryDark = BootstrapV5Base.Purple400
            private val onTertiaryDark = BootstrapV5Base.White
            private val tertiaryContainerDark = BootstrapV5Base.Purple500
            private val onTertiaryContainerDark = BootstrapV5Base.White
            private val errorDark = BootstrapV5Base.Red400
            private val onErrorDark = BootstrapV5Base.White
            private val errorContainerDark = BootstrapV5Base.Red200
            private val onErrorContainerDark = BootstrapV5Base.Red800
            private val backgroundDark = BootstrapV5Base.Gray900
            private val onBackgroundDark = BootstrapV5Base.Gray100
            private val surfaceDark = BootstrapV5Base.Black
            private val onSurfaceDark = BootstrapV5Base.Gray100
            private val surfaceVariantDark = BootstrapV5Base.Gray900
            private val onSurfaceVariantDark = BootstrapV5Base.Gray200
            private val outlineDark = BootstrapV5Base.Gray600
            private val outlineVariantDark = BootstrapV5Base.Gray500
            private val scrimDark = BootstrapV5Base.White
            private val inverseSurfaceDark = BootstrapV5Base.Gray100
            private val inverseOnSurfaceDark = BootstrapV5Base.Gray900
            private val inversePrimaryDark = BootstrapV5Base.Blue100
            private val surfaceDimDark = BootstrapV5Base.Black
            private val surfaceBrightDark = BootstrapV5Base.Black
            private val surfaceContainerLowestDark = BootstrapV5Base.Gray800
            private val surfaceContainerLowDark = BootstrapV5Base.Gray800
            private val surfaceContainerDark = BootstrapV5Base.Gray800
            private val surfaceContainerHighDark = BootstrapV5Base.Gray800
            private val surfaceContainerHighestDark = BootstrapV5Base.Gray800
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
            private val primaryDarkMediumContrast = BootstrapV5Base.Blue300
            private val onPrimaryDarkMediumContrast = BootstrapV5Base.Black
            private val primaryContainerDarkMediumContrast = BootstrapV5Base.Blue500
            private val onPrimaryContainerDarkMediumContrast = BootstrapV5Base.White
            private val secondaryDarkMediumContrast = BootstrapV5Base.Gray300
            private val onSecondaryDarkMediumContrast = BootstrapV5Base.Black
            private val secondaryContainerDarkMediumContrast = BootstrapV5Base.Gray500
            private val onSecondaryContainerDarkMediumContrast = BootstrapV5Base.Gray800
            private val tertiaryDarkMediumContrast = BootstrapV5Base.Purple300
            private val onTertiaryDarkMediumContrast = BootstrapV5Base.Black
            private val tertiaryContainerDarkMediumContrast = BootstrapV5Base.Purple500
            private val onTertiaryContainerDarkMediumContrast = BootstrapV5Base.White
            private val errorDarkMediumContrast = BootstrapV5Base.Red300
            private val onErrorDarkMediumContrast = BootstrapV5Base.Black
            private val errorContainerDarkMediumContrast = BootstrapV5Base.Red200
            private val onErrorContainerDarkMediumContrast = BootstrapV5Base.Red800
            private val backgroundDarkMediumContrast = BootstrapV5Base.Gray900
            private val onBackgroundDarkMediumContrast = BootstrapV5Base.Gray100
            private val surfaceDarkMediumContrast = BootstrapV5Base.Black
            private val onSurfaceDarkMediumContrast = BootstrapV5Base.Gray100
            private val surfaceVariantDarkMediumContrast = BootstrapV5Base.Gray900
            private val onSurfaceVariantDarkMediumContrast = BootstrapV5Base.Gray200
            private val outlineDarkMediumContrast = BootstrapV5Base.Gray600
            private val outlineVariantDarkMediumContrast = BootstrapV5Base.Gray500
            private val scrimDarkMediumContrast = BootstrapV5Base.White
            private val inverseSurfaceDarkMediumContrast = BootstrapV5Base.Gray100
            private val inverseOnSurfaceDarkMediumContrast = BootstrapV5Base.Gray900
            private val inversePrimaryDarkMediumContrast = BootstrapV5Base.Blue100
            private val surfaceDimDarkMediumContrast = BootstrapV5Base.Black
            private val surfaceBrightDarkMediumContrast = BootstrapV5Base.Black
            private val surfaceContainerLowestDarkMediumContrast = BootstrapV5Base.Gray800
            private val surfaceContainerLowDarkMediumContrast = BootstrapV5Base.Gray800
            private val surfaceContainerDarkMediumContrast = BootstrapV5Base.Gray800
            private val surfaceContainerHighDarkMediumContrast = BootstrapV5Base.Gray800
            private val surfaceContainerHighestDarkMediumContrast = BootstrapV5Base.Gray800
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
            private val primaryDarkHighContrast = BootstrapV5Base.Blue200
            private val onPrimaryDarkHighContrast = BootstrapV5Base.Black
            private val primaryContainerDarkHighContrast = BootstrapV5Base.Blue500
            private val onPrimaryContainerDarkHighContrast = BootstrapV5Base.White
            private val secondaryDarkHighContrast = BootstrapV5Base.Gray200
            private val onSecondaryDarkHighContrast = BootstrapV5Base.Black
            private val secondaryContainerDarkHighContrast = BootstrapV5Base.Gray500
            private val onSecondaryContainerDarkHighContrast = BootstrapV5Base.Gray800
            private val tertiaryDarkHighContrast = BootstrapV5Base.Purple200
            private val onTertiaryDarkHighContrast = BootstrapV5Base.Black
            private val tertiaryContainerDarkHighContrast = BootstrapV5Base.Purple500
            private val onTertiaryContainerDarkHighContrast = BootstrapV5Base.White
            private val errorDarkHighContrast = BootstrapV5Base.Red200
            private val onErrorDarkHighContrast = BootstrapV5Base.Black
            private val errorContainerDarkHighContrast = BootstrapV5Base.Red200
            private val onErrorContainerDarkHighContrast = BootstrapV5Base.Red800
            private val backgroundDarkHighContrast = BootstrapV5Base.Gray900
            private val onBackgroundDarkHighContrast = BootstrapV5Base.Gray100
            private val surfaceDarkHighContrast = BootstrapV5Base.Black
            private val onSurfaceDarkHighContrast = BootstrapV5Base.Gray100
            private val surfaceVariantDarkHighContrast = BootstrapV5Base.Gray900
            private val onSurfaceVariantDarkHighContrast = BootstrapV5Base.Gray200
            private val outlineDarkHighContrast = BootstrapV5Base.Gray600
            private val outlineVariantDarkHighContrast = BootstrapV5Base.Gray500
            private val scrimDarkHighContrast = BootstrapV5Base.White
            private val inverseSurfaceDarkHighContrast = BootstrapV5Base.Gray100
            private val inverseOnSurfaceDarkHighContrast = BootstrapV5Base.Gray900
            private val inversePrimaryDarkHighContrast = BootstrapV5Base.Blue100
            private val surfaceDimDarkHighContrast = BootstrapV5Base.Black
            private val surfaceBrightDarkHighContrast = BootstrapV5Base.Black
            private val surfaceContainerLowestDarkHighContrast = BootstrapV5Base.Gray800
            private val surfaceContainerLowDarkHighContrast = BootstrapV5Base.Gray800
            private val surfaceContainerDarkHighContrast = BootstrapV5Base.Gray800
            private val surfaceContainerHighDarkHighContrast = BootstrapV5Base.Gray800
            private val surfaceContainerHighestDarkHighContrast = BootstrapV5Base.Gray800
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