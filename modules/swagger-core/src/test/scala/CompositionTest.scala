import models.composition._

import com.wordnik.swagger.util.Json
import com.wordnik.swagger.models._
import com.wordnik.swagger.converter._

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.Matchers

@RunWith(classOf[JUnitRunner])
class CompositionTest extends FlatSpec with Matchers {
  val m = Json.mapper()

  it should "read a model with required params and description" in {
    val schemas = ModelConverters.readAll(classOf[Human])
    Json.pretty(schemas) should equal (
"""{
  "Human" : {
    "required" : [ "name", "type" ],
    "properties" : {
      "name" : {
        "type" : "string",
        "position" : 1,
        "description" : "The name of the animal"
      },
      "type" : {
        "type" : "string",
        "position" : 2,
        "description" : "The animal type"
      },
      "firstName" : {
        "type" : "string"
      },
      "lastName" : {
        "type" : "string"
      }
    },
    "discriminator" : "type"
  },
  "Pet" : {
    "allOf" : [ {
      "$ref" : "Human"
    }, {
      "required" : [ "isDomestic" ],
      "properties" : {
        "isDomestic" : {
          "type" : "boolean",
          "position" : 3
        }
      }
    } ]
  }
}""")
  }

  it should "read a model with composition" in {
    val schemas = ModelConverters.readAll(classOf[Animal])
    Json.pretty(schemas) should equal (
"""{
  "Animal" : {
    "required" : [ "name", "type" ],
    "properties" : {
      "name" : {
        "type" : "string",
        "position" : 1,
        "description" : "The name of the animal"
      },
      "type" : {
        "type" : "string",
        "position" : 2,
        "description" : "The animal type"
      }
    },
    "discriminator" : "type"
  },
  "Human" : {
    "allOf" : [ {
      "$ref" : "Animal"
    }, {
      "properties" : {
        "firstName" : {
          "type" : "string"
        },
        "lastName" : {
          "type" : "string"
        }
      }
    } ]
  },
  "Pet" : {
    "allOf" : [ {
      "$ref" : "Human"
    }, {
      "required" : [ "isDomestic" ],
      "properties" : {
        "isDomestic" : {
          "type" : "boolean",
          "position" : 3
        }
      }
    } ]
  }
}""")
  }

}