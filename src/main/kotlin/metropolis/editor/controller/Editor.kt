package metropolis.editor.controller

import metropolis.metropolis.data.City
import metropolis.metropolis.data.Country
import metropolis.xtracted.controller.editor.EditorController
import metropolis.xtracted.controller.editor.get
import metropolis.xtracted.model.*
import metropolis.xtracted.repository.CRUDLazyRepository
import java.util.*

fun countryEditorController(id: Int, repository: CRUDLazyRepository<Country>, onDeleted: () -> Unit): EditorController<Country> {
    return EditorController(
        id = id,
        title = "Country Editor",
        locale = Locale.ENGLISH,
        repository = repository,
        onInit = Country(id = id, name = "", areaSqm = 0.0, continent = "", isoAlpha2 = "", neighbours = "", isoNumeric = 0, population = 0),
        onDeleted = onDeleted,
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
                 syntaxValidator = { (it.length <= 25).asValidationResult(Message.NAME_TOO_LONG) })),

                (stringAttribute(
                    id = Id.CAPITAL,
                    value = country.capital,
                    required = true,
                    syntaxValidator = { (it.length <= 25).asValidationResult(Message.NAME_TOO_LONG) })),

                (stringAttribute(
                    id = Id.CONTINENT,
                    value = country.continent,
                    required = true,
                    syntaxValidator = { (it.length <= 25).asValidationResult(Message.NAME_TOO_LONG) })),

                (intAttribute(
                    id = Id.POPULATION,
                    value = country.population,
                    semanticValidator = {
                        when{
                        it == null -> ValidationResult(false, Message.NO_VALUE)
                         else -> ValidationResult(true, null)
                        }
                    })),

                (intAttribute(
                    id = Id.ISO_NUMERIC,
                    value = country.isoNumeric,
                    semanticValidator = {
                        when {
                            it == null -> ValidationResult(false, Message.NO_VALUE)
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
                        it == null -> ValidationResult(false, Message.NO_VALUE)
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



fun cityEditorController(id: Int, repository: CRUDLazyRepository<City>, onDeleted: () -> Unit): EditorController<City> {
    return EditorController(
        id = id,
        title = "City Editor",
        locale = Locale.ENGLISH,
        repository = repository,
        onInit = City(id = id, admin1Code = "", countryCode = "", dem = 0, elevation = 0, longitude = 0.0, latitude = 0.0, name = "", population = 0, timezone = ""),
        onDeleted = onDeleted,
        asData = { attributes ->
            City(
                id = id,
                name = attributes[Id.CITY_NAME],
                latitude = attributes[Id.LATITUDE],
                longitude = attributes[Id.LONGITUDE],
                countryCode = attributes[Id.COUNTRY_CODE],
                admin1Code = attributes[Id.ADMIN_1_CODE],
                population = attributes[Id.CITY_POPULATION],
                elevation = attributes[Id.ELEVATION],
                dem = attributes[Id.DEM],
                timezone = attributes[Id.TIMEZONE]
            )
        },
        asAttributeList = { city ->
            listOf(
                (stringAttribute(
                    id = Id.NAME,
                    value = city.name,
                    required = true,
                    syntaxValidator = { (it.length <= 25).asValidationResult(Message.NAME_TOO_LONG) })),

                (stringAttribute(
                    id = Id.COUNTRY_CODE,
                    value = city.countryCode,
                    required = true,
                    syntaxValidator = { (it.length <= 25).asValidationResult(Message.NAME_TOO_LONG) })),

                (stringAttribute(
                    id = Id.ADMIN_1_CODE,
                    value = city.admin1Code,
                    required = true,
                    syntaxValidator = { (it.length <= 25).asValidationResult(Message.NAME_TOO_LONG) })),

                (intAttribute(
                    id = Id.CITY_POPULATION,
                    value = city.population,
                    semanticValidator = {
                        when {
                            it == null -> ValidationResult(false, Message.NO_VALUE)
                            else -> ValidationResult(true, null)
                        }
                    })),

                (intAttribute(
                    id = Id.DEM,
                    value = city.dem,
                    semanticValidator = {
                        when {
                            it == null -> ValidationResult(false, Message.NO_VALUE)
                            else -> ValidationResult(true, null)
                        }
                    })),

                (intAttribute(
                    id = Id.ELEVATION,
                    value = city.elevation,
                    semanticValidator = {
                        when {
                            it == null -> ValidationResult(false, Message.NO_VALUE)
                            else -> ValidationResult(true, null)
                        }
                    })),

                (stringAttribute(
                    id = Id.TIMEZONE,
                    value = city.timezone,
                    required = true,
                    syntaxValidator = { (it.length <= 20).asValidationResult(Message.NAME_TOO_LONG) })),


                (doubleAttribute(id                = Id.LATITUDE,
                    value             = city.latitude,
                    semanticValidator = { when {
                        it == null -> ValidationResult(false, Message.NO_VALUE)
                        else       -> ValidationResult(true,  null)
                    }
                    })),

                (doubleAttribute(id                = Id.LONGITUDE,
                    value             = city.longitude,
                    semanticValidator = { when {
                        it == null -> ValidationResult(false, Message.NO_VALUE)
                        else       -> ValidationResult(true,  null)
                    }
                    })),

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
    FIPS_CODE("Bundesstandard für Informationsverarbeitung", "FIPS Code"),
    CITY_NAME("Name", "Name"),
    LATITUDE("Breitengrad", "Latitude"),
    LONGITUDE("Längengrad", "Longitude"),
    COUNTRY_CODE("Land Code", "Country code"),
    ADMIN_1_CODE("Admin 1 Code", "Admin 1 code"),
    CITY_POPULATION("Population", "Population"),
    ELEVATION("Höhe", "Elevation"),
    DEM("Digitales Höhenmodell", "DEM"),
    TIMEZONE("Zeitzone", "DEM"),
}

private enum class Message(override val german: String, override val english: String) : Translatable {
    NAME_TOO_LONG("Name zu lang", "Name too long"),
    NO_VALUE("Trage einen Wert ein", "Add a Value")
}
