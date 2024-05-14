package metropolis.xtracted.controller.editor

import java.util.*
import metropolis.xtracted.controller.ControllerBase
import metropolis.xtracted.controller.undo.UndoAction
import metropolis.xtracted.controller.undo.UndoController
import metropolis.xtracted.model.Attribute
import metropolis.xtracted.model.AttributeId
import metropolis.xtracted.model.EditorState
import metropolis.xtracted.model.Translatable
import metropolis.xtracted.model.UndoState
import metropolis.xtracted.model.ValidationResult
import metropolis.xtracted.repository.CrudRepository
import metropolis.xtracted.repository.Identifiable



class EditorController<D: Identifiable>(val id              : Int,                        // der EditorController verwaltet die Daten mit der ID 'id'
                                        val repository      : CrudRepository<D>,
                                        val asData          : (List<Attribute<*>>) -> D,  // die Attribute müssen in eine Instanz der data class umgewandelt werden können. Das wird z.B. für das Speichern benötigt
                                        val asAttributeList : (D) -> List<Attribute<*>>,  // die Instanz der data class wird in eine Liste von Attributen gemappt. Damit werden die von der DB gelieferten Daten im Formular editierbar
                                        title               : Translatable,
                                        locale              : Locale,
                                        testMode: Boolean = false) :
        ControllerBase<EditorState<D>, EditorAction>(initialState = EditorState(title      = title,
                                                                                locale     = locale,
                                                                                attributes = asAttributeList(repository.read(id)!!)), // der Editor liest initial die Daten von der DB
                                                     testMode = testMode) {

    private val undoController = UndoController(debounceStart = state)  // jeder Editor unterstützt undo/redo

    val undoState : UndoState  // Zugriff auf den aktuellen UndoState des UndoControllers. Dadurch muss der UndoController nicht exponiert werden
        get() = undoController.state


    override fun executeAction(action: EditorAction): EditorState<D> =
        when (action) {
            is EditorAction.Update    -> update(action.attribute, action.value)

            is EditorAction.Reload    -> reload()
            is EditorAction.Save      -> save()

            is EditorAction.Undo      -> undo()
            is EditorAction.Redo      -> redo()

            is EditorAction.SetLocale -> setLocale(action.locale)
        }

    private fun update(attribute: Attribute<*>, valueAsText: String) : EditorState<D> {
        val nextEditorState = state.copy(attributes = state.attributes.replaceAll(updateAffectedAttributes(attribute, valueAsText)))

        undoController.triggerAction(UndoAction.PushOnUndoStack(newApplicationState = nextEditorState))

        return nextEditorState
    }

    private fun reload(): EditorState<D> {
        undoController.triggerAction(UndoAction.Reset)

        return state.copy(attributes = asAttributeList(repository.read(id)!!))
    }

    private fun save() : EditorState<D> {
        repository.update(asData(state.attributes))
        val updatedAttributes = buildList {
            for(attribute in state.attributes){
                add(attribute.copy(persistedValue = attribute.value))
            }

        }
        return state.copy(attributes =  updatedAttributes)
    }

    private fun undo() : EditorState<D>{
        undoController.triggerAction(UndoAction.Undo<EditorState<D>>(updateApplicationState =  { state = it }))
        return state
    }

    private fun redo() : EditorState<D>{
        undoController.triggerAction(UndoAction.Redo<EditorState<D>>(updateApplicationState =  { state = it }))
        return state
    }

    private fun setLocale(locale: Locale) : EditorState<D> {
        val nextEditorState = state.copy(locale = locale)
        undoController.triggerAction(UndoAction.PushOnUndoStack(newApplicationState = nextEditorState))

        return nextEditorState
    }

    // durch das Ändern eines Attribut-Werts können sich die Werte anderer Attribute ändern (der dependentAttributes).
    // Daher wird eine Liste der geänderten Attribute zurückgegeben.
    private fun<T : Any> updateAffectedAttributes(attribute: Attribute<T>, valueAsText: String): List<Attribute<*>> {
        if(attribute.readOnly){
            return listOf(attribute) // wenn das Attribut readonly ist, wird es nicht geändert
        }
        else {
            if(attribute.required && valueAsText.isBlank()){
                return listOf( attribute.copy (valueAsText      = valueAsText,
                                               validationResult = ValidationResult(false, ErrorMessage.REQUIRED)))
            }

            var validationResult = attribute.syntaxValidator(valueAsText)
            if(validationResult.valid){
                //syntaktisch korrekte Eingaben können auch konvertiert und auf semantische Korrektheit geprüft werden
                validationResult = attribute.semanticValidator(attribute.converter(valueAsText))
            }

            return when {
                validationResult.valid -> {
                    val newValue = attribute.converter(valueAsText)
                    buildList{
                    val updatedAttribute =  attribute.copy(value            = newValue,
                                                           valueAsText      = attribute.formatter(newValue, valueAsText),
                                                           validationResult = validationResult)
                        add(updatedAttribute)
                        attribute.dependentAttributes.forEach{ entry ->
                            val dependentAttribute = state.attributes.first { it.id == entry.key }
                            var updatedDependentAttribute = entry.value(updatedAttribute, dependentAttribute)
                            updatedDependentAttribute = if(updatedDependentAttribute.required && updatedDependentAttribute.value == null){
                                updatedDependentAttribute.copy(validationResult = ValidationResult(false, ErrorMessage.REQUIRED))
                            } else {
                                updatedDependentAttribute.copy(validationResult = updatedDependentAttribute.syntaxValidator(updatedDependentAttribute.valueAsText))
                            }

                            add(updatedDependentAttribute)
                        }
                    }

                }

                else                   -> { listOf(attribute.copy(valueAsText      = valueAsText,
                                                                  validationResult = validationResult)
                                                  )
                                           }
            }

        }
    }

    private fun List<Attribute<*>>.replaceAll(attributes: List<Attribute<*>>) : List<Attribute<*>> =
        toMutableList().apply {
            for (attribute in attributes) {
                val idx = indexOfFirst { attribute.id == it.id }

                removeAt(idx)
                add(idx, attribute) // das neue Attribut sollte wieder an der gleichen Stelle eingefügt werden
            }
        }

}

// damit kann mittels [attributeId] auf den Wert eines Attributs zugegriffen werden, z.B. val name = attributes[NAME]
operator fun<T> List<Attribute<*>>.get(attributeId: AttributeId) : T = first{it.id == attributeId}.value as T


private enum class ErrorMessage(override val german: String, override val english: String) : Translatable {
    REQUIRED("obligatorisches Feld", "required field")
}
