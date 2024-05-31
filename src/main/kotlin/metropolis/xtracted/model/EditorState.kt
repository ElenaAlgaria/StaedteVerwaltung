package metropolis.xtracted.model

import java.util.*

data class EditorState<T>(val title      : String,
                          val locale     : Locale             = CH,
                          val attributes : List<Attribute<*>> = emptyList()) {
    val changed : Boolean
        get() = attributes.any{ it.changed }

    val valid : Boolean
        get() = attributes.all{ it.valid }
}

operator fun<T: Any> EditorState<*>.get(id : AttributeId) : Attribute<T>  {
    println("get")
    return attributes.find{it.id == id} as Attribute<T>
}
