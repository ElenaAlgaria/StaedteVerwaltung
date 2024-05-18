package metropolis.editor.controller

import metropolis.metropolis.data.Country
import metropolis.xtracted.controller.editor.EditorController
import metropolis.xtracted.controller.editor.get
import metropolis.xtracted.model.*
import metropolis.xtracted.repository.CRUDLazyRepository

fun editorController(id: Int, repository: CRUDLazyRepository<Country>): EditorController<Country> {
    return EditorController(
        id = id,
        title = Message.TITLE,
        locale = ch,
        repository = repository,
        // diese Attribute von Mountain werden in der DB abgespeichert, dazu muss eine Instanz von Mountain aus den Attributen erzeugt werden
        //TODO: die abzuspeichernden Werte muss mit der Attribut-Liste übereinstimmen
        asData = { attributes ->
            Country(
                id = id,
                name = attributes[Id.NAME],
                capital = attributes[Id.CAPITAL],
                population = attributes[Id.POPULATION],
                continent = attributes[Id.CONTINENT],
                currencyName = attributes[Id.CURRENCY_NAME],
                phone = attributes[Id.PHONE],
                languages = attributes[Id.LANGUAGES],
                isoAlpha2 = attributes[Id.ISO_ALPHA2],
                isoNumeric = attributes[Id.ISO_NUMERIC],
                areaSqm = attributes[Id.AREA_IN_SQKM],
                currencyCode = attributes[Id.CURRENCY_CODE],
                neighbours = attributes[Id.NEIGHBOURS],
                fipsCode = attributes[Id.FIPS_CODE]

            )
        },
        asAttributeList = { country ->
            listOf(
                (stringAttribute(
                    id = Id.NAME,
                    value = country.name,
                    required = true,
                 syntaxValidator = { (it.length <= 15).asValidationResult(Message.NAME_TOO_LONG) })),

                (stringAttribute(
                    id = Id.CAPITAL,
                    value = country.capital,
                    required = true,
                    syntaxValidator = { (it.length <= 15).asValidationResult(Message.NAME_TOO_LONG) })),

                (stringAttribute(
                    id = Id.CONTINENT,
                    value = country.continent,
                    required = true,
                    syntaxValidator = { (it.length <= 15).asValidationResult(Message.NAME_TOO_LONG) })),

                (intAttribute(
                    id = Id.POPULATION,
                    value = country.population,
                    semanticValidator = {
                        when {
                            it == null -> ValidationResult(true, null)
                            else -> ValidationResult(true, null)
                        }
                    })),

                (intAttribute(
                    id = Id.ISO_NUMERIC,
                    value = country.isoNumeric,
                    semanticValidator = {
                        when {
                            it == null -> ValidationResult(true, null)
                            else -> ValidationResult(true, null)
                        }
                    })),

                (stringAttribute(
                    id = Id.CURRENCY_NAME,
                    value = country.currencyName,
                    required = true,
                    syntaxValidator = { (it.length <= 20).asValidationResult(Message.NAME_TOO_LONG) })),

                (stringAttribute(
                    id = Id.CURRENCY_CODE,
                    value = country.currencyCode,
                    required = true,
                    syntaxValidator = { (it.length <= 20).asValidationResult(Message.NAME_TOO_LONG) })),


                (stringAttribute(
                    id = Id.PHONE,
                    value = country.phone,
                    required = true,
                    syntaxValidator = { (it.length <= 25).asValidationResult(Message.NAME_TOO_LONG) })),

                (stringAttribute(
                    id = Id.LANGUAGES,
                    value = country.languages,
                    required = true,
                    syntaxValidator = { (it.length <= 50).asValidationResult(Message.NAME_TOO_LONG) })),

                (stringAttribute(
                    id = Id.ISO_ALPHA2,
                    value = country.isoAlpha2,
                    required = true,
                    syntaxValidator = { (it.length <= 5).asValidationResult(Message.NAME_TOO_LONG) })),

                (doubleAttribute(id                = Id.AREA_IN_SQKM,
                    value             = country.areaSqm,
                    semanticValidator = { when {
                        it == null -> ValidationResult(true,  null)
                        else       -> ValidationResult(true,  null)
                    }
                    })),

                (stringAttribute(
                    id = Id.NEIGHBOURS,
                    value = country.neighbours,
                    required = true,
                    syntaxValidator = { (it.length <= 20).asValidationResult(Message.NAME_TOO_LONG) })),

                (stringAttribute(
                    id = Id.FIPS_CODE,
                    value = country.fipsCode,
                    required = true,
                    syntaxValidator = { (it.length <= 20).asValidationResult(Message.NAME_TOO_LONG) })),


                )
        })
}


enum class Id(override val german: String, override val english: String) : AttributeId {
    NAME("Name", "Name"),
    CAPITAL("Hauptstadt", "Capital"),
    POPULATION("Population", "Population"),
    CONTINENT("Kontinent", "Continent"),
    CURRENCY_NAME("Währung", "Currency"),
    PHONE("Telefon", "Phone"),
    LANGUAGES("Sprachen", "Languages"),
    ISO_ALPHA2("ISO_ALPHA2", "ISOALPHA2"),
    ISO_NUMERIC("ISO_Numerisch", "ISO_NUMERIC"),
    AREA_IN_SQKM("Areal in qKM", "Area in SQKM"),
    CURRENCY_CODE("Währungscode", "Currencycode"),
    NEIGHBOURS("Nachbarn", "Neighbours"),
    FIPS_CODE("Bundesstandard für Informationsverarbeitung", "FIPS Code")
}


private enum class Message(override val german: String, override val english: String) : Translatable {
    TITLE("Editor", "Editor"),
    NAME_TOO_LONG("Name zu lang", "name too long"),
}
