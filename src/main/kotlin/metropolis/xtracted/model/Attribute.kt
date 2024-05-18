package metropolis.xtracted.model

import java.text.DecimalFormatSymbols
import java.util.*

data class Attribute<T : Any>(val id                 : AttributeId,
                              val value              : T?,
                              val persistedValue     : Any?,
                              val valueAsText        : String,
                              val formatter          : (T?, String) -> String,
                              val converter          : (String) -> T?,
                              val unit               : String,
                              val required           : Boolean,
                              val readOnly           : Boolean,
                              val syntaxValidator    : (String) -> ValidationResult,
                              val semanticValidator  : (T?) -> ValidationResult,
                              val validationResult   : ValidationResult,
                              val span               : Int,
                              val dependentAttributes: Map<AttributeId, (Attribute<T>, Attribute<*>) -> Attribute<*>>
){
    val changed : Boolean
        get() = value != persistedValue

    val valid : Boolean
        get() = validationResult.valid
}

fun stringAttribute(id                 : AttributeId,
                    value              : String?,
                    formatter          : (String?, String) -> String                                         = { value, _ -> value ?: "" },
                    converter          : (String) -> String?                                                 = { it.ifBlank { null } },
                    unit               : String                                                              = "",
                    required           : Boolean                                                             = false,
                    readOnly           : Boolean                                                             = false,
                    syntaxValidator    : (String)  -> ValidationResult                                       = { ValidationResult(true, null) },
                    semanticValidator  : (String?) -> ValidationResult                                       = { ValidationResult(true, null) },
                    span               : Int                                                                 = 1,
                    dependentAttributes: Map<AttributeId, (Attribute<String>, Attribute<*>) -> Attribute<*>> = emptyMap())   =
    Attribute(id                  = id,
        value               = value,
        persistedValue      = value,
        valueAsText         = formatter(value, value.format(nullFormat = "")),
        formatter           = formatter,
        converter           = converter,
        unit                = unit,
        required            = required,
        readOnly            = readOnly,
        syntaxValidator     = syntaxValidator,
        semanticValidator   = semanticValidator,
        validationResult    = syntaxValidator(formatter(value, value.format(""))),
        span                = span,
        dependentAttributes = dependentAttributes
    )

fun doubleAttribute(id               : AttributeId,
                    value            : Double?,
                    validationRegex  : Regex                                                                 = floatCHRegex,
                    semanticValidator: (Double?) -> ValidationResult                                         = { ValidationResult(true, null) },
                    unit             : String                                                                = "",
                    required         : Boolean                                                               = false,
                    readOnly         : Boolean                                                               = false,
                    dependentAttributes: Map<AttributeId, (Attribute<Double>, Attribute<*>) -> Attribute<*>> = emptyMap())  =
    Attribute(id                  = id,
        value               = value,
        persistedValue      = value,
        valueAsText         = value.format(userInput = value.toString(), nullFormat = ""),
        formatter           = { value, userInput -> value.format(userInput = userInput, nullFormat = "") },
        converter           = { it.asDouble() },
        unit                = unit,
        required            = required,
        readOnly            = readOnly,
        syntaxValidator     = { it.matches(validationRegex).asValidationResult(ErrorMessage.NOT_A_DOUBLE) },
        semanticValidator   = semanticValidator,
        validationResult    = ValidationResult(true, null),
        span                = 1,
        dependentAttributes = dependentAttributes
    )

fun intAttribute(id               : AttributeId,
                 value            : Int?,
                 validationRegex  : Regex                                                                 = floatCHRegex,
                 semanticValidator: (Int?) -> ValidationResult                                         = { ValidationResult(true, null) },
                 unit             : String                                                                = "",
                 required         : Boolean                                                               = false,
                 readOnly         : Boolean                                                               = false,
                 dependentAttributes: Map<AttributeId, (Attribute<Int>, Attribute<*>) -> Attribute<*>> = emptyMap())  =
    Attribute(id                  = id,
        value               = value,
        persistedValue      = value,
        valueAsText         = value.format(userInput = value.toString(), nullFormat = ""),
        formatter           = { value, userInput -> value.format(userInput = userInput, nullFormat = "") },
        converter           = { it.asInt() },
        unit                = unit,
        required            = required,
        readOnly            = readOnly,
        syntaxValidator     = { it.matches(validationRegex).asValidationResult(ErrorMessage.NOT_A_DOUBLE) },
        semanticValidator   = semanticValidator,
        validationResult    = ValidationResult(true, null),
        span                = 1,
        dependentAttributes = dependentAttributes
    )


interface AttributeId : Translatable

data class ValidationResult(val valid: Boolean, val message: Translatable?)

val ch = Locale("de", "CH")
private val chGroupingSeparator = DecimalFormatSymbols(ch).groupingSeparator

val intCHRegex   = Regex(pattern = """^\s*[+-]?[\d$chGroupingSeparator]{1,11}\s*$""")
val floatCHRegex = Regex(pattern = """^\s*[+-]?[\d$chGroupingSeparator]{0,11}\.?\d*\s*$""")

val matchAllRegex  = Regex(pattern = """^.*$""")
val asciiTextRegex = Regex(pattern = """[\s\S]*""")
val zipCodeRegex   = Regex(pattern = """^[1-9](\d){3}$""")
val yearRegex      = Regex(pattern = """^(19|20)(\d){2}$""")
val dateRegex      = Regex(pattern = """^[0123]\d[.][[01]\d.](19|20)(\d){2}$""")

private fun String.asDouble() = trim().replace("${chGroupingSeparator}", "").toDoubleOrNull()
private fun String.asInt() = trim().replace("${chGroupingSeparator}", "").toIntOrNull()

fun Boolean.asValidationResult(errorMessage: Translatable) =
    if(this) ValidationResult(true, null) else ValidationResult(false, errorMessage)


private enum class ErrorMessage(override val german: String, override val english: String) : Translatable {
    NOT_A_DOUBLE("keine Kommazahl", "not a decimal")
}


val CH = Locale("de", "CH")

fun Double?.format(userInput: String, nullFormat: String = "?", locale: Locale = CH) : String =
    if (null == this) {
        nullFormat
    } else {
        val pattern = when {
            userInput.endsWith(".") -> "%,.0f."
            userInput.contains(".") -> "%,.${userInput.length - 1 - userInput.indexOf(".")}f"
            else                    -> "%,.0f"
        }
        pattern.format(locale, this)
    }

fun Number?.pp(pattern: String, nullFormat: String = ""): String {
    return if (null == this) nullFormat else pattern.format(CH, this)
}

fun String?.format(nullFormat : String = "") = this ?: nullFormat


fun Int?.format(userInput: String, nullFormat: String = "?", locale: Locale = CH) : String =
    if (null == this) {
        nullFormat
    } else {
        userInput;
    }
