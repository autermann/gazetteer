# Gazetteer reference

The Gazetteer is based on [Solr][solr] 4.10.1 and can be used like any other Solr instance.

## Schema

### Internal fields

* `id`: The identifier of the feature.
  Example:

  ```json
  {
    "id": "745044"
  }
  ```

* `_version_`: The version of the document.
  Example:

  ```json
  {
    "_version_": 1500314799815262200
  }
  ```

* `featureType` (`PPL | ADM1 | ADM2`): The Gazetteer contains municipalities (populated places, `PPL`) as well as administrative units (`ADM1` and `ADM2`). To distinguish these this field can be used (e.g. in the `fq` query parameter).
  Example:

  ```json
  {
    "featureType": "PPL"
  }
  ```

### Name
* `name`: The primary name of the feature. The language of this name is not explicitly defined. It may the local language of the feature, or about any other language. This field is indexed and allows partial matches.
  Example:

  ```json
  {
    "name": "City of London"
  }
  ```

* `name_{lang}`: The name of the feature in the respective language. This fields contains a list of names as there may be more than one name in a specific language. For a list of languages see [Supported Languages](#supported-languages).
  Example:

  ```json
  {
    "name_fr": [
      "Cité de Londres"
    ]
  }
  ```

* `names`: A list of all names of the feature.  This field is indexed and allows partial matches. This field can not be retrieved, but is only for searching.
  Example:

  ```json
  {
    "names": [
      "Beirlín",
      "Berliin",
      "Berliini",
      "Berlijn",
      "Berlim",
      "Berlini",
      "Berlino",
      "Berlynas",
      "Berlín",
      "Berlín",
      "Berlīne",
      "Βερολίνο",
      "Берлин"
    ]
  }
  ```

### First-order administrative divisions

(e.g. regions in France, states in Germany)

* `admunit1_name`: The primary name of the administrative unit of this feature. The language of this name is not explicitly defined. It may the local language of the feature, or about any other language.
  Example:

  ```json
  {
    "admunit1_name": "İstanbul"
  }
  ```

* `admunit1_name_{lang}`: The name of the administrative unit in the respective language. This fields contains a list of names as there may be more than one name in a specific language. For a list of languages see [Supported Languages](#supported-languages).
  Example:

  ```json
  {
    "admunit1_name_de": [
      "Autonome Region Madrid"
    ]
  }
  ```

* `admunit1_names`: A list of all names of the administrative unit. This field can not be retrieved, but is only for searching.
  Example:

  ```json
  {
    "admunit1_names": [
      "Istanbul",
      "Istanbul",
      "İstanbul",
      "Provincia de Estambul",
      "Ισταμπούλ",
      "Истанбул"
    ]
  }
  ```

### Second-order administrative divisions

(e.g. departments in France, municipalities in Denmark)


* `admunit2_name`: The primary name of the administrative unit of this feature. The language of this name is not explicitly defined. It may the local language of the feature, or about any other language. This field is indexed and allows partial matches.
  Example:

  ```json
  {
    "admunit2_name": "Département des Alpes-de-Haute-Provence"
  }
  ```

* `admunit2_name_{lang}`: The name of the administrative unit in the respective language. This fields contains a list of names as there may be more than one name in a specific language. For a list of languages see [Supported Languages](#supported-languages).

  ```json
  {
    "admunit2_name_en": [
      "Province of Madrid"
    ]
  }
  ```

* `admunit2_names`: A list of all names of the administrative unit. This field is indexed and allows partial matches. This field can not be retrieved, but is only for searching.
  Example:

  ```json
  {
    "admunit2_names": [
      "Alpes da Alta Provença",
      "Alpes de Alta Provenza",
      "Alpes-de-Haute-Provence",
      "Alpi dell'Alta Provenza",
      "Département des Alpes-de-Haute-Provence"
    ]
  }
  ```

### Additional Information
* `geometry`: The coordinates of the feature.
  Example:

  ```json
  {
    "geometry": [ "48.13743 11.57549" ]
  }
  ```

* `country`: The [ISO 3166-1 alpha-2][iso-3166-1] country code of the feature.
  Example:

  ```json
  {
    "country": "DE"
  }
  ```

* `population`: The population of the municipality. This information is taken from [Geonames][geonames] and does not represent any official record. Nor it is known, for which year the value applies. Nevertheless the field can be used to improve the ranking of larger cities, if irrelevant municipalities of the same name exist.
  Example:

  ```json
  {
    "population": 11174257
  }
  ```

* `dbpedia`: A link to the [DBPedia][dbpedia] entry of the feature.
  Example:

  ```json
  {
    "dbpedia": "http://dbpedia.org/resource/London"
  }
  ```

### Additional Search Fields
The following fields contain the respective unprocessed names  to allow better scoring of exact matches:
* `name_exact`
* `names_exact`
* `admunit1_name_exact`
* `admunit1_names_exact`
* `admunit2_name_exact`
* `admunit2_names_exact`

Please note, that not all fields are present for all municipalities. I.e. the municipality's administrative unit, population or name in a specific language may not be known.

## Supported Languages

Languages are identified by their [ISO 639-1][iso-639-1] code:

| Language      | ISO 639-1 |
|---------------|-----------|
| Albanian      | `sq`      |
| Bosnian       | `bs`      |
| Bulgarian     | `bg`      |
| Croatian      | `hr`      |
| Czech         | `cs`      |
| Danish        | `da`      |
| Dutch         | `nl`      |
| English       | `en`      |
| Estonian      | `et`      |
| Finnish       | `fi`      |
| French        | `fr`      |
| German        | `de`      |
| Greek         | `el`      |
| Hungarian     | `hu`      |
| Icelandic     | `is`      |
| Irish         | `ga`      |
| Italian       | `it`      |
| Latvian       | `lv`      |
| Lithuanian    | `lt`      |
| Luxembourgish | `lb`      |
| Macedonian    | `mk`      |
| Maltese       | `mt`      |
| Norwegian     | `no`      |
| Polish        | `pl`      |
| Portuguese    | `pt`      |
| Romanian      | `ro`      |
| Romansh       | `rm`      |
| Serbian       | `sr`      |
| Slovak        | `sk`      |
| Slovene       | `sl`      |
| Spanish       | `es`      |
| Swedish       | `sv`      |
| Turkish       | `tr`      |

## Queries

The Gazetteer supports the default [Solr Query Syntax][solrQuerySyntax]. For extensive details of the API, please refer to the Solr documentation. There are two endpoints to query municipalities: `/select` and `/query`. Both support the same operations, but the latter sets sane defaults for most query parameters:

| Parameter | Default Value                                  |
|-----------|------------------------------------------------|
| `defType` | `dismax`                                       |
| `df`      | `name_exact,names_exact,name,names`            |
| `indent`  | `false`                                        |
| `qf`      | `name_exact^25 names_exact^20 name^10 names^1` |
| `fq`      | `featureType:PPL`                              |
| `fl`      | `*,score`                                      |
| `rows`    | `20`                                           |
| `wt`      | `json`                                         |

A query (parameter `q`) will search the the primary and language-specific names of all municipalities. Exact matches and matches of the primary name are scored higher than those of other names or partial matches.

Per default the endpoint returns all fields (i.e. the names in all languages) plus the score value. When using the service, the `fl` parameter should be set to reflect the user's  preferred language. E.g. for English it should be set to `name`, `name_en`, `country`, `admunit1_name`, `admunit1_name_en`, `admunit2_name`, `admunit2_name_en`, `geometry`.

This would be a complete query:
`/query?fl=name,name_en,country,admunit1_name,admunit1_name_en,admunit2_name,admunit2_name_en,geometry&q=M%C3%BCnchen`

```json
{
  "responseHeader": { "status": 0, "QTime": 2 },
  "response": {
    "numFound": 29,
    "start": 0,
    "docs": [
      {
        "name": "Munich",
        "name_en": [ "Munich" ],
        "country": "DE",
        "admunit1_name": "Bavaria",
        "admunit1_name_en": [ "Bavaria" ],
        "admunit2_name": "Upper Bavaria",
        "admunit2_name_en": [ "Upper Bavaria" ],
        "geometry": ["48.13743 11.57549"]
      },
      {
        "name": "Grafing bei München",
        "country": "DE",
        "admunit1_name": "Bavaria",
        "admunit1_name_en": [ "Bavaria" ],
        "admunit2_name": "Upper Bavaria",
        "admunit2_name_en": [ "Upper Bavaria" ],
        "geometry": ["48.04596 11.96797"]
      },
      {
        "name": "München",
        "country": "DE",
        "admunit1_name": "Bavaria",
        "admunit1_name_en": [ "Bavaria" ],
        "admunit2_name": "Lower Bavaria",
        "admunit2_name_en": [ "Lower Bavaria" ],
        "geometry":["48.6962905027349 13.4634167318815"]
      },
      {
        "name": "München",
        "country": "DE",
        "admunit1_name": "Brandenburg",
        "admunit1_name_en": [ "Brandenburg" ],
        "geometry": ["51.60698 13.31243"]
      },
      ...
    ]
  }
}
```

Additionally the scoring can be altered to boost large municipalities by adding population to the score. This can be done by setting the `bf` query parameter to something like `sqrt(population)`.

[solr]: <http://lucene.apache.org/solr/>
[solrQuerySyntax]: <https://wiki.apache.org/solr/SolrQuerySyntax>
[iso-639-1]: <http://www.iso.org/iso/catalogue_detail?csnumber=22109>
[iso-3166-1]: <http://www.iso.org/iso/catalogue_detail.htm?csnumber=63545>
[dbpedia]: <http://dbpedia.org/>
[wikipedia]: <http://wikipedia.org/>
[geonames]: <http://www.geonames.org/>
