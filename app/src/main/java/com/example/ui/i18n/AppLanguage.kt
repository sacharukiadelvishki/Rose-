package com.example.ui.i18n

enum class AppLanguage(
    val code: String,
    val displayName: String,
    val nativeName: String,
    val flag: String
) {
    FRENCH("fr", "Français", "Français", "🇫🇷"),
    ENGLISH("en", "English", "English (US)", "🇺🇸"),
    JAPANESE("ja", "Japonais", "日本語", "🇯🇵"),
    KOREAN("ko", "Coréen", "한국어", "🇰🇷"),
    CHINESE("zh", "Chinois", "中文 (简体)", "🇨🇳");

    companion object {
        fun fromCode(code: String): AppLanguage {
            return entries.find { it.code.equals(code, ignoreCase = true) } ?: FRENCH
        }
    }
}
